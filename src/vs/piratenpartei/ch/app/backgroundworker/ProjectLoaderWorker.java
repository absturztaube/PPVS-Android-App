package vs.piratenpartei.ch.app.backgroundworker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.redmine.IssueItemCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineParser;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProjectLoaderWorker extends AsyncTask<Void, Void, IssueItemCollection> {

	private static final String TAG = ".ProjectsLoaderTask";
	private Context _context;
	
	public ProjectLoaderWorker(Context pContext)
	{
		this._context = pContext;
	}

	@Override
	protected IssueItemCollection doInBackground(Void... pParams) 
	{
		Log.d(TAG, "doInBackground()");
		IssueItemCollection result = new IssueItemCollection();
		try 
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(this._context.getString(R.string.config_issues_xml) + "issues.xml?" + _xml_sort_attribute + _xml_tracker_id + _xml_status);
			HttpResponse response;
			response = client.execute(httpget);
			if(response.getStatusLine().getStatusCode() == 200)
			{
				HttpEntity entity = response.getEntity();
				if(entity != null)
				{
					InputStream in = entity.getContent();
					RedmineParser parser = new RedmineParser(in);
					result = parser.getIssuesList();
					in.close();
				}
			}
		} 
		catch (ClientProtocolException e) 
		{
			Log.e("[PPVS App]:ProjectsFragment -> ClientProtocolException", e.getMessage());
		}
		catch (IOException e) 
		{
			Log.e("[PPVS App]:ProjectsFragment -> IOException", e.getMessage());
		} catch (XmlPullParserException e) {
			Log.e("[PPVS App]:ProjectsFragment -> XmlPullParserException", e.getMessage());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(IssueItemCollection pResult)
	{
		Log.d(TAG, "onPostExecute()");
		ArrayList<String> titles = new ArrayList<String>();
		for(int i = 0; i < pResult.size(); i++)
		{
			titles.add("[" + pResult.get(i).getId() + "] " + pResult.get(i).getSubject());
		}
		try
		{
			ListView proj_list = (ListView)this._context.getActivity().findViewById(R.id.list_projects);
			proj_list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titles));
			getActivity().setProgressBarIndeterminateVisibility(false);
		}
		catch(NullPointerException e)
		{
			Log.w(TAG, "Activity doesnt exists anymore");
		}
	}
}

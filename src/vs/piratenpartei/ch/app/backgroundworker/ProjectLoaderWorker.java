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

import vs.piratenpartei.ch.app.redmine.IssueItemCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineLink;
import vs.piratenpartei.ch.parser.redmine.RedmineParser;
import android.os.AsyncTask;
import android.util.Log;

public class ProjectLoaderWorker extends AsyncTask<RedmineLink, Void, IssueItemCollection> {

	private static final String TAG = ".ProjectsLoaderTask";
	private IAsyncTaskAction<IssueItemCollection, ArrayList<String>> _onComplete;
	
	public ProjectLoaderWorker(IAsyncTaskAction<IssueItemCollection, ArrayList<String>> pOnComplete)
	{
		this._onComplete = pOnComplete;
	}

	@Override
	protected IssueItemCollection doInBackground(RedmineLink... pParams) 
	{
		Log.d(TAG, "doInBackground()");
		RedmineLink link = pParams[0];
		IssueItemCollection result = new IssueItemCollection();
		try 
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(link.getUrlString());
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
		return result;
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
			this._onComplete.onComplete(pResult, titles);
		}
		catch(NullPointerException e)
		{
			Log.w(TAG, "Activity doesnt exists anymore");
		}
	}
}

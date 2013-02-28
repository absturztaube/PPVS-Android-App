package vs.piratenpartei.ch.app.redmine;

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

import vs.piratenpartei.ch.app.ProjectActivity;
import vs.piratenpartei.ch.app.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ProjectsFragment extends Fragment 
{
	private ArrayList<IssueItem> _issues = new ArrayList<IssueItem>();
	private String _xml_sort_attribute = "sort=updated_on:desc&limit=100";
	private String _xml_tracker_id = "";
	private String _xml_status = "&status_id=open";
	
	@Override
	public void onResume()
	{
		super.onResume();
		new ProjectsLoaderTask().execute();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.projects_fragment, container, false);
	}
	
	@Override 
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		Spinner tracker_spinner = (Spinner)getActivity().findViewById(R.id.project_type);
		tracker_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				switch(arg2)
				{
				case 0:
					_xml_tracker_id = "";
					break;
				case 1:
					_xml_tracker_id = "&tracker_id=" + getString(R.string.config_tracker_information_id);
					break;
				case 2:
					_xml_tracker_id = "&tracker_id=" + getString(R.string.config_tracker_task_id);
					break;
				case 3:
					_xml_tracker_id = "&tracker_id=" + getString(R.string.config_tracker_motion_id);
					break;
				}
				new ProjectsLoaderTask().execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				_xml_tracker_id = "";
				new ProjectsLoaderTask().execute();
			}
		});
		tracker_spinner.setSelection(0);

		Spinner status_spinner = (Spinner)getActivity().findViewById(R.id.project_state);
		status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) 
			{
				switch(arg2)
				{
				case 0:
					_xml_status = "&status_id=*";
					break;
				case 1:
					_xml_status = "&status_id=open";
					break;
				case 2:
					_xml_status = "&status_id=closed";
					break;
				}
				new ProjectsLoaderTask().execute();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) 
			{
				_xml_status = "";
				new ProjectsLoaderTask().execute();
			}
		});
		status_spinner.setSelection(1);
		
		ListView list_projects = (ListView)getActivity().findViewById(R.id.list_projects);
		list_projects.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				IssueItem clicked = _issues.get(arg2);
				Intent intent = new Intent(getActivity(), ProjectActivity.class);
				intent.putExtra("issue_id", clicked.getId());
				intent.putExtra("issue_subject", clicked.getSubject());
				startActivity(intent);
			}
			
		});
	}
	
	private class ProjectsLoaderTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) 
		{
			try 
			{
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(getString(R.string.config_issues_xml) + "issues.xml?" + _xml_sort_attribute + _xml_tracker_id + _xml_status);
				HttpResponse response;
				response = client.execute(httpget);
				if(response.getStatusLine().getStatusCode() == 200)
				{
					HttpEntity entity = response.getEntity();
					if(entity != null)
					{
						InputStream in = entity.getContent();
						_issues = IssueItem.readRedmineXml(in);
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
		protected void onPostExecute(Void result)
		{
			ArrayList<String> titles = new ArrayList<String>();
			for(int i = 0; i < _issues.size(); i++)
			{
				titles.add("[" + _issues.get(i).getId() + "] " + _issues.get(i).getSubject());
			}
			try
			{
				ListView proj_list = (ListView)getActivity().findViewById(R.id.list_projects);
				proj_list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titles));
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
}

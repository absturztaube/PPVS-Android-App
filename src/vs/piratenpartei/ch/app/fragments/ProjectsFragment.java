package vs.piratenpartei.ch.app.fragments;

import java.io.IOException;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.backgroundworker.AsyncXmlParserTask;
import vs.piratenpartei.ch.app.backgroundworker.IAsyncTaskAction;
import vs.piratenpartei.ch.app.helpers.Intents;
import vs.piratenpartei.ch.app.redmine.IssueItem;
import vs.piratenpartei.ch.app.redmine.IssueItemCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineLink;
import vs.piratenpartei.ch.parser.redmine.RedmineLinkParameter;
import vs.piratenpartei.ch.parser.redmine.RedmineLinkParameterCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineLinkSortParameter;
import vs.piratenpartei.ch.parser.redmine.RedmineParser;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ProjectsFragment extends Fragment 
{
	private static final String TAG = "ProjectsFragment";
	
	private IssueItemCollection _issues = new IssueItemCollection();
	private RedmineLink _redmineLink;
	
	@Override
	public void onCreate(Bundle pSavedInstanceState)
	{
		super.onCreate(pSavedInstanceState);
		Log.d(TAG, "onCreate(Bundle)");
		this._redmineLink = new RedmineLink(this.getString(R.string.config_issues_xml), RedmineLink.SUB_PAGE_ISSUES, RedmineLink.DATA_TYPE_XML, new RedmineLinkParameterCollection());
		this._redmineLink.addParameter(new RedmineLinkSortParameter("updated_on", RedmineLinkSortParameter.SORT_DESC));
		this._redmineLink.addParameter(new RedmineLinkParameter("limit", "100"));
		this._redmineLink.addParameter(new RedmineLinkParameter("status_id", "open"));
		this.setHasOptionsMenu(true);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Log.d(TAG, "onResume()");
		getActivity().setProgressBarIndeterminateVisibility(true);
		getIssuesFromRedmine();
	}

	public void getIssuesFromRedmine() 
	{
		Log.d(TAG, "getIssuesFromRedmine()");
		try {
			new AsyncXmlParserTask<IssueItemCollection>(new RedmineParser(), new ProjectLoaderCompleteAction()).execute(_redmineLink.getUrlString());
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, Bundle pSavedInstanceState)
	{
		Log.d(TAG, "onCreateView(LayoutInflater, ViewGroup, Bundle)");
		return pInflater.inflate(R.layout.projects_fragment, pContainer, false);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu pMenu, MenuInflater pInflater)
	{
		Log.d(TAG, "onCreateOptionsMenu(Menu, MenuInflater)");
		pInflater.inflate(R.menu.projects_fragment_menu, pMenu);
		pMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() 
		{
			private static final String TAG_EXT = ".Menu[0]";
			
			@Override
			public boolean onMenuItemClick(MenuItem pItem) 
			{
				Log.d(TAG + TAG_EXT, "onMenuItemClick(MenuItem)");
				getActivity().setProgressBarIndeterminateVisibility(true);
				getIssuesFromRedmine();
				return true;
			}
		});
	}
	
	@Override 
	public void onActivityCreated(Bundle pSavedInstanceState)
	{
		super.onActivityCreated(pSavedInstanceState);
		Log.d(TAG, "onActivityCreated(Bundle)");
		Spinner tracker_spinner = (Spinner)getActivity().findViewById(R.id.project_type);
		tracker_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			private static final String TAG_EXT = ".tracker_spinner";
			
			@Override
			public void onItemSelected(AdapterView<?> pAdapterView, View pView,
					int pPosition, long pId) 
			{
				Log.d(TAG + TAG_EXT, "onItemSelected(AdapterView<?>, View, int, long)");
				switch(pPosition)
				{
				case 0:
					_redmineLink.removeParameter("tracker_id");
					break;
				case 1:
					_redmineLink.updateParameter("tracker_id", getString(R.string.config_tracker_information_id));
					break;
				case 2:
					_redmineLink.updateParameter("tracker_id", getString(R.string.config_tracker_task_id));
					break;
				case 3:
					_redmineLink.updateParameter("tracker_id", getString(R.string.config_tracker_motion_id));
					break;
				}
				getActivity().setProgressBarIndeterminateVisibility(true);
				getIssuesFromRedmine();
			}

			@Override
			public void onNothingSelected(AdapterView<?> pAdapterView) 
			{
				Log.d(TAG + TAG_EXT, "onNothingSelected(AdapterView<?>)");
				_redmineLink.removeParameter("tracker_id");
				getActivity().setProgressBarIndeterminateVisibility(true);
				getIssuesFromRedmine();
			}
		});
		tracker_spinner.setSelection(0);

		Spinner status_spinner = (Spinner)getActivity().findViewById(R.id.project_state);
		status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() 
		{
			private static final String TAG_EXT = ".status_spinner";
			@Override
			public void onItemSelected(AdapterView<?> pAdapterView, View pView,
					int pPosition, long pId) 
			{
				Log.d(TAG + TAG_EXT, "onItemSelected(AdapterView<?>, View, int, long)");
				switch(pPosition)
				{
				case 0:
					_redmineLink.updateParameter("status_id", "*");
					break;
				case 1:
					_redmineLink.updateParameter("status_id", "open");
					break;
				case 2:
					_redmineLink.updateParameter("status_id", "closed");
					break;
				}
				getActivity().setProgressBarIndeterminateVisibility(true);
				getIssuesFromRedmine();
			}

			@Override
			public void onNothingSelected(AdapterView<?> pAdapterView) 
			{
				Log.d(TAG + TAG_EXT, "onNothingSelected(AdapterView<?>)");
				_redmineLink.removeParameter("status_id");
				getActivity().setProgressBarIndeterminateVisibility(true);
				getIssuesFromRedmine();
			}
		});
		status_spinner.setSelection(1);
		
		ListView list_projects = (ListView)getActivity().findViewById(R.id.list_projects);
		list_projects.setOnItemClickListener(new AdapterView.OnItemClickListener() 
		{
			private static final String TAG_EXT = ".list_projects";
			@Override
			public void onItemClick(AdapterView<?> pAdapterView, View pView, int pPosition,
					long pId) {
				Log.d(TAG + TAG_EXT, "onItemClick(AdapterView<?>, View, int, long)");
				IssueItem clicked = _issues.get(pPosition);
				Intent intent = Intents.getIssueDetailIntent(getActivity(), clicked);
				startActivity(intent);
			}
			
		});
	}
	
	private class ProjectLoaderCompleteAction implements IAsyncTaskAction<IssueItemCollection>
	{
		private static final String TAG_EXT = ".ProjectLoaderCompleteAction";
		
		@Override
		public void onComplete(IssueItemCollection pResult) 
		{
			Log.d(TAG + TAG_EXT, "onComplete(IssueItemCollection)");
			_issues = pResult;
			String[] titles = new String[_issues.size()];
			int index = 0;
			for(IssueItem item : _issues)
			{
				titles[index] = "[" + item.getId() + "]" + item.getSubject();
				index++;
			}
			ListView proj_list = (ListView)getActivity().findViewById(R.id.list_projects);
			proj_list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titles));
			getActivity().setProgressBarIndeterminateVisibility(false);
		}
	}
}

package vs.piratenpartei.ch.app.activities;

import java.io.IOException;
import java.text.DateFormat;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.backgroundworker.AsyncXmlParserTask;
import vs.piratenpartei.ch.app.backgroundworker.IAsyncTaskAction;
import vs.piratenpartei.ch.app.configuration.AppConfiguration;
import vs.piratenpartei.ch.app.fragments.DummySectionFragment;
import vs.piratenpartei.ch.app.listadapters.JournalListAdapter;
import vs.piratenpartei.ch.app.redmine.IssueDetailItem;
import vs.piratenpartei.ch.app.redmine.JournalItemCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineLink;
import vs.piratenpartei.ch.parser.redmine.RedmineLinkParameter;
import vs.piratenpartei.ch.parser.redmine.RedmineLinkParameterCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
//import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProjectActivity extends FragmentActivity 
{
	private static final String TAG = "ProjectActivity";
	
	private SectionsPagerAdapter _sectionsPagerAdapter;
	private ViewPager _viewPager;
	private RedmineLink _issueLink;
	private IssueDetailItem _data = null;
	public JournalListAdapter _adapterJournal;

	@Override
	protected void onCreate(Bundle pSavedInstanceState) 
	{
		super.onCreate(pSavedInstanceState);
		
		Log.d(TAG, "onCreate(Bundle)");
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		setContentView(R.layout.activity_project);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);

		_sectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		_viewPager = (ViewPager) findViewById(R.id.pager);
		_viewPager.setAdapter(_sectionsPagerAdapter);

		_adapterJournal = new JournalListAdapter(this, R.layout.journal_list_item, new JournalItemCollection());
		
		Bundle params = getIntent().getExtras();
		RedmineLinkParameterCollection linkParameters = new RedmineLinkParameterCollection();
		linkParameters.add(new RedmineLinkParameter("include", "journals"));
		this._issueLink = new RedmineLink(
				AppConfiguration.getActiveConfig().getRedmineIssuesPage(), 
				params.getInt("issue_id") + "", 
				RedmineLink.DATA_TYPE_XML, 
				linkParameters
				);
		
		TextView id_text = (TextView)findViewById(R.id.project_detail_id);
		id_text.setText(this._issueLink.getSubPage() + "");
		
		TextView subject_text = (TextView)findViewById(R.id.project_detail_title);
		subject_text.setText(params.getString("issue_subject"));
		
		setProgressBarIndeterminateVisibility(true);
		if(_data == null)
		{
			getIssueDetail();
		}
	}
	
	private void getIssueDetail()
	{
		Log.d(TAG, "getIssueDetail()");
		try 
		{
			new AsyncXmlParserTask<IssueDetailItem>(new RedmineParser(), new OnCompleteAction()).execute(this._issueLink.getUrlString());
		} 
		catch (XmlPullParserException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu pMenu) 
	{
		Log.d(TAG, "onCreateOptionsMenu(Menu)");
		getMenuInflater().inflate(R.menu.activity_project, pMenu);
		pMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() 
		{
			@Override
			public boolean onMenuItemClick(MenuItem pItem) 
			{
				setProgressBarIndeterminateVisibility(true);
				getIssueDetail();
				return true;
			}
		});
		return true;
	}
	
	public void updateView(int pPosition) {
		Log.d(TAG, "updateView(int)");
		if(_data != null)
		{
			switch(pPosition)
			{
			case 0:
				TextView author = (TextView)findViewById(R.id.text_project_detail_author);
				author.setText(_data.getAuthor());
				
				TextView asignee = (TextView)findViewById(R.id.text_project_detail_assignee);
				asignee.setText(_data.getAssignedTo());
				
				TextView priority = (TextView)findViewById(R.id.text_project_detail_priority);
				priority.setText(_data.getPriority());
				
				TextView startDate = (TextView)findViewById(R.id.text_project_detail_startdate);
				if(_data.getStartDate() != null)
				{
					startDate.setText(DateFormat.getInstance().format(_data.getStartDate()));
				}
				
				TextView dueDate = (TextView)findViewById(R.id.text_project_detail_duedate);
				if(_data.getDueDate() != null)
				{
					dueDate.setText(DateFormat.getInstance().format(_data.getDueDate()));
				}
				
				TextView createdOn = (TextView)findViewById(R.id.text_project_detail_created);
				if(_data.getCreatedOn() != null)
				{
					createdOn.setText(DateFormat.getInstance().format(_data.getCreatedOn()));
				}
				
				TextView updatedOn = (TextView)findViewById(R.id.text_project_detail_updated);
				if(_data.getUpdatedOn() != null)
				{
					updatedOn.setText(DateFormat.getInstance().format(_data.getUpdatedOn()));
				}
				break;
			case 1:
				TextView description = (TextView)findViewById(R.id.text_project_desc);
				description.setText(_data.getDescription());
				break;
			case 2:
				ProgressBar progressbar = (ProgressBar)findViewById(R.id.progress_project_detail_progress);
				progressbar.setProgress(_data.getProgress());
				
				TextView progressText = (TextView)findViewById(R.id.text_project_detail_progress);
				progressText.setText(_data.getProgress() + "%");
				
				TextView status = (TextView)findViewById(R.id.text_project_detail_status);
				status.setText(_data.getStatus());
				
				TextView estHours = (TextView)findViewById(R.id.text_project_detail_esthours);
				estHours.setText(_data.getEstimatedHours());
				break;
			case 3:
				Log.i(TAG, "Update List Adapter");
				_adapterJournal.clear();
				_adapterJournal.addAll(_data.getJournal());
				_adapterJournal.notifyDataSetChanged();
				break;
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem pItem) {
		Log.d(TAG, "onOptionsItemSelected(MenuItem)");
		switch (pItem.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			//NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(pItem);
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		
		private static final String TAG_EXT = ".SectionsPagerAdapter";

		public SectionsPagerAdapter(FragmentManager pFragmentManager) {
			super(pFragmentManager);
			Log.d(TAG + TAG_EXT, "new SectionsPagerAdapter(FragmentManager)");
		}

		@Override
		public Fragment getItem(int pPosition) {
			Log.d(TAG + TAG_EXT, "getItem(int)");
			Fragment fragment;
			switch(pPosition)
			{
			case 0:
				fragment = new OverviewFragment();
				break;
			case 1:
				fragment = new DescriptionFragment();
				break;
			case 2:
				fragment = new StatusFragment();
				break;
			case 3:
				Log.i(TAG + TAG_EXT, "Create Fragment");
				fragment = new JournalFragment();
				break;
			default:
				fragment = new DummySectionFragment();
				Bundle args = new Bundle();
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, pPosition + 1);
				fragment.setArguments(args);
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			Log.d(TAG + TAG_EXT, "getCount()");
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int pPosition) 
		{
			Log.d(TAG + TAG_EXT, "getPageTitle(int)");
			switch (pPosition) {
			case 0:
				return getString(R.string.issue_overview);
			case 1:
				return getString(R.string.issue_description);
			case 2:
				return getString(R.string.issue_status);
			case 3:
				return getString(R.string.issue_journal);
			}
			return null;
		}
	}


	public static class OverviewFragment extends Fragment
	{
		private static final String TAG_EXT = ".OverviewFragment";
		
		@Override
		public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, 
				Bundle pSavedInstanceState)
		{
			Log.d(TAG + TAG_EXT, "onCreateView(LayoutInflater, ViewGroup, Bundle)");
			return pInflater.inflate(R.layout.project_overview_fragment, pContainer, false);
		}
		
		public void onResume()
		{
			super.onResume();
			Log.d(TAG + TAG_EXT, "onResume()");
			((ProjectActivity) getActivity()).updateView(0);
		}
	}
	
	public static class DescriptionFragment extends Fragment
	{
		private static final String TAG_EXT = ".DescriptionFragment";
		
		@Override
		public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, 
				Bundle pSavedInstanceState)
		{
			Log.d(TAG + TAG_EXT, "onCreateView(LayoutInflater, ViewGroup, Bundle)");
			return pInflater.inflate(R.layout.project_description_fragment, pContainer, false);
		}

		public void onResume()
		{
			super.onResume();
			Log.d(TAG + TAG_EXT, "onResume()");
			((ProjectActivity) getActivity()).updateView(1);
		}
	}
	
	public static class StatusFragment extends Fragment
	{
		private static final String TAG_EXT = ".StatusFragment";
		
		@Override
		public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, 
				Bundle pSavedInstanceState)
		{
			Log.d(TAG + TAG_EXT, "onCreateView(LayoutInflater, ViewGroup, Bundle)");
			return pInflater.inflate(R.layout.project_status_fragment, pContainer, false);
		}

		public void onResume()
		{
			super.onResume();
			Log.d(TAG + TAG_EXT, "onResume()");
			((ProjectActivity) getActivity()).updateView(2);
		}
	}
	
	public static class JournalFragment extends ListFragment
	{
		private static final String TAG_EXT = ".JournalFragment";

		public void onResume()
		{
			super.onResume();
			Log.d(TAG + TAG_EXT, "onResume()");
			ProjectActivity parent = ((ProjectActivity) getActivity());
			if(parent._data != null)
			{
				this.adaptListAdapter(parent._adapterJournal);
			}
			Log.i(TAG + TAG_EXT, "Fragment->Update()");
			parent.updateView(3);
		}
		
		public void adaptListAdapter(ListAdapter pListAdapter)
		{
			Log.i(TAG + TAG_EXT, "Adapt ListAdapter");
			this.setListAdapter(pListAdapter);
			this.setListShown(true);
		}
	}
	
	private class OnCompleteAction implements IAsyncTaskAction<IssueDetailItem>
	{
		private static final String TAG_EXT = ".OnCompleteAction";

		@Override
		public void onComplete(IssueDetailItem pResult) 
		{
			Log.d(TAG + TAG_EXT, "onComplete(IssueDetailItem)");
			_data = pResult;
			int index = _viewPager.getCurrentItem();
			Log.i(TAG + TAG_EXT, "Backgroundworker->Update()");
			updateView(index);
			updateView(index+1);
			updateView(index-1);
			setProgressBarIndeterminateVisibility(false);
		}
		
	}
}

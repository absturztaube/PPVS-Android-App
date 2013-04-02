package vs.piratenpartei.ch.app.activities;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.fragments.ContactFragment;
import vs.piratenpartei.ch.app.fragments.ForumFragment;
import vs.piratenpartei.ch.app.fragments.NewsFragment;
import vs.piratenpartei.ch.app.fragments.ProjectsFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class MainActivity extends FragmentActivity 
{
	private static final String TAG = "vs.piratenpartei.ch.app.FragmentActivity";
	
	private SectionsPagerAdapter _sectionPagerAdapter;
	private ViewPager _viewPager;

	@Override
	protected void onCreate(Bundle pSavedInstanceState) 
	{
		Log.d(TAG, "onCreate()");
		super.onCreate(pSavedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		_sectionPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		_viewPager = (ViewPager) findViewById(R.id.pager);
		_viewPager.setAdapter(_sectionPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu pMenu) 
	{
		Log.d(TAG, "onCreateOptionsMenu(" + pMenu.toString() + ")");
		getMenuInflater().inflate(R.menu.activity_main, pMenu);
		return true;
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private static final String TAG_EXT = ".SectionsPagerAdapter";
		
		public SectionsPagerAdapter(FragmentManager pFragmentManager) 
		{
			super(pFragmentManager);
			Log.d(TAG + TAG_EXT, "new SectionsPagerAdapter(" + pFragmentManager.toString() + ")");
		}

		@Override
		public Fragment getItem(int pPosition) {
			Log.d(TAG + TAG_EXT, "getItem(" + pPosition + ")");
			Fragment fragment;

			fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, pPosition + 1);
			fragment.setArguments(args);
			
			switch(pPosition)
			{
				case 0:
					fragment = new NewsFragment();
					break;
				case 1:
					fragment = new ForumFragment();
					break;
				case 2:
					fragment = new ProjectsFragment();
					break;
				case 3:
					fragment = new ContactFragment();
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
			Log.d(TAG + TAG_EXT, "getPageTitle(" + pPosition + ")");
			switch (pPosition) {
			case 0:
				return getString(R.string.title_news);
			case 1:
				return getString(R.string.title_forum);
			case 2:
				return getString(R.string.title_projects);
			case 3:
				return getString(R.string.title_contact);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		private static final String TAG_EXT = ".DummySectionFragment";

		public DummySectionFragment() {
			Log.d(TAG + TAG_EXT, "new DummySectionFragment()");
		}

		@Override
		public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer,
				Bundle pSavedInstanceState) 
		{
			Log.d(TAG + TAG_EXT, "onCreateView(" + pInflater.toString() + ", " + pContainer.toString() + ", " + pSavedInstanceState.toString() + ")");
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
	}

}

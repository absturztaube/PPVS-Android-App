package vs.piratenpartei.ch.app;

import vs.piratenpartei.ch.app.contact.ContactFragment;
import vs.piratenpartei.ch.app.forum.ForumFragment;
import vs.piratenpartei.ch.app.news.NewsFragment;
import vs.piratenpartei.ch.app.redmine.ProjectsFragment;
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
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		Log.d(TAG, "onCreateOptionsMenu(" + menu.toString() + ")");
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private static final String TAG_EXT = ".SectionsPagerAdapter";
		
		public SectionsPagerAdapter(FragmentManager fm) 
		{
			super(fm);
			Log.d(TAG + TAG_EXT, "new SectionsPagerAdapter(" + fm.toString() + ")");
		}

		@Override
		public Fragment getItem(int position) {
			Log.d(TAG + TAG_EXT, "getItem(" + position + ")");
			Fragment fragment;

			fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			
			switch(position)
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
		public CharSequence getPageTitle(int position) 
		{
			Log.d(TAG + TAG_EXT, "getPageTitle(" + position + ")");
			switch (position) {
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
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) 
		{
			Log.d(TAG + TAG_EXT, "onCreateView(" + inflater.toString() + ", " + container.toString() + ", " + savedInstanceState.toString() + ")");
			// Create a new TextView and set its text to the fragment's section
			// number argument value.
			TextView textView = new TextView(getActivity());
			textView.setGravity(Gravity.CENTER);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return textView;
		}
	}

}

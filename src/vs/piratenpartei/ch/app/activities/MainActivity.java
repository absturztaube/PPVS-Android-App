package vs.piratenpartei.ch.app.activities;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.fragments.ContactFragment;
import vs.piratenpartei.ch.app.fragments.DummySectionFragment;
import vs.piratenpartei.ch.app.fragments.ForumFragment;
import vs.piratenpartei.ch.app.fragments.NewsFragment;
import vs.piratenpartei.ch.app.fragments.ProjectsFragment;
import vs.piratenpartei.ch.app.helpers.Intents;
import vs.piratenpartei.ch.app.services.NotificationService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * The main activity contains a pager that displays for layout fragments:
 * {@link NewsFragment}, {@link ForumFragment}, {@link ProjectsFragment}, {@link ContactFragment} 
 * @author absturztaube
 * @see NewsFragment
 * @see ForumFragment
 * @see ProjectsFragment
 * @see ContactFragment
 */
public class MainActivity extends FragmentActivity 
{
	private static final String TAG = "MainActivity";
	
	private SectionsPagerAdapter _sectionPagerAdapter;
	private ViewPager _viewPager;

	/**
	 * Creates the activity
	 */
	@Override
	protected void onCreate(Bundle pSavedInstanceState) 
	{
		super.onCreate(pSavedInstanceState);
		Log.d(TAG, "onCreate(Bundle)");
		
		//Activate the progress thingy in the action bar
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		//Setting up the layout
		setContentView(R.layout.activity_main);
		_sectionPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		_viewPager = (ViewPager) findViewById(R.id.pager);
		_viewPager.setAdapter(_sectionPagerAdapter);
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		if(preferences.getBoolean(MainSettingsActivity.KEY_NOTIFY_ENABLE, true))
		{
			if(NotificationService.getInstance() == null)
			{
				Intent service = new Intent(this, NotificationService.class);
				startService(service);
			}
		}
	}

	/**
	 * Creates the options menu for this activity
	 * @params pMenu is the menu container to set up
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu pMenu) 
	{
		Log.d(TAG, "onCreateOptionsMenu(Menu)");
		getMenuInflater().inflate(R.menu.activity_main, pMenu);
		pMenu.findItem(R.id.main_settings).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) 
			{
				Intent intent = Intents.getMainSettingsIntent(MainActivity.this);
				startActivity(intent);
				return true;
			}
		});
		return true;
	}
	
	/**
	 * Simple view pager adapter, that handles the fragments displayed by the pager
	 * @author absturztaube
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		private static final String TAG_EXT = ".SectionsPagerAdapter";
		
		/**
		 * Creates a new pager adapter
		 * @param pFragmentManager see {@link FragmentPagerAdapter}
		 */
		public SectionsPagerAdapter(FragmentManager pFragmentManager) 
		{
			super(pFragmentManager);
			Log.d(TAG + TAG_EXT, "new SectionsPagerAdapter(FragmentManager)");
		}

		/**
		 * Gets an item from the adapter
		 */
		@Override
		public Fragment getItem(int pPosition) {
			Log.d(TAG + TAG_EXT, "getItem(int)");
			
			//Create an empty fragment
			//so the app doesn't crash if you add
			//new pages to the pager
			Fragment fragment;
			fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, pPosition + 1);
			fragment.setArguments(args);
			
			//Getting the proper fragment depending on its position
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

		/**
		 * Gets the number of fragments in this adapter
		 */
		@Override
		public int getCount() {
			Log.d(TAG + TAG_EXT, "getCount()");
			return 4;
		}

		/**
		 * Gets the page titles
		 */
		@Override
		public CharSequence getPageTitle(int pPosition) 
		{
			Log.d(TAG + TAG_EXT, "getPageTitle(int)");
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
}

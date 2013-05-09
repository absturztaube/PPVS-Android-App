package vs.piratenpartei.ch.app.activities;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.helpers.Intents;
import vs.piratenpartei.ch.app.services.NotificationService;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

/**
 * this activity presents the main settings for this application, where the user can specify
 * how the app will behave
 * @author absturztaube
 * TODO But layout into a fragment and instancing fragment here
 */
public class MainSettingsActivity extends PreferenceActivity 
{
	private static final String TAG = "MainSettingsActivity";
	
	private static final String url_calendar = "https://vs.piratenpartei.ch/?plugin=all-in-one-event-calendar&controller=ai1ec_exporter_controller&action=export_events&cb=1731167005&lang=de";
	private static final String url_github = "https://github.com/absturztaube/PPVS-Android-App";
	private static final String url_gnu_gpl = "http://www.gnu.org/licenses/gpl.txt";

	/**
	 * Key to Notification enable preference
	 */
	public static final String KEY_NOTIFY_ENABLE = "pref_key_notify_enable";
	
	/**
	 * Key to notification interval preference
	 */
	public static final String KEY_NOTIFY_INTERVAL = "pref_key_notify_interval";
	
	/**
	 * Key to notification news preference
	 */
	public static final String KEY_NOTIFY_NEWS = "pref_key_notify_news";
	
	/**
	 * Key to notification forum preference
	 */
	public static final String KEY_NOTIFY_FORUM = "pref_key_notify_forum";
	
	/**
	 * Key to notification redmine preference
	 */
	public static final String KEY_NOTIFY_REDMINE = "pref_key_notify_redmine";

	/**
	 * Creates the View. initial setup for layout and events
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle)");
		
		//Setting up layout
		this.addPreferencesFromResource(R.xml.main_preference);

		//Setting up Events
		this.findPreference("pref_key_data_export_cal").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() 
		{
			@Override
			public boolean onPreferenceClick(Preference preference) 
			{
				Intent intent = Intents.getViewUrlIntent(MainSettingsActivity.this, MainSettingsActivity.url_calendar);
				startActivity(Intent.createChooser(intent, getString(R.string.pref_data_export_cal)));
				return true;
			}
		});

		this.findPreference("pref_about_github").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) 
			{
				Intent intent = Intents.getViewUrlIntent(MainSettingsActivity.this, MainSettingsActivity.url_github);
				startActivity(Intent.createChooser(intent, getString(R.string.pref_about_github_summary)));
				return true;
			}
		});

		this.findPreference("pref_about_license").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference preference) 
			{
				Intent intent = Intents.getViewUrlIntent(MainSettingsActivity.this, MainSettingsActivity.url_gnu_gpl);
				startActivity(Intent.createChooser(intent, getString(R.string.pref_about_license_summary)));
				return true;
			}
		});

		this.findPreference(KEY_NOTIFY_ENABLE).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) 
			{
				boolean newVal = (Boolean)newValue;
				Intent service = new Intent(MainSettingsActivity.this, NotificationService.class);
				if(newVal)
				{
					startService(service);
				}
				else
				{
					stopService(service);
				}
				return true;
			}
		});
	}
}

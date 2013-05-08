package vs.piratenpartei.ch.app.activities;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.helpers.Intents;
import vs.piratenpartei.ch.app.services.NotificationService;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * 
 * @author absturztaube
 *
 */
public class MainSettingsActivity extends PreferenceActivity 
{
	private static final String url_calendar = "https://vs.piratenpartei.ch/?plugin=all-in-one-event-calendar&controller=ai1ec_exporter_controller&action=export_events&cb=1731167005&lang=de";
	private static final String url_github = "https://github.com/absturztaube/PPVS-Android-App";
	private static final String url_gnu_gpl = "http://www.gnu.org/licenses/gpl.txt";

	public static final String KEY_NOTIFY_ENABLE = "pref_key_notify_enable";
	public static final String KEY_NOTIFY_INTERVAL = "pref_key_notify_interval";
	public static final String KEY_NOTIFY_NEWS = "pref_key_notify_news";
	public static final String KEY_NOTIFY_FORUM = "pref_key_notify_forum";
	public static final String KEY_NOTIFY_REDMINE = "pref_key_notify_redmine";

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.addPreferencesFromResource(R.xml.main_preference);

		this.findPreference("pref_key_data_export_cal").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

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

package vs.piratenpartei.ch.app.configuration;

import java.util.HashMap;

import android.util.Log;

public class AppConfiguration 
{
	private static final String TAG = "AppConfiguration";
	
	public static final String CONFIG_SECTION_VS = "PPVS";

	private static HashMap<String, AppConfiguration> _configurations;
	private static String _activeConfig = CONFIG_SECTION_VS;

	public static AppConfiguration getConfig(String pConfigName)
	{
		Log.d(TAG, "getConfig(String)");
		if(AppConfiguration._configurations == null)
		{
			AppConfiguration.setupConfigs();
		}
		return AppConfiguration._configurations.get(pConfigName);
	}
	
	public static AppConfiguration getActiveConfig()
	{
		Log.d(TAG, "getActiveConfig()");
		if(AppConfiguration._configurations == null)
		{
			AppConfiguration.setupConfigs();
		}
		return AppConfiguration._configurations.get(AppConfiguration._activeConfig);
	}
	
	public static void setActiveConfig(String pConfigName)
	{
		Log.d(TAG, "setActiveConfig(String)");
		AppConfiguration._activeConfig = pConfigName;
	}
	
	public static String getActiveConfigName()
	{
		Log.d(TAG, "getActiveConfigName()");
		return AppConfiguration._activeConfig;
	}

	public static void setupConfigs()
	{
		Log.d(TAG, "setupConfigs()");
		
		AppConfiguration._configurations = new HashMap<String, AppConfiguration>();
		
		// Sektion Wallis
		AppConfiguration sectionVs = new AppConfiguration();
		sectionVs._rssFeed = "http://vs.piratenpartei.ch/feed/";
		sectionVs._website = "http://vs.piratenpartei.ch";
		sectionVs._email = "info@vs.piratenpartei.ch";
		sectionVs._facebookWebProfile = "https://www.facebook.com/PPValais";
		sectionVs._facebookAppProfile = "fb://profile/385164908189129";
		sectionVs._googlePlusProfile = "https://plus.google.com/112596815441394554834/posts";
		sectionVs._twitterProfile = "https://twitter.com/PPSVS";
		sectionVs._redmineProjectPage = "http://projects.piratenpartei.ch/projects/section-vs";
		sectionVs._redmineIssuesPage = "http://projects.piratenpartei.ch/issues";
		sectionVs._forumBoardId = 174;
		AppConfiguration._configurations.put(CONFIG_SECTION_VS, sectionVs);
	}

	private AppConfiguration()
	{	
	}

	private String _rssFeed;
	private String _website;
	private String _email;
	private String _facebookWebProfile;
	private String _facebookAppProfile;
	private String _googlePlusProfile;
	private String _twitterProfile;
	private String _redmineIssuesPage;
	private String _redmineProjectPage;
	private int _forumBoardId;
	
	public String getRssFeed()
	{
		Log.d(TAG, "getRssFeed()");
		return this._rssFeed;
	}

	public String getWebsite()
	{
		Log.d(TAG, "getWebsite()");
		return this._website;
	}

	public String getEmail()
	{
		Log.d(TAG, "getEmail()");
		return this._email;
	}

	public String getFaceBookWebProfile()
	{
		Log.d(TAG, "getFaceBookWebProfile()");
		return this._facebookWebProfile;
	}

	public String getFaceBookAppProfile()
	{
		Log.d(TAG, "getFaceBookAppProfile()");
		return this._facebookAppProfile;
	}

	public String getGooglePlusProfile()
	{
		Log.d(TAG, "getGooglePlusProfile()");
		return this._googlePlusProfile;
	}

	public String getTwitterProfile()
	{
		Log.d(TAG, "getTwitterProfile()");
		return this._twitterProfile;
	}

	public String getRedmineIssuesPage()
	{
		Log.d(TAG, "getRedmineIssuesPage()");
		return this._redmineIssuesPage;
	}

	public String getRedmineProjectPage()
	{
		Log.d(TAG, "getRedmineProjectPage()");
		return this._redmineProjectPage;
	}
	
	public int getForumBoardId()
	{
		Log.d(TAG, "getForumBoardId()");
		return this._forumBoardId;
	}
}

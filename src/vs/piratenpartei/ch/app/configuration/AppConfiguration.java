package vs.piratenpartei.ch.app.configuration;

import java.util.HashMap;

public class AppConfiguration 
{
	public static final String CONFIG_SECTION_VS = "PPVS";

	private static HashMap<String, AppConfiguration> _configurations;
	private static String _activeConfig = CONFIG_SECTION_VS;

	public static AppConfiguration getConfig(String pConfigName)
	{
		if(AppConfiguration._configurations == null)
		{
			AppConfiguration.setupConfigs();
		}
		return AppConfiguration._configurations.get(pConfigName);
	}
	
	public static AppConfiguration getActiveConfig()
	{
		if(AppConfiguration._configurations == null)
		{
			AppConfiguration.setupConfigs();
		}
		return AppConfiguration._configurations.get(AppConfiguration._activeConfig);
	}
	
	public static void setActiveConfig(String pConfigName)
	{
		AppConfiguration._activeConfig = pConfigName;
	}
	
	public static String getActiveConfigName()
	{
		return AppConfiguration._activeConfig;
	}

	public static void setupConfigs()
	{
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
		return this._rssFeed;
	}

	public String getWebsite()
	{
		return this._website;
	}

	public String getEmail()
	{
		return this._email;
	}

	public String getFaceBookWebProfile()
	{
		return this._facebookWebProfile;
	}

	public String getFaceBookAppProfile()
	{
		return this._facebookAppProfile;
	}

	public String getGooglePlusProfile()
	{
		return this._googlePlusProfile;
	}

	public String getTwitterProfile()
	{
		return this._twitterProfile;
	}

	public String getRedmineIssuesPage()
	{
		return this._redmineIssuesPage;
	}

	public String getRedmineProjectPage()
	{
		return this._redmineProjectPage;
	}
	
	public int getForumBoardId()
	{
		return this._forumBoardId;
	}
}

package vs.piratenpartei.ch.app.forum;

import java.util.Date;

public class ForumParser 
{
	public static class Thread
	{
		public static final String REGEX_FULL_THREAD_TITLE = "<span id=\"msg_[0-9]+\"><a href=\"(.+)\">(.*)</a></span>";
		public static final String REGEX_THREAD_STARTER = "<a href=\"(.*action=profile.*)\" title=\".*\">(.*)</a>";
		public static final String REGEX_THREAD_POSTS = "([0-9]+) Antworten";
		public static final String REGEX_THREAD_VIEWS = "([0-9]+) Aufrufe";
		
		private String _title;
		private String _beginner;
		private int _posts;
		private int _views;
		private Date _lastUpdate;
		private String _lastUpdater;
		
		public String getTitle()
		{
			return this._title;
		}
		
		public String getThreadStarter()
		{
			return this._beginner;
		}
		
		public int getPostCount()
		{
			return this._posts;
		}
		
		public int getViewCount()
		{
			return this._views;
		}
		
		public Date getLastUpdateDate()
		{
			return this._lastUpdate;
		}
		
		public String getLastUpdateAuthor()
		{
			return this._lastUpdater;
		}
		
		public static void parseHtml(String pHtml, int pPostIndex)
		{
			
		}
	}
}

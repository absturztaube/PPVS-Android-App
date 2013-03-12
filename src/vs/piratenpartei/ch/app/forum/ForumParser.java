package vs.piratenpartei.ch.app.forum;

public class ForumParser 
{
	public static class Thread
	{
		public static final String REGEX_THREAD_TITLE = "<span id=\"msg_[0-9]+\"><a href=\"(.+)\">(.*)</a></span>";
		public static final String REGEX_THREAD_STARTER = "<a href=\"(.*action=profile.*)\" title=\".*\">(.*)</a>";
		public static final String REGEX_THREAD_POSTS = "([0-9]+) Antworten";
		public static final String REGEX_THREAD_VIEWS = "([0-9]+) Aufrufe";
		
		private String _title;
		private String _beginner;
		private int _posts;
		private int _views;
		
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
		
		public void setTitle(String pTitle)
		{
			this._title = pTitle;
		}
		
		public void setThreadStarter(String pStarter)
		{
			this._beginner = pStarter;
		}
		
		public void setPostCount(int pPost)
		{
			this._posts = pPost;
		}
		
		public void setViewCount(int pViews)
		{
			this._views = pViews;
		}

		public static boolean DetectPost(String pPartHtml)
		{
			return false;
		}

		public static Thread parseHtml(String pPartHtml)
		{
			Thread result = new Thread();
			//Do Parsing here
			return result;
		}
	}
}

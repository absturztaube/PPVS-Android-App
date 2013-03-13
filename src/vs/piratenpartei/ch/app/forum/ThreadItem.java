package vs.piratenpartei.ch.app.forum;

import java.util.Date;

public class ThreadItem 
{
	private String _title;
	private String _author;
	private Date _created;
	private int _views;
	private int _posts;
	private Date _lastUpdate;
	private String _lastUpdated;
	
	public String getTitle()
	{
		return this._title;
	}
	
	public String getStarter()
	{
		return this._author;
	}
	
	public Date getCreationDate()
	{
		return this._created;
	}
	
	public int getViews()
	{
		return this._views;
	}
	
	public int getPosts()
	{
		return this._posts;
	}
	
	public Date getLastUpdateDate()
	{
		return this._lastUpdate;
	}
	
	public String getLastUpdateAuthor()
	{
		return this._lastUpdated;
	}
}

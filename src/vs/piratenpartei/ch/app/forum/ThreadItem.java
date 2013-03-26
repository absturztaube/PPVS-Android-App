package vs.piratenpartei.ch.app.forum;

public class ThreadItem 
{
	private String _title;
	private String _author;
	private String _link;
	private String _lastUpdate;
	private String _lastUpdated;
	private int _lastPossibleOffset;
	public static int LastBoardOffset = Integer.MAX_VALUE;
	
	public String getTitle()
	{
		return this._title;
	}
	
	public void setTitle(String pTitle)
	{
		this._title = pTitle;
	}
	
	public String getStarter()
	{
		return this._author;
	}
	
	public void setStarter(String pStarter)
	{
		this._author = pStarter;
	}
	
	public String getTopicLink()
	{
		return this._link;
	}
	
	public void setTopicLink(String pLink)
	{
		this._link = pLink;
	}
	
	public String getLastUpdateDate()
	{
		return this._lastUpdate;
	}
	
	public void setLastUpdateDate(String pLastUpdateDate)
	{
		this._lastUpdate = pLastUpdateDate;
	}
	
	public String getLastUpdateAuthor()
	{
		return this._lastUpdated;
	}
	
	public void setLastUpdateAuthor(String pLastUpdateAuthor)
	{
		this._lastUpdated = pLastUpdateAuthor;
	}
	
	public int getLastPossibleOffset()
	{
		return this._lastPossibleOffset;
	}
	
	public void setLastPossibleOffset(int pLastPossibleOffset)
	{
		this._lastPossibleOffset = pLastPossibleOffset;
	}
}

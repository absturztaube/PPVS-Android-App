package vs.piratenpartei.ch.app.redmine;

import java.util.Date;

public class IssueItem 
{
	private int _id;
	private String _subject;
	private Date _lastUpdate;
	
	public int getId()
	{
		return this._id;
	}
	
	public void setId(int pId)
	{
		this._id = pId;
	}
	
	public String getSubject()
	{
		return this._subject;
	}
	
	public void setSubject(String pSubject)
	{
		this._subject = pSubject;
	}
	
	public Date getLastUpdate()
	{
		return this._lastUpdate;
	}
	
	public void setLastUpdate(Date pUpdate)
	{
		this._lastUpdate = pUpdate;
	}
}

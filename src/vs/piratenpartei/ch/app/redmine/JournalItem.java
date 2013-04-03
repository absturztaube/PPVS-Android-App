package vs.piratenpartei.ch.app.redmine;

import java.util.Date;

public class JournalItem
{
	private String _author;
	private String _notes;
	private Date _createdOn;
	
	public String getAuthor()
	{
		return this._author;
	}
	
	public void setAuthor(String pAuthor)
	{
		this._author = pAuthor;
	}
	
	public String getNotes()
	{
		return this._notes;
	}
	
	public void setNotes(String pNotes)
	{
		this._notes = pNotes;
	}
	
	public Date getCreatedOn()
	{
		return this._createdOn;
	}
	
	public void setCreatedOn(Date pCreatedOn)
	{
		this._createdOn = pCreatedOn;
	}
}

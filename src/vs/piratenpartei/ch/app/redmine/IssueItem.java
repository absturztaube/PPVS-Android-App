package vs.piratenpartei.ch.app.redmine;

public class IssueItem 
{
	private int _id;
	private String _subject;
	
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
}

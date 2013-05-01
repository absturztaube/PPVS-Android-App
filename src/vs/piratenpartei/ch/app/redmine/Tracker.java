package vs.piratenpartei.ch.app.redmine;

public class Tracker 
{
	private int _id;
	private String _name;
	
	public Tracker(int pId, String pName)
	{
		this._id = pId;
		this._name = pName;
	}
	
	public int getId()
	{
		return this._id;
	}
	
	public String getName()
	{
		return this._name;
	}
}

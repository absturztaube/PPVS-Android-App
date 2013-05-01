package vs.piratenpartei.ch.parser.redmine;

public class RedmineLinkParameter 
{
	private String _name;
	protected String _value;
	
	public RedmineLinkParameter(String pName, String pValue)
	{
		this._name = pName;
		this._value = pValue;
		this.loadValue();
	}
	
	public String getName()
	{
		return this._name;
	}
	
	public void setName(String pName)
	{
		this._name = pName;
	}
	
	public String getValue()
	{
		this.createValue();
		return this._value;
	}
	
	public void setValue(String pValue)
	{
		this._value = pValue;
		this.loadValue();
	}
	
	public String getParameterString()
	{
		this.createValue();
		return this._name + "=" + this._value;
	}
	
	protected void createValue()
	{
	}
	
	protected void loadValue()
	{
	}
}

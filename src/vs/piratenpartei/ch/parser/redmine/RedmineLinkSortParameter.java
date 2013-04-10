package vs.piratenpartei.ch.parser.redmine;

public class RedmineLinkSortParameter extends RedmineLinkParameter
{
	public static final String SORT_ASC = "asc";
	public static final String SORT_DESC = "desc";
	
	private String _field;
	private String _direction = RedmineLinkSortParameter.SORT_DESC;
	
	public RedmineLinkSortParameter(String pField, String pDirection)
	{
		super("sort", pField + ":" + pDirection);
	}
	
	public String getField()
	{
		return this._field;
	}
	
	public void setField(String pField)
	{
		this._field = pField;
	}
	
	public String getDirection()
	{
		return this._direction;
	}
	
	public void setDirection(String pDirection)
	{
		this._direction = pDirection;
	}
	
	@Override
	protected void createValue()
	{
		this._value = this._field + ":" + this._direction;
	}
	
	@Override
	protected void loadValue()
	{
		String[] parts = this._value.split(":");
		if(parts.length >= 2)
		{
			this._field = parts[0];
			this._direction = parts[1];
		}
		else
		{
			throw new IndexOutOfBoundsException("Value does contain corrupt data (Value: '" + this._value + "')");
		}
	}
}

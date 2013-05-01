package vs.piratenpartei.ch.parser.redmine;

import java.util.ArrayList;

public class RedmineLinkParameterCollection extends
		ArrayList<RedmineLinkParameter> 
{
	private static final long serialVersionUID = -4498769468345739860L;

	public RedmineLinkParameter get(String pParameterName)
	{
		for(RedmineLinkParameter parameter : this)
		{
			if(parameter.getName().equals(pParameterName))
			{
				return parameter;
			}
		}
		return null;
	}
	
	public void remove(String pParameterName)
	{
		RedmineLinkParameter itemToRemove = this.get(pParameterName);
		if(itemToRemove != null)
		{
			this.remove(this.get(pParameterName));
		}
	}
	
	public void update(String pParameterName, String pValue)
	{
		this.remove(pParameterName);
		this.add(new RedmineLinkParameter(pParameterName, pValue));
	}
}

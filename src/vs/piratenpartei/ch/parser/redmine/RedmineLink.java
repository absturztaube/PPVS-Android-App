package vs.piratenpartei.ch.parser.redmine;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.StringUtil;

public class RedmineLink 
{
	public static final String SUB_PAGE_ISSUES  = "issues";
	public static final String DATA_TYPE_JSON = "json";
	public static final String DATA_TYPE_XML = "xml";
	
	private String _baseUrl;
	private String _subPage;
	private String _dataType;
	private List<RedmineLinkParameter> _parameters;
	
	public String getUrlString()
	{
		return this._baseUrl + "/" + this._subPage + "." + this._dataType + "?" + this.getParameterString();
	}
	
	public String getParameterString()
	{
		List<String> parameters = new ArrayList<String>();
		for(RedmineLinkParameter param : this._parameters)
		{
			parameters.add(param.getParameterString());
		}
		return StringUtil.join(parameters, "&");
	}
}

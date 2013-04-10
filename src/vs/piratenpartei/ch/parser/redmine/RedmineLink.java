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
	private RedmineLinkParameterCollection _parameters;
	
	public RedmineLink(String pBaseUrl, String pSubPage, String pDataType, RedmineLinkParameterCollection pParameters)
	{
		this._baseUrl = pBaseUrl;
		this._subPage = pSubPage;
		this._dataType = pDataType;
		this._parameters = pParameters;
	}
	
	public String getHost()
	{
		return this._baseUrl;
	}
	
	public void setHost(String pBaseUrl)
	{
		this._baseUrl = pBaseUrl;
	}
	
	public String getSubPage()
	{
		return this._subPage;
	}
	
	public void setSubPage(String pSubPage)
	{
		this._subPage = pSubPage;
	}
	
	public String getDataType()
	{
		return this._dataType;
	}
	
	public void setDataType(String pDataType)
	{
		this._dataType = pDataType;
	}
	
	public void addParameter(RedmineLinkParameter pParameter)
	{
		this._parameters.add(pParameter);
	}
	
	public void removeParameter(String pParameterName)
	{
		this._parameters.remove(pParameterName);
	}
	
	public void updateParameter(String pParameterName, String pValue)
	{
		this._parameters.update(pParameterName, pValue);
	}
	
	public void removeParameter(RedmineLinkParameter pParameter)
	{
		this._parameters.remove(pParameter);
	}
	
	public RedmineLinkParameterCollection getParameterCollection()
	{
		return this._parameters;
	}
	
	public void setParameterCollection(RedmineLinkParameterCollection pParameters)
	{
		this._parameters = pParameters;
	}
	
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

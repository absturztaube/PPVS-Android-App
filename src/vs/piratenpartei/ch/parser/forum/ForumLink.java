package vs.piratenpartei.ch.parser.forum;

public class ForumLink 
{
	public static final String TYPE_BOARD = "board";
	public static final String TYPE_TOPIC = "topic";
	
	private String _baseUrl = "http://forum.piratenpartei.ch/index.php/";
	private String _type = "board";
	private int _id = 174;
	private int _offset = 0;
	
	public ForumLink()
	{
		this("http://forum.piratenpartei.ch/index.php/");
	}
	
	public ForumLink(String pBaseUrl)
	{
		this(pBaseUrl, ForumLink.TYPE_BOARD);
	}
	
	public ForumLink(String pBaseUrl, String pType)
	{
		this(pBaseUrl, pType, 174, 0);
	}
	
	public ForumLink(String pBaseUrl, String pType, int pId)
	{
		this(pBaseUrl, pType, pId, 0);
	}
	
	public ForumLink(String pBaseUrl, String pType, int pId, int pOffset)
	{
		this._baseUrl = pBaseUrl;
		this._type = pType;
		this._id = pId;
		this._offset = pOffset;
	}
	
	public String getBaseUrl()
	{
		return this._baseUrl;
	}
	
	public void setBaseUrl(String pBaseUrl)
	{
		this._baseUrl = pBaseUrl;
	}
	
	public String getType()
	{
		return this._type;
	}
	
	public void setType(String v)
	{
		this._type = v;
	}
	
	public int getId()
	{
		return this._id;
	}
	
	public void setId(int v)
	{
		this._id = v;
	}
	
	public int getOffset()
	{
		return this._offset;
	}
	
	public void setOffset(int v)
	{
		this._offset = v;
	}
	
	public String getUrlString()
	{
		return this._baseUrl + this._type + "," + this._id + "." + this._offset + ".html";
	}
	
	public static ForumLink parse(String pString)
	{
		ForumLink result = new ForumLink();
		String[] urlParts = pString.split("\\/");
		String importantPart = urlParts[urlParts.length - 1];
		String[] typeParams = importantPart.split(",");
		result.setType(typeParams[0]);
		String params = typeParams[typeParams.length - 1];
		String[] paramsParted = params.split("\\.");
		int id = Integer.parseInt(paramsParted[0]);
		result.setId(id);
		int offset = Integer.parseInt(paramsParted[1]);
		result.setOffset(offset);
		return result;
	}
}

package vs.piratenpartei.ch.app.news;

import java.util.ArrayList;
import java.util.Date;

public class NewsItem 
{	
	private String _title;
	private String _link;
	private String _comments;
	private Date _publishDate;
	private String _creator;
	private ArrayList<String> _categories = new ArrayList<String>();
	private String _description;
	private String _content;

	public String getTitle()
	{
		return this._title;
	}
	
	public void setTitle(String pTitle)
	{
		this._title = pTitle;
	}
	
	public String getLink()
	{
		return this._link;
	}
	
	public void setLink(String pLink)
	{
		this._link = pLink;
	}
	
	public String getComments()
	{
		return this._comments;
	}
	
	public void setComments(String pComments)
	{
		this._comments = pComments;
	}
	
	public Date getPublishDate()
	{
		return this._publishDate;
	}
	
	public void setPublishDate(Date pPublishDate)
	{
		this._publishDate = pPublishDate;
	}
	
	public String getCreator()
	{
		return this._creator;
	}
	
	public void setCreator(String pCreator)
	{
		this._creator = pCreator;
	}
	
	public ArrayList<String> getCategories()
	{
		return this._categories;
	}
	
	public void setCategories(ArrayList<String> pCategories)
	{
		this._categories = pCategories;
	}
	
	public void addCategory(String pCategory)
	{
		this._categories.add(pCategory);
	}
	
	public String getDescription()
	{
		return this._description;
	}
	
	public void setDescription(String pDescription)
	{
		this._description = pDescription;
	}
	
	public String getContent()
	{
		return this._content;
	}
	
	public void setContent(String pContent)
	{
		this._content = pContent;
	}
}

package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ForumParser 
{
	private URL _forumUrl;
	private Document _dom;
	private boolean _docLoaded = false;
	
	private String _selectorSubject = "div.topic_table td.subject span a";
	private String _selectorStarter = "div.topic_table td.subject p>a";
	private String _selectorViews = "div.topic_table td.stats";
	private String _selectorPosts = "div.topic_table td.stats";
	private String _selectorLastUpdateDate = "div.topic_table td.lastpost";
	private String _selectorLastUpdateAuthor = "div.topic_table td.lastpost a:last-child";
	private String _selectorCompletePost = "#forumposts div.post_wrapper";
	private String _selectorPostAuthor = "div.poster h4>a";
	private String _selectorPostAuthorAvatar = "div.poster img.avatar";
	private String _selectorPostContent = "div.postarea div.post div.inner";
	private String _selectorPostDate = "div.postarea div.keyinfo div.smalltext";
	
	public ForumParser(URL pForumUrl)
	{
		this._forumUrl = pForumUrl;
	}
	
	public String getSelectorPostAuthor()
	{
		return this._selectorPostAuthor;
	}
	
	public String getSelectorPostAvatar()
	{
		return this._selectorPostAuthorAvatar;
	}
	
	public String getSelectorPostContent()
	{
		return this._selectorPostContent;
	}
	
	public String getSelectorPostDate()
	{
		return this._selectorPostDate;
	}
	
	public void setSelectorPostAuthor(String pSelector)
	{
		this._selectorPostAuthor = pSelector;
	}
	
	public void setSelectorPostAuthorAvatar(String pSelector)
	{
		this._selectorPostAuthorAvatar = pSelector;
	}
	
	public void setSelectorPostContent(String pSelector)
	{
		this._selectorPostContent = pSelector;
	}
	
	public void setSelectorPostDate(String pSelector)
	{
		this._selectorPostDate = pSelector;
	}
	
	public void setSelectorSubject(String pSelector)
	{
		this._selectorSubject = pSelector;
	}
	
	public void setSelectorStarter(String pSelector)
	{
		this._selectorStarter = pSelector;
	}
	
	public void setSelectorViews(String pSelector)
	{
		this._selectorViews = pSelector;
	}
	
	public void setSelectorPosts(String pSelector)
	{
		this._selectorPosts = pSelector;
	}
	
	public void setSelectorLastUpdateDate(String pSelector)
	{
		this._selectorLastUpdateDate = pSelector;
	}
	
	public void setSelectorLastUpdateAuthor(String pSelector)
	{
		this._selectorLastUpdateAuthor = pSelector;
	}
	
	public void setSelectorPostTotal(String pSelector)
	{
		this._selectorCompletePost = pSelector;
	}
	
	public boolean isDocumentLoaded()
	{
		return this._docLoaded;
	}
	
	public void parseDocument() throws IOException
	{
		this._dom = Jsoup.parse(_forumUrl, 10000);
		this._docLoaded = true;
	}	

	public Elements getSubjects()
	{
		return this.getElementsBySelector(this._selectorSubject);
	}
	
	public Elements getStarters()
	{
		return this.getElementsBySelector(this._selectorStarter);
	}
	
	public Elements getViews()
	{
		return this.getElementsBySelector(this._selectorViews);
	}
	
	public Elements getPosts()
	{
		return this.getElementsBySelector(this._selectorPosts);
	}
	
	public Elements getLastUpdateDates()
	{
		return this.getElementsBySelector(this._selectorLastUpdateDate);
	}
	
	public Elements getLastUpdateAuthors()
	{
		return this.getElementsBySelector(this._selectorLastUpdateAuthor);
	}
	
	public Elements getPostAuthors()
	{
		return this.getElementsBySelector(this._selectorPostAuthor);
	}
	
	public Elements getPostAvatars()
	{
		return this.getElementsBySelector(this._selectorPostAuthorAvatar);
	}
	
	public Elements getPostContents()
	{
		return this.getElementsBySelector(this._selectorPostContent);
	}
	
	public Elements getPostDates()
	{
		return this.getElementsBySelector(this._selectorPostDate);
	}
	
	public Elements getCompletePost()
	{
		return this.getElementsBySelector(this._selectorCompletePost);
	}
	
	public Elements getElementsBySelector(String pSelector)
	{
		if(!this._docLoaded)
		{
			throw new IllegalStateException("Document is not loaded yet");
		}
		return this._dom.select(pSelector);		
	}
}

package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ForumParser 
{
	private URL _forumUrl;
	private Document _dom;
	private boolean _docLoaded = false;
	
	private String _selectorSubject = "div.topic_table td.subject span a";
	private String _selectorStarter = "div.topic_table td.subject p a";
	private String _selectorViews = "div.topic_table td.stats";
	private String _selectorPosts = "div.topic_table td.stats";
	private String _selectorLastUpdateDate = "div.topic_table td.lastpost";
	private String _selectorLastUpdateAuthor = "div.topic_table td.lastpost a:last-child";
	
	public ForumParser(URL pForumUrl)
	{
		this._forumUrl = pForumUrl;
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
	
	public Elements getElementsBySelector(String pSelector)
	{
		if(!this._docLoaded)
		{
			throw new IllegalStateException("Document is not loaded yet");
		}
		return this._dom.select(pSelector);		
	}
}

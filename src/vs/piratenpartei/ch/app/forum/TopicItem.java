package vs.piratenpartei.ch.app.forum;

import android.graphics.drawable.Drawable;
import android.text.Spanned;

public class TopicItem 
{	
	private String _author;
	private Drawable _avatar;
	private Spanned _content;
	private String _date;
	
	public String getAuthor()
	{
		return this._author;
	}
	
	public void setAuthor(String pAuthor)
	{
		this._author = pAuthor;
	}
	
	public Drawable getAvatar()
	{
		return this._avatar;
	}
	
	public void setAvatar(Drawable pDrawable)
	{
		this._avatar = pDrawable;
	}
	
	public Spanned getContent()
	{
		return this._content;
	}
	
	public void setContent(Spanned pContent)
	{
		this._content = pContent;
	}
	
	public String getDate()
	{
		return this._date;
	}
	
	public void setDate(String pDate)
	{
		this._date = pDate;
	}
}

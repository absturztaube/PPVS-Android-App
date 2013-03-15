package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;

import android.text.Html;
import android.util.Log;

public class TopicItem 
{
	private String _author;
	private String _avatarLink;
	private String _content;
	private String _date;
	
	public String getAuthor()
	{
		return this._author;
	}
	
	public String getAvatarLink()
	{
		return this._avatarLink;
	}
	
	public String getContent()
	{
		return this._content;
	}
	
	public String getDate()
	{
		return this._date;
	}
	
	public static List<TopicItem> loadTopic(String pTopicUrl) throws IOException
	{
		List<TopicItem> result = new ArrayList<TopicItem>();
		ForumParser parser = new ForumParser(new URL(pTopicUrl));
		parser.parseDocument();
		Elements authors = parser.getPostAuthors();
		Elements avatars = parser.getPostAvatars();
		Elements contents = parser.getPostContents();
		Elements dates = parser.getPostDates();
		for(int index = 0; index < authors.size(); index++)
		{
			Log.d("PPVS App", index + ". Item");
			TopicItem current = new TopicItem();
			Log.d("PPVS App", " setting author");
			current._author = authors.get(index).text();
			/*
			Log.d("PPVS App", " setting avatar");
			current._avatarLink = avatars.get(index).attr("src");
			//*/
			Log.d("PPVS App", " setting content");
			current._content = Html.fromHtml(contents.get(index).html()).toString();
			Log.d("PPVS App", " setting date");
			current._date = Html.fromHtml(dates.get(index).html()).toString();
			Log.d("PPVS App", "Topic Object: " + current.toString());
			result.add(current);
		}
		return result;
	}
}

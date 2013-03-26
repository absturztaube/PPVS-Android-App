package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.XMLReader;

import vs.piratenpartei.ch.app.helpers.ForumParser;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

public class TopicItem 
{
	private static final String TAG = "vs.piratenpartei.ch.app.forum.TopicItem";
	
	private String _author;
	private Drawable _avatar;
	private Spanned _content;
	private String _date;
	
	public String getAuthor()
	{
		return this._author;
	}
	
	public Drawable getAvatar()
	{
		return this._avatar;
	}
	
	public Spanned getContent()
	{
		return this._content;
	}
	
	public String getDate()
	{
		return this._date;
	}
	
	public static List<TopicItem> loadTopic(String pTopicUrl) throws IOException
	{
		Log.d(TAG, "loadTopic(" + pTopicUrl + ")");
		List<TopicItem> result = new ArrayList<TopicItem>();
		ForumParser parser = new ForumParser(new URL(pTopicUrl));
		parser.parseDocument();
		Elements posts = parser.getCompletePost();
		for(int index = 0; index < posts.size(); index++)
		{
			Element post = posts.get(index);
			TopicItem current = new TopicItem();
			Elements authors = post.select(parser.getSelectorPostAuthor());
			if(authors.size() > 0)
			{
				Element author = authors.get(0);
				current._author = author.text();
			}
			Elements avatars = post.select(parser.getSelectorPostAvatar());
			if(avatars.size() > 0)
			{
				Element avatar = avatars.get(0);
				String avatarUrl = avatar.attr("src");
				URL avatarRef = new URL(avatarUrl);
				Drawable avatarDrawable = Drawable.createFromStream(avatarRef.openStream(), current._author);
				current._avatar = avatarDrawable;
			}
			Elements contents = post.select(parser.getSelectorPostContent());
			if(contents.size() > 0)
			{
				Element content = contents.get(0);
				current._content = Html.fromHtml(content.html(), new Html.ImageGetter() {
					
					@Override
					public Drawable getDrawable(String source) {
						Log.i(TAG, "Getting Image from: " + source);
						URL src;
						try {
							src = new URL(source);
							Drawable image = Drawable.createFromStream(src.openStream(), source);
							return image;
						} catch (IOException e) {
							Log.w(TAG, e.getMessage());
							e.printStackTrace();
						}
						return null;
					}
				}, new Html.TagHandler() {
					
					@Override
					public void handleTag(boolean arg0, String arg1, Editable arg2,
							XMLReader arg3) {
						//do nothing
					}
				});
			}
			Elements dates = post.select(parser.getSelectorPostDate());
			if(dates.size() > 0)
			{
				Element date = dates.get(0);
				current._date = Html.fromHtml(date.html()).toString();
			}
			result.add(current);
		}
		return result;
	}
}

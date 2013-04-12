package vs.piratenpartei.ch.parser.rss;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import vs.piratenpartei.ch.app.news.NewsItem;
import vs.piratenpartei.ch.app.news.NewsItemCollection;
import vs.piratenpartei.ch.parser.AbstractXmlParser;

public class RssParser extends AbstractXmlParser 
{
	private static final String TAG = "vs.piratenpartei.ch.app.helpers.RssParser";

	public RssParser() throws XmlPullParserException, IOException
	{
		super();
	}
	
	public NewsItemCollection getFeed() throws XmlPullParserException, IOException, ParseException
	{
		Log.d(TAG, "getFeed()");

		NewsItemCollection result = new NewsItemCollection();
		this._parser.nextTag();
		this._parser.require(XmlPullParser.START_TAG, null, "channel");
		while(this._parser.next() != XmlPullParser.END_TAG)
		{
			if(this._parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = this._parser.getName();
			if(name.equals("item"))
			{
				result.add(this.getFeedItem());
			}
			else
			{
				this.skip();
			}
		}

		return result;
	}

	public NewsItem getFeedItem() throws XmlPullParserException, IOException, ParseException
	{
		NewsItem result = new NewsItem();
		this._parser.require(XmlPullParser.START_TAG, null, "item");
		while(this._parser.next() != XmlPullParser.END_TAG)
		{
			if(this._parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = this._parser.getName();
			if(name.equals("title"))
			{
				result.setTitle(this.getText(name));
			}
			else if(name.equals("link"))
			{
				result.setLink(this.getText(name));
			}
			else if(name.equals("comments"))
			{
				result.setComments(this.getText(name));
			}
			else if(name.equals("pubDate"))
			{
				DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z", Locale.ENGLISH);
				result.setPublishDate(df.parse(this.getText(name)));
			}
			else if(name.equals("dc:creator"))
			{
				result.setCreator(this.getText(name));
			}
			else if(name.equals("category"))
			{
				result.addCategory(this.getText(name));
			}
			else if(name.equals("description"))
			{
				result.setDescription(this.getText(name));
			}
			else if(name.equals("content:encoded"))
			{
				result.setContent(this.getText(name));
			}
			else
			{
				this.skip();
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <Result> Result parse() throws XmlPullParserException, IOException,
			ParseException {
		return (Result)this.getFeed();
	}
}

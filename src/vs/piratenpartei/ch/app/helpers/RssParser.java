package vs.piratenpartei.ch.app.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.news.NewsItem;
import vs.piratenpartei.ch.app.news.NewsItemCollection;
import android.util.Log;
import android.util.Xml;

public class RssParser 
{
	private static final String TAG = "vs.piratenpartei.ch.app.helpers.RssParser";
	
	public static NewsItemCollection readFeed(InputStream pIn) throws XmlPullParserException, IOException, ParseException
	{
		Log.d(TAG, "readFeed(" + pIn.toString() + ")");
		
		NewsItemCollection result = new NewsItemCollection();

		try 
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(pIn, null);
			parser.nextTag();
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, null, "channel");
			while(parser.next() != XmlPullParser.END_TAG)
			{
				if(parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}
				String name = parser.getName();
				Log.i("[PPVS App]NewsItem.readFeed()", name);
				if(name.equals("item"))
				{
					result.add(readItem(parser));
				}
				else
				{
					skip(parser);
				}
			}
		}
		finally
		{
			pIn.close();
		}
		
		return result;
	}
	
	private static NewsItem readItem(XmlPullParser pParser) throws XmlPullParserException, IOException, ParseException
	{
		Log.d(TAG, "readItem(" + pParser.toString() + ")");
		NewsItem result = new NewsItem();
		pParser.require(XmlPullParser.START_TAG, null, "item");
		while(pParser.next() != XmlPullParser.END_TAG)
		{
			if(pParser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = pParser.getName();
			if(name.equals("title"))
			{
				result.setTitle(readText(pParser, "title"));
			}
			else if(name.equals("link"))
			{
				result.setLink(readText(pParser, "link"));
			}
			else if(name.equals("comments"))
			{
				result.setComments(readText(pParser, "comments"));
			}
			else if(name.equals("pubDate"))
			{
				DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy kk:mm:ss z", Locale.ENGLISH);
				result.setPublishDate(df.parse(readText(pParser, "pubDate")));
			}
			else if(name.equals("dc:creator"))
			{
				result.setCreator(readText(pParser, "dc:creator"));
			}
			else if(name.equals("category"))
			{
				result.addCategory(readText(pParser, "category"));
			}
			else if(name.equals("description"))
			{
				result.setDescription(readText(pParser, "description"));
			}
			else if(name.equals("content:encoded"))
			{
				result.setContent(readText(pParser, "content:encoded"));
			}
			else
			{
				skip(pParser);
			}
		}
		return result;
	}
	
	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "skip(" + parser.toString() + ")");
	    if (parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
	
	private static String readText(XmlPullParser pParser, String pTag) throws XmlPullParserException, IOException
	{
		Log.d(TAG, "readText(" + pParser.toString() + ", " + pTag + ")");
		pParser.require(XmlPullParser.START_TAG, null, pTag);
		String result = pParser.nextText();
		pParser.require(XmlPullParser.END_TAG, null, pTag);
		return result;
	}
	
}

package vs.piratenpartei.ch.app.redmine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public class IssueItem 
{
	private static final String TAG = "vs.piratenpartei.ch.app.redmine.IssueItem";
	
	private int _id;
	private String _subject;
	
	public int getId()
	{
		return this._id;
	}
	
	public String getSubject()
	{
		return this._subject;
	}
	
	public static ArrayList<IssueItem> readRedmineXml(InputStream pIn) throws XmlPullParserException, IOException
	{
		Log.d(TAG, "readRedmineXml(" + pIn.toString() + ")");
		ArrayList<IssueItem> result = new ArrayList<IssueItem>();
		
		XmlPullParser parser = Xml.newPullParser();
		try 
		{
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(pIn, null);
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, null, "issues");
			while(parser.next() != XmlPullParser.END_TAG)
			{
				if(parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}
				String name = parser.getName();
				Log.i("[PPVS App]IssueItem.readRedmineXml()", name);
				if(name.equals("issue"))
				{
					result.add(IssueItem.readItem(parser));
				}
				else
				{
					IssueItem.skip(parser);
				}
			}
		}
		finally
		{
			pIn.close();
		}
		
		return result;
	}

	private static IssueItem readItem(XmlPullParser pParser) throws XmlPullParserException, IOException 
	{
		Log.d(TAG, "readItem(" + pParser.toString() + ")");
		IssueItem result = new IssueItem();
		pParser.require(XmlPullParser.START_TAG, null, "issue");
		while(pParser.next() != XmlPullParser.END_TAG)
		{
			if(pParser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = pParser.getName();
			if(name.equals("id"))
			{
				result._id = Integer.parseInt(IssueItem.readText(pParser, "id"));
			}
			else if(name.equals("subject"))
			{
				result._subject = IssueItem.readText(pParser, "subject");
			}
			else
			{
				IssueItem.skip(pParser);
			}
		}
		return result;
	}

	private static String readText(XmlPullParser pParser, String pTag) throws XmlPullParserException, IOException 
	{
		Log.d(TAG, "readText(" + pParser.toString() + ", " + pTag + ")");
		pParser.require(XmlPullParser.START_TAG, null, pTag);
		String result = pParser.nextText();
		pParser.require(XmlPullParser.END_TAG, null, pTag);
		return result;
	}

	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException 
	{
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
}

package vs.piratenpartei.ch.app.redmine;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.news.NewsItem;

import android.util.Log;
import android.util.Xml;

public class IssueItem 
{
	private int _id;
	private String _project;
	private String _tracker;
	private String _status;
	private String _priority;
	private String _author;
	private String _assignedTo;
	private String _subject;
	private String _description;
	private String _start_date;
	private String _due_date;
	private int _done_ratio;
	private float _estimated_hours;
	private String _createdOn;
	private String _updatedOn;
	
	public IssueItem()
	{
		
	}
	
	public int getId()
	{
		return this._id;
	}
	
	public String getProject()
	{
		return this._project;
	}
	
	public String getTracker()
	{
		return this._tracker;
	}
	
	public String getStatus()
	{
		return this._status;
	}
	
	public String getPriority()
	{
		return this._priority;
	}
	
	public String getAuthor()
	{
		return this._author;
	}
	
	public String getAssignedTo()
	{
		return this._assignedTo;
	}
	
	public String getSubject()
	{
		return this._subject;
	}
	
	public String getDescription()
	{
		return this._description;
	}
	
	public String getStartDate()
	{
		return this._start_date;
	}
	
	public String getDueDate()
	{
		return this._due_date;
	}
	
	public int getDoneRatio()
	{
		return this._done_ratio;
	}
	
	public float getEstimatedHours()
	{
		return this._estimated_hours;
	}
	
	public String getCreatedOn()
	{
		return this._createdOn;
	}
	
	public String getUpdatedOn()
	{
		return this._updatedOn;
	}
	
	public static ArrayList<IssueItem> readRedmineXml(InputStream pIn) throws XmlPullParserException, IOException
	{
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
		IssueItem result = new IssueItem();
		pParser.require(XmlPullParser.START_TAG, null, "issue");
		while(pParser.next() != XmlPullParser.END_TAG)
		{
			if(pParser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = pParser.getName();
			Log.i("[PPVS App]IssueItem.readItem()", name);
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
		pParser.require(XmlPullParser.START_TAG, null, pTag);
		String result = pParser.nextText();
		pParser.require(XmlPullParser.END_TAG, null, pTag);
		return result;
	}

	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException 
	{
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

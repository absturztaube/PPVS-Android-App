package vs.piratenpartei.ch.app.redmine;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

public class IssueDetailItem 
{
	private String _author;
	private String _assignee;
	private String _priority;
	private Date _startDate;
	private Date _dueDate;
	private Date _createdOn;
	private Date _updatedOn;
	private String _description;
	private int _progress;
	private String _status;
	private String _estimatedHours;
	private ArrayList<JournalItem> _journal = new ArrayList<JournalItem>();
	
	public String getAuthor()
	{
		return this._author;
	}
	
	public String getAssignedTo()
	{
		return this._assignee;
	}
	
	public String getPriority()
	{
		return this._priority;
	}
	
	public Date getStartDate()
	{
		return this._startDate;
	}
	
	public Date getDueDate()
	{
		return this._dueDate;
	}
	
	public Date getCreatedOn()
	{
		return this._createdOn;
	}
	
	public Date getUpdatedOn()
	{
		return this._updatedOn;
	}
	
	public String getDescription()
	{
		return this._description;
	}
	
	public int getProgress()
	{
		return this._progress;
	}
	
	public String getStatus()
	{
		return this._status;
	}
	
	public String getEstimatedHours()
	{
		return this._estimatedHours;
	}
	
	public ArrayList<JournalItem> getJournal()
	{
		return this._journal;
	}
	
	public IssueDetailItem()
	{
		
	}
	
	public static IssueDetailItem readRedmineXml(InputStream pIn) throws IOException, XmlPullParserException, ParseException
	{
		IssueDetailItem result = new IssueDetailItem();
		try
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(pIn, null);
			parser.nextTag();
			parser.require(XmlPullParser.START_TAG, null, "issue");
			while(parser.next() != XmlPullParser.END_TAG)
			{
				if(parser.getEventType() != XmlPullParser.START_TAG)
				{
					continue;
				}
				String name = parser.getName();
				if(name.equals("author"))
				{
					result._author = IssueDetailItem.readAuthor(parser);
				}
				else if(name.equals("assigned_to"))
				{
					result._assignee = IssueDetailItem.readAssignedTo(parser);
				}
				else if(name.equals("priority"))
				{
					result._priority = IssueDetailItem.readPriority(parser);
				}
				else if(name.equals("start_date"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
					result._startDate = df.parse(IssueDetailItem.readStartDate(parser));
				}
				else if(name.equals("due_date"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
					result._dueDate = df.parse(IssueDetailItem.readEndDate(parser));
				}
				else if(name.equals("created_on"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZ", Locale.getDefault());
					result._createdOn = df.parse(IssueDetailItem.readCreateDate(parser));
				}
				else if(name.equals("updated_on"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZ", Locale.getDefault());
					result._updatedOn = df.parse(IssueDetailItem.readUpdateDate(parser));
				}
				else if(name.equals("description"))
				{
					result._description = IssueDetailItem.readDescription(parser);
				}
				else if(name.equals("done_ratio"))
				{
					result._progress = Integer.parseInt(IssueDetailItem.readDoneRatio(parser));
				}
				else if(name.equals("status"))
				{
					result._status = IssueDetailItem.readStatus(parser);
				}
				else if(name.equals("estimated_hours"))
				{
					result._estimatedHours = IssueDetailItem.readEstimatedHours(parser);
				}
				else if(name.equals("journals"))
				{
					result._journal = IssueDetailItem.readJournal(parser);
				}
				else
				{
					IssueDetailItem.skip(parser);
				}
			}
		}
		finally
		{
			pIn.close();
		}
		
		return result;
	}

	private static String readStatus(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "status");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "status");
		return result;
	}

	private static ArrayList<JournalItem> readJournal(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
		ArrayList<JournalItem> result = new ArrayList<JournalItem>();
		parser.require(XmlPullParser.START_TAG, null, "journals");
		while(parser.next() != XmlPullParser.END_TAG)
		{
			if(parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = parser.getName();
			if(name.equals("journal"))
			{
				result.add(IssueDetailItem.readItem(parser));
			}
			else
			{
				IssueDetailItem.skip(parser);
			}
		}
		return result;
	}

	private static JournalItem readItem(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
		JournalItem result = new JournalItem();
		parser.require(XmlPullParser.START_TAG, null, "journal");
		while(parser.next() != XmlPullParser.END_TAG)
		{
			if(parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = parser.getName();
			if(name.equals("user"))
			{
				result._author = IssueDetailItem.readJournalAuthor(parser);
			}
			else if(name.equals("notes"))
			{
				result._notes = IssueDetailItem.readJournalNotes(parser);
			}
			else if(name.equals("created_on"))
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZ", Locale.getDefault());
				result._createdOn = df.parse(IssueDetailItem.readJournalCreationDate(parser));
			}
			else
			{
				IssueDetailItem.skip(parser);
			}
		}
		return result;
	}

	private static String readJournalCreationDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "created_on");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "created_on");
		return result;
	}

	private static String readJournalNotes(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "notes");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "notes");
		return result;
	}

	private static String readJournalAuthor(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "user");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "user");
		return result;
	}

	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
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

	private static String readEstimatedHours(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "estimated_hours");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "estimated_hours");
		return result;
	}

	private static String readDoneRatio(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "done_ratio");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "done_ratio");
		return result;
	}

	private static String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "description");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "description");
		return result;
	}

	private static String readUpdateDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "updated_on");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "updated_on");
		return result;
	}

	private static String readCreateDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "created_on");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "created_on");
		return result;
	}

	private static String readEndDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "due_date");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "due_date");
		return result;
	}

	private static String readStartDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "start_date");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "start_date");
		return result;
	}

	private static String readPriority(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "priority");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "priority");
		return result;
	}

	private static String readAuthor(XmlPullParser parser) throws XmlPullParserException, IOException 
	{
		parser.require(XmlPullParser.START_TAG, null, "author");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "author");
		return result;
	}
	
	private static String readAssignedTo(XmlPullParser parser) throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, null, "assigned_to");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "assigned_to");
		return result;
	}

	public static class JournalItem
	{
		private String _author;
		private String _notes;
		private Date _createdOn;
		
		public String getAuthor()
		{
			return this._author;
		}
		
		public String getNotes()
		{
			return this._notes;
		}
		
		public Date getCreatedOn()
		{
			return this._createdOn;
		}
		
		public JournalItem()
		{
			
		}
	}
}

package vs.piratenpartei.ch.app.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.redmine.IssueDetailItem;
import vs.piratenpartei.ch.app.redmine.IssueItem;
import vs.piratenpartei.ch.app.redmine.IssueItemCollection;
import vs.piratenpartei.ch.app.redmine.JournalItem;
import vs.piratenpartei.ch.app.redmine.JournalItemCollection;
import android.util.Log;
import android.util.Xml;

public class RedmineParser 
{
	private static final String TAG = "vs.piratenpartei.ch.app.helpers.RedmineParser";
	
	public static IssueItemCollection readIssuesList(InputStream pIn) throws XmlPullParserException, IOException
	{
		Log.d(TAG, "readRedmineXml(" + pIn.toString() + ")");
		IssueItemCollection result = new IssueItemCollection();
		
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
					result.add(readIssueItem(parser));
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

	private static IssueItem readIssueItem(XmlPullParser pParser) throws XmlPullParserException, IOException 
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
				result.setId(Integer.parseInt(readText(pParser, "id")));
			}
			else if(name.equals("subject"))
			{
				result.setSubject(readText(pParser, "subject"));
			}
			else
			{
				skip(pParser);
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
	

	
	public static IssueDetailItem readIssueDetail(InputStream pIn) throws IOException, XmlPullParserException, ParseException
	{
		Log.d(TAG, "readRedmineXml(" + pIn.toString() +")");
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
					result.setAuthor(readAuthor(parser));
				}
				else if(name.equals("assigned_to"))
				{
					result.setAssignedTo(readAssignedTo(parser));
				}
				else if(name.equals("priority"))
				{
					result.setPriority(readPriority(parser));
				}
				else if(name.equals("start_date"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
					result.setStartDate(df.parse(readStartDate(parser)));
				}
				else if(name.equals("due_date"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
					result.setDueDate(df.parse(readEndDate(parser)));
				}
				else if(name.equals("created_on"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZ", Locale.getDefault());
					result.setCreatedOn(df.parse(readCreateDate(parser)));
				}
				else if(name.equals("updated_on"))
				{
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZ", Locale.getDefault());
					result.setUpdatedOn(df.parse(readUpdateDate(parser)));
				}
				else if(name.equals("description"))
				{
					result.setDescription(readDescription(parser));
				}
				else if(name.equals("done_ratio"))
				{
					result.setProgress(Integer.parseInt(readDoneRatio(parser)));
				}
				else if(name.equals("status"))
				{
					result.setStatus(readStatus(parser));
				}
				else if(name.equals("estimated_hours"))
				{
					result.setEstimatedHours(readEstimatedHours(parser));
				}
				else if(name.equals("journals"))
				{
					result.setJournal(readJournal(parser));
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

	private static String readStatus(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readStatus(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "status");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "status");
		return result;
	}

	private static JournalItemCollection readJournal(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
		Log.d(TAG, "readJournal(" + parser.toString() + ")");
		JournalItemCollection result = new JournalItemCollection();
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
				result.add(readJournalItem(parser));
			}
			else
			{
				skip(parser);
			}
		}
		return result;
	}

	private static JournalItem readJournalItem(XmlPullParser parser) throws XmlPullParserException, IOException, ParseException {
		Log.d(TAG, "readItem(" + parser.toString() + ")");
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
				result.setAuthor(readJournalAuthor(parser));
			}
			else if(name.equals("notes"))
			{
				result.setNotes(readJournalNotes(parser));
			}
			else if(name.equals("created_on"))
			{
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZ", Locale.getDefault());
				result.setCreatedOn(df.parse(readJournalCreationDate(parser)));
			}
			else
			{
				skip(parser);
			}
		}
		return result;
	}

	private static String readJournalCreationDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readJournalCreationDate(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "created_on");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "created_on");
		return result;
	}

	private static String readJournalNotes(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readJournalNotes(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "notes");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "notes");
		return result;
	}

	private static String readJournalAuthor(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readJournalAuthor(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "user");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "user");
		return result;
	}

	private static String readEstimatedHours(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readEstimatedHours(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "estimated_hours");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "estimated_hours");
		return result;
	}

	private static String readDoneRatio(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readDoneRatio(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "done_ratio");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "done_ratio");
		return result;
	}

	private static String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readDescription(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "description");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "description");
		return result;
	}

	private static String readUpdateDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readUpdateDate(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "updated_on");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "updated_on");
		return result;
	}

	private static String readCreateDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readCreateDate(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "created_on");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "created_on");
		return result;
	}

	private static String readEndDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readEndDate(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "due_date");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "due_date");
		return result;
	}

	private static String readStartDate(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readStartDate(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "start_date");
		String result = parser.nextText();
		parser.require(XmlPullParser.END_TAG, null, "start_date");
		return result;
	}

	private static String readPriority(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readPriority(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "priority");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "priority");
		return result;
	}

	private static String readAuthor(XmlPullParser parser) throws XmlPullParserException, IOException 
	{
		Log.d(TAG, "readAuthor(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "author");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "author");
		return result;
	}
	
	private static String readAssignedTo(XmlPullParser parser) throws XmlPullParserException, IOException {
		Log.d(TAG, "readAssignedTo(" + parser.toString() + ")");
		parser.require(XmlPullParser.START_TAG, null, "assigned_to");
		String result = parser.getAttributeValue(null, "name");
		parser.nextTag();
		parser.require(XmlPullParser.END_TAG, null, "assigned_to");
		return result;
	}
}

package vs.piratenpartei.ch.parser;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

public abstract class AbstractXmlParser 
{
	private static final String TAG = "AbstractXmlParser";
	
	protected XmlPullParser _parser;
	
	public AbstractXmlParser() throws XmlPullParserException, IOException
	{
		Log.d(TAG, "new AbstractXmlParser()");
		this._parser = Xml.newPullParser();
		this._parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	}
	
	public void setInput(InputStream pIn) throws XmlPullParserException, IOException
	{
		Log.d(TAG, "setInput(InputStream)");
		this._parser.setInput(pIn, null);
		this._parser.nextTag();
	}
	
	protected void skip() throws XmlPullParserException, IOException
	{
		Log.d(TAG, "skip()");
		if (this._parser.getEventType() != XmlPullParser.START_TAG) {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (this._parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	}
	
	protected String getText(String pTag) throws XmlPullParserException, IOException
	{
		Log.d(TAG, "getText(String)");
		this._parser.require(XmlPullParser.START_TAG, null, pTag);
		String result = this._parser.nextText();
		this._parser.require(XmlPullParser.END_TAG, null, pTag);
		return result;
	}
	
	protected Integer getTextAsInt(String pTag) throws NumberFormatException, XmlPullParserException, IOException
	{
		Log.d(TAG, "getTextAsInt(String)");
		return Integer.parseInt(this.getText(pTag));
	}
	
	protected String getAttributeWithInnerXml(String pTag, String pAttributeName) throws XmlPullParserException, IOException
	{
		Log.d(TAG, "getAttributeWithInnerXml(String, String)");
		this._parser.require(XmlPullParser.START_TAG, null, pTag);
		String result = this._parser.getAttributeValue(null, pAttributeName);
		this._parser.nextTag();
		return result;
	}
	
	protected String getAttributeWithoutInnerXml(String pTag, String pAttributeName) throws XmlPullParserException, IOException
	{
		Log.d(TAG, "getAttributeWithoutInnerXml(String, String)");
		String result = this.getAttributeWithInnerXml(pTag, pAttributeName);
		this._parser.require(XmlPullParser.END_TAG, null, pTag);
		return result;
	}
	
	public static Date convertXmlDate(String pXmlDate) throws ParseException
	{
		Log.d(TAG, "convertXmlDate(String)");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		return df.parse(pXmlDate);
	}
	
	public static Date convertXmlDateTime(String pXmlDate) throws ParseException
	{
		Log.d(TAG, "convertXmlDataTime(String)");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ssZ", Locale.getDefault());
		return df.parse(pXmlDate);
	}
	
	public abstract <Result> Result parse() throws XmlPullParserException, IOException, ParseException;
}

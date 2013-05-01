package vs.piratenpartei.ch.parser;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.util.Log;

public abstract class AbstractWebParser 
{
	private static final String TAG = "AbstractWebParser";
	
	protected Document _dom;
	private boolean _docLoaded = false;
	protected URL _url;
	protected Hashtable<String, String> _selectors = new Hashtable<String, String>();
	
	public void initialize(URL pLink) throws IOException
	{
		Log.d(TAG, "initialize(URL)");
		this._dom = Jsoup.parse(pLink, 10000);
		this._url = pLink;
		this._docLoaded = true;
	}
	
	public boolean isDocumentLoaded()
	{
		return this._docLoaded;
	}
	
	public String getPredefinedSelector(String pSelectorName)
	{
		return this._selectors.get(pSelectorName);
	}
	
	public Elements getElementsBySelector(String pSelector)
	{
		return this._dom.select(pSelector);
	}
	
	public Elements getElementsByPredefinedSelector(String pSelectorName)
	{
		String selector = this._selectors.get(pSelectorName);
		return this._dom.select(selector);
	}
	
	public abstract <Result> Result fillClass();
}

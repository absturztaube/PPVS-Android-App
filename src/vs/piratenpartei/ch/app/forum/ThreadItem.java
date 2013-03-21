package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;

import android.util.Log;

public class ThreadItem 
{
	private static final String TAG = "vs.piratenpartei.ch.app.forum.ThreadItem";
	
	private String _title;
	private String _author;
	private String _link;
	private String _lastUpdate;
	private String _lastUpdated;
	
	public String getTitle()
	{
		return this._title;
	}
	
	public String getStarter()
	{
		return this._author;
	}
	
	public String getTopicLink()
	{
		return this._link;
	}
	
	public String getLastUpdateDate()
	{
		return this._lastUpdate;
	}
	
	public String getLastUpdateAuthor()
	{
		return this._lastUpdated;
	}
	
	public static List<ThreadItem> getBoard(int pBoardId) throws IOException
	{
		return ThreadItem.getBoard(pBoardId, 0);
	}
	
	public static List<ThreadItem> getBoard(int pBoardId, int pThreadOffset) throws IOException
	{
		Log.d(TAG, "getBoard(" + pBoardId + ", " + pThreadOffset + ")");
		URL boardUrl = new URL("http://forum.piratenpartei.ch/index.php/board," + pBoardId + "." + pThreadOffset + ".html");
		List<ThreadItem> result = new ArrayList<ThreadItem>();
		ForumParser boardParser = new ForumParser(boardUrl);
		boardParser.parseDocument();
		Elements subjects = boardParser.getSubjects();
		Elements starters = boardParser.getStarters();
		Elements updateDates = boardParser.getLastUpdateDates();
		Elements updateAuthors = boardParser.getLastUpdateAuthors();
		for(int index = 0; index < subjects.size(); index++)
		{
			ThreadItem current = new ThreadItem();
			current._title = subjects.get(index).text();
			current._link = subjects.get(index).attr("href");
			current._author = starters.get(index).text();
			current._lastUpdate = ThreadItem.detectString(updateDates.get(index).text());
			current._lastUpdated = updateAuthors.get(index).text();
			result.add(current);
		}
		return result;
	}
	
	private static String detectString(String pInput)
	{
		String[] tempSplitted = pInput.split("\\sby\\s");
		String date = tempSplitted[0];
		return date;
	}
}

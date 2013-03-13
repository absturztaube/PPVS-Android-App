package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;

public class ThreadItem 
{
	private String _title;
	private String _author;
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
			current._author = starters.get(index).text();
			current._lastUpdate = updateDates.get(index).text();
			current._lastUpdated = updateAuthors.get(index).text();
			result.add(current);
		}
		return result;
	}
}

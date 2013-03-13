package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class ForumFragment extends ListFragment 
{
	private Elements _subjects;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		new BoardLoaderTask().execute();
	}
	
	private class BoardLoaderTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) 
		{
			try {
				ForumParser parser = new ForumParser(new URL("http://forum.piratenpartei.ch/index.php?board=174.0.html"));
				parser.parseDocument();
				_subjects = parser.getSubjects();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			List<String> subjectText = new ArrayList<String>();
			for(int i = 0; i < _subjects.size(); i++)
			{
				Element current = _subjects.get(i);
				subjectText.add(current.text());
			}
			
			setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, subjectText));
			setListShown(true);
		}
		
	}
}

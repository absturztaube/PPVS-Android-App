package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vs.piratenpartei.ch.app.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

public class ForumFragment extends ListFragment 
{
	private List<ThreadItem> _threads = new ArrayList<ThreadItem>();
	
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
				_threads = ThreadItem.getBoard(174);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{			
			ThreadItem[] items = new ThreadItem[_threads.size()];
			_threads.toArray(items);
			setListAdapter(new BoardListAdapter(getActivity(), R.layout.forum_list_item, items));
			setListShown(true);
		}
		
	}
}

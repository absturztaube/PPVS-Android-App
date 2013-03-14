package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.ThreadActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

public class ForumFragment extends ListFragment 
{
	private List<ThreadItem> _threads = new ArrayList<ThreadItem>();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		new BoardLoaderTask().execute();
	}
	
	@Override
	public void onListItemClick(ListView pListView, View pView, int pPosition, long pId)
	{
		ThreadItem selectedItem = this._threads.get(pPosition);
		String title = selectedItem.getTitle();
		String topicLink = selectedItem.getTopicLink();
		Intent intent = new Intent(getActivity(), ThreadActivity.class);
		Bundle params = new Bundle();
		params.putString("title", title);
		params.putString("topicUrl", topicLink);
		intent.putExtras(params);
		startActivity(intent);
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

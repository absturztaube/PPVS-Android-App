package vs.piratenpartei.ch.app.fragments;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.activities.ThreadActivity;
import vs.piratenpartei.ch.app.forum.ThreadItem;
import vs.piratenpartei.ch.app.listadapters.BoardListAdapter;
import vs.piratenpartei.ch.parser.forum.ForumLink;
import vs.piratenpartei.ch.parser.forum.ForumParser;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class ForumFragment extends ListFragment 
{
	private static final int ITEMS_PER_PAGE = 25;
	private static final String TAG = "ForumFragment";

	private int _lastLoadedOffset = -1;
	private ForumLink _boardLink;
	private BoardListAdapter _arrayAdapter;
	private boolean _clearBeforeUpdate = false;

	@Override
	public void onCreate(Bundle pSavedInstanceState)
	{
		super.onCreate(pSavedInstanceState);
		Log.d(TAG, "onCreate(Bundle)");
		this.setHasOptionsMenu(true);
	}
	
	@Override
	public void onActivityCreated(Bundle pSavedInstanceState)
	{
		super.onActivityCreated(pSavedInstanceState);
		Log.d(TAG, "onActivityCreated(Bundle)");
		
		this._boardLink = new ForumLink();
		
		this.getListView().setOnScrollListener(new AbsListView.OnScrollListener() 
		{			
			private static final String TAG_EXT = ".onScrollListener";
			@Override
			public void onScrollStateChanged(AbsListView pView, int pScrollState) 
			{
				Log.d(TAG + TAG_EXT, "onScrollStateChanged(AbsListView, int)");
			}

			@Override
			public void onScroll(AbsListView pView, int pFirstVisibleItem,
					int pVisibleItemCount, int pTotalItemCount) 
			{
				Log.d(TAG + TAG_EXT, "onScroll(AbsListView, int, int, int)");
				Log.i(TAG + TAG_EXT, "if(" + ThreadItem.LastBoardOffset + " >= " + pTotalItemCount + ")");
				if(ThreadItem.LastBoardOffset >= pTotalItemCount)
				{
					Log.i(TAG + TAG_EXT, "if(" + pFirstVisibleItem + " >= (" + pTotalItemCount + " - " + pVisibleItemCount + ")");
					if(pFirstVisibleItem >= (pTotalItemCount - pVisibleItemCount - 5))
					{
						Log.i(TAG + TAG_EXT, "loading next page...");
						getActivity().setProgressBarIndeterminateVisibility(true);
						new BoardLoaderTask(pTotalItemCount / ITEMS_PER_PAGE).execute();
					}
				}
			}
		});
	}

	@Override
	public void onViewCreated(View pView, Bundle pSavedInstanceState)
	{
		super.onViewCreated(pView, pSavedInstanceState);
		Log.d(TAG, "onViewCreated(View, Bundle)");
		getActivity().setProgressBarIndeterminateVisibility(true);
		new BoardLoaderTask().execute();
	}

	@Override
	public void onListItemClick(ListView pListView, View pView, int pPosition, long pId)
	{
		Log.d(TAG, "onListItemClick(ListView, View, int, long)");
		ThreadItem selectedItem = _arrayAdapter.getData().get(pPosition);
		String title = selectedItem.getTitle();
		String topicLink = selectedItem.getTopicLink();
		int maxOffset = selectedItem.getLastPossibleOffset();
		Intent intent = new Intent(getActivity(), ThreadActivity.class);
		Bundle params = new Bundle();
		params.putString("title", title);
		params.putString("topicUrl", topicLink);
		params.putInt("maxOffset", maxOffset);
		intent.putExtras(params);
		startActivity(intent);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu pMenu, MenuInflater pInflater)
	{
		Log.d(TAG, "onCreateOptionsMenu(Menu, MenuInflater)");
		pInflater.inflate(R.menu.forum_fragment_menu, pMenu);
		pMenu.findItem(R.id.forum_fragment_refresh).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() 
		{
			private static final String TAG_EXT = ".Menu[0]";
			
			@Override
			public boolean onMenuItemClick(MenuItem pItem) 
			{
				Log.d(TAG + TAG_EXT, "onMenuItemClick(MenuItem)");
				_clearBeforeUpdate = true;
				_lastLoadedOffset = -1;
				getActivity().setProgressBarIndeterminateVisibility(true);
				new BoardLoaderTask().execute();
				return true;
			}
		});
	}

	private class BoardLoaderTask extends AsyncTask<Void, Void, Void>
	{
		private static final String TAG_EXT = ".BoardLoaderTask";
		
		private int _offset;
		private List<ThreadItem> _newThreads;

		public BoardLoaderTask()
		{
			super();
			Log.d(TAG + TAG_EXT, "new BoardLoaderTask()");
			this._offset = 0;
		}

		public BoardLoaderTask(int pPage)
		{
			super();
			Log.d(TAG + TAG_EXT, "new BoardLoaderTask(int)");
			this._offset = pPage * ITEMS_PER_PAGE;
		}

		@Override
		protected Void doInBackground(Void... pParams) 
		{
			Log.d(TAG + TAG_EXT, "doInBackground(Void[])");
			try {
				if(_lastLoadedOffset < this._offset)
				{
					_lastLoadedOffset = this._offset;
					_boardLink.setOffset(this._offset);
					ForumParser parser = new ForumParser();
					parser.initialize(new URL(_boardLink.getUrlString()));
					_newThreads = parser.getBoard();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void pResult)
		{
			Log.d(TAG + TAG_EXT, "onPostExecute(Void)");
			if(getView() != null && _newThreads != null)
			{
				Log.d(TAG + TAG_EXT, "Loaded Items: " + _newThreads.size());
				if(_arrayAdapter == null)
				{
					_arrayAdapter = new BoardListAdapter(getActivity(), R.layout.forum_list_item, _newThreads);
					setListAdapter(_arrayAdapter);
					setListShown(true);
				}
				else
				{
					if(_clearBeforeUpdate)
					{
						_arrayAdapter.clear();
						_clearBeforeUpdate = false;
					}
					_arrayAdapter.addAll(_newThreads);
					_arrayAdapter.notifyDataSetChanged();
				}
			}
			try
			{
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			catch(NullPointerException ex)
			{
				Log.w(TAG + TAG_EXT, "Activity does not exists anymore");
			}
		}

	}
}

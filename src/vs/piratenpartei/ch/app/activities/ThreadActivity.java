package vs.piratenpartei.ch.app.activities;

import java.io.IOException;
import java.net.URL;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.forum.TopicItemCollection;
import vs.piratenpartei.ch.app.listadapters.TopicListAdapter;
import vs.piratenpartei.ch.parser.forum.ForumLink;
import vs.piratenpartei.ch.parser.forum.ForumParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

public class ThreadActivity extends Activity 
{
	private static final String TAG = "ThreadActivity";
	private static final int ITEMS_PER_PAGE = 20;
	
	private ListView _postList;
	private TopicListAdapter _arrayAdapter;
	private ForumLink _topicLink;
	private Context _context = this;
	private int _lastLoadedOffset = -1;
	private int _maxOffset = 0;
	
    @Override
    protected void onCreate(Bundle pSavedInstanceState) 
    {
        super.onCreate(pSavedInstanceState);
        Log.d(TAG, "onCreate(Bundle)");
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_thread);
        Bundle params = getIntent().getExtras();
        String title = params.getString("title");
        this._topicLink = ForumLink.parse(params.getString("topicUrl"));
        this._maxOffset = params.getInt("maxOffset");
        TextView titleTextView = (TextView)findViewById(R.id.text_thread_title);
        titleTextView.setText(title);
        setupActionBar();
        this._postList = (ListView)findViewById(R.id.list_thread_posts);
        this._postList.setOnScrollListener(new AbsListView.OnScrollListener() 
        {
        	private static final String TAG_EXT = ".list_thread_posts";
        	
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
				if(_maxOffset >= pTotalItemCount)
				{
					if(pFirstVisibleItem >= (pTotalItemCount - pVisibleItemCount))
					{
						setProgressBarIndeterminateVisibility(true);
						new TopicLoaderTask(pTotalItemCount / ITEMS_PER_PAGE).execute();
					}
				}
			}
		});
        this.setProgressBarIndeterminateVisibility(true);
        new TopicLoaderTask().execute();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu pMenu)
    {
    	Log.d(TAG, "onCreateOptionsMenu(Menu)");
    	getMenuInflater().inflate(R.menu.actiivity_thread, pMenu);
    	pMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    	{
    		private static final String TAG_EXT = ".Menu[0]";
    		
			@Override
			public boolean onMenuItemClick(MenuItem pClickedItem) 
			{
				Log.d(TAG + TAG_EXT, "onMenuItemClick(MenuItem)");
				_lastLoadedOffset = -1;
				_arrayAdapter = null;
				setProgressBarIndeterminateVisibility(true);
				new TopicLoaderTask().execute();
				return true;
			}
    		
    	});
    	return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() 
    {
    	Log.d(TAG, "setupActionBar()");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
        {
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    private class TopicLoaderTask extends AsyncTask<Void, Void, Void>
	{
    	private static final String TAG_EXT = ".TopicLoaderTask";
    	
    	private ForumLink _realLink;
		private TopicItemCollection _data = new TopicItemCollection();
    	
		public TopicLoaderTask()
		{
			Log.d(TAG + TAG_EXT, "new TopicLoaderTask()");
			this._realLink = _topicLink;
		}
		
		public TopicLoaderTask(int pPage)
		{
			Log.d(TAG + TAG_EXT, "new TopicLoaderTask(int)");
			this._realLink = _topicLink;
			this._realLink.setOffset(pPage * ITEMS_PER_PAGE);
		}
		
		@Override
		protected Void doInBackground(Void... pParams) 
		{
			Log.d(TAG + TAG_EXT, "doInBackground(Void[])");
			if(_lastLoadedOffset < this._realLink.getOffset())
			{
				try {
					_lastLoadedOffset = this._realLink.getOffset();
					ForumParser parser = new ForumParser();
					parser.initialize(new URL(this._realLink.getUrlString()));
					this._data = parser.getTopic();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void pResult)
		{
			Log.d(TAG + TAG_EXT, "onPostExecute(Void)");
			if(_arrayAdapter == null)
			{
				_arrayAdapter = new TopicListAdapter(_context, R.layout.thread_list_item, this._data);
				_postList.setAdapter(_arrayAdapter);
			}
			else
			{
				_arrayAdapter.addAll(this._data);
				_arrayAdapter.notifyDataSetChanged();
			}
			setProgressBarIndeterminateVisibility(false);
		}
		
	}
}

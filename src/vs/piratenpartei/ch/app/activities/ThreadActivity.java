package vs.piratenpartei.ch.app.activities;

import java.io.IOException;
import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.forum.ForumLink;
import vs.piratenpartei.ch.app.forum.TopicItemCollection;
import vs.piratenpartei.ch.app.helpers.ForumParser;
import vs.piratenpartei.ch.app.listadapters.TopicListAdapter;
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
	private static final String TAG = "vs.piratenpartei.ch.app.ThreadActivity";
	private static final int ITEMS_PER_PAGE = 20;
	
	private ListView _postList;
	private TopicListAdapter _arrayAdapter;
	private String _topicLink;
	private Context _context = this;
	private int _lastLoadedOffset = -1;
	private int _maxOffset = 0;
	
    @Override
    protected void onCreate(Bundle pSavedInstanceState) 
    {
        super.onCreate(pSavedInstanceState);
        Log.d(TAG, "onCreate()");
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_thread);
        Bundle params = getIntent().getExtras();
        String title = params.getString("title");
        this._topicLink = params.getString("topicUrl");
        this._maxOffset = params.getInt("maxOffset");
        TextView titleTextView = (TextView)findViewById(R.id.text_thread_title);
        titleTextView.setText(title);
        setupActionBar();
        this._postList = (ListView)findViewById(R.id.list_thread_posts);
        this._postList.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView pView, int pScrollState) {
			}
			
			@Override
			public void onScroll(AbsListView pView, int pFirstVisibleItem,
					int pVisibleItemCount, int pTotalItemCount) {
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
    	getMenuInflater().inflate(R.menu.actiivity_thread, pMenu);
    	pMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener()
    	{

			@Override
			public boolean onMenuItemClick(MenuItem pClickedItem) 
			{
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
			this._realLink = ForumLink.parse(_topicLink);
		}
		
		public TopicLoaderTask(int pPage)
		{
			this._realLink = ForumLink.parse(_topicLink);
			this._realLink.setOffset(pPage * ITEMS_PER_PAGE);
		}
		
		@Override
		protected Void doInBackground(Void... pParams) 
		{
			Log.d(TAG + TAG_EXT, "doInBackground()");
			if(_lastLoadedOffset < this._realLink.getOffset())
			{
				try {
					_lastLoadedOffset = this._realLink.getOffset();
					this._data = ForumParser.loadTopic(this._realLink.getUrlString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void pResult)
		{
			Log.d(TAG + TAG_EXT, "onPostExecute()");
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

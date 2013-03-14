package vs.piratenpartei.ch.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.forum.TopicItem;
import vs.piratenpartei.ch.app.forum.TopicListAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class ThreadActivity extends Activity 
{

	private ListView _postList;
	private String _topicLink;
	private Context ctx = this;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_thread);
        Bundle params = getIntent().getExtras();
        String title = params.getString("title");
        this._topicLink = params.getString("topicUrl");
        TextView titleTextView = (TextView)findViewById(R.id.text_thread_title);
        titleTextView.setText(title);
        setupActionBar();
        this._postList = (ListView)findViewById(R.id.list_thread_posts);
        this.setProgressBarIndeterminateVisibility(true);
        new TopicLoaderTask().execute();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
        {
        	getActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    
    private class TopicLoaderTask extends AsyncTask<Void, Void, Void>
	{
		private List<TopicItem> _data = new ArrayList<TopicItem>();
    	
		@Override
		protected Void doInBackground(Void... params) 
		{
			try {
				this._data = TopicItem.loadTopic(_topicLink);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			TopicItem[] array = new TopicItem[this._data.size()];
			this._data.toArray(array);
			Log.d("PPVS App", "Topic Recieved: " + array.length + " " + this._data.size());
			_postList.setAdapter(new TopicListAdapter(ctx, R.layout.thread_list_item, array));
	        setProgressBarIndeterminateVisibility(false);
		}
		
	}
}

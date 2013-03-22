package vs.piratenpartei.ch.app.news;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.NewsActivity;
import vs.piratenpartei.ch.app.R;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class NewsFragment extends ListFragment 
{	
	private static final String TAG = "vs.piratenpartei.ch.app.news.NewsFragment";
	
	private ArrayList<NewsItem> _feedItems = new ArrayList<NewsItem>();
		
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate()");
		setHasOptionsMenu(true);
		getActivity().setProgressBarIndeterminateVisibility(true);
		new NewsLoaderTask().execute();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		inflater.inflate(R.menu.news_fragment_menu, menu);
		menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) 
			{
				getActivity().setProgressBarIndeterminateVisibility(true);
				new NewsLoaderTask().execute();
				return true;
			}
		});
	}
	
	@Override
	public void onListItemClick(ListView pListView, View pView, int pPosition, long pId)
	{
		Log.d(TAG, "onListItemClick(" + pListView.toString() + ", " + pView.toString() + ", " + pPosition + ", " + pId + ")");
		NewsItem clicked = this._feedItems.get(pPosition);
		Intent intent = new Intent(getActivity(), NewsActivity.class);
		Bundle params = new Bundle();
		params.putString("title", clicked.getTitle());
		params.putString("author", clicked.getCreator());
		Date pubDate = clicked.getPublishDate();
		params.putString("date", DateFormat.getInstance().format(pubDate));
		params.putString("content", clicked.getContent());
		intent.putExtras(params);
		startActivity(intent);
	}
	
	private class NewsLoaderTask extends AsyncTask<Void, Void, Void>
	{
		private static final String TAG_EXT = ".NewsLoaderTask";
		
		@Override
		protected Void doInBackground(Void... params) 
		{
			Log.d(TAG + TAG_EXT, "doInBackground()");
			try 
			{
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(getString(R.string.config_news_rss));
				HttpResponse response;
				response = client.execute(httpget);
				if(response.getStatusLine().getStatusCode() == 200)
				{
					HttpEntity entity = response.getEntity();
					if(entity != null)
					{
						InputStream in = entity.getContent();
						_feedItems = NewsItem.readFeed(in);
						in.close();
					}
				}
			} 
			catch (ClientProtocolException e) 
			{
				Log.e("[PPVS App]:NewsFragment -> ClientProtocolException", e.getMessage());
			}
			catch (IOException e) 
			{
				Log.e("[PPVS App]:NewsFragment -> IOException", e.getMessage());
			} catch (XmlPullParserException e) {
				Log.e("[PPVS App]:NewsFragment -> XmlPullParserException", e.getMessage());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			Log.d(TAG + TAG_EXT, "onPostExecute()");
			ArrayList<String> titles = new ArrayList<String>();
			for(int i = 0; i < _feedItems.size(); i++)
			{
				titles.add(_feedItems.get(i).getTitle());
			}
			try
			{
				setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titles));
				setListShown(true);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			getActivity().setProgressBarIndeterminateVisibility(false);
		}
		
	}
}

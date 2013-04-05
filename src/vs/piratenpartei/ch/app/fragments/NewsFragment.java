package vs.piratenpartei.ch.app.fragments;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.helpers.Intents;
import vs.piratenpartei.ch.app.news.NewsItem;
import vs.piratenpartei.ch.app.news.NewsItemCollection;
import vs.piratenpartei.ch.parser.rss.RssParser;
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
	
	private NewsItemCollection _feedItems = new NewsItemCollection();
		
	@Override
	public void onCreate(Bundle pSavedInstanceState)
	{
		super.onCreate(pSavedInstanceState);
		Log.d(TAG, "onCreate()");
		setHasOptionsMenu(true);
		getActivity().setProgressBarIndeterminateVisibility(true);
		new NewsLoaderTask().execute();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu pMenu, MenuInflater pInflater)
	{
		pInflater.inflate(R.menu.news_fragment_menu, pMenu);
		pMenu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem pItem) 
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
		Intent intent = Intents.getNewsDetailIntent(getActivity(), clicked);
		startActivity(intent);
	}
	
	private class NewsLoaderTask extends AsyncTask<Void, Void, Void>
	{
		private static final String TAG_EXT = ".NewsLoaderTask";
		
		@Override
		protected Void doInBackground(Void... pParams) 
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
						RssParser parser = new RssParser(in);
						_feedItems = parser.getFeed();
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
		protected void onPostExecute(Void pResult)
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
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			catch(NullPointerException e)
			{
				Log.w(TAG + TAG_EXT, "Activity doesnt exists anymore");
			}
		}
		
	}
}

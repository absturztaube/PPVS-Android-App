package vs.piratenpartei.ch.app.fragments;

import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.backgroundworker.AsyncXmlParserTask;
import vs.piratenpartei.ch.app.backgroundworker.IAsyncTaskAction;
import vs.piratenpartei.ch.app.helpers.Intents;
import vs.piratenpartei.ch.app.news.NewsItem;
import vs.piratenpartei.ch.app.news.NewsItemCollection;
import vs.piratenpartei.ch.parser.rss.RssParser;
import android.content.Intent;
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
	private static final String TAG = "NewsFragment";
	
	private NewsItemCollection _feedItems = new NewsItemCollection();
		
	@Override
	public void onCreate(Bundle pSavedInstanceState)
	{
		super.onCreate(pSavedInstanceState);
		Log.d(TAG, "onCreate(Bundle)");
		setHasOptionsMenu(true);
		getActivity().setProgressBarIndeterminateVisibility(true);
		getNewsItems();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu pMenu, MenuInflater pInflater)
	{
		Log.d(TAG, "onCreateOptionsMenu(Menu, MenuInflater)");
		pInflater.inflate(R.menu.news_fragment_menu, pMenu);
		pMenu.findItem(R.id.news_fragment_refresh).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() 
		{
			private static final String TAG_EXT = "Menu[0]";
			
			@Override
			public boolean onMenuItemClick(MenuItem pItem) 
			{
				Log.d(TAG + TAG_EXT, "onMenuItemClick(MenuItem)");
				getActivity().setProgressBarIndeterminateVisibility(true);
				getNewsItems();
				return true;
			}
		});
	}
	
	@Override
	public void onListItemClick(ListView pListView, View pView, int pPosition, long pId)
	{
		Log.d(TAG, "onListItemClick(ListView, View, int, long)");
		NewsItem clicked = this._feedItems.get(pPosition);
		Intent intent = Intents.getNewsDetailIntent(getActivity(), clicked);
		startActivity(intent);
	}
	
	private void getNewsItems()
	{
		Log.d(TAG, "getNewsItems()");
		try {
			new AsyncXmlParserTask<NewsItemCollection>(new RssParser(), new NewsLoadedAction()).execute(getString(R.string.config_news_rss));
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class NewsLoadedAction implements IAsyncTaskAction<NewsItemCollection>
	{
		private static final String TAG_EXT = "NewsLoadedAction";

		@Override
		public void onComplete(NewsItemCollection pResult) 
		{
			Log.d(TAG + TAG_EXT, "onComplete(NewsItemCollection)");
			
			_feedItems = pResult;
			ArrayList<String> titles = new ArrayList<String>();
			for(int i = 0; i < pResult.size(); i++)
			{
				titles.add(pResult.get(i).getTitle());
			}
			try
			{
				setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, titles));
				setListShown(true);
				getActivity().setProgressBarIndeterminateVisibility(false);
			}
			catch(NullPointerException e)
			{
				Log.w(TAG, "Activity doesnt exists anymore");
			}
		}
		
	}
}

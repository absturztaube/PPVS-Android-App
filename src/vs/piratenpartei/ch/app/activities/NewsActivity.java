package vs.piratenpartei.ch.app.activities;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.helpers.Intents;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
//import android.support.v4.app.NavUtils;

public class NewsActivity extends Activity 
{
	private static final String TAG = "NewsActivity";
	private String _shareLink = "";
	private String _title = "";

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
		
		Log.d(TAG, "onCreate(Bundle)");
		
		setContentView(R.layout.activity_news);
		Bundle params = getIntent().getExtras();
		
		this._shareLink = params.getString("link");
		TextView title = (TextView)findViewById(R.id.news_title);
		title.setText(params.getString("title"));
		this._title = params.getString("title");
		TextView author = (TextView)findViewById(R.id.news_author);
		author.setText(params.getString("author"));
		TextView date = (TextView)findViewById(R.id.news_date);
		date.setText(params.getString("date"));
		WebView content = (WebView)findViewById(R.id.news_content);
		String html = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" " +
				"\"http://www.w3.org/TR/html4/loose.dtd\">" +
				"<html>" +
				"<body>" + 
				params.getString("content") + 
				"</body>" +
				"</html>";
		content.loadData(html, "text/html; charset=UTF-8", null);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(getString(R.string.app_name));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu pMenu) 
	{
		Log.d(TAG, "onCreateOptionsMenu(Menu)");
		getMenuInflater().inflate(R.menu.activity_news, pMenu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem pItem) 
	{
		Log.d(TAG, "onOptionsItemSelected(MenuItem)");
		switch (pItem.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			//NavUtils.navigateUpFromSameTask(this);
			return true;
		case R.id.news_share:
			Intent intentShare = Intents.getShareTextIntent(this, this._title, this._shareLink);
			startActivity(Intent.createChooser(intentShare, getString(R.string.menu_share)));
			return true;
		case R.id.news_view:
			Intent intentView = Intents.getViewUrlIntent(this, this._shareLink);
			startActivity(Intent.createChooser(intentView, getString(R.string.menu_view)));
			return true;
		}
		return super.onOptionsItemSelected(pItem);
	}

}

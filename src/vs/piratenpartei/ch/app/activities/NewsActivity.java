package vs.piratenpartei.ch.app.activities;

import vs.piratenpartei.ch.app.R;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class NewsActivity extends Activity 
{
	
	private static final String TAG = "vs.piratenpartei.ch.app.NewsActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Log.d(TAG, "onCreate()");
		
		setContentView(R.layout.activity_news);
		Bundle params = getIntent().getExtras();
		
		TextView title = (TextView)findViewById(R.id.news_title);
		title.setText(params.getString("title"));
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
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		Log.d(TAG, "onCreateOptionsMenu(" + menu.toString() + ")");
		getMenuInflater().inflate(R.menu.activity_news, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		Log.d(TAG, "onOptionsItemSelected(" + item.toString() + ")");
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

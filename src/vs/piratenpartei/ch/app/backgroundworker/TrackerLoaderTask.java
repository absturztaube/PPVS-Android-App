package vs.piratenpartei.ch.app.backgroundworker;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import vs.piratenpartei.ch.app.redmine.TrackerCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineLink;
import vs.piratenpartei.ch.parser.redmine.RedmineParser;
import android.os.AsyncTask;
import android.util.Log;

public class TrackerLoaderTask extends
AsyncTask<RedmineLink, Void, TrackerCollection> 
{
	private static final String TAG = "TrackerLoaderTask";
	
	private IAsyncTaskAction<TrackerCollection> _onComplete;

	public TrackerLoaderTask(IAsyncTaskAction<TrackerCollection> pOnComplete)
	{
		Log.d(TAG, "new TrackerLoaderTask(IAsyncTaskAction<TrackerCollection>)");
		this._onComplete = pOnComplete;
	}

	@Override
	protected TrackerCollection doInBackground(RedmineLink... params) 
	{
		Log.d(TAG, "doInBackground(RedmineLink[])");
		TrackerCollection result = new TrackerCollection();
		if(params.length > 0)
		{
			RedmineLink link = params[0];
			try 
			{
				RedmineParser parser = new RedmineParser();
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(link.getUrlString());
				HttpResponse response;
				response = client.execute(httpget);
				if(response.getStatusLine().getStatusCode() == 200)
				{
					HttpEntity entity = response.getEntity();
					if(entity != null)
					{
						InputStream in = entity.getContent();
						parser.setInput(in);
						result = parser.getTrackerList();
						in.close();
					}
				}
			} 
			catch (XmlPullParserException e) 
			{
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}

		}
		return result;
	}

	@Override
	protected void onPostExecute(TrackerCollection pResult)
	{
		Log.d(TAG, "onPostExecute(TrackerCollection)");
		try
		{
			this._onComplete.onComplete(pResult);
		}
		catch(NullPointerException ex)
		{
			ex.printStackTrace();
		}
	}

}

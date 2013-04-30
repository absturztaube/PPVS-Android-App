package vs.piratenpartei.ch.app.backgroundworker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import vs.piratenpartei.ch.parser.AbstractXmlParser;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

@SuppressLint("ShowToast")
public class AsyncXmlParserTask<Result> extends
		AsyncTask<String, Void, List<Result>> 
{
	private static final String TAG = "AsyncWebTask";
	
	private AbstractXmlParser _parser;
	private IAsyncTaskAction<Result> _onCompleteAction;
	
	public AsyncXmlParserTask(AbstractXmlParser pParser, IAsyncTaskAction<Result> pOnCompleteAction)
	{
		Log.d(TAG, "new AsyncXmlParserTask(AbstractXmlParser, IAsyncTaskAction<Result>)");
		this._parser = pParser;
		this._onCompleteAction = pOnCompleteAction;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<Result> doInBackground(String... params) 
	{
		Log.d(TAG, "doInBackground(String[])");
		List<Result> result = new ArrayList<Result>();
		for(String currentUrl : params)
		{
			try 
			{
				HttpClient client = new DefaultHttpClient();
				HttpGet httpget = new HttpGet(currentUrl);
				HttpResponse response;
				response = client.execute(httpget);
				if(response.getStatusLine().getStatusCode() == 200)
				{
					HttpEntity entity = response.getEntity();
					if(entity != null)
					{
						InputStream in = entity.getContent();
						this._parser.setInput(in);
						result.add((Result) this._parser.parse());
						in.close();
					}
				}
			} 
			catch (ClientProtocolException exception) 
			{
				exception.getStackTrace();
			}
			catch (IOException exception) 
			{
				exception.getStackTrace();
			}
			catch (Exception exception)
			{
				exception.getStackTrace();
			}
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(List<Result> pResult)
	{
		for(Result result : pResult)
		{
			try
			{
				this._onCompleteAction.onComplete(result);
			}
			catch(NullPointerException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}

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
import android.os.AsyncTask;
import android.util.Log;

public class AsyncXmlParserTask<Result> extends
		AsyncTask<String, Void, List<Result>> 
{
	private static final String TAG = "AsyncWebTask";
	
	private AbstractXmlParser _parser;
	private IAsyncTaskAction<Result> _onCompleteAction;
	
	public AsyncXmlParserTask(AbstractXmlParser pParser, IAsyncTaskAction<Result> pOnCompleteAction)
	{
		this._parser = pParser;
		this._onCompleteAction = pOnCompleteAction;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected List<Result> doInBackground(String... params) 
	{
		Log.d(TAG, "doInBackground()");
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
				Log.e("[PPVS App]:ProjectsFragment -> ClientProtocolException", exception.getMessage());
			}
			catch (IOException exception) 
			{
				Log.e("[PPVS App]:ProjectsFragment -> IOException", exception.getMessage());
			}
			catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(List<Result> pResult)
	{
		Log.d(TAG, "onPostExecute()");
		for(Result result : pResult)
		{
			this._onCompleteAction.onComplete(result);
		}
	}
}

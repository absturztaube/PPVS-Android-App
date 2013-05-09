package vs.piratenpartei.ch.app.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.activities.MainSettingsActivity;
import vs.piratenpartei.ch.app.configuration.AppConfiguration;
import vs.piratenpartei.ch.app.forum.ThreadItem;
import vs.piratenpartei.ch.app.forum.ThreadItemCollection;
import vs.piratenpartei.ch.app.news.NewsItem;
import vs.piratenpartei.ch.app.news.NewsItemCollection;
import vs.piratenpartei.ch.app.redmine.IssueItem;
import vs.piratenpartei.ch.app.redmine.IssueItemCollection;
import vs.piratenpartei.ch.parser.forum.ForumLink;
import vs.piratenpartei.ch.parser.forum.ForumParser;
import vs.piratenpartei.ch.parser.redmine.RedmineLink;
import vs.piratenpartei.ch.parser.redmine.RedmineLinkParameterCollection;
import vs.piratenpartei.ch.parser.redmine.RedmineParser;
import vs.piratenpartei.ch.parser.rss.RssParser;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
//TODO Refactoring Code
public class NotificationService extends IntentService 
{
	private static final String TAG = "NotificationService";
	private static final boolean DEBUG = false;

	private static NotificationService instance;

	public NotificationService() 
	{
		super("PpvsNotificationService");
		Log.d(TAG, "new NotificationService()");
		NotificationService.instance = this;
	}

	public static NotificationService getInstance()
	{
		Log.d(TAG, "getInstance()");
		return NotificationService.instance;
	}

	@Override
	protected void onHandleIntent(Intent pIntent) 
	{
		Log.d(TAG, "onHandleIntent(Intent)");
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		Date lastUpdate = new Date();
		if(DEBUG)
		{
			Calendar instance = Calendar.getInstance();
			instance.set(2013, 1, 1, 8, 13, 22);
			lastUpdate = instance.getTime();
		}
		while(preferences.getBoolean(MainSettingsActivity.KEY_NOTIFY_ENABLE, true))
		{
			long startedTime = System.currentTimeMillis();
			ArrayList<String> messages = new ArrayList<String>();
			Log.i(TAG, "notification service checks for new things");
			if(preferences.getBoolean(MainSettingsActivity.KEY_NOTIFY_NEWS, true))
			{
				Log.i(TAG, "check news");
				try 
				{
					HttpClient client = new DefaultHttpClient();
					HttpGet httpget = new HttpGet(AppConfiguration.getActiveConfig().getRssFeed());
					HttpResponse response;
					response = client.execute(httpget);
					if(response.getStatusLine().getStatusCode() == 200)
					{
						HttpEntity entity = response.getEntity();
						if(entity != null)
						{
							InputStream in = entity.getContent();
							RssParser parser = new RssParser();
							parser.setInput(in);
							NewsItemCollection news = new NewsItemCollection();
							news = (NewsItemCollection)parser.parse();
							in.close();
							for(NewsItem newsItem : news)
							{
								if(newsItem.getPublishDate().after(lastUpdate))
								{
									messages.add(this.getString(R.string.title_news) + ": " + newsItem.getTitle());
								}
							}
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
			if(preferences.getBoolean(MainSettingsActivity.KEY_NOTIFY_FORUM, true))
			{
				Log.i(TAG, "check forum");
				try {
					ForumLink link = new ForumLink();
					ForumParser parser = new ForumParser();
					parser.initialize(new URL(link.getUrlString()));
					ThreadItemCollection board = parser.getBoard();
					for(ThreadItem thread : board)
					{
						if(ForumParser.convertForumDate(thread.getLastUpdateDate()).after(lastUpdate))
						{
							messages.add(this.getString(R.string.title_forum) + ": " + thread.getTitle());
						}
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(preferences.getBoolean(MainSettingsActivity.KEY_NOTIFY_REDMINE, true))
			{
				Log.i(TAG, "check redmine");
				try 
				{
					RedmineLink link = new RedmineLink(AppConfiguration.getActiveConfig().getRedmineProjectPage(), RedmineLink.SUB_PAGE_ISSUES, RedmineLink.DATA_TYPE_XML, new RedmineLinkParameterCollection());
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
							RedmineParser parser = new RedmineParser();
							parser.setInput(in);
							IssueItemCollection issues = new IssueItemCollection();
							issues = (IssueItemCollection)parser.parse();
							in.close();
							for(IssueItem issue : issues)
							{
								if(issue.getLastUpdate() == null)
								{
									continue;
								}
								if(issue.getLastUpdate().after(lastUpdate))
								{
									messages.add(this.getString(R.string.title_projects) + ": " + issue.getSubject());
								}
							}
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

			Log.i(TAG, messages.size() + " new things to notify");
			if(messages.size() > 0)
			{
				NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this);
				notifyBuilder.setSmallIcon(R.drawable.ic_launcher);
				notifyBuilder.setContentTitle(this.getString(R.string.app_name) + ": " + messages.size() + " Aktualisierungen");
				notifyBuilder.setContentText(messages.get(0));
				notifyBuilder.setNumber(messages.size());

				NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
				inboxStyle.setBigContentTitle("Aktualisierungen");
				for(String msg : messages)
				{
					inboxStyle.addLine(msg);
				}
				notifyBuilder.setStyle(inboxStyle);

				NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.notify(1, notifyBuilder.build());
			}

			lastUpdate = new Date();
			int interval = Integer.parseInt(preferences.getString(MainSettingsActivity.KEY_NOTIFY_INTERVAL, "60"));
			long endedTime = System.currentTimeMillis();
			try 
			{
				Thread.sleep(interval * 60000 - (endedTime - startedTime));
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
				this.stopSelf();
			}
		}
	}

}

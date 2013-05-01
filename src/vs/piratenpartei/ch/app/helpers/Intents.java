package vs.piratenpartei.ch.app.helpers;

import java.text.DateFormat;
import java.util.Date;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.activities.NewsActivity;
import vs.piratenpartei.ch.app.activities.ProjectActivity;
import vs.piratenpartei.ch.app.news.NewsItem;
import vs.piratenpartei.ch.app.redmine.IssueItem;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class Intents 
{
	private static final String TAG = "Intents";

	public static Intent getNewsDetailIntent(Context pContext, NewsItem pNewsItem)
	{
		Log.d(TAG, "getNewsDetailIntent(Context, NewsItem)");
		Intent intent = new Intent(pContext, NewsActivity.class);
		Bundle params = new Bundle();
		params.putString("link", pNewsItem.getLink());
		params.putString("title", pNewsItem.getTitle());
		params.putString("author", pNewsItem.getCreator());
		Date pubDate = pNewsItem.getPublishDate();
		params.putString("date", DateFormat.getInstance().format(pubDate));
		params.putString("content", pNewsItem.getContent());
		intent.putExtras(params);
		return intent;
	}

	public static Intent getIssueDetailIntent(Context pContext, IssueItem pIssueItem)
	{
		Log.d(TAG, "getIssueDetailIntent(Context, IssueItem)");
		Intent intent = new Intent(pContext, ProjectActivity.class);
		intent.putExtra("issue_id", pIssueItem.getId());
		intent.putExtra("issue_subject", pIssueItem.getSubject());
		return intent;
	}

	public static Intent getWebsiteIntent(Context pContext)
	{
		Log.d(TAG, "getWebsiteIntent(Context)");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(pContext.getString(R.string.config_website)));
		return intent;
	}

	public static Intent getMailToIntent(Context pContext)
	{
		Log.d(TAG, "getMailToIntent(Context)");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		intent.setData(Uri.parse("mailto:" + pContext.getString(R.string.config_email)));
		intent.putExtra(Intent.EXTRA_SUBJECT, "[" + pContext.getString(R.string.app_name) + "]");
		return intent;
	}

	public static Intent getFacebookIntent(Context pContext)
	{
		Log.d(TAG, "getFacebookIntent(Context)");
		Intent intent;
		try {
			pContext.getPackageManager().getPackageInfo("com.facebook.katana", 0);
			intent = new Intent(Intent.ACTION_VIEW, Uri.parse(pContext.getString(R.string.config_facebook_app_intent)));
		} 
		catch (Exception e) 
		{
			intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(pContext.getString(R.string.config_facebook)));
		}
		return intent;
	}

	public static Intent getGooglePlusIntent(Context pContext)
	{
		Log.d(TAG, "getGooglePlusIntent(Context)");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(pContext.getString(R.string.config_googleplus)));
		return intent;
	}

	public static Intent getTwitterIntent(Context pContext)
	{
		Log.d(TAG, "getTwitterIntent(Context)");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(pContext.getString(R.string.config_twitter)));
		return intent;
	}

	public static Intent getShareTextIntent(Context pContext, String pTitle, String pContent)
	{
		Log.d(TAG, "getShareTextIntent(Context, String, String)");
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, pTitle);
		intent.putExtra(Intent.EXTRA_TEXT, pContent);
		return intent;
	}

	public static Intent getViewUrlIntent(Context pContext, String pUrl)
	{
		Log.d(TAG, "getViewUrlIntent(Context, String)");
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(pUrl));
		return intent;
	}
}

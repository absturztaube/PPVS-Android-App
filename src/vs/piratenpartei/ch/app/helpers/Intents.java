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

public class Intents 
{
	public static Intent getNewsDetailIntent(Context pContext, NewsItem pNewsItem)
	{
		Intent intent = new Intent(pContext, NewsActivity.class);
		Bundle params = new Bundle();
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
		Intent intent = new Intent(pContext, ProjectActivity.class);
		intent.putExtra("issue_id", pIssueItem.getId());
		intent.putExtra("issue_subject", pIssueItem.getSubject());
		return intent;
	}
	
	public static Intent getWebsiteIntent(Context pContext)
	{
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(pContext.getString(R.string.config_website)));
		return intent;
	}
	
	public static Intent getMailToIntent(Context pContext)
	{
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		intent.setData(Uri.parse("mailto:" + pContext.getString(R.string.config_email)));
		intent.putExtra(Intent.EXTRA_SUBJECT, "[" + pContext.getString(R.string.app_name) + "]");
		return intent;
	}
	
	public static Intent getFacebookIntent(Context pContext)
	{
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
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(pContext.getString(R.string.config_googleplus)));
		return intent;
	}
	
	public static Intent getTwitterIntent(Context pContext)
	{
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(pContext.getString(R.string.config_twitter)));
		return intent;
	}
}

package vs.piratenpartei.ch.app.forum;

import java.io.IOException;
import java.net.URL;
import vs.piratenpartei.ch.app.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicListAdapter extends ArrayAdapter<TopicItem> 
{
	Context context;
	int ressourceLayoutId;
	TopicItem data[] = null;
	
	public TopicListAdapter(Context context, int layoutResourceId, TopicItem[] data)
	{
		super(context, layoutResourceId, data);
		this.ressourceLayoutId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		TopicItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(ressourceLayoutId, parent, false);
			holder = new TopicItemHolder();
			holder.author = (TextView)row.findViewById(R.id.post_list_author);
			holder.content = (WebView)row.findViewById(R.id.post_list_content);
			holder.date = (TextView)row.findViewById(R.id.post_list_datetime);
			holder.avatar = (ImageView)row.findViewById(R.id.post_list_avatar);
			row.setTag(holder);
		}
		else
		{
			holder = (TopicItemHolder)row.getTag();
		}
		
		TopicItem item = data[position];
		holder.author.setText(item.getAuthor());
		holder.date.setText(item.getDate());
		holder.content.loadData(item.getContent(), "text/html", "utf-8");
		new AvatarLoaderTask(item.getAvatarLink(), holder.avatar);		
		return row;
	}
	
	private class AvatarLoaderTask extends AsyncTask<Void, Void, Void>
	{    	
		Drawable _drawable;
		ImageView _holder;
		String _avatarLink;
		
		public AvatarLoaderTask(String pLink, ImageView pHolder)
		{
			super();
			this._avatarLink = pLink;
			this._holder = pHolder;
		}
		
		@Override
		protected Void doInBackground(Void... params) 
		{
			try {
				URL avatarUrl = new URL(this._avatarLink);
				this._drawable = Drawable.createFromStream(avatarUrl.openStream(), this._avatarLink);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result)
		{
			this._holder.setImageDrawable(_drawable);
		}
		
	}
	
	static class TopicItemHolder
	{
		public TextView author;
		public TextView date;
		public WebView content;
		public ImageView avatar;
	}
}

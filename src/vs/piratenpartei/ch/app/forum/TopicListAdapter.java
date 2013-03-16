package vs.piratenpartei.ch.app.forum;

import vs.piratenpartei.ch.app.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
			holder.content = (TextView)row.findViewById(R.id.post_list_content);
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
		holder.content.setText(item.getContent());
		holder.avatar.setImageDrawable(item.getAvatar());
		return row;
	}
	
	static class TopicItemHolder
	{
		public TextView author;
		public TextView date;
		public TextView content;
		public ImageView avatar;
	}
}

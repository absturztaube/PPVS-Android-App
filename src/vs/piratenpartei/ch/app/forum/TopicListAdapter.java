package vs.piratenpartei.ch.app.forum;

import java.util.ArrayList;
import java.util.List;

import vs.piratenpartei.ch.app.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicListAdapter extends ArrayAdapter<TopicItem> 
{
	private static final String TAG = "vs.piratenpartei.ch.app.forum.TopicListAdapter";
	
	Context context;
	int ressourceLayoutId;
	List<TopicItem> data = new ArrayList<TopicItem>();
	
	public TopicListAdapter(Context context, int layoutResourceId, List<TopicItem> data)
	{
		super(context, layoutResourceId, data);
		Log.d(TAG, "new TopicListAdapter(" + context.toString() + ", " + layoutResourceId + ", " + data.toString() + ")");
		this.ressourceLayoutId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		Log.d(TAG, "getView()");
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
		
		TopicItem item = data.get(position);
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

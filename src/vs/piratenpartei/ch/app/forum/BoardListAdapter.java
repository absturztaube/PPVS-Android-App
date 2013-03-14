package vs.piratenpartei.ch.app.forum;

import vs.piratenpartei.ch.app.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BoardListAdapter extends ArrayAdapter<ThreadItem> 
{
	Context context;
	int ressourceLayoutId;
	ThreadItem data[] = null;
	
	public BoardListAdapter(Context context, int layoutResourceId, ThreadItem[] data)
	{
		super(context, layoutResourceId, data);
		this.ressourceLayoutId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		JournalItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(ressourceLayoutId, parent, false);
			holder = new JournalItemHolder();
			holder.starter = (TextView)row.findViewById(R.id.text_forum_list_starter);
			holder.summary = (TextView)row.findViewById(R.id.text_forum_list_summary);
			holder.lastUpdateDate = (TextView)row.findViewById(R.id.text_forum_list_last_update);
			holder.lastUpdateAuthor = (TextView)row.findViewById(R.id.text_forum_list_last_update_author);
			row.setTag(holder);
		}
		else
		{
			holder = (JournalItemHolder)row.getTag();
		}
		
		ThreadItem item = data[position];
		holder.starter.setText(item.getStarter());
		holder.summary.setText(item.getTitle());
		holder.lastUpdateAuthor.setText(item.getLastUpdateAuthor());
		holder.lastUpdateDate.setText(item.getLastUpdateDate());
		
		return row;
	}
	
	static class JournalItemHolder
	{
		public TextView starter;
		public TextView summary;
		public TextView lastUpdateDate;
		public TextView lastUpdateAuthor;
	}
}

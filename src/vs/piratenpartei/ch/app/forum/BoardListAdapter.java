package vs.piratenpartei.ch.app.forum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vs.piratenpartei.ch.app.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BoardListAdapter extends ArrayAdapter<ThreadItem> 
{
	private static final String TAG = "vs.piratenpartei.ch.app.forum.BoardListAdapter";
	
	Context context;
	int ressourceLayoutId;
	List<ThreadItem> data = new ArrayList<ThreadItem>();
	
	public BoardListAdapter(Context context, int layoutResourceId, List<ThreadItem> data)
	{
		super(context, layoutResourceId, data);
		Log.d(TAG, "new BoardListAdapter(" + context.toString() + ", " + layoutResourceId + ", " + data.toString() + ")");
		this.ressourceLayoutId = layoutResourceId;
		this.context = context;
		this.data = data;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		Log.d(TAG, "getView()");	//strange. if this line is before the prev line, eclipse shows me dead code!?!
		BoardItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(ressourceLayoutId, parent, false);
			holder = new BoardItemHolder();
			holder.starter = (TextView)row.findViewById(R.id.text_forum_list_starter);
			holder.summary = (TextView)row.findViewById(R.id.text_forum_list_summary);
			holder.lastUpdateDate = (TextView)row.findViewById(R.id.text_forum_list_last_update);
			holder.lastUpdateAuthor = (TextView)row.findViewById(R.id.text_forum_list_last_update_author);
			row.setTag(holder);
		}
		else
		{
			holder = (BoardItemHolder)row.getTag();
		}
		
		ThreadItem item = data.get(position);
		holder.starter.setText(item.getStarter());
		holder.summary.setText(item.getTitle());
		holder.lastUpdateAuthor.setText(item.getLastUpdateAuthor());
		holder.lastUpdateDate.setText(item.getLastUpdateDate());
		
		return row;
	}
	
	public void add(ThreadItem pNewItem)
	{
		this.data.add(pNewItem);
	}
	
	public void addAll(Collection<? extends ThreadItem> pNewItems)
	{
		this.data.addAll(pNewItems);
	}
	
	static class BoardItemHolder
	{
		public TextView starter;
		public TextView summary;
		public TextView lastUpdateDate;
		public TextView lastUpdateAuthor;
	}
}

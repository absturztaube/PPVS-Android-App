package vs.piratenpartei.ch.app.redmine;

import java.text.DateFormat;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.redmine.IssueDetailItem.JournalItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JournalArrayAdapter extends ArrayAdapter<JournalItem> 
{
	Context context;
	int ressourceLayoutId;
	JournalItem data[] = null;
	
	public JournalArrayAdapter(Context context, int layoutResourceId, JournalItem[] data)
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
			holder.header = (TextView)row.findViewById(R.id.text_list_item_header);
			holder.footer = (TextView)row.findViewById(R.id.text_list_item_footer);
			holder.content = (TextView)row.findViewById(R.id.text_list_item_content);
			row.setTag(holder);
		}
		else
		{
			holder = (JournalItemHolder)row.getTag();
		}
		
		JournalItem journal = data[position];
		holder.header.setText(journal.getAuthor());
		holder.footer.setText(DateFormat.getInstance().format(journal.getCreatedOn()));
		holder.content.setText(journal.getNotes());
		return row;
	}
	
	static class JournalItemHolder
	{
		TextView header;
		TextView footer;
		TextView content;
	}
}

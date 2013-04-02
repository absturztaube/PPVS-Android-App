package vs.piratenpartei.ch.app.listadapters;

import java.text.DateFormat;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.redmine.JournalItem;
import vs.piratenpartei.ch.app.redmine.JournalItemCollection;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JournalListAdapter extends ArrayAdapter<JournalItem> 
{
	private static final String TAG = "vs.piratenpartei.ch.app.redmine.JournalArrayAdapter";
	
	private Context _context;
	private int _ressourceLayoutId;
	private JournalItemCollection _data = null;
	
	public JournalListAdapter(Context pContext, int pLayoutResourceId, JournalItemCollection pData)
	{
		super(pContext, pLayoutResourceId, pData);
		Log.d(TAG, "new JournalListAdapter(" + pContext.toString() + ", " + pLayoutResourceId + ", " + pData.toString() + ")");
		this._ressourceLayoutId = pLayoutResourceId;
		this._context = pContext;
		this._data = pData;
	}
	
	public View getView(int pPosition, View pConvertView, ViewGroup pParent)
	{
		View row = pConvertView;
		Log.d(TAG, "getView()");
		JournalItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
			row = inflater.inflate(_ressourceLayoutId, pParent, false);
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
		
		JournalItem journal = _data.get(pPosition);
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

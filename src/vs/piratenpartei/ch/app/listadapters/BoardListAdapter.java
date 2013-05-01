package vs.piratenpartei.ch.app.listadapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.forum.ThreadItem;
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
	private static final String TAG = "BoardListAdapter";

	private Context _context;
	private int _ressourceLayoutId;
	private List<ThreadItem> _data = new ArrayList<ThreadItem>();

	public BoardListAdapter(Context pContext, int pLayoutResourceId, List<ThreadItem> pData)
	{
		super(pContext, pLayoutResourceId, pData);
		Log.d(TAG, "new BoardListAdapter(Context, int, List<ThreadItem>)");
		this._ressourceLayoutId = pLayoutResourceId;
		this._context = pContext;
		this._data = pData;
	}

	public View getView(int pPosition, View pConvertView, ViewGroup pParent)
	{
		Log.d(TAG, "getView(int, View, ViewGroup)");
		View row = pConvertView;
		BoardItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
			row = inflater.inflate(_ressourceLayoutId, pParent, false);
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

		ThreadItem item = _data.get(pPosition);
		holder.starter.setText(item.getStarter());
		holder.summary.setText(item.getTitle());
		holder.lastUpdateAuthor.setText(item.getLastUpdateAuthor());
		holder.lastUpdateDate.setText(item.getLastUpdateDate());

		return row;
	}

	public void add(ThreadItem pNewItem)
	{
		Log.d(TAG, "add(ThreadItem)");
		this._data.add(pNewItem);
	}

	public void addAll(Collection<? extends ThreadItem> pNewItems)
	{
		Log.d(TAG, "addAll(Collection<? extends ThreadItem>)");
		this._data.addAll(pNewItems);
	}

	public List<ThreadItem> getData()
	{
		Log.d(TAG, "getData()");
		return this._data;
	}

	static class BoardItemHolder
	{
		public TextView starter;
		public TextView summary;
		public TextView lastUpdateDate;
		public TextView lastUpdateAuthor;
	}
}

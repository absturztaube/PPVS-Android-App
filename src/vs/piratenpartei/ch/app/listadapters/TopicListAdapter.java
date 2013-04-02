package vs.piratenpartei.ch.app.listadapters;

import java.util.Collection;
import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.forum.TopicItem;
import vs.piratenpartei.ch.app.forum.TopicItemCollection;
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
	
	private Context _context;
	private int _ressourceLayoutId;
	private TopicItemCollection _data = new TopicItemCollection();
	
	public TopicListAdapter(Context pContext, int pLayoutResourceId, TopicItemCollection pData)
	{
		super(pContext, pLayoutResourceId, pData);
		Log.d(TAG, "new TopicListAdapter(" + pContext.toString() + ", " + pLayoutResourceId + ", " + pData.toString() + ")");
		this._ressourceLayoutId = pLayoutResourceId;
		this._context = pContext;
		this._data = pData;
	}
	
	public View getView(int pPosition, View pConvertView, ViewGroup pParent)
	{
		View row = pConvertView;
		Log.d(TAG, "getView()");
		TopicItemHolder holder = null;
		if(row == null)
		{
			LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
			row = inflater.inflate(_ressourceLayoutId, pParent, false);
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
		
		TopicItem item = _data.get(pPosition);
		holder.author.setText(item.getAuthor());
		holder.date.setText(item.getDate());
		holder.content.setText(item.getContent());
		holder.avatar.setImageDrawable(item.getAvatar());
		return row;
	}
	
	public void add(TopicItem pNewItem)
	{
		this._data.add(pNewItem);
	}
	
	public void addAll(Collection<? extends TopicItem> pNewItems)
	{
		this._data.addAll(pNewItems);
	}
	
	public TopicItemCollection getData()
	{
		return this._data;
	}
	
	static class TopicItemHolder
	{
		public TextView author;
		public TextView date;
		public TextView content;
		public ImageView avatar;
	}
}

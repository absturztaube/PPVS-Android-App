package org.absturztaube.snippets;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

/**
 * A little helper class, that gives the ability to work with arrays with unique entries
 * @author absturztaube
 *
 * @param <E> Subtype of the array
 */
public class UniqueList<E>
{
	private static final String TAG = "org.absturztaube.snippets.UniqueList";
	
	private List<E> _data;
	
	/**
	 * Creates a new UniqueList
	 */
	public UniqueList()
	{
		Log.d(TAG, "Creating a new UniqueList");
		this._data = new ArrayList<E>();
	}
	
	/**
	 * Adds an item to the list, if it is not already in it
	 * @param pItem the item to add
	 */
	public void add(E pItem)
	{
		Log.d(TAG, "Adding " + pItem.toString());
		if(!this._data.contains(pItem))
		{
			Log.d(TAG, "+Item is not in List, adding item");
			this._data.add(pItem);
		}
	}
	
	/**
	 * Adds multiple items to the list, if they are not already in it
	 * @param pRange the array to add
	 */
	public void addRange(List<E> pRange)
	{
		Log.d(TAG, "Adding " + pRange.size() + " items");
		for(E item : pRange)
		{
			this.add(item);
		}
	}
	
	/**
	 * Sets the arraylist to work on
	 * @param pData the arraylist to work on
	 */
	public void setArrayList(List<E> pData)
	{
		Log.d(TAG, "Setting data to " + pData.toString());
		this._data = pData;
	}
	
	/**
	 * Gets an element from the unique list by a given index
	 * @param pIndex the index, where the element is located
	 * @return the element at the given index in the unique listview
	 */
	public E get(int pIndex)
	{
		Log.d(TAG, "Getting item at " + pIndex);
		return this._data.get(pIndex);
	}
	
	/**
	 * gets the arraylist
	 * @return the arraylist
	 */
	public List<E> getArrayList()
	{
		Log.d(TAG, "Getting data");
		return this._data;
	}
}

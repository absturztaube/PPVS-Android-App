package vs.piratenpartei.ch.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DummySectionFragment extends Fragment 
{
	public static final String ARG_SECTION_NUMBER = "section_number";
	
	private static final String TAG = "DummySectionFragment";

	public DummySectionFragment() 
	{
		Log.d(TAG, "new DummySectionFragment()");
	}

	@Override
	public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer,
			Bundle pSavedInstanceState) 
	{
		Log.d(TAG, "onCreateView(" + pInflater.toString() + ", " + pContainer.toString() + ", " + pSavedInstanceState.toString() + ")");
		TextView textView = new TextView(getActivity());
		textView.setGravity(Gravity.CENTER);
		textView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));
		return textView;
	}
}
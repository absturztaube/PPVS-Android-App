package vs.piratenpartei.ch.app.fragments;

import vs.piratenpartei.ch.app.R;
import vs.piratenpartei.ch.app.helpers.Intents;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ContactFragment extends Fragment
{
	private static final String TAG = "ContactFragment";

	@Override
	public View onCreateView(LayoutInflater pInflater, ViewGroup pContainer, Bundle pSavedInstanceState)
	{
		Log.d(TAG, "onCreateView(LayoutInflater, ViewGroup, Bundle)");
		return pInflater.inflate(R.layout.contact_fragment, pContainer, false);
	}

	@Override
	public void onActivityCreated(Bundle pSavedInstanceState)
	{
		super.onActivityCreated(pSavedInstanceState);
		Log.d(TAG, "onActivityCreated(Bundle)");
		Button btn_webpage = (Button)getActivity().findViewById(R.id.btn_webpage);
		btn_webpage.setOnClickListener(new View.OnClickListener() 
		{
			private static final String TAG_EXT = ".btn_webpage";
			
			@Override
			public void onClick(View pView) 
			{
				Log.d(TAG + TAG_EXT, "onClick(View)");
				Intent intent = Intents.getWebsiteIntent(getActivity());
				startActivity(intent);
			}
		});
		Button btn_mail = (Button)getActivity().findViewById(R.id.btn_mail);
		btn_mail.setOnClickListener(new View.OnClickListener() 
		{
			private static final String TAG_EXT = ".btn_mail";
			@Override
			public void onClick(View pView) {
				Log.d(TAG + TAG_EXT, "onClick(View)");
				Intent intent = Intents.getMailToIntent(getActivity());
				startActivity(Intent.createChooser(intent, getString(R.string.btn_mail) + "..."));
			}
		});
		Button btn_facebook = (Button)getActivity().findViewById(R.id.btn_facebook);
		btn_facebook.setOnClickListener(new View.OnClickListener() {
			private static final String TAG_EXT = ".btn_facebook";
			@Override
			public void onClick(View pView) {
				Log.d(TAG + TAG_EXT, "onClick(View)");
				Intent intent = Intents.getFacebookIntent(getActivity());
				startActivity(intent);
			}
		});
		Button btn_googleplus = (Button)getActivity().findViewById(R.id.btn_googleplus);
		btn_googleplus.setOnClickListener(new View.OnClickListener() {
			private static final String TAG_EXT = ".btn_googleplus";
			@Override
			public void onClick(View pView) {
				Log.d(TAG + TAG_EXT, "onClick(View)");
				Intent intent = Intents.getGooglePlusIntent(getActivity());
				startActivity(intent);
			}
		});
		Button btn_twitter = (Button)getActivity().findViewById(R.id.btn_twitter);
		btn_twitter.setOnClickListener(new View.OnClickListener() {
			private static final String TAG_EXT = ".btn_twitter";
			@Override
			public void onClick(View pView) {
				Log.d(TAG + TAG_EXT, "onClick(View)");
				Intent intent = Intents.getTwitterIntent(getActivity());
				startActivity(intent);
			}
		});
	}
}

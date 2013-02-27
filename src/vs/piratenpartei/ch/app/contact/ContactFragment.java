package vs.piratenpartei.ch.app.contact;

import vs.piratenpartei.ch.app.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ContactFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.contact_fragment, container, false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		Button btn_webpage = (Button)getActivity().findViewById(R.id.btn_webpage);
		btn_webpage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://vs.piratenpartei.ch"));
				startActivity(intent);
			}
		});
		Button btn_mail = (Button)getActivity().findViewById(R.id.btn_mail);
		btn_mail.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SENDTO);
				intent.setType("text/plain");
				intent.setData(Uri.parse("mailto:info@vs.piratenpartei.ch"));
				intent.putExtra(Intent.EXTRA_SUBJECT, "[PPVS]");
				startActivity(Intent.createChooser(intent, "E-Mail..."));
			}
		});
		Button btn_facebook = (Button)getActivity().findViewById(R.id.btn_facebook);
		btn_facebook.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://www.facebook.com/PPValais"));
				startActivity(intent);
			}
		});
		Button btn_googleplus = (Button)getActivity().findViewById(R.id.btn_googleplus);
		btn_googleplus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://plus.google.com/112596815441394554834/posts"));
				startActivity(intent);
			}
		});
		Button btn_twitter = (Button)getActivity().findViewById(R.id.btn_twitter);
		btn_twitter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("https://twitter.com/PPSVS"));
				startActivity(intent);
			}
		});
	}
}
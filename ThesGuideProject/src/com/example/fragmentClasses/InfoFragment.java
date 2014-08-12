package com.example.fragmentClasses;

import com.example.thesguideproject.PlacesDetailsTabs;
import com.example.thesguideproject.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("DefaultLocale") 
public class InfoFragment extends Fragment{

	private TextView placenameElFragmenttv;
	private TextView infoFragmenttv;
	private TextView telFragmenttv;
	private TextView linkFragmenttv;
	private TextView fbLinkFragmenttv;
	private TextView emailFragmenttv;
	private Button telFragmentButton;
	private Button emailFragmentButton;
	private String name;
	PlacesDetailsTabs pdt = new PlacesDetailsTabs();
	public InfoFragment(){}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.info_fragment, container, false);
		//this.nameEltv = (TextView) view.findViewById(R.id.testtv);
		//nameEltv.setText(s);
		this.placenameElFragmenttv = (TextView) view.findViewById(R.id.placeNameFragmentTextView);
		this.infoFragmenttv = (TextView) view.findViewById(R.id.infoFragmentTextView);
		this.telFragmenttv = (TextView) view.findViewById(R.id.telFragmentTextView);
		this.linkFragmenttv = (TextView) view.findViewById(R.id.linkFragmentTextView);
		this.fbLinkFragmenttv = (TextView) view.findViewById(R.id.fbLinkFragmentTextView);
		this.emailFragmenttv = (TextView) view.findViewById(R.id.emailFragmentTextView);
		this.telFragmentButton = (Button) view.findViewById(R.id.telFragmentButton);
		this.emailFragmentButton = (Button) view.findViewById(R.id.emailFragmentButton);
		
		
		return view;
	}



	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		//String s = getArguments().getString("info"); 
		String place_nameEl = getArguments().getString("place_nameEl_info");
		String desc_info = getArguments().getString("desc_info");
		String tel = getArguments().getString("telephone");
		String link = getArguments().getString("link");	
		String fbLink = getArguments().getString("fbLink");
		String email = getArguments().getString("email");
		
		placenameElFragmenttv.setText(place_nameEl.toUpperCase());
		infoFragmenttv.setText(desc_info);
		telFragmenttv.setText(" Tel: " + tel);
		//linkFragmenttv.setText(" Link: " + link);
		linkFragmenttv.setText(Html.fromHtml("Link: <a href=\"" + link + "\">"+link+"</a>"));
		linkFragmenttv.setMovementMethod(LinkMovementMethod.getInstance());
		fbLinkFragmenttv.setText(Html.fromHtml("Facebook: <a href=\"" + fbLink + "\">"+fbLink+"</a"));
		fbLinkFragmenttv.setMovementMethod(LinkMovementMethod.getInstance());
		//fbLinkFragmenttv.setText(" Facebook: " + fbLink);
		emailFragmenttv.setText(" Email: " + email);
		final String  phone_number = tel;
		final String  emailToSend = email;
		telFragmentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String uri = phone_number.trim(); 
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:" + uri));
				startActivity(intent);
			}
		});
		
        emailFragmentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailToSend});
				i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
				i.putExtra(Intent.EXTRA_TEXT   , "body of email");
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Log.i("Log message =>", "There are no email clients installed.");
				}
			}
		});
	}

	
	
	
	
	/*@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setContentView(R.layout.info_fragment);
		
		this.nameEltv = (TextView) getView().findViewById(R.id.testtv);
		nameEltv.setText("Hi!");
		
	}*/
	
	
	

}

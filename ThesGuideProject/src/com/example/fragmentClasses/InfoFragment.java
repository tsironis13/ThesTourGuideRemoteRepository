package com.example.fragmentClasses;

import com.example.thesguideproject.PlacesDetailsTabs;
import com.example.thesguideproject.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
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
	private String language;
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
		final String language = getArguments().getString("language");
		String place_nameEl = getArguments().getString("place_nameEl_info");
		String headerName = getArguments().getString("Headername");
		String desc_info = getArguments().getString("desc_info");
		String tel = getArguments().getString("telephone");
		String link = getArguments().getString("link");	
		String fbLink = getArguments().getString("fbLink");
		String email = getArguments().getString("email");
		
		//placenameElFragmenttv.setText(place_nameEl.toUpperCase());
		String pattern = "^[A-Za-z0-9.& ]+$";
		if (place_nameEl.matches(pattern)){
			placenameElFragmenttv.setText(place_nameEl.toUpperCase());
			//placenameElFragmenttv.setTypeface(null, Typeface.BOLD);
		}else{
			placenameElFragmenttv.setText(headerName.toUpperCase());
			//placenameElFragmenttv.setTypeface(null, Typeface.BOLD);
		}	
			infoFragmenttv.setText(desc_info);
			//infoFragmenttv.setTypeface(null, Typeface.BOLD);
		if (language.equals("English")){
			telFragmenttv.setText(" Tel: " + tel);
		}
		else{
			telFragmenttv.setText(" Τηλ: " + tel);
		}
		//linkFragmenttv.setText(" Link: " + link);
		if (!link.equals("null")){
			linkFragmenttv.setText(Html.fromHtml("Link: <a href=\"" + link + "\">"+link+"</a>"));
			linkFragmenttv.setMovementMethod(LinkMovementMethod.getInstance());
		}
		else{
			linkFragmenttv.setVisibility(View.GONE);
		}
		if (!fbLink.equals("null")){
			fbLinkFragmenttv.setText(Html.fromHtml("Facebook: <a href=\"" + fbLink + "\">"+fbLink+"</a"));
			fbLinkFragmenttv.setMovementMethod(LinkMovementMethod.getInstance());
		}
		else{
			fbLinkFragmenttv.setVisibility(View.GONE);
		}
		//fbLinkFragmenttv.setText(" Facebook: " + fbLink);
		if (!email.equals("null")){
			emailFragmenttv.setText(" Email: " + email);
		}
		else{
			emailFragmenttv.setVisibility(View.GONE);
			emailFragmentButton.setVisibility(View.GONE);
		}
		final String  phone_number = tel;
		final String  emailToSend = email;
		telFragmentButton.setOnClickListener(new View.OnClickListener() {
			
		@Override
		public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
			 if (language.equals("English")){	
				alertDialogBuilder.setTitle("Making a call");
				alertDialogBuilder.setMessage("Are you sure you want to call this number?");
				alertDialogBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					}
			    });
				alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						String uri = phone_number.trim(); 
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + uri));
						startActivity(intent);   
				 	}
	           });
			 }	
			 else{
				alertDialogBuilder.setTitle("Κλήση");
			    alertDialogBuilder.setMessage("Είσαι σίγουρος οτι θές να καλέσεις αυτόν τον αριθμό;");
			    alertDialogBuilder.setPositiveButton("Όχι",new DialogInterface.OnClickListener() {
			    	public void onClick(DialogInterface dialog,int id) {
			    		dialog.cancel();
					}
			    });
			    alertDialogBuilder.setNegativeButton("Ναι",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						String uri = phone_number.trim(); 
						Intent intent = new Intent(Intent.ACTION_CALL);
						intent.setData(Uri.parse("tel:" + uri));
						startActivity(intent);   
				 	}
			    });	
			 }
			 AlertDialog alertDialog = alertDialogBuilder.create();
			 alertDialog.show();
		}
				
				
			
		});
		
        emailFragmentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
				 if (language.equals("English")){	
					alertDialogBuilder.setTitle("Sending email");
					alertDialogBuilder.setMessage("Are you sure you want to send email?");
					alertDialogBuilder.setPositiveButton("No",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
				    		dialog.cancel();
						}
				    });
					alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
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
				 else{
					alertDialogBuilder.setTitle("Κλήση");
				    alertDialogBuilder.setMessage("Είσαι σίγουρος οτι θές να στείλεις email;");
				    alertDialogBuilder.setPositiveButton("Όχι",new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog,int id) {
				    		dialog.cancel();
						}
				    });
				    alertDialogBuilder.setNegativeButton("Ναι",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailToSend});
							i.putExtra(Intent.EXTRA_SUBJECT, "θέμα του μηνύματος");
							i.putExtra(Intent.EXTRA_TEXT   , "σώμα του μηνύματος");
							try {
							    startActivity(Intent.createChooser(i, "Send mail..."));
							} catch (android.content.ActivityNotFoundException ex) {
							    Log.i("Log message =>", "There are no email clients installed.");
							}
					 	}
				    });	
				 }
				 AlertDialog alertDialog = alertDialogBuilder.create();
				 alertDialog.show();
				
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

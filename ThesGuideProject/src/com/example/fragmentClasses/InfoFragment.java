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
	private TextView emailFragmenttv, infosourcetv;
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
		this.infosourcetv = (TextView) view.findViewById(R.id.infosourcetv);
		
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
		String button_pressed = getArguments().getString("button_pressed");
		
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
		  if(tel.equals("null")){
			telFragmentButton.setVisibility(View.GONE);
		  }
		  else{
			telFragmenttv.setText(" Tel: " + tel);
		  }	
		}
		else{
		  if(tel.equals("null")){
			telFragmentButton.setVisibility(View.GONE);  
		  }	
		  else{
			telFragmenttv.setText(" ���: " + tel);
		  }	
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
				alertDialogBuilder.setTitle("�����");
			    alertDialogBuilder.setMessage("����� �������� ��� ��� �� �������� ����� ��� ������;");
			    alertDialogBuilder.setPositiveButton("���",new DialogInterface.OnClickListener() {
			    	public void onClick(DialogInterface dialog,int id) {
			    		dialog.cancel();
					}
			    });
			    alertDialogBuilder.setNegativeButton("���",new DialogInterface.OnClickListener() {
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
					alertDialogBuilder.setTitle("�����");
				    alertDialogBuilder.setMessage("����� �������� ��� ��� �� �������� email;");
				    alertDialogBuilder.setPositiveButton("���",new DialogInterface.OnClickListener() {
				    	public void onClick(DialogInterface dialog,int id) {
				    		dialog.cancel();
						}
				    });
				    alertDialogBuilder.setNegativeButton("���",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							Intent i = new Intent(Intent.ACTION_SEND);
							i.setType("message/rfc822");
							i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailToSend});
							i.putExtra(Intent.EXTRA_SUBJECT, "���� ��� ���������");
							i.putExtra(Intent.EXTRA_TEXT   , "���� ��� ���������");
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
        
        if (button_pressed.equals("Byzantine") || button_pressed.equals("Basiliki") || button_pressed.equals("PaleoChristian") || button_pressed.equals("Macedonian")){
        	if (language.equals("English")){
        		infosourcetv.setText("Source: http://www.it.uom.gr");
        	}
        	else{
        		infosourcetv.setText("����: http://www.it.uom.gr");
        	}
        }
        else if (button_pressed.equals("museums")){
        	if (language.equals("English")){
        		infosourcetv.setText("Source: http://www.thessalonikicityguide.gr");
        	}
        	else{
        		infosourcetv.setText("����: http://www.thessalonikicityguide.gr");
        	}
        }
        else if (button_pressed.equals("sightseeings")){
        	if (language.equals("English")){
        		infosourcetv.setText("Source: http://www.taxidologio.gr");
        	}
        	else{
        		infosourcetv.setText("����: http://www.taxidologio.gr");
        	}
        }
        else if (button_pressed.equals("seafood") || button_pressed.equals("restaurants") || button_pressed.equals("bar-restaurant") || button_pressed.equals("intcuisine")){
        	if (language.equals("English")){
        		infosourcetv.setText("Source: www.biscotto.gr");
        	}
        	else{
        		infosourcetv.setText("����: www.biscotto.gr");
        	}
        }
        else if (button_pressed.equals("bars") || button_pressed.equals("clubs") || button_pressed.equals("pubs")){
        	if (language.equals("English")){
        		if (place_nameEl.equals("Art House")){
         		   infosourcetv.setText("����: www.in2life.gr");
         		}else{
         		   infosourcetv.setText("����: www.biscotto.gr");
         		}
        		
        		if (place_nameEl.equals("Habanita Latin Club")){
          		   infosourcetv.setText("����: www.monopoli.gr");
          		}else{
          		   infosourcetv.setText("����: www.biscotto.gr");
          		} 
        	}
        	else{
        		if (place_nameEl.equals("Art House")){
        		   infosourcetv.setText("����: www.in2life.gr");
        		}else{
        		   infosourcetv.setText("����: www.biscotto.gr");
        		}
        		
        		if (place_nameEl.equals("Habanita Latin Club")){
          		   infosourcetv.setText("����: www.monopoli.gr");
          		}else{
          		   infosourcetv.setText("����: www.biscotto.gr");
          		}
        	}
        }
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

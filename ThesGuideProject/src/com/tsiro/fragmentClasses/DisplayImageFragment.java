package com.tsiro.fragmentClasses;

import java.io.IOException;
import java.io.InputStream;

import com.example.thesguideproject.R;
import com.tsiro.thesguideproject.SplashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class DisplayImageFragment extends Fragment{

	private String language;
	private TextView descText;
	private Button show, hide;
	//public DisplayImageFragment(){}

	public DisplayImageFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.display_image_fragment, container, false);
		descText = (TextView) view.findViewById(R.id.description_text);
		show = (Button) view.findViewById(R.id.show);
		hide = (Button) view.findViewById(R.id.hide);
        
		
		language = getArguments().getString("language");
			if (language.equals("English")){
				 try {
			            InputStream is = getActivity().getAssets().open("welcome_fragment_en.txt");
			            // We guarantee that the available method returns the total
			            // size of the asset...  of course, this does mean that a single
			            // asset can't be more than 2 gigs.
			            int size = is.available();
			            
			            // Read the entire asset into a local byte buffer.
			            byte[] buffer = new byte[size];
			            is.read(buffer);
			            is.close();
			            
			            // Convert the buffer into a string.
			            String text = new String(buffer);
			            
			            // Finally stick the string into the text view.
			            descText.setText(text);
			        } catch (IOException e) {
			            // Should never happen!
			            throw new RuntimeException(e);
			        }
			}else{
				show.setText("Περισσότερα...");
				try{
				   InputStream is = getActivity().getAssets().open("welcome_fragment_el.txt");
		            // We guarantee that the available method returns the total
		            // size of the asset...  of course, this does mean that a single
		            // asset can't be more than 2 gigs.
		            int size = is.available();
		            
		            // Read the entire asset into a local byte buffer.
		            byte[] buffer = new byte[size];
		            is.read(buffer);
		            is.close();
		            
		            // Convert the buffer into a string.
		            String text = new String(buffer);
		            
		            // Finally stick the string into the text view.
		            descText.setText(text);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		
		//b = (Button) view.findViewById(R.id.reopenbutton);
		
		
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	    show.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 show.setVisibility(View.INVISIBLE);
				 hide.setVisibility(View.VISIBLE);
				 descText.setMaxLines(Integer.MAX_VALUE);
			  if (!language.equals("English")){
				  hide.setText("Λιγότερα...");
			  }		 
			}
		});
	    
	    hide.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				 hide.setVisibility(View.INVISIBLE);
				 show.setVisibility(View.VISIBLE);
				 descText.setMaxLines(10);
				 if (!language.equals("English")){
					  show.setText("Περισσότερα...");
				  }		 
			}
		});
	}
	
	
	
	
	
	
}

package com.example.fragmentClasses;

import com.example.thesguideproject.R;
import com.example.thesguideproject.SplashScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class DisplayImageFragment extends Fragment{

	private String text;
	private TextView t;
	private Button b;
	//public DisplayImageFragment(){}

	public DisplayImageFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.display_image_fragment, container, false);
		t = (TextView) view.findViewById(R.id.textviewdisplay);
		//b = (Button) view.findViewById(R.id.reopenbutton);
		
		
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	
	}
	
	
	
	
	
	
}

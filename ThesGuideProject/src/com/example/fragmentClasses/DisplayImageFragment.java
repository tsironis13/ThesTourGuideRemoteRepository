package com.example.fragmentClasses;

import com.example.thesguideproject.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class DisplayImageFragment extends Fragment{

	private String text;
	//public DisplayImageFragment(){}

	public DisplayImageFragment(){
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.display_image_fragment, container, false);
		TextView t = (TextView) view.findViewById(R.id.textviewdisplay);
		
		return view;
	}
	
	
	
}

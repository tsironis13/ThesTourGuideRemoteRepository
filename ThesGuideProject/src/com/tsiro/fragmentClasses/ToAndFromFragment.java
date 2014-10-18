package com.tsiro.fragmentClasses;

import com.tsiro.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ToAndFromFragment extends Fragment{

	private TextView fromlocation;
	private TextView tolocation;
	private String language;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.toandfrom_fragment, container, false);
		fromlocation = (TextView) view.findViewById(R.id.fromlocationtv);
		tolocation = (TextView) view.findViewById(R.id.tolocationtv);
	
		return view;		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		String language = getArguments().getString("language");
		String fromlocationarg = getArguments().getString("fromlocation");
		String tolocationarg = getArguments().getString("tolocation");
			if (language.equals("English")){
				fromlocation.setText(fromlocationarg);
				tolocation.setText(tolocationarg);
			}else{
				fromlocation.setText(fromlocationarg);
				tolocation.setText(tolocationarg);
			}
	}
	
	
	

}

package com.example.fragmentClasses;

import com.example.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class KatalogFragment extends Fragment{

	private TextView katalogtv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.katalogfragment, container, false);
		katalogtv = (TextView) view.findViewById(R.id.katalogfragmenttv);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String menu_contents = getArguments().getString("menu");
		
		katalogtv.setText(menu_contents);
	}
	
	

}

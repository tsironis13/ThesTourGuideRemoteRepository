package com.example.fragmentClasses;

import com.example.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ExhibitionFragment extends Fragment{
	
	private TextView exhibitionfragmenttv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.exhibition_fragment, container, false);	

		this.exhibitionfragmenttv = (TextView) view.findViewById(R.id.exhibitionfragmenttv);
		
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		String exhibition = getArguments().getString("exhibition");
		exhibitionfragmenttv.setText(exhibition);
	}
	
	

}

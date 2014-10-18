package com.tsiro.fragmentClasses;

import com.tsiro.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ExhibitionFragment extends Fragment{
	
	private TextView exhibitionfragmenttv, exhibitionsourcetv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.exhibition_fragment, container, false);	

		this.exhibitionfragmenttv = (TextView) view.findViewById(R.id.exhibitionfragmenttv);
		this.exhibitionsourcetv = (TextView) view.findViewById(R.id.exhibitionsourcetv);
		
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		String language = getArguments().getString("language");
		String exhibition = getArguments().getString("exhibition");
		exhibitionfragmenttv.setText(exhibition);
		
		if (language.equals("English")){
			exhibitionsourcetv.setText("Source: http://www.thessalonikicityguide.gr");
    	}
    	else{
    		exhibitionsourcetv.setText("ÐçãÞ: http://www.thessalonikicityguide.gr");
    	}
	}
	
	

}

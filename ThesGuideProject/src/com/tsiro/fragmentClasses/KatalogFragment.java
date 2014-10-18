package com.tsiro.fragmentClasses;

import com.tsiro.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class KatalogFragment extends Fragment{

	private TextView katalogtv, menutitle, menusource;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.katalogfragment, container, false);
		katalogtv = (TextView) view.findViewById(R.id.katalogfragmenttv);
		menutitle = (TextView) view.findViewById(R.id.katalogfragmentmenutitletv);
		menusource = (TextView) view.findViewById(R.id.menusourcetv);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String menu_contents = getArguments().getString("menu");
		String language = getArguments().getString("language");
		
		katalogtv.setText(menu_contents);
		
		if (language.equals("English")){
			menusource.setText("Source: www.biscotto.gr");
		}else{
			menusource.setText("ÐçãÞ: www.biscotto.gr");
		}
	}
	
	

}

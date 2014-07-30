package com.example.fragmentClasses;

import com.example.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FourthFragment extends Fragment{

	private TextView menufragmenttv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.menu_fragment, container, false);	
		this.menufragmenttv = (TextView) view.findViewById(R.id.menufragmenttv);
		menufragmenttv.setText("Menu Fragment");
		return view;
	}
	
}

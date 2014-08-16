package com.example.fragmentClasses;

import com.example.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuFragment extends Fragment{
	
	private Button churhesButton;
	private Button clearButton;
	private Button museumsButton;
	private Fragment fragment;
	private FragmentTransaction fragmentTransaction;
	
	public MenuFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.menu_fragment, container, false);
		
		fragment = new DisplayImageFragment();
		fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.containerlist, fragment);
		fragmentTransaction.commit();
		
		churhesButton = (Button) view.findViewById(R.id.churchesbutton);
		clearButton = (Button) view.findViewById(R.id.clearDataDBbutton);
		museumsButton = (Button) view.findViewById(R.id.museumsbutton);
		
		museumsButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String museums = "museums";
				ListPlacesFragment l = new ListPlacesFragment(museums);
				//fragmentTransaction = getFragmentManager().beginTransaction().remove(fragment);
				//fragmentTransaction.commit();
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, l);
				fragmentTransaction.commit();
			}
		});
		
		churhesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String t = "PaleoChristian";
				ListPlacesFragment l1 = new ListPlacesFragment(t);
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, l1);
				fragmentTransaction.commit();
				//new ListPlacesFragment();
				// TODO Auto-generated method stub
				//fragment = ListPlacesFragment.newInstance("churches");
				//fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, fragment);
				//fragmentTransaction.commit();
			}
		});
		
		clearButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//fragment = new DisplayImageFragment();
				//fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, fragment);
				//fragmentTransaction.commit();
			}
		});
		
		return view; 
	}
	
	

}

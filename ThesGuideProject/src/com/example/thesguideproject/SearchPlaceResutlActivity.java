package com.example.thesguideproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.example.fragmentClasses.SearchPlaceResultListFragment;
import com.example.thesguideproject.R;


public class SearchPlaceResutlActivity extends FragmentActivity{

	private SearchPlaceResultListFragment searchPlaceResultListFragment;
	private FragmentTransaction fragmentTransaction;
	private TextView te;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_place_result);
		
		Intent intent = getIntent();
		String nameEl = intent.getStringExtra("PlaceName");
		
		if (savedInstanceState == null) { 
			searchPlaceResultListFragment = new SearchPlaceResultListFragment(nameEl);
			getSupportFragmentManager().beginTransaction() .add(R.id.searchresultlist, searchPlaceResultListFragment).commit(); 
		}
		
		//te = (TextView) findViewById(R.id.te);
		//
		//
		//te.setText(t);
 	}

	
	
	
}

package com.example.thesguideproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.example.fragmentClasses.SearchPlaceResultListFragment;
import com.example.thesguideproject.R;

public class SearchPlaceResutlActivity extends FragmentActivity{

	private SearchPlaceResultListFragment searchPlaceResultListFragment;
	
	public SearchPlaceResutlActivity(){}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_place_result);
		
		Intent intent = getIntent();
		String nameEl = intent.getStringExtra("PlaceName");
		String language = intent.getStringExtra("language");
		
		if (savedInstanceState == null) { 
			Bundle langbundle = new Bundle();
			langbundle.putString("language", language);
			searchPlaceResultListFragment = new SearchPlaceResultListFragment(nameEl);
			searchPlaceResultListFragment.setArguments(langbundle);
			getSupportFragmentManager().beginTransaction() .add(R.id.searchresultlist, searchPlaceResultListFragment).commit(); 
		}

 	}

	
	
	
}

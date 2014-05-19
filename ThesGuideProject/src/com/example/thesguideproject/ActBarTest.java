package com.example.thesguideproject;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

public class ActBarTest extends ActionBarActivity{

	private static String tag = "ActBarTest Activity";
	
	//action Bar
  	private android.app.ActionBar actionBar;
  	
  	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actbartestlayout);
		
		actionBar = getActionBar();
		
		//Enabling Back navigation on Action Bar icon
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}



	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
				 MenuInflater inflater = getMenuInflater();
				 inflater.inflate(R.menu.main, menu);
		 
		        
		        //enable the Search Widget
		        
		        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		        //get the search widget
		        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		        //Assumes current activity is the searchable activity
		        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		        if(searchView == null){
		        	Log.wtf(tag, "searchView is null");
		        }
		        
		        SearchableInfo si = searchManager.getSearchableInfo(getComponentName());
		        if( si == null){
		        	Log.wtf(tag, "failed to get searchable info");
		        }
		        else{
		        	Log.wtf(tag, "success in getting the searchable info!!");
		        }
		        //Properties in the SearchableInfo are used to display labels, hints, 
		        //suggestions, create intents for launching search results screens
		        searchView.setSearchableInfo(si);
		        searchView.setIconifiedByDefault(true); //// Do not iconify the widget; expand it by default
		        
		        return super.onCreateOptionsMenu(menu);
	}
	
	
	

}

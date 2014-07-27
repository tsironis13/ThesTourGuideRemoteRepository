package com.example.thesguideproject;

import java.util.ArrayList;

import com.example.adapters.LocationsDataAdapter;
import com.example.locationData.LocationData;
import com.example.tasks.ImageTask;
import com.example.tasks.JsonWebAPITask;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActBarTest extends ActionBarActivity{

	private static String tag = "ActBarTest Activity";
	
	//action Bar
  	private android.app.ActionBar actionBar;
  	
  	private ArrayList<LocationData> locations;
  	private ListView locationsList;
  	private LayoutInflater layoutInflator;
  	private ImageTask imgFetcher;
  	
  	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actbartestlayout);
		
		this.locationsList = (ListView) findViewById(R.id.locations_list);
		this.imgFetcher = new ImageTask(this);
        this.layoutInflator = LayoutInflater.from(this);
        
        
		//JsonWebAPITask webtask = new JsonWebAPITask(ActBarTest.this);
		//webtask.execute();
		
		actionBar = getActionBar();
		
		//Enabling Back navigation on Action Bar icon
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	    
	    
	 // Restore any already fetched data on orientation change. 
        final Object[] data = (Object[]) getLastNonConfigurationInstance();
        if(data != null) {
        	this.locations = (ArrayList<LocationData>) data[0];
        	this.imgFetcher = (ImageTask)data[1];
         	//locationsList.setAdapter(new LocationsDataAdapter(this,this.imgFetcher,this.layoutInflator, this.locations));
        }
	    
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
		        
		        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		        
		        
		        //SearchableInfo si = searchManager.getSearchableInfo(getComponentName());
		        //if( si == null){
		        	//Log.wtf(tag, "failed to get searchable info");
		        //}
		        //else{
		        	//Log.wtf(tag, "success in getting the searchable info!!");
		        //}
		        //Properties in the SearchableInfo are used to display labels, hints, 
		        //suggestions, create intents for launching search results screens
		        //searchView.setSearchableInfo(si);
		        //searchView.setIconifiedByDefault(true); //// Do not iconify the widget; expand it by default
		        
		        return super.onCreateOptionsMenu(menu);
	}
	
	
	 /*
     * Bundle to hold refs to row items views
     */
    public static class MyViewHolder{
    	public TextView genre, nameEl, latitude, longtitude;
    	//public RelativeLayout relLay;
    	//public Button trackButton;
    	public Button detailsButton;
    	public ImageView icon;
    	public LocationData locations;
    } 
    
/*
    public void setTracks(ArrayList<LocationData> locData) {
		// TODO Auto-generated method stub
    	
		this.locations = locData;
		this.locationsList.setAdapter(new LocationsDataAdapter(this, this.imgFetcher, this.layoutInflator, this.locations));
	}
	*/
    
    
    
	
	

}

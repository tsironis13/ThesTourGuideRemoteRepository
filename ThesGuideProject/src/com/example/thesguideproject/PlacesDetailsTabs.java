package com.example.thesguideproject;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.adapters.TabsPagerAdapter;
import com.example.fragmentClasses.GoogleMapFragment;
import com.example.fragmentClasses.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.example.fragmentClasses.PhotoGridViewFragment;
import com.example.fragmentClasses.InfoFragment;
import com.example.fragmentClasses.ExhibitionFragment;
import com.example.fragmentClasses.OnMapFragment;
import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
//import android.app.ActionBar;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.widget.SearchView;
//import android.app.ActionBar.Tab;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesDetailsTabs extends ActionBarActivity implements OnGoogleMapFragmentListener{

	// Google Map
    private GoogleMap googleMap;
    
    LatLng myPosition;
    
    private GoogleMap mUIGoogleMap;
    private ProgressDialog simpleWaitDialog;
    private String button_pressed;
    private String placenameEl;
    private String description_info;
    private String telephone;
    private String link;
    private String fbLink;
    private String email;
    private String exhibition;
    private String photoLink1;
    private String photoLink2;
    private String photoLink3;
    private String photoLink4;
    private TabsPagerAdapter mAdapter;
    private double doublelatitude;
    private double doublelongtitude;
    private double doubleCurrentLatitude;
    private double doubleCurrentLongtitude;
    private String latitude;
    private String longtitude;
    private String current_latitude;
    private String current_longtitude;
    
    //private Button onMapButton;
    //private ActionBar act;
    private TextView t;
  	//private android.app.ActionBar actionBar;
  	private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private Context context;
    private SearchView searchView;
    Bundle exhibitionBundle = new Bundle();
    
    private ActionBar mActionBar;

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		mUIGoogleMap = map;
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testlayout);
		mActionBar = getSupportActionBar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Intent i = getIntent();
		
		button_pressed = i.getStringExtra("button_pressed_text");
		placenameEl = i.getStringExtra("placeNameEl");
		description_info = i.getStringExtra("desc_info");
		telephone = i.getStringExtra("telephone");
		link = i.getStringExtra("link");
		fbLink = i.getStringExtra("fbLink");
		email = i.getStringExtra("email");
		exhibition = i.getStringExtra("exhibition");
		photoLink1 = i.getStringExtra("photoLink1");
		photoLink2 = i.getStringExtra("photoLink2");
		photoLink3 = i.getStringExtra("photoLink3");
		photoLink4 = i.getStringExtra("photoLink4");
		latitude = i.getStringExtra("latitude");
		longtitude = i.getStringExtra("longtitude");
		current_latitude = i.getStringExtra("current latitude");
		current_longtitude = i.getStringExtra("current longtitude");
		Toast.makeText(getApplicationContext(), current_latitude + " " + current_longtitude, Toast.LENGTH_SHORT).show();
		
		
		doublelatitude = Double.parseDouble(latitude);
		doublelongtitude = Double.parseDouble(longtitude);
		
		doubleCurrentLatitude = Double.parseDouble(current_latitude);
		doubleCurrentLongtitude = Double.parseDouble(current_longtitude);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setClipToPadding(false);

		//actionBar = getSupportActionBar();
		//mAdapter = new TabsPagerAdapter(this, viewPager, name, doublelatitude, doublelongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
		
		viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        //this.tabsPagerAdapter = new TabsPagerAdapter(this, viewPager, name, doublelatitude, doublelongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
        this.tabsPagerAdapter = new TabsPagerAdapter(this, viewPager, mActionBar);
        tabsPagerAdapter.createHistory();
        
        
        
        Bundle infoBundle = new Bundle();
        infoBundle.putString("place_nameEl_info", placenameEl);
        infoBundle.putString("desc_info", description_info);
        infoBundle.putString("telephone", telephone);
        infoBundle.putString("link", link);
        infoBundle.putString("fbLink", fbLink);
        infoBundle.putString("email", email);
       // tabsPagerAdapter.addTab(actionBar.newTab().setText("Info"), InfoFragment.class, infoBundle);
       
        tabsPagerAdapter.addTab(mActionBar.newTab().setText("Info"), InfoFragment.class, infoBundle);
        
        exhibitionBundle = new Bundle();
        exhibitionBundle.putString("exhibition", exhibition);
        //tabsPagerAdapter.addTab(actionBar.newTab().setText("Exhibition"), ExhibitionFragment.class, exhibitionBundle);
        if (!exhibition.equals("null"))
        tabsPagerAdapter.addTab(mActionBar.newTab().setText("Exhibition"), ExhibitionFragment.class, exhibitionBundle);
        
        TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
        
        DisplayMetrics metrics = new DisplayMetrics();
     		getWindowManager().getDefaultDisplay().getMetrics(metrics);
     		
     		int scr_height = metrics.heightPixels;
     		int scr_width = metrics.widthPixels;
     		String s_height = Integer.toString(scr_height);
     		String s_width = Integer.toString(scr_width);
     		Log.i("SCRENN HEIGHT => ", s_height);
     		Log.i("SCRENN WIDTH => ", s_width);
        
     //   if (button_pressed.equals("museums")){
        	
        	//ArrayList<Photo>  photoLinksArray = testDB.getPhotoLinksArray(placenameEl);
        	String[] photoLinkStringArray = testDB.getPhotoLinksArray(placenameEl);
        	int list_length = photoLinkStringArray.length;
        	if (!photoLinkStringArray[0].equals("null")){
        	String s = Integer.toString(list_length);
        	
        	Log.i("PHOTO LIST RETURN FROM DATABASE =>", s);
  	
        	//Toast.makeText(getApplicationContext(), "Button pressed text =>" + " " + button_pressed, Toast.LENGTH_SHORT).show();
        	Bundle photoBundle = new Bundle();
        	photoBundle.putSerializable("linksList", photoLinkStringArray);
        	photoBundle.putInt("Screen Height", scr_height);
        	photoBundle.putInt("Screen Width", scr_width);
        	//tabsPagerAdapter.addTab(actionBar.newTab().setText("Photo tab"), PhotoGridViewFragment.class, photoBundle);
        	tabsPagerAdapter.addTab(mActionBar.newTab().setText("Photo tab"), PhotoGridViewFragment.class, photoBundle);
        	
        	testDB.close();
        	}
       // }
     
        
     
        
        Bundle onmapBundle = new Bundle();
        onmapBundle.putDouble("doubleCurrentLatitude", doubleCurrentLatitude);
        onmapBundle.putDouble("doubleCurrentLongtitude", doubleCurrentLongtitude);
        onmapBundle.putDouble("doublePlaceLatitude", doublelatitude);
        onmapBundle.putDouble("doublePlaceLongtitude", doublelongtitude);
        //tabsPagerAdapter.addTab(actionBar.newTab().setText("OnMap"), OnMapFragment.class, onmapBundle);
        //tabsPagerAdapter.addTab(actionBar.newTab().setText("OnMap"), GoogleMapFragment.class, onmapBundle);
        tabsPagerAdapter.addTab(mActionBar.newTab().setText("OnMap"), GoogleMapFragment.class, onmapBundle);
      //  tabsPagerAdapter.replace(2, ExhibitionFragment.class, exhibitionBundle);
        
      /*  ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
				
				
			}
			
			@Override
			public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
				
				if (tab.getPosition() == 2){
					Toast.makeText(getApplicationContext(), "On Map Tab Pressed!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" 
							+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doublelatitude + "," + doublelongtitude));
							startActivity(intent);
				}
				viewPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
				
				if (tab.getPosition() == 2){
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" 
							+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doublelatitude + "," + doublelongtitude));
							startActivity(intent);
				}
				viewPager.setCurrentItem(tab.getPosition());
			}
		};*/
 
		//Add new Tabs
	//	actionBar.addTab(actionBar.newTab().setText("Info").setTabListener(tabListener));
	//	actionBar.addTab(actionBar.newTab().setText("MenuTab").setTabListener(tabListener));
	//	actionBar.addTab(actionBar.newTab().setText("OnMap").setTabListener(tabListener));
		

	}

	public boolean onCreateOptionsMenu(Menu menu){
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
		
		//Find the search item
		MenuItem searchItem = menu.findItem(R.id.action_search);
		
		//Retrieve the SearchView
		searchView  = (SearchView) MenuItemCompat.getActionView(searchItem);		
		
		return super.onCreateOptionsMenu(menu);
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_search:
	            Toast.makeText(getApplicationContext(), "Action Search!!", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
}

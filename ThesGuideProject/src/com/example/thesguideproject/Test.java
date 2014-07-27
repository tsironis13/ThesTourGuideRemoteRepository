package com.example.thesguideproject;

import com.example.adapters.TabsPagerAdapter;
import com.example.fragmentClasses.InfoFragment;
import com.example.fragmentClasses.OnMapFragment;
import com.example.myLocation.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;









import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
public class Test extends FragmentActivity {

	// Google Map
    private GoogleMap googleMap;
    
    LatLng myPosition;
	
    private String name;
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
    private TextView t;
  	private android.app.ActionBar actionBar;
  	private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    
    private String[] tabs = {"OnMap", "OnMap"};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testlayout);
		
		Intent i = getIntent();
		
		name = i.getStringExtra("nameEl");
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
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(name, doublelatitude, doublelongtitude, doubleCurrentLatitude, doubleCurrentLongtitude, getSupportFragmentManager());
		
		viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
        
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				if (tab.getPosition() == 1){
					//Toast.makeText(getApplicationContext(), "On Map Tab Pressed!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" 
							+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doublelatitude + "," + doublelongtitude));
							startActivity(intent);
				}
				viewPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
				// TODO Auto-generated method stub
				if (tab.getPosition() == 1){
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" 
							+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doublelatitude + "," + doublelongtitude));
							startActivity(intent);
				}
			}
		};
 
		//Add new Tabs
		actionBar.addTab(actionBar.newTab().setText("Info").setTabListener(tabListener));
		actionBar.addTab(actionBar.newTab().setText("OnMap").setTabListener(tabListener));
		

	}
	
}

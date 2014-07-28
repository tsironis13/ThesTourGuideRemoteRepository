package com.example.thesguideproject;

import com.example.adapters.TabsPagerAdapter;
import com.example.fragmentClasses.InfoFragment;
import com.example.fragmentClasses.MenuFragment;
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
		//mAdapter = new TabsPagerAdapter(this, viewPager, name, doublelatitude, doublelongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
		
		viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
        
        this.tabsPagerAdapter = new TabsPagerAdapter(this, viewPager, name, doublelatitude, doublelongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
        Bundle infoBundle = new Bundle();
        infoBundle.putString("info", name);
        tabsPagerAdapter.addTab(actionBar.newTab().setText("Info"), InfoFragment.class, infoBundle);
        tabsPagerAdapter.addTab(actionBar.newTab().setText("MenuTab"), MenuFragment.class, null);
        Bundle onmapBundle = new Bundle();
        onmapBundle.putDouble("doubleCurrentLatitude", doubleCurrentLatitude);
        onmapBundle.putDouble("doubleCurrentLongtitude", doubleCurrentLongtitude);
        tabsPagerAdapter.addTab(actionBar.newTab().setText("OnMap"), OnMapFragment.class, onmapBundle);
        
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
	
}

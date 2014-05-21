package com.example.thesguideproject;

import com.example.myLocation.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class Test extends FragmentActivity{

	// Google Map
    private GoogleMap googleMap;
    
    LatLng myPosition;
	
    String name;
    double doublelatitude;
    double doublelongtitude;
    String latitude;
    String longtitude;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testlayout);
		
		TextView t = (TextView) findViewById(R.id.testtv);
		
		Intent i = getIntent();
		
		
		name = i.getStringExtra("nameEl");
		latitude = i.getStringExtra("latitude");
		longtitude = i.getStringExtra("longtitude");
		
		doublelatitude = Double.parseDouble(latitude);
		doublelongtitude = Double.parseDouble(longtitude);
		
		t.setText(name);
		
		
		
		
		// Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		
        //Getting GoogleMap object from the fragment
        googleMap = fm.getMap();

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        //GPSTracker gpstr = new GPSTracker(Test.this);
        
        // Getting latitude of the current location
        //double latitude = gpstr.getLatitude();
        
        // Getting longitude of the current location
        //double longitude = gpstr.getLongitude();
        
        // Creating a LatLng object for the current location
        
        
        LatLng latLng = new LatLng(doublelatitude, doublelongtitude);

         myPosition = new LatLng(doublelatitude, doublelongtitude);

        googleMap.addMarker(new MarkerOptions().position(myPosition).title("Start"));
        
        
        /*
        String tag = "KALASEEEEEEEEEEE";
        setList(loc);
		Log.d(tag , "LOC DATA : " + loc);
        
        //Creating a LatLng object for a specific location
        LatLng latLng1 = new LatLng(40.6250548, 22.9529811);

        myPosition1 = new LatLng(40.6250548, 22.9529811);

       googleMap.addMarker(new MarkerOptions().position(myPosition1).title("Finish"));*/
        //JsonWebAPITask object = new JsonWebAPITask();
       // String lat = object.getLatitudeandLongLatitudeoftheSpecificLocation();
        
        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(doublelatitude, doublelongtitude));
        CameraUpdate zoom= CameraUpdateFactory.zoomTo(16);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
		
	}

	
	
}

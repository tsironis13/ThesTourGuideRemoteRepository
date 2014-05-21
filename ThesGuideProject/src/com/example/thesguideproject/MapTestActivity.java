package com.example.thesguideproject;

import java.util.ArrayList;

import com.example.locationData.LocationData;
import com.example.myLocation.GPSTracker;
import com.example.tasks.JsonWebAPITask;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class MapTestActivity extends FragmentActivity {

	// Google Map
    private GoogleMap googleMap;
    
    LatLng myPosition;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_view);
		
		// Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		
        //Getting GoogleMap object from the fragment
        googleMap = fm.getMap();

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        GPSTracker gpstr = new GPSTracker(MapTestActivity.this);
        
        // Getting latitude of the current location
        double latitude = gpstr.getLatitude();
        
        // Getting longitude of the current location
        double longitude = gpstr.getLongitude();
        
        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

         myPosition = new LatLng(latitude, longitude);

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
        
        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        CameraUpdate zoom= CameraUpdateFactory.zoomTo(16);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
		
            try {
            // Loading map
            //initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	

	
	 /**
     * function to load map. If map is not created it will create it for you
     * */
    /*@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT).show();
            }
        }
    }*/
 
    @Override
    protected void onResume() {
        super.onResume();
       // initilizeMap();
    }

	
	
}

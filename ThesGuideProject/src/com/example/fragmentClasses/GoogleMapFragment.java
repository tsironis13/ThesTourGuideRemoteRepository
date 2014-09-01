package com.example.fragmentClasses;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.myLocation.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapFragment extends SupportMapFragment {

	private static final String SUPPORT_MAP_BUNDLE_KEY = "MapOptions";
	private OnGoogleMapFragmentListener mCallback;
	private Double currentLong;
	private Double currentLat;
	private Double placeLong;
	private Double placeLat;
	private LatLng placePosition;
	private LatLng secondPlacePosition;
	
    public static interface OnGoogleMapFragmentListener {
        void onMapReady(GoogleMap map);
    }
    
    public static GoogleMapFragment newInstance() {
        return new GoogleMapFragment();
    }
    
    public static GoogleMapFragment newInstance(GoogleMapOptions options) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(SUPPORT_MAP_BUNDLE_KEY, options);

        GoogleMapFragment fragment = new GoogleMapFragment();
        fragment.setArguments(arguments);
        return fragment;
    }
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try {
            mCallback = (OnGoogleMapFragmentListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().getClass().getName() + " must implement OnGoogleMapFragmentListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		currentLong = getArguments().getDouble("doubleCurrentLongtitude");
		currentLat = getArguments().getDouble("doubleCurrentLatitude");
			
		placeLong = getArguments().getDouble("doublePlaceLongtitude");
		placeLat = getArguments().getDouble("doublePlaceLatitude");
		
		//Set a linearLayout to add buttons
	    LinearLayout linearLayout = new LinearLayout(getActivity());
	    
	    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    linearLayout.setBackgroundColor(Color.LTGRAY);
	    //params.height = 103;
	    linearLayout.setLayoutParams(params);
	    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
	    
	    Button button = new Button(getActivity());
	    //For buttons visibility, you must set the layout params in order to give some width and height: 
	    LayoutParams paramss = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    //paramss.height=100;
	    button.setText("Map Details");
	    button.setLayoutParams(paramss);
		
	    button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			    Uri.parse("http://maps.google.com/maps?saddr=" + currentLat + "," + currentLong + "&daddr=" + placeLat + "," + placeLong + ""));
				startActivity(intent);
			}
		});
	    
	   
		
		ViewGroup viewGroup = (ViewGroup) view;

	    linearLayout.addView(button);
	    
	    viewGroup.addView(linearLayout);
		
		
        if (mCallback != null) {
            mCallback.onMapReady(getMap());
            GoogleMap googleMap = getMap();
            
            googleMap.setMyLocationEnabled(true);
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setPadding(0, 103, 0, 0);
            //googleMap.setOnMapClickListener(listener);
           
            //Creating a LatLng object for a specific location
            placePosition = new LatLng(placeLat, placeLong);
            
            secondPlacePosition = new LatLng(currentLat, currentLong);
            
            googleMap.addMarker(new MarkerOptions().position(secondPlacePosition).title("Start"));
           
            googleMap.addMarker(new MarkerOptions().position(placePosition).title("Finish"));
                       
            CameraUpdate center= CameraUpdateFactory.newLatLng(placePosition);
            CameraUpdate zoom= CameraUpdateFactory.zoomTo(12);

            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
        return view;
	}

	
	
	
}

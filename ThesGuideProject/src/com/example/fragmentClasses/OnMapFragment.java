package com.example.fragmentClasses;

import com.example.thesguideproject.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

@SuppressLint("ValidFragment") 
public class OnMapFragment extends Fragment{

	private double doubleLatitude;
	private double doubleLongtitude;
	private double doubleCurrentLatitude;
	private double doubleCurrentLongtitude;
	private WebView webview;
	// Google Map
    private GoogleMap googleMap;
    
	public OnMapFragment(double doubleLatitude, double doubleLongtitude, double doubleCurrentLatitude, double doubleCurrentLongtitude){
		 this.doubleLatitude = doubleLatitude;
	     this.doubleLongtitude = doubleLongtitude;
	     this.doubleCurrentLatitude = doubleCurrentLatitude;
	     this.doubleCurrentLongtitude = doubleCurrentLongtitude;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.onmap_fragment, container, false);
		this.webview = (WebView) view.findViewById(R.id.webview);
		webview.setWebViewClient(new WebViewClient());
		
		// Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
		
        //Getting GoogleMap object from the fragment
        googleMap = fm.getMap();
        
        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        
        //Creating a LatLng object for a specific location
        LatLng latLng1 = new LatLng(doubleLatitude, doubleLongtitude);
        
        googleMap.addMarker(new MarkerOptions().position(latLng1).title("Finish"));
        
        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(doubleLatitude, doubleLongtitude));
        CameraUpdate zoom= CameraUpdateFactory.zoomTo(16);

        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
		//webview.loadUrl("sds");
		//webview.loadUrl("https://maps.google.com/maps?saddr=" 
			//+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doubleLatitude + "," + doubleLongtitude);
		//webview.loadUrl("https://maps.google.com/maps?saddr=40.5555,40.5655&daddr=40.6544,40.6666");
		/*Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" 
				+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doubleLatitude + "," + doubleLongtitude));
				startActivity(intent);*/
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	

}

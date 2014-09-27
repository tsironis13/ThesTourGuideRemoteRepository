package com.example.fragmentClasses;

import com.example.thesguideproject.R;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NoInternetConnectionFragment extends Fragment{

	private Button retrybutton;
	private TextView nonetcontv;
	private String language;
	private double doublelatitude;
    private double doublelongtitude;
    private double doubleCurrentLatitude;
    private double doubleCurrentLongtitude;
	private FragmentTransaction fragmentTransaction;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.nointernetconnection, container, false);
		
		retrybutton = (Button) view.findViewById(R.id.retrybuttton);
		nonetcontv = (TextView) view.findViewById(R.id.nonetconnectiontv);
		language = getArguments().getString("language");
			if (!language.equals("English")){
				retrybutton.setText("Παρακαλώ Ξαναπροσπάθησε");
				nonetcontv.setText("Χωρίς σύνδεση στο Διαδίκτυο");
			}
			
		doublelatitude = getArguments().getDouble("doublePlaceLatitude");
		doublelongtitude = getArguments().getDouble("doublePlaceLongtitude");
		doubleCurrentLatitude = getArguments().getDouble("doubleCurrentLatitude");
		doubleCurrentLongtitude = getArguments().getDouble("doubleCurrentLongtitude");
	
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		retrybutton.setOnClickListener(new View.OnClickListener() {		
				@Override
				public void onClick(View v) {
					Toast.makeText(getActivity(), "Retry Button!", Toast.LENGTH_SHORT).show();
					WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
					if (wifi.isWifiEnabled()){
						nonetcontv.setVisibility(View.GONE);
						retrybutton.setVisibility(View.GONE);
						GoogleMapFragment googleMapFragment = new GoogleMapFragment();
						Bundle onmapBundle = new Bundle();
				        onmapBundle.putString("language", language);
				        onmapBundle.putDouble("doubleCurrentLatitude", doubleCurrentLatitude);
				        onmapBundle.putDouble("doubleCurrentLongtitude", doubleCurrentLongtitude);
				        onmapBundle.putDouble("doublePlaceLatitude", doublelatitude);
				        onmapBundle.putDouble("doublePlaceLongtitude", doublelongtitude);
				        googleMapFragment.setArguments(onmapBundle);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.framemap, googleMapFragment);
						
						fragmentTransaction.commit();	
					}else{
						//RetryButtonFragment retryButtonFragment = new RetryButtonFragment();
						//fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.framebutton, retryButtonFragment);
						//fragmentTransaction.commit();	
					}
				}
				
				
			});
			
			
			
			
		
		
		
	}
	
	

}

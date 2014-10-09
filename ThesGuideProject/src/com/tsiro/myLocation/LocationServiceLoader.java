package com.tsiro.myLocation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocationServiceLoader extends Service{

	GPSTracker mylocLis;
	
	public LocationServiceLoader(){}
	
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}



	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}


	public int onStartCommand(Intent intent, int flags, int startId){
		//mylocLis = new 	GPSTracker();
		super.onStartCommand(intent, flags, startId);
		
		return START_STICKY;
	}
	
	
	/*
	 * public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}
	*/



	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}

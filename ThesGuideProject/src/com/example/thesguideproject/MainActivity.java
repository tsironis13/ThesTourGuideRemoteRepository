package com.example.thesguideproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.storage.InternalStorage;
import com.example.tasks.ServiceHandler;
import com.example.tasks.PlacesJsonWebApiTask;

import android.annotation.TargetApi;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class MainActivity extends ListActivity {
 
	private static String tag = "Main Activity";
 
    //GPSTracker class
    GPSTracker gps;
   
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> mouseiaList;
    TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			testDB.createDataBase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		testDB.openDataBase();
		
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled()){
			
			testDB.clearPlacesTableIfExists();
			PlacesJsonWebApiTask testwebtask = new PlacesJsonWebApiTask(MainActivity.this);
			testwebtask.execute();
			testDB.close();
		}
		
		mouseiaList = new ArrayList<HashMap<String, String>>();
			ListView lv = getListView();
        
        
			// Calling async task to get json
			//new GetMouseia().execute();

			/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
			}*/
        
        Button mapButton = (Button) findViewById(R.id.mapButton);
        Button actBarButton = (Button) findViewById(R.id.actBarButton);
        Button curLocButton = (Button) findViewById(R.id.curLocationButton);
        Button mainLayoutButton = (Button) findViewById(R.id.mainLayoutButton);
        Button fragmentTestButton = (Button) findViewById(R.id.fragmentTestButton);
        Button cursorAdapterButton = (Button) findViewById(R.id.cursorAdapterButton);
        Button internalStorageButton = (Button) findViewById(R.id.internalStorageButton);
        Button listFragmentButton = (Button) findViewById(R.id.listFragmentTest);
        
        mapButton.setOnClickListener(new View.OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent myIntent = new Intent(MainActivity.this, MapTestActivity.class);
        		startActivity(myIntent);
        		}
        	});
        
        
        actBarButton.setOnClickListener(new View.OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		Intent actBarIntent = new Intent(MainActivity.this, ActBarTest.class);
        		startActivity(actBarIntent);
        		}
        	});
        
        curLocButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// create class object
                gps = new GPSTracker(MainActivity.this);
 
                // check if GPS enabled    
                if(gps.canGetLocation()){
                     
                	double finallatitude = 40.6250548;
                    double finallonglatitude = 22.9529811;
                	
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    double apostasti = gps.getDistance(latitude, longitude, finallatitude, finallonglatitude);
                    
                    Toast.makeText(getApplicationContext(), "The distance is : " + apostasti/1000, Toast.LENGTH_SHORT).show();
                    
                    // \n is for new line
                   // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
                 
            }
			
		});
        
        
        mainLayoutButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent(MainActivity.this, MainLayoutActivity.class);
				startActivity(mainIntent);
			}
		});
        
        fragmentTestButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent fragmentIntent = new Intent(MainActivity.this, FragmentActivityTest.class);
				startActivity(fragmentIntent);
			}
		});
        
        cursorAdapterButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent cursorAdapter = new Intent(MainActivity.this, CursorAdapterExample.class);
				startActivity(cursorAdapter);
			}
		});
        
        
        listFragmentButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent cursorAdapter = new Intent(MainActivity.this, CursorAdapterExample.class);
				//startActivity(cursorAdapter);
				//PlacesListFragmentTest p = new PlacesListFragmentTest();
				Intent cursorAdapter = new Intent(MainActivity.this, PlacesListFragmentActivityTest.class);
				startActivity(cursorAdapter);
			}
		});
        
        internalStorageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent internalStorage = new Intent(MainActivity.this, InternalStorage.class);
				//startActivity(internalStorage);
			}
		});
}




    		@Override
    		public boolean onCreateOptionsMenu(Menu menu) {
    			return false;
    		}

    		@Override
    		public boolean onOptionsItemSelected(MenuItem item) {
    			// Handle action bar item clicks here. The action bar will
    			// automatically handle clicks on the Home/Up button, so long
    			// as you specify a parent activity in AndroidManifest.xml.
    			int id = item.getItemId();
    				if (id == R.id.action_settings) {
    					return true;
    				}
    				return super.onOptionsItemSelected(item);
    			}

		/**
		 * A placeholder fragment containing a simple view.
		 */
		public static class PlaceholderFragment extends Fragment {

				public PlaceholderFragment() {}

				@Override
				public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
						View rootView = inflater.inflate(R.layout.fragment_main, container,false);
						return rootView;
				}
		}
   
}
package com.example.thesguideproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.storage.InternalStorage;
import com.example.tasks.BitmapTask;
import com.example.tasks.PlacesJsonWebApiTask;

import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ListActivity {
 
	private static String tag = "Main Activity";
 
    //GPSTracker class
    GPSTracker gps;
    private Cursor allDisplayImageLinkcursor;
    private BitmapTask imgFetcher;
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> mouseiaList;
   // TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
    InternalStorage i = new InternalStorage();
    private ProgressDialog progressDialog; 
    int startCursorSize;
    private static int SPLASH_TIME_OUT = 0000;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		imgFetcher = new BitmapTask(this);
		
		//testDB.createDataBase();
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled()){
			//testDB.openDataBase();
			//allDisplayImageLinkcursor = testDB.getAllPhotoDisplayImageLink(); 	
			
			
			
			if (allDisplayImageLinkcursor.moveToFirst()){
				do{
					 String name = allDisplayImageLinkcursor.getString(allDisplayImageLinkcursor.getColumnIndex("_id"));
					 String url = allDisplayImageLinkcursor.getString(allDisplayImageLinkcursor.getColumnIndex("photo_link"));
					// Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
					 
					 if (url.equals("")){
						 //testDB.close(); 
						 //s.add(url);
					 }
					 else{
						 
					//	Bitmap b = imgFetcher.loadImage(this, url, getApplicationContext(), name);	 
					//	if (b != null){
				//			break;
				//		} 
			//			else{
				//			imgFetcher.loadImage(this, url, getApplicationContext(), name);
			//			}
					   
						
					     //testDB.close();		     
					 } 
				}while(allDisplayImageLinkcursor.moveToNext());
			}
		}
		else{
			
		}
		
		
		new LoadViewTask().execute();   
		
		
		mouseiaList = new ArrayList<HashMap<String, String>>();
	/*	    
        Button mapButton = (Button) findViewById(R.id.mapButton);
        Button actBarButton = (Button) findViewById(R.id.actBarButton);
        Button curLocButton = (Button) findViewById(R.id.curLocationButton);
        Button mainLayoutButton = (Button) findViewById(R.id.mainLayoutButton);
        Button fragmentTestButton = (Button) findViewById(R.id.fragmentTestButton);
        Button cursorAdapterButton = (Button) findViewById(R.id.cursorAdapterButton);
        Button internalStorageButton = (Button) findViewById(R.id.internalStorageButton);
        
        
        mapButton.setOnClickListener(new View.OnClickListener() {

        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		//Intent myIntent = new Intent(MainActivity.this, MapTestActivity.class);
        		//startActivity(myIntent);
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
			
			@SuppressWarnings("static-access")
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
        
        
      
        
        internalStorageButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent internalStorage = new Intent(MainActivity.this, InternalStorage.class);
				//startActivity(internalStorage);
			}
		});
		*/
}


    
    //To use the AsyncTask, it must be subclassed  
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
    {  
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute()  
        {  
            //Create a new progress dialog  
            progressDialog = new ProgressDialog(MainActivity.this);  
            //Set the progress dialog to display a horizontal progress bar  
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
            //Set the dialog title to 'Loading...'  
            progressDialog.setTitle("Loading...");  
            //Set the dialog message to 'Loading application View, please wait...'  
            progressDialog.setMessage("Loading application View, please wait...");  
            //This dialog can't be canceled by pressing the back key  
            progressDialog.setCancelable(false);  
            //This dialog isn't indeterminate  
            progressDialog.setIndeterminate(false);  
            //The maximum number of items is 100  
            progressDialog.setMax(100);  
            //Set the current progress to zero  
            progressDialog.setProgress(0);  
            //Display the progress dialog  
            progressDialog.show();  
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params)  
        {  
            /* This is just a code that delays the thread execution 4 times, 
             * during 850 milliseconds and updates the current progress. This 
             * is where the code that is going to be executed on a background 
             * thread must be placed. 
             */  
            try  
            {  
                //Get the current thread's token  
                synchronized (this)  
                {  
                    //Initialize an integer (that will act as a counter) to zero  
                    int counter = 0;  
                    //While the counter is smaller than four  
                    while(counter <= 4)  
                    {  
                        //Wait 850 milliseconds  
                        this.wait(850);  
                        //Increment the counter  
                        counter++;  
                        //Set the current progress.  
                        //This value is going to be passed to the onProgressUpdate() method.  
                        publishProgress(counter*25);  
                    }  
                }  
            }  
            catch (InterruptedException e)  
            {  
                e.printStackTrace();  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Integer... values)  
        {  
            //set the current progress of the progress dialog  
            progressDialog.setProgress(values[0]);  
        }  
  
        //after executing the code in the thread  
        @Override  
        protected void onPostExecute(Void result)  
        {  
            //close the progress dialog  
            progressDialog.dismiss();  
            //initialize the View  
           // setContentView(R.layout.activity_main); 
            
           
            new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when you
				 * want to show case your app logo / company
				 */

				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					Intent i = new Intent(MainActivity.this, PlacesListFragmentActivity.class);
					startActivity(i);

					// close this activity
					finish();
				}
			}, SPLASH_TIME_OUT);
            
            
          /*  Button listFragmentButton = (Button) findViewById(R.id.listFragmentTest);
            listFragmentButton.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				//Intent cursorAdapter = new Intent(MainActivity.this, CursorAdapterExample.class);
    				//startActivity(cursorAdapter);
    				//PlacesListFragmentTest p = new PlacesListFragmentTest();
    				//t.openDataBase();
    				//t.setSuggestionPressedField("false");
    			//	t.close();
    				
    				Intent cursorAdapter = new Intent(MainActivity.this, PlacesListFragmentActivity.class);
    				startActivity(cursorAdapter);
    			}
    		});*/
        }  
    } 


    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//testDB.close();
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
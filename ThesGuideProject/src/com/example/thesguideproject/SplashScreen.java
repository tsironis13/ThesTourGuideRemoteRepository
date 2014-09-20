package com.example.thesguideproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.storage.InternalStorage;
import com.example.tasks.BitmapTask;
import com.example.tasks.PlacesJsonWebApiTask;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class SplashScreen extends Activity{

    //A ProgressDialog object  
    private ProgressDialog progressDialog;  
    private TestLocalSqliteDatabase testDB;
    private static int SPLASH_TIME_OUT = 4000;
    private static int SPLASH_TIME_OUT2 = 2000;
    //TestLocalSqliteDatabase t;
    private static final String debugTag = "SplashScreen";
	
    ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	ArrayList<String> s = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper);
		
		String languagePhone = Locale.getDefault().getLanguage();
		Log.i("Language phone =>", languagePhone);
		
		testDB = new TestLocalSqliteDatabase(this);
		
		final Button englishButton = (Button) findViewById(R.id.englishButton);
		final Button greekButton = (Button) findViewById(R.id.greekButton);
		
		final Button text = (Button) findViewById(R.id.textButton);
			
		try {
			testDB.createDataBase();
			testDB.openDataBase(debugTag);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		/*if (isNetworkConnected()) {
			testDB.clearPlacesTableIfExists();
			PlacesJsonWebApiTask testwebtask = new PlacesJsonWebApiTask(SplashScreen.this);
			testwebtask.execute();
			
			new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when you
				 * want to show case your app logo / company
				 */

			/*	@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					greekButton.setVisibility(View.VISIBLE);
					englishButton.setVisibility(View.VISIBLE);
					
					greekButton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
							i.putExtra("language", "Greek");
							startActivity(i);

							// close this activity
							//finish();
						}
					});
					
					englishButton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
							i.putExtra("language", "English");
							Toast.makeText(getApplicationContext(), "English", Toast.LENGTH_SHORT).show();
							startActivity(i);
						}
					});
					
					//Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
					//startActivity(i);

					// close this activity
					//finish();
				}
			}, SPLASH_TIME_OUT);
		}*/
		if (isNetworkConnected()){
			testDB.clearPlacesTableIfExists();
			PlacesJsonWebApiTask testwebtask = new PlacesJsonWebApiTask(SplashScreen.this);
			testwebtask.execute();
			
			greekButton.setVisibility(View.VISIBLE);
			englishButton.setVisibility(View.VISIBLE);
			
			greekButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
					i.putExtra("language", "Greek");
					startActivity(i);
				}
			});
			
			englishButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
					i.putExtra("language", "English");
					Toast.makeText(getApplicationContext(), "English", Toast.LENGTH_SHORT).show();
					startActivity(i);
				}
			});
		}
		else{
			boolean flag = testDB.checkPlacesDataTable();
			if (flag == false){
				Toast.makeText(getApplicationContext(), "Please enable wifi widget to download app data!", Toast.LENGTH_SHORT).show();
					
				text.setVisibility(0);
				text.setText("Reopen App");
				text.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(SplashScreen.this, SplashScreen.class);
						startActivity(i);
						
						finish();
					}
				});
			}else{
			
			
			new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when you
				 * want to show case your app logo / company
				 */

				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					greekButton.setVisibility(View.VISIBLE);
					englishButton.setVisibility(View.VISIBLE);
							
					greekButton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
							i.putExtra("language", "Greek");
							Toast.makeText(getApplicationContext(), "Greek", Toast.LENGTH_SHORT).show();
							startActivity(i);

							// close this activity
							//finish();
						}
					});
					
					englishButton.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
							i.putExtra("language", "English");
							Toast.makeText(getApplicationContext(), "English", Toast.LENGTH_SHORT).show();
							startActivity(i);
						}
					});
					
					
				}
			}, SPLASH_TIME_OUT2);
		  }
		}
		
			int size = s.size();
			String si = Integer.toString(size);
    }
	
	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}
    
	@Override
   	protected void onDestroy() {
   		// TODO Auto-generated method stub
   		super.onDestroy();
   		testDB.close(debugTag);
   	}
    
	private class LoadViewTask extends AsyncTask<Void, Integer, Void>{

		@Override
		protected void onPreExecute() {
			  //Create a new progress dialog  
            progressDialog = new ProgressDialog(SplashScreen.this);  
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

		@Override
		protected Void doInBackground(Void... params) {
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
	                        this.wait(350);  
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

		@Override
		protected void onProgressUpdate(Integer... values) {
			 //set the current progress of the progress dialog  
            progressDialog.setProgress(values[0]);  		
        }
		
		@Override  
        protected void onPostExecute(Void result)  
        {  
            //close the progress dialog  
            progressDialog.dismiss();  
        }
	}
	
	
}

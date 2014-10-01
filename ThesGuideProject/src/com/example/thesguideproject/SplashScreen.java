package com.example.thesguideproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class SplashScreen extends ActionBarActivity{

    //A ProgressDialog object  
	private ActionBar mActionBar;
    private ProgressDialog progressDialog;  
    private TestLocalSqliteDatabase testDB;
    private static int SPLASH_TIME_OUT = 4000;
    private static int SPLASH_TIME_OUT2 = 2000;
    //TestLocalSqliteDatabase t;
    private static final String debugTag = "SplashScreen";
	private String languagePhone;
    
    ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper);
		
		mActionBar= getSupportActionBar();
		mActionBar.setBackgroundDrawable(null);
		
		languagePhone = Locale.getDefault().getLanguage();
		Log.i("Language phone =>", languagePhone);
		
		testDB = new TestLocalSqliteDatabase(this);
		
		final Button englishButton = (Button) findViewById(R.id.englishButton);
		final Button greekButton = (Button) findViewById(R.id.greekButton);
		englishButton.setBackgroundColor(Color.TRANSPARENT);
		greekButton.setBackgroundColor(Color.TRANSPARENT);
		
		
		final Button text = (Button) findViewById(R.id.textButton);
			
		try {
			testDB.createDataBase();
			testDB.openDataBase(debugTag);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	
		if (isNetworkConnected()){
			testDB.clearPlacesTableIfExists();
			PlacesJsonWebApiTask testwebtask = new PlacesJsonWebApiTask(SplashScreen.this, "splash", "null");
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
			   	
			 if (!languagePhone.equals("el")){	
				Toast.makeText(getApplicationContext(), "Please enable Wifi widget to download app data!", Toast.LENGTH_SHORT).show();	
				text.setVisibility(0);
				text.setText("Reopen App");
			 }
			 else{
				 Toast.makeText(getApplicationContext(), "�������� ������������ �� Wifi ��� �� ���������� �� �������� ��� ���������!", Toast.LENGTH_SHORT).show();	
				 text.setVisibility(0);
			     text.setText("�������� ���������");
			 }
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
  
}

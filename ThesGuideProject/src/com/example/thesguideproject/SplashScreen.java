package com.example.thesguideproject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;




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
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class SplashScreen extends Activity{

	private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;  
    private Button playButton;
    private Button pauseButton;
    private Button text;
    private Context mContext;
    //A ProgressDialog object  
    private ProgressDialog progressDialog;  
    private BitmapTask imgFetcher;
    private TestLocalSqliteDatabase testDB;
    private TestLocalSqliteDatabase testDB1;
    private Cursor allDisplayImageLinkcursor;
    private static int SPLASH_TIME_OUT = 4000;
    private static int SPLASH_TIME_OUT2 = 2000;
    //TestLocalSqliteDatabase t;
    private static final String debugTag = "SplashScreen";
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
	
    ProgressDialog barProgressDialog;
	Handler updateBarHandler;
	ArrayList<String> s = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewflipper);
		testDB = new TestLocalSqliteDatabase(this);
		//testDB1 = new TestLocalSqliteDatabase(this);
		//imgFetcher = new BitmapTask(this);
		//Initialize a LoadViewTask object and call the execute() method  
		text = (Button) findViewById(R.id.textButton);
		/*text = (Button) findViewById(R.id.textButton);
		text.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Click event works.", Toast.LENGTH_SHORT).show();
				Intent myIntent = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(myIntent);
			}
		});*/
		
		try {
			testDB.createDataBase();
			testDB.openDataBase(debugTag);
			
			//testDB1.openDataBase();
			//t.openDataBase();
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (isNetworkConnected()) {
			testDB.clearPlacesTableIfExists();
			PlacesJsonWebApiTask testwebtask = new PlacesJsonWebApiTask(SplashScreen.this);
			testwebtask.execute();
			
			new Handler().postDelayed(new Runnable() {

				/*
				 * Showing splash screen with a timer. This will be useful when you
				 * want to show case your app logo / company
				 */

				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
					startActivity(i);

					// close this activity
					finish();
				}
			}, SPLASH_TIME_OUT);
		}
		else{
			boolean flag = testDB.checkPlacesDataTable();
			if (flag == false){
				Toast.makeText(getApplicationContext(), "Please enable wifi widget to download app data!", Toast.LENGTH_SHORT).show();
				text.setVisibility(0);
				text.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i = new Intent(SplashScreen.this, SplashScreen.class);
						startActivity(i);
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
					Intent i = new Intent(SplashScreen.this, PlacesListFragmentActivity.class);
					startActivity(i);

					// close this activity
					finish();
				}
			}, SPLASH_TIME_OUT2);
		  }
		}
		
		
	
		
		
	
	int size = s.size();
	String si = Integer.toString(size);
	
		/*mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				//detector.onTouchEvent(event);
                return true;
			}
		});
        
        playButton = (Button) this.findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//sets auto flipping
                //mViewFlipper.setAutoStart(true);
                //mViewFlipper.setFlipInterval(2000);
                //mViewFlipper.startFlipping();
			}
		}); 
        
        pauseButton = (Button) this.findViewById(R.id.stop);
        pauseButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mViewFlipper.stopFlipping();
			}
		});     
*/
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}



	@Override
   	protected void onDestroy() {
   		// TODO Auto-generated method stub
   		super.onDestroy();
   		testDB.close(debugTag);
   	}
    
    
    
	class SwipeGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));                 
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                    mViewFlipper.showPrevious();
                    return true;
                }
 
            } catch (Exception e) {
                e.printStackTrace();
            }
 
            return false;
        }
    }

	
	
	
}

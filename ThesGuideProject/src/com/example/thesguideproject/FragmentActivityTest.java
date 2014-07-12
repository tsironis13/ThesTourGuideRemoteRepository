package com.example.thesguideproject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.os.Bundle;


public class FragmentActivityTest extends FragmentActivity{

	String tag = this.getClass().getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		Log.i(tag, "onCreate");
		setContentView(R.layout.fragment_main);
		
		
	}
	
	
	@Override
	protected void onDestroy() {
		Log.i(tag, "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.i(tag, "onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.i(tag, "onResume");
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.i(tag, "onStart");
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.i(tag, "onStop");
		super.onStop();
	}

	
	

	
	

}

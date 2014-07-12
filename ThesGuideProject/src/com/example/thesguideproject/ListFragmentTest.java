package com.example.thesguideproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragmentTest extends Fragment{

	String tag = this.getClass().getSimpleName();
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i(tag, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.i(tag, "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(tag, "onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i(tag, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		return view;
	}

	@Override
	public void onDestroy() {
		Log.i(tag, "onDestroy");
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		Log.i(tag, "onDestroyView");
		super.onDestroyView();
	}

	@Override
	public void onDetach() {
		Log.i(tag, "onDetach");
		super.onDetach();
	}

	@Override
	public void onPause() {
		Log.i(tag, "onPause");
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i(tag, "onResume");
		super.onResume();
	}

	@Override
	public void onStart() {
		Log.i(tag, "onStart");
		super.onStart();
	}

	@Override
	public void onStop() {
		Log.i(tag, "onStop");
		super.onStop();
	}

	
	
}

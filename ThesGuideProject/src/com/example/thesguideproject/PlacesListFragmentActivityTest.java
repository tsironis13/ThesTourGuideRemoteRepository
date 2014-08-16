package com.example.thesguideproject;

import com.example.fragmentClasses.MenuFragment;
import com.example.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PlacesListFragmentActivityTest extends ActionBarActivity{

	private ActionBar mActionBar;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeslistfragmentactivityfragment);
		
		mActionBar = getSupportActionBar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (savedInstanceState == null){
			getSupportFragmentManager().beginTransaction().add(R.id.containertest, new MenuFragment()).commit();
		}
	}

	
 /*public static class TestListFragment extends Fragment{
	 
	 public TestListFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.splash_screen_fragment, container, false);
		//Button b = (Button) rootView.findViewById(R.id.testbut);
		return rootView;
	}
	 
	 
	 
 }*/
	
	
}

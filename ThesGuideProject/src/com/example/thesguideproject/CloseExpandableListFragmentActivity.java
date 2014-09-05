package com.example.thesguideproject;

import com.example.fragmentClasses.CloseExpandableListFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class CloseExpandableListFragmentActivity extends ActionBarActivity{

	private CloseExpandableListFragment closeExpListFragment;
	private FragmentTransaction fragmentTransaction;
	private String language;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.closeexplistfragmentactivity);
		
		Bundle extras = getIntent().getExtras();
		language = extras.getString("language");
		
		closeExpListFragment = new CloseExpandableListFragment();
		Bundle langbundle = new Bundle();
		langbundle.putString("language", language);
		if (savedInstanceState == null){
			closeExpListFragment.setArguments(langbundle);
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.close, closeExpListFragment);
			fragmentTransaction.commit();
		}
		
	}
	
	

}

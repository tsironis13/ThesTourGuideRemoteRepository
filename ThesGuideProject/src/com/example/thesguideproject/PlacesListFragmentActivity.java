package com.example.thesguideproject;

import java.util.Stack;

import com.example.fragmentClasses.DisplayImageFragment;
import com.example.fragmentClasses.ListPlacesFragment;
import com.example.fragmentClasses.MenuFragment;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.ECLAIR) 
public class PlacesListFragmentActivity extends ActionBarActivity{

	private ActionBar mActionBar;
	private Stack<Fragment> fragmentStack;
	private MenuFragment menuFragment;
	private Fragment fragment;
	private Fragment fragment2;
	private ListPlacesFragment listPlacesFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	TextView hiddentv;
	
	
	public PlacesListFragmentActivity(){}
	
	TestLocalSqliteDatabase t = new TestLocalSqliteDatabase(this);
	
	
	
	MenuFragment m = new MenuFragment();
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeslistfragmentactivityfragment);
	
		
		menuFragment = new MenuFragment();
		
		fragmentStack = new Stack<Fragment>();
		
		mActionBar = getSupportActionBar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		hiddentv = (TextView) findViewById(R.id.hiddentv);
		
		
		if (savedInstanceState == null){
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.containermenu, menuFragment);
			t.close();
			fragmentTransaction.commit();
		}
	
	}

	
	int y;
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			
		int y = t.getAuxiliaryVariableI();
	
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK && y != 0){
			
			t.insertValueForIAuxiliaryVariable(0);
			t.close(); 
			String s = Integer.toString(i);
			Toast.makeText(getApplication(), s+ "pressed!", Toast.LENGTH_SHORT).show();
			Log.d(this.getClass().getName(), "back button pressed");
			hiddentv.setText("you are in!");
			fragment2 = new MenuFragment();
			FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction().replace(R.id.containermenu, fragment2);
			ft2.addToBackStack(null);
			ft2.commit();
		}
		else {
			fragment2 = new MenuFragment();
			FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction().replace(R.id.containermenu, fragment2);
			ft2.commit();
			Log.d(this.getClass().getName(), "NO back button pressed");
		}
		return super.onKeyDown(keyCode, event);
		
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

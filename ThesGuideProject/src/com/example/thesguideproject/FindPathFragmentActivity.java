package com.example.thesguideproject;

import java.util.ArrayList;

import com.example.adapters.DisarableLocationCursorAdapter;
import com.example.fragmentClasses.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.example.fragmentClasses.SettingsMapFragment;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.google.android.gms.maps.GoogleMap;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class FindPathFragmentActivity extends ActionBarActivity implements OnGoogleMapFragmentListener{

	private SettingsMapFragment settingsMapFragment;
	private FragmentTransaction fragmentTransaction;
	private GoogleMap mUIGoogleMap;
	private String language;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpathfragmentactivity);	
		
		Bundle extras = getIntent().getExtras();
		language = extras.getString("language");
		
		settingsMapFragment = new SettingsMapFragment();
		Bundle langbundle = new Bundle();
		langbundle.putString("language", language);
		if (savedInstanceState == null){
			settingsMapFragment.setArguments(langbundle);
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.containersettings, settingsMapFragment);
			fragmentTransaction.commit();
		}
	}

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		mUIGoogleMap = map;
	}
	
	//@Override
	///protected void onListItemClick(ListView l, View v, int position, long id) {
		//super.onListItemClick(l, v, position, id);
		//String item = (String) getListAdapter().getItem(position);
		//startingpointtv.setText(item);
		//setListAdapter(null);
		//categoryrb.setChecked(false);
	//}

	
	
}

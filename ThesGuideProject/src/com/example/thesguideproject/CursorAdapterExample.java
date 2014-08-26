package com.example.thesguideproject;

import java.io.IOException;

import com.example.adapters.PLacesDataListCursorAdapter;
import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.tasks.BitmapTask;

import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;

public class CursorAdapterExample extends ActionBarActivity implements OnItemSelectedListener{

	private Spinner nightLifeSpinner;
	private Button museumsButton;
	private Button sightseeings;
	private Button churchesButton;
	private ListView listExample;
	private BitmapTask imgFetcher;
	private LayoutInflater layoutInflator;
	private Cursor specificPlacecursor;
	private String[] columns;
	private int[] to;
	private String genre;
	private double current_latitude;
	private double current_longtitude;
	private String button_pressed;
	private ActionBar mActionBar;
	private SearchView searchView;
	//TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
	
	//GPSTracker class
	private GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		mActionBar = getSupportActionBar();
		//setContentView(R.layout.list_example);
		
		//this.listExample = (ListView) findViewById(R.id.list_exam);
		this.nightLifeSpinner = (Spinner) findViewById(R.id.nightlifespinner);
		this.museumsButton = (Button) findViewById(R.id.museumsbutton);
		this.sightseeings = (Button) findViewById(R.id.sightseeingsbutton);
		this.churchesButton = (Button) findViewById(R.id.churchesbutton);
		this.listExample = (ListView) findViewById(R.id.listview1);
		this.layoutInflator = LayoutInflater.from(this);
		this.imgFetcher = new BitmapTask(this);
		
		gps = new GPSTracker(CursorAdapterExample.this);
		
		if (gps.canGetLocation()){
			 current_latitude = gps.getLatitude();
             current_longtitude = gps.getLongitude();
		}
		else
		{
            gps.showSettingsAlert();
        }
		
		// create the adapter using the cursor pointing to the desired data as well as the layout information
		//@SuppressWarnings("deprecation")
		//SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to);
		//this.listExample.setAdapter(new TestDataListCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to));
		//this.listExample.setAdapter(new TestDataListCursorAdapter(this, R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher));
		//this.setListAdapter(mAdapter);
		this.nightLifeSpinner.setOnItemSelectedListener(this);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_dropdown_item);
		this.nightLifeSpinner.setAdapter(adapter);
		
		museumsButton.setOnClickListener(new View.OnClickListener() {
			
			//private ListView listExample = (ListView) findViewById(R.id.listview1);
			@Override
			public void onClick(View v) {
				button_pressed = "museums";
				HelperMethodDependingOnButtonClick("museums");
				setAdapterFromSpecificCursor(button_pressed, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
				//testDB.close();
			}
		});
		
		sightseeings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				button_pressed = "sightseeings";
				HelperMethodDependingOnButtonClick("sightseeings");
				setAdapterFromSpecificCursor(button_pressed, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
			//	testDB.close();
			}
		});
		
		churchesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				button_pressed = "church";
				registerForContextMenu(v); 
				openContextMenu(v);
				//HelperMethodDependingOnButtonClick("church");
				//setAdapterFromSpecificCursor(button_pressed, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
				//testDB.close();
			}
		});
		
	}
	
	String palcChr = "PaleoChristian";
	String bizan = "Byzantine";
	String basiliki = "Basiliki";
    String macedon = "Macedonian";
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		 case R.id.paleochristianikes:
			 HelperMethodDependingOnChurchMenuItemButtonClick(palcChr);
			 setAdapterFromSpecificCursor(palcChr, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		    // testDB.close();
			 break;
		 case R.id.bizantines:
			 HelperMethodDependingOnChurchMenuItemButtonClick(bizan);
			 setAdapterFromSpecificCursor(bizan, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		   //  testDB.close();
			 break;
		 case R.id.basiliki:
			 HelperMethodDependingOnChurchMenuItemButtonClick(basiliki);
			 setAdapterFromSpecificCursor(basiliki, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		  //   testDB.close();
			 break;
		 case R.id.macedonian:
			 HelperMethodDependingOnChurchMenuItemButtonClick(macedon);
			 setAdapterFromSpecificCursor(macedon, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		  //   testDB.close();
			 break;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.categ_churchmenu, menu);
	}
	
/*	public void checkablemenufired(View view){
		PopupMenu menu = new PopupMenu(this, view);
		menu.getMenuInflater().inflate(R.menu.categ_churchmenu, menu.getMenu());
		menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				return false;
			}
		});
		menu.show();
		
	}*/
	public void HelperMethodDependingOnChurchMenuItemButtonClick(String subcategory){
		//testDB.openDataBase();
		//specificPlacecursor = testDB.getSpecificChurchData(subcategory);
		
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info"};
		
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
	} 
	
	
	public void HelperMethodDependingOnButtonClick(String genre){
		//testDB.openDataBase();
		//specificPlacecursor = testDB.getSpecificPlaceData(genre);
		
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info"};
		
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
	}
	
	
	

	
	private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, BitmapTask imgFetcher, double current_latitude, double current_longtitude){
		//this.listExample.setAdapter(new PLacesDataListCursorAdapter(button_pressed, this, this,  R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher, current_latitude, current_longtitude) );
	}

	
	//Restore any already fetched data on orientation change
	/*@SuppressWarnings("deprecation")
	final Object[] data = (Object[]) getLastNonConfigurationInstance();
	if (data != null){
		this.listExample.setAdapter(new TestDataListCursorAdapter(button_pressed, this, this,  R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher, current_latitude, current_longtitude) );
	}*/
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	public boolean onCreateOptionsMenu(Menu menu){
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
		
		//Find the search item
		MenuItem searchItem = menu.findItem(R.id.action_search);
		
		//Retrieve the SearchView
		searchView  = (SearchView) MenuItemCompat.getActionView(searchItem);		
		
		return super.onCreateOptionsMenu(menu);
	}
	

	
}

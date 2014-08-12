package com.example.thesguideproject;

import java.io.IOException;

import com.example.adapters.PLacesDataListCursorAdapter;
import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.tasks.BitmapTask;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class CursorAdapterExample extends Activity implements OnItemSelectedListener{

	private Spinner nightLifeSpinner;
	private Button museumsButton;
	private Button sightseeings;
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
	TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
	
	//GPSTracker class
	GPSTracker gps;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		//setContentView(R.layout.list_example);
		
		//this.listExample = (ListView) findViewById(R.id.list_exam);
		this.nightLifeSpinner = (Spinner) findViewById(R.id.nightlifespinner);
		this.museumsButton = (Button) findViewById(R.id.museumsbutton);
		this.sightseeings = (Button) findViewById(R.id.sightseeingsbutton);
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
		
		try {
			testDB.createDataBase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
				testDB.close();
			}
		});
		
		sightseeings.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				button_pressed = "sightseeings";
				HelperMethodDependingOnButtonClick("sightseeings");
				setAdapterFromSpecificCursor(button_pressed, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
				testDB.close();
			}
		});
		
	}
	
	
	public void HelperMethodDependingOnButtonClick(String genre){
		testDB.openDataBase();
		specificPlacecursor = testDB.getSpecificPlaceData(genre);
		
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info"};
		
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
	}
	
	
	private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, BitmapTask imgFetcher, double current_latitude, double current_longtitude){
		this.listExample.setAdapter(new PLacesDataListCursorAdapter(button_pressed, this, this,  R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher, current_latitude, current_longtitude) );
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

	

	
}

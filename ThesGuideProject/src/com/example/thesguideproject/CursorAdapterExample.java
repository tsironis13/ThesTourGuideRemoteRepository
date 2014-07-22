package com.example.thesguideproject;

import java.io.IOException;

import com.example.adapters.TestDataListCursorAdapter;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.tasks.BitmapTask;
import com.example.tasks.ImageTask;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class CursorAdapterExample extends Activity{

	private Button museumsButton;
	private ListView listExample;
	private BitmapTask imgFetcher;
	private LayoutInflater layoutInflator;
	private Cursor cursor;
	private String[] columns;
	private int[] to;
	TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		//setContentView(R.layout.list_example);
		
		//this.listExample = (ListView) findViewById(R.id.list_exam);
		this.museumsButton = (Button) findViewById(R.id.museumsbutton);
		this.listExample = (ListView) findViewById(R.id.listview1);
		this.layoutInflator = LayoutInflater.from(this);
		this.imgFetcher = new BitmapTask(this);
		
		try {
			testDB.createDataBase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String genre = "museums";
		
		testDB.openDataBase();
		cursor = testDB.getAllTestData(genre);
		
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link"};
		
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.nameEl, R.id.locationImage};
		
		// create the adapter using the cursor pointing to the desired data as well as the layout information
		//@SuppressWarnings("deprecation")
		//SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to);
		//this.listExample.setAdapter(new TestDataListCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to));
		//this.listExample.setAdapter(new TestDataListCursorAdapter(this, R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher));
		//this.setListAdapter(mAdapter);
		
		
		museumsButton.setOnClickListener(new View.OnClickListener() {
			
			//private ListView listExample = (ListView) findViewById(R.id.listview1);
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setAdapterFromSpecificCursor(listExample, cursor, columns, to, imgFetcher);
				testDB.close();
			}
		});
		
	}
	
	
	private void setAdapterFromSpecificCursor(ListView listExample, Cursor cursor, String[] columns, int[] to, BitmapTask imgFetcher){
		this.listExample.setAdapter(new TestDataListCursorAdapter(this,  R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher) );
	}

	
	
	
}

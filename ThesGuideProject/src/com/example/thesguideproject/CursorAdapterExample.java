package com.example.thesguideproject;

import java.io.IOException;

import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.tasks.BitmapTask;
import com.example.tasks.ImageTask;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.widget.ListView;

public class CursorAdapterExample extends Activity{

	private ListView listExample;
	private BitmapTask imgFetcher;
	private LayoutInflater layoutInflator;
	TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_example);
		
		this.listExample = (ListView) findViewById(R.id.list_exam);
		this.layoutInflator = LayoutInflater.from(this);
		this.imgFetcher = new BitmapTask(this);
		
		try {
			testDB.createDataBase();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String type = "male";
		
		testDB.openDataBase();
		Cursor cursor = testDB.getAllTestData(type);
		
		// the desired columns to be bound
		String[] columns = new String[] {"_id", "surname", "image_link"};
		
		// the XML defined views which the data will be bound to
		int[] to = new int[] {R.id.locationName, R.id.nameEl, R.id.locationImage};
		
		// create the adapter using the cursor pointing to the desired data as well as the layout information
		//@SuppressWarnings("deprecation")
		//SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to);
		//this.listExample.setAdapter(new TestDataListCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to));
		this.listExample.setAdapter(new TestDataListCursorAdapter(this, R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher));
		//this.setListAdapter(mAdapter);
		
		
		testDB.close();
	}

	
	
	
}

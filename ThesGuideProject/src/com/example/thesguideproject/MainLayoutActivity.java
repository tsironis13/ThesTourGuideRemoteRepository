package com.example.thesguideproject;

import java.util.ArrayList;

import com.example.adapters.LocationsDataAdapter;
import com.example.locationData.LocationData;
import com.example.sqlHelper.DatabaseHolder;
import com.example.tasks.ImageTask;
import com.example.tasks.JsonWebAPITask;

import android.app.Activity;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainLayoutActivity extends Activity implements OnItemSelectedListener {

	private static final String MY_DEBUG_TAG = null;
	private Spinner spinner1;
	private ListView locationsList;
	private Button museumsButton;
	private Button sightseeingsButton;
	private Button clearDataButton;
	private ImageTask imgFetcher;
	private LayoutInflater layoutInflator;
	private ArrayList<LocationData> locations;
	
	public String genre_type; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		//MainLayoutActivity listview = (MainLayoutActivity) findViewById(R.id.listview);
		
        
        /**
         * CRUD Operations
         * */
		/*
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
        db.addLocation(new LocationData(1, "mouseio", "assaa", "sdssd", "sdssdds", "dssd"));       
        db.addLocation(new LocationData(2, "mouseio", "assaa", "sdssd", "sdssdds", "dssd")); 
        db.addLocation(new LocationData(3, "aksiotheato", "assaa", "sdssd", "sdssdds", "dssd")); 
        db.addLocation(new LocationData(4, "mouseio", "assaa", "sdssd", "sdssdds", "dssd")); 
         
        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        ArrayList<LocationData> locations = db.getAllLocations();      
         
        for (LocationData ld : locations) {
            String log = "Id: "+ld.getId()+" ,Name: " + ld.getNameEl() + " ,Genre: " + ld.getGenre();
                // Writing Contacts to log
        Log.d("Name: ", log);
    }*/
		
		
		// Spinner element
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		
		// Spinner click listener
		spinner1.setOnItemSelectedListener(this);
		
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_dropdown_item);
        
        spinner1.setAdapter(adapter); 
       
        
        this.locationsList = (ListView) findViewById(R.id.listview1);
        this.museumsButton = (Button) findViewById(R.id.museumsbutton);
        this.sightseeingsButton = (Button) findViewById(R.id.sightseeingsbutton);
        this.clearDataButton = (Button) findViewById(R.id.clearDataDBbutton);
        this.imgFetcher = new ImageTask(this);
        this.layoutInflator = LayoutInflater.from(this);
        
        final DatabaseHolder db = new DatabaseHolder(this);
        
        clearDataButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db.clearTableIfExists();
			}
		});
        
        
        museumsButton.setOnClickListener(new View.OnClickListener() {
        	@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
        		 db.clearTableIfExists();
        		 genre_type = "museums";
				 JsonWebAPITask webtask = new JsonWebAPITask(MainLayoutActivity.this, genre_type);
				 webtask.execute();
				//Toast.makeText(getApplicationContext(), "is clicked", Toast.LENGTH_SHORT).show();
				
				//db.getLocationsByGenre("museums");
			
			}
		});
        
        sightseeingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				db.clearTableIfExists();
       		    genre_type = "sightseeings";
				JsonWebAPITask webtask = new JsonWebAPITask(MainLayoutActivity.this, genre_type);
				webtask.execute();
			}
		});
		
      /*  
     // Restore any already fetched data on orientation change. 
        final Object[] data = (Object[]) getLastNonConfigurationInstance();
        if(data != null) {
        	this.locations = (ArrayList<LocationData>) data[0];
        	this.imgFetcher = (ImageTask)data[1];
         	//locationsList.setAdapter(new LocationsDataAdapter(this,this.imgFetcher,this.layoutInflator, this.locations));
        }*/
        
	}
	
	
	public ArrayList<LocationData> getResults(String genre){
		DatabaseHolder db = new DatabaseHolder(this);
		
		
		
		ArrayList<LocationData> resultList = new ArrayList<LocationData>();
		
		Cursor c = db.getLocationsByGenre(genre);
		//Cursor c = (Cursor) db.getAllLocations();
		int count = c.getCount();
		String s = Integer.toString(count);
		Log.d("Cursor row count: ", s);
		
		while(c.moveToNext()){
			
			String name_el = c.getString(c.getColumnIndex("name_el"));
			String genre1 = c.getString(c.getColumnIndex("genre"));
			String photo_link = c.getString(c.getColumnIndex("photo_link"));
			String latitude = c.getString(c.getColumnIndex("latitude"));
			String longtitude = c.getString(c.getColumnIndex("longtitude"));
			
			try{
				resultList.add(new LocationData(genre1, photo_link, name_el, latitude, longtitude));
			}
			catch(Exception e){
				Log.e(MY_DEBUG_TAG, "Error " + e.toString());
			}
		}
		
		c.close();

	    db.close();
	    return resultList;
	}
	
	
	
	
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		// TODO Auto-generated method stub
		// On selecting a spinner item
       /* String item = parent.getItemAtPosition(pos).toString();
        
        Spinner spinner2 = new Spinner(this);
        
        LinearLayout ln = (LinearLayout) this.findViewById(R.id.linearlayout1);
        
        if(item.equals("Category 1")){
        	//ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, new String[] { "Apple", "Peach", "Banana" });
        	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.subcategory_array, android.R.layout.simple_spinner_dropdown_item);  
        	spinner2.setAdapter(adapter);
        	
        	ln.addView(spinner2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
        	        LinearLayout.LayoutParams.WRAP_CONTENT));
        	
        }*/
        
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static class MyViewHolder{
	    	public TextView genre, nameEl, latitude, longtitude;
	    	//public RelativeLayout relLay;
	    	//public Button trackButton;
	    	public Button detailsButton;
	    	public ImageView icon;
	    	public LocationData locations;
	} 
	
	public void setTracks(ArrayList<LocationData> locData) {
		// TODO Auto-generated method stub
		this.locations = locData;
		this.locationsList.setAdapter(new LocationsDataAdapter(this, this.imgFetcher, this.layoutInflator, this.locations));
	}
	
	

	
	
}

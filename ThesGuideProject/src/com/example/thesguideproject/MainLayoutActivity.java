package com.example.thesguideproject;

import java.util.ArrayList;

import com.example.adapters.LocationsDataAdapter;
import com.example.locationData.LocationData;
import com.example.tasks.ImageTask;
import com.example.tasks.JsonWebAPITask;

import android.app.Activity;
import android.content.ContextWrapper;
import android.os.Bundle;
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

	private Spinner spinner1;
	private ListView locationsList;
	private Button museumsButton;
	private Button sightseeingsButton;
	private ImageTask imgFetcher;
	private LayoutInflater layoutInflator;
	private ArrayList<LocationData> locations;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		//MainLayoutActivity listview = (MainLayoutActivity) findViewById(R.id.listview);
		
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
        this.imgFetcher = new ImageTask(this);
        this.layoutInflator = LayoutInflater.from(this);
        
        
        museumsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 JsonWebAPITask webtask = new JsonWebAPITask(MainLayoutActivity.this);
				 webtask.execute();
				//Toast.makeText(getApplicationContext(), "is clicked", Toast.LENGTH_SHORT).show();
			}
		});
        
        sightseeingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				JsonWebAPITask webtask = new JsonWebAPITask(MainLayoutActivity.this);
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

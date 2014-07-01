package com.example.tasks;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.locationData.LocationData;
import com.example.sqlHelper.DatabaseHolder;
import com.example.thesguideproject.ActBarTest;
import com.example.thesguideproject.MainActivity;
import com.example.thesguideproject.MainLayoutActivity;
import com.example.thesguideproject.MapTestActivity;
import com.example.thesguideproject.R;

/**
* Async task class to get json by making HTTP call
* */
public class JsonWebAPITask extends AsyncTask<Void, Integer, String> {
 
	 //URL to get contacts JSON
    private static String url = "http://aetos.it.teithe.gr/~tsironis/json.php";
    
    private ProgressDialog pDialog;
    private MainLayoutActivity activity;
    private Context context;
    private static final String debugTag = "JsonWebAPITask";
    
    ServiceHandler sh = new ServiceHandler();
    
    ArrayList<LocationData> locData = new ArrayList<LocationData>(); 
    
    //JSON Node names
    private static final String TAG_LOCATIONS = "locations";
    private static final String TAG_GENRE = "museums";
    private static final String TAG_ID = "id";
    private static final String TAG_PHOTO_LINK = "photo_link";
    private static final String TAG_NAME_EL = "name_el";
    
    public JsonWebAPITask(){}
       
    public JsonWebAPITask(MainLayoutActivity activity){
    	super();
    	this.activity = activity;
    	this.context = this.activity.getApplicationContext();
    }
    
    
    // contacts JSONArray
    JSONArray locations = null;
	
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(this.activity);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected String doInBackground(Void... params) {
            // Creating service handler class instance
            try{
            	Log.d(debugTag, "Background: " + Thread.currentThread().getName());
            	String result = sh.makeServiceCall(url, ServiceHandler.GET);
            	return result;
            	
            }catch(Exception e){
            	return new String();
            }
        	
        }	
        	/*
        	ServiceHandler sh = new ServiceHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
            Log.d("Response: ", "> " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                     
                    // Getting JSON Array node
                    museum = jsonObj.getJSONArray(TAG_MOUSEIA);
 
                    // looping through All Contacts
                    for (int i = 0; i < museum.length(); i++) {
                        JSONObject c = museum.getJSONObject(i);
                         
                        String id = c.getString(TAG_ID);
                        String link = c.getString(TAG_LINK);
                        String phone = c.getString(TAG_PHONE);
                        //String address = c.getString(TAG_ADDRESS);
                        //String gender = c.getString(TAG_GENDER);
 
                        // Phone node is JSON Object
                        //JSONObject phone = c.getJSONObject(TAG_PHONE);
                       // String mobile = phone.getString(TAG_PHONE_MOBILE);
                        //String home = phone.getString(TAG_PHONE_HOME);
                        //String office = phone.getString(TAG_PHONE_OFFICE);
 
                        // tmp hashmap for single contact
                        HashMap<String, String> mouseio = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        mouseio.put(TAG_ID, id);
                        mouseio.put(TAG_LINK, link);
                        mouseio.put(TAG_PHONE, phone);
                        //contact.put(TAG_PHONE_MOBILE, mobile);
 
                        // adding contact to contact list
                        mouseiaList.add(mouseio);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;*/
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
           
           // ArrayList<LocationData> locData = new ArrayList<LocationData>(); 
             
            if(result.length() == 0){
    			AlertDialog.Builder builder = new AlertDialog.Builder(context);
    			builder.setMessage("Unable to find location data. Try again later");
    			
    			AlertDialog alert = builder.create();
                alert.show();
                
                return;
    		}
    		else {
    			Toast.makeText(this.activity.getApplicationContext(), "result is full", Toast.LENGTH_SHORT).show();
    		}
            
            
            try {
				JSONObject respObj = new JSONObject(result);
				
				locations = respObj.getJSONArray(TAG_LOCATIONS);
				
				for(int i=0; i<locations.length(); i++){
					JSONObject c = locations.getJSONObject(i);
					
					String id = c.getString("id");
					int integer_id = Integer.parseInt(id);
					String genre = c.getString("genre");
					String photo_link = c.getString("photo_link");
					String name_el = c.getString("name_el");
					String latitude = c.getString("latitude");
					String longtitude = c.getString("longtitude");
					
					locData.add(new LocationData(integer_id, genre, photo_link, name_el, latitude, longtitude));
					
					
					//LocationData l = locData.get(0);
					//String lat = l.getLatitude();
					//Log.d(debugTag, "Latitude is: " + lat);
					
				}
				
				
				DatabaseHolder dbholder = new DatabaseHolder(context);
				
				Log.d("Insert: ", "Inserting .."); 
				//dbholder.addLocation(new LocationData(integer_id, genre, photo_link, name_el, latitude, longtitude));
				  for (LocationData ld : locData){
			        	dbholder.addLocation(ld);
			      }
				
				
				 // Reading all contacts
		        Log.d("Reading: ", "Reading all contacts..");
		        ArrayList<LocationData> locations = dbholder.getAllLocations();      
		         
		      
		        
		        for (LocationData ld : locData) {
		            String log = "Id: "+ld.getId()+" ,Name: " + ld.getNameEl() + " ,Genre: " + ld.getGenre() + " ,Longtitude: " + ld.getLongtitude()
		            		+ " ,Latitude: " + ld.getLatitude();
		                // Writing Contacts to log
		        Log.d("Name: ", log);
		        }
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
          
            //MapTestActivity m = new MapTestActivity();
            //m.setList(locData);            
            /**
             * Updating parsed JSON data into ListView
             * */
            
            /*ListAdapter adapter = new SimpleAdapter(MainActivity.this, mouseiaList,
                    R.layout.list_item, new String[] { TAG_ID, TAG_LINK, TAG_PHONE }, new int[]
                     { R.id.kodikos, R.id.link, R.id.number });
 
            setListAdapter(adapter); */
            this.activity.setTracks(locData);
            
            
            
            //this.activity.getLocationDataFromDatabase(locData);
            
            
         
        }
 
        
    } 



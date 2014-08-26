package com.example.tasks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.locationData.LocationData;
import com.example.locationData.PlacesData;
import com.example.sqlHelper.DatabaseHolder;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.MainActivity;
import com.example.thesguideproject.MainLayoutActivity;
import com.example.thesguideproject.SplashScreen;

public class PlacesJsonWebApiTask extends AsyncTask<Void, Integer, String> {

	 private static String url = "http://aetos.it.teithe.gr/~tsironis/places_file.php";
	 private ProgressDialog pDialog;
	 //private MainLayoutActivity activity;
	 private MainActivity activity;
	 private SplashScreen s;
	 private Context context;
	 private static final String debugTag = "PlacesJsonWebApiTask";
	 public String encodedUrl;
	 
	 ServiceHandler sh = new ServiceHandler();
	 
	 ArrayList<PlacesData> placesDataArray = new ArrayList<PlacesData>(); 
	 
	 
	 private static final String TAG_PLACES = "places";
	 private static final String TAG_ID = "id";
	 private static final String TAG_NAME_EL = "name_el";
	 private static final String TAG_NAME_EN = "name_en";
	 private static final String TAG_LINK = "link";
	 private static final String TAG_LATITUDE = "latitude";
	 private static final String TAG_LONGTITUDE = "longtitude";
	 private static final String TAG_TEL = "tel";
	 private static final String TAG_EMAIL = "email";
	 private static final String TAG_FB_LINK = "facebook_link";
	 private static final String TAG_PHOTO_LINK = "photo_link";
	 private static final String TAG_GENRE = "genre";
	 private static final String TAG_SUBCATEGORY = "subcategory";
	 
	 public PlacesJsonWebApiTask(){}
     
	 public PlacesJsonWebApiTask(MainActivity activity){
	    	super();
	    	this.activity = activity;
	    	this.context = this.activity.getApplicationContext();
	 }
	 
	 public PlacesJsonWebApiTask(SplashScreen activity){
	    	super();
	    	this.s = activity;
	    	this.context = this.s.getApplicationContext();
	 } 
	
	 // contacts JSONArray
	    JSONArray persons = null;
		
	  @Override
	    protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            pDialog = new ProgressDialog(this.s);
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
	  
	  
	  public ArrayList<PlacesData> tD;
	  
	  
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
  			Toast.makeText(this.s.getApplicationContext(), "result is full", Toast.LENGTH_SHORT).show();
  		}
          
          
        try{
        	
        	JSONObject respObj = new JSONObject(result);
        	
        	persons = respObj.getJSONArray(TAG_PLACES);
        	
        	for(int i=0; i<persons.length(); i++){
        		JSONObject c = persons.getJSONObject(i);
        		
        		String id = c.getString(TAG_ID);
        		int integer_id = Integer.parseInt(id);
        		String name_el = c.getString(TAG_NAME_EL);
        		String name_en = c.getString(TAG_NAME_EN);
        		String link = c.getString(TAG_LINK);
        		String latitude = c.getString(TAG_LATITUDE);
        		double double_latitude = Double.parseDouble(latitude);
        		String longtitude = c.getString(TAG_LONGTITUDE);
        		double double_longtitude = Double.parseDouble(longtitude);
        		String photo_link = c.getString(TAG_PHOTO_LINK);
        		String genre = c.getString(TAG_GENRE);
        		String tel = c.getString(TAG_TEL);
        		String email = c.getString(TAG_EMAIL);
        		String fb_link = c.getString(TAG_FB_LINK);
        		String subcategory = c.getString(TAG_SUBCATEGORY);
        		
        		String info = null;
        		String exhibition = null;
        		String menu = null;
        		String link1 = null;
        		String link2 = null;
        		String link3 = null;
        		String link4 = null;
        		String link5 = null;
        		JSONArray desc_array = c.getJSONArray("desc");
        			for (int j=0; j<desc_array.length(); j++){
        				JSONObject descObject = desc_array.getJSONObject(j);
        				info = descObject.getString("info");
        				exhibition = descObject.getString("exhibition");
        				menu = descObject.getString("menu");
        				link1 = descObject.getString("photo_link1");
        				link2 = descObject.getString("photo_link2");
        				link3 = descObject.getString("photo_link3");
        				link4 = descObject.getString("photo_link4");
        				link5 = descObject.getString("photo_link5");
        			}
        		
        		try {
        			encodedUrl = url +"/" + URLEncoder.encode(url, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		placesDataArray.add(new PlacesData(integer_id, name_el, name_en, link, double_latitude, double_longtitude, 
        				photo_link, genre, info, exhibition, menu, link1, link2, link3, link4, link5, subcategory, tel, email, fb_link));
        	}
        	
        	
        
        	
        	TestLocalSqliteDatabase dbtest = new TestLocalSqliteDatabase(context);
			dbtest.openDataBase(debugTag);
			//Log.d("Insert: ", "Inserting .."); 
			//dbholder.addLocation(new LocationData(integer_id, genre, photo_link, name_el, latitude, longtitude));
			  for (PlacesData td : placesDataArray){
		        	//dbtest.addTestData(td);
		      }
			  Log.d("Reading: ", "Reading all places..");
		        
		     /*   for (PlacesData td : placesDataArray) {
		            String log = "Id: "+td.getId()+" ,NameEl: " + td.getNameEl()+" ,NameEn: " + td.getNameEn()+" ,Link: " + td.getLink() + 
		            " ,Latitude: " + td.getLatitude() + " ,Longtitude: " + td.getLongtitude() + " ,PhotoLink: " + td.getPhotoLink() + " ,Genre: " + td.getGenre();
		                // Writing Contacts to log
		        Log.d("Name: ", log);
		        }*/
			  
			dbtest.getArrayListwithPlacesJsonData(placesDataArray);
			dbtest.close(debugTag);
			 // Reading all contacts
	        
        	
        } 
        catch(JSONException e){
           e.printStackTrace();	
        } 
          
          
        
        
          
	  }
	   
	  
	 
}

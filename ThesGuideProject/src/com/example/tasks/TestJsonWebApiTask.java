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
import com.example.locationData.TestData;
import com.example.sqlHelper.DatabaseHolder;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.MainLayoutActivity;

public class TestJsonWebApiTask extends AsyncTask<Void, Integer, String> {

	 private static String url = "http://aetos.it.teithe.gr/~tsironis/test.php";
	 private ProgressDialog pDialog;
	 private MainLayoutActivity activity;
	 private Context context;
	 private static final String debugTag = "TestJsonWebApiTask";
	 public String encodedUrl;
	 
	 ServiceHandler sh = new ServiceHandler();
	 
	 ArrayList<TestData> testData = new ArrayList<TestData>(); 
	 
	 
	 private static final String TAG_PERSONS = "persons";
	 private static final String TAG_ID = "id";
	 private static final String TAG_NAMES = "name";
	 private static final String TAG_SURNAMES = "surname";
	 private static final String TAG_TYPE = "type";
	 
	 public TestJsonWebApiTask(){}
     
	 public TestJsonWebApiTask(MainLayoutActivity activity){
	    	super();
	    	this.activity = activity;
	    	this.context = this.activity.getApplicationContext();
	 }
	 
	
	 // contacts JSONArray
	    JSONArray persons = null;
		
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
	  
	  
	  public ArrayList<TestData> tD;
	  
	  
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
          
          
        try{
        	
        	JSONObject respObj = new JSONObject(result);
        	
        	persons = respObj.getJSONArray(TAG_PERSONS);
        	
        	for(int i=0; i<persons.length(); i++){
        		JSONObject c = persons.getJSONObject(i);
        		
        		String id = c.getString("id");
        		int integer_id = Integer.parseInt(id);
        		String name = c.getString("name");
        		String surname = c.getString("surname");
        		String type = c.getString("type");
        		String link = c.getString("imagelink");
        		
        		try {
        			encodedUrl = link +"/" + URLEncoder.encode(link, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		testData.add(new TestData(integer_id, name, surname, type, link));
        	}
        	
        	
        
        	
        	TestLocalSqliteDatabase dbtest = new TestLocalSqliteDatabase(context);
			
			//Log.d("Insert: ", "Inserting .."); 
			//dbholder.addLocation(new LocationData(integer_id, genre, photo_link, name_el, latitude, longtitude));
			  for (TestData td : testData){
		        	//dbtest.addTestData(td);
		      }
			  Log.d("Reading: ", "Reading all persons..");
		        
		        for (TestData td : testData) {
		            String log = "Id: "+td.getId()+" ,Name: " + td.getName()+" ,Surname: " + td.getSurname()+" ,Type: " + td.getType() + " ,Image Link: " + td.getImageLink();
		                // Writing Contacts to log
		        Log.d("Name: ", log);
		        }
			  
			dbtest.getArrayListwithTestJsonData(testData);
			
			 // Reading all contacts
	        
        	
        } 
        catch(JSONException e){
           e.printStackTrace();	
        } 
          
          
          
	  }
	   
	  
	 
}

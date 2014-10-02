package com.example.tasks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.example.adapters.EventsBaseAdapter;
import com.example.adapters.InEnglishEventsBaseAdapter;
import com.example.locationData.PlacesData;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.storage.InternalStorage;
import com.example.thesguideproject.R;
import com.example.thesguideproject.SplashScreen;

public class PlacesJsonWebApiTask extends AsyncTask<Void, Integer, String> {

	 private static String url = "http://aetos.it.teithe.gr/~tsironis/places_file.php";
	 private ProgressDialog pDialog;
	 private SplashScreen s;
	 private FragmentActivity cf;
	 private Context context;
	 private static final String debugTag = "PlacesJsonWebApiTask";
	 public String encodedUrl;
	 //private String languagePhone = Locale.getDefault().getLanguage();
	 
	 ServiceHandler sh = new ServiceHandler();
	 
	 ArrayList<PlacesData> placesDataArray = new ArrayList<PlacesData>(); 
	 HashMap<String, Bitmap> listbit = new HashMap<String, Bitmap>();
	 InternalStorage internal = new InternalStorage();
		
	 private static final String TAG_PLACES = "places";
	 private static final String TAG_ID = "id";
	 private static final String TAG_NAME_EL = "name_el";
	 private static final String TAG_NAME_EL_LOWER = "nameel_lower";
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
	 private String flag;
	 
	 private ListView eventslistview;
	 private String currentDate;
	 private double current_latitude;
     private double current_longtitude;
     private String language;
	 
	 public PlacesJsonWebApiTask(){}
     
	 public PlacesJsonWebApiTask(FragmentActivity activity, String flag, ListView eventslistview, String currentDate, double current_latitude, double current_longtitude, String language){
	    	super();
	    	this.cf = activity;
	    	this.context = this.cf.getApplicationContext();
	    	this.flag = flag;
	    	this.eventslistview = eventslistview;
	    	this.currentDate = currentDate;
	    	this.current_latitude = current_latitude;
	    	this.current_longtitude = current_longtitude;
	    	this.language = language;
	 } 
	 
	 public PlacesJsonWebApiTask(SplashScreen activity, String flag, String language){
	    	super();
	    	this.s = activity;
	    	this.context = this.s.getApplicationContext();
	    	this.flag = flag;
	    	this.language = language;
	 } 
	
	 // contacts JSONArray
	    JSONArray persons = null;
		
	  @Override
	    protected void onPreExecute() {
	            super.onPreExecute();
	            // Showing progress dialog
	            //languagePhone = Locale.getDefault().getLanguage();
	          if (language.equals("Greek")){  
	              pDialog = new ProgressDialog(this.cf);
	              pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	              pDialog.setTitle("Παρακαλώ περιμένετε...");
			      pDialog.setMessage("Φόρτωση τρέχων εκδηλώσεων...");   
	          }
	          else if (language.equals("null")){
	        	  pDialog = new ProgressDialog(this.s);
	        	  pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		          pDialog.setTitle("Please wait...");
			      pDialog.setMessage("Downloading application data...");   
	          }
	          else{
	        	  pDialog = new ProgressDialog(this.cf);
	        	  pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        	  pDialog.setTitle("Please wait...");
			      pDialog.setMessage("Loading current events...");   
	          }
	   
	            pDialog.setCancelable(false);
	            pDialog.setIndeterminate(false);  
	            pDialog.setMax(100);  
	            pDialog.setProgress(0);  
	            pDialog.show();
	    }
	 
	 
	  @Override
      protected String doInBackground(Void... params) {
          // Creating service handler class instance
          try{
          	Log.d(debugTag, "Background: " + Thread.currentThread().getName());
          	String result = sh.makeServiceCall(url, ServiceHandler.GET);
            //Get the current thread's token  
            synchronized (this)  
            {  
                //Initialize an integer (that will act as a counter) to zero  
                int counter = 0;  
                //While the counter is smaller than four  
                while(counter <= 4)  
                {  
                    //Wait 850 milliseconds  
                    this.wait(550);  
                    //Increment the counter  
                    counter++;  
                    //Set the current progress.  
                    //This value is going to be passed to the onProgressUpdate() method.  
                    publishProgress(counter*25);  
                }
            }  
          	return result;
          	
          }catch(Exception e){
          	return new String();
          }
      	
      }	
	  
	  @Override
	  protected void onProgressUpdate(Integer... values) {
		  //set the current progress of the progress dialog  
          pDialog.setProgress(values[0]);  		
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
  		  if (this.flag.equals("splash")){	
  			Toast.makeText(this.s.getApplicationContext(), "result is full", Toast.LENGTH_SHORT).show();
  		  }
  		  
  		}
          
          
        try{
        	
        	JSONObject respObj = new JSONObject(result);
        	
        	persons = respObj.getJSONArray(TAG_PLACES);
        	
        	for(int i=0; i<persons.length(); i++){
        		JSONObject c = persons.getJSONObject(i);
        		
        		String id = c.getString(TAG_ID);
        		int integer_id = Integer.parseInt(id);
        		String name_el = c.getString(TAG_NAME_EL);
        		String nameel_lower = c.getString(TAG_NAME_EL_LOWER);
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
        		String info_en = null;
        		String exhibition_en = null;
        		String menu_en = null;
        		String link1 = null;
        		String link2 = null;
        		String link3 = null;
        		String link4 = null;
        		JSONArray desc_array = c.getJSONArray("desc");
        			for (int j=0; j<desc_array.length(); j++){
        				JSONObject descObject = desc_array.getJSONObject(j);
        				info = descObject.getString("info");
        				exhibition = descObject.getString("exhibition");
        				menu = descObject.getString("menu");
        				info_en = descObject.getString("info_en");
        				exhibition_en =  descObject.getString("exhibition_en");
        				menu_en = descObject.getString("menu_en");
        				link1 = descObject.getString("photo_link1");
        				link2 = descObject.getString("photo_link2");
        				link3 = descObject.getString("photo_link3");
        				link4 = descObject.getString("photo_link4");
        			}
        		
        		try {
        			encodedUrl = url +"/" + URLEncoder.encode(url, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		placesDataArray.add(new PlacesData(integer_id, name_el, nameel_lower, name_en, link, double_latitude, double_longtitude, 
        				photo_link, genre, info, exhibition, menu, info_en, exhibition_en, menu_en, link1, link2, link3, link4, subcategory, tel, email, fb_link));
        	}
        	
      	
        	TestLocalSqliteDatabase dbtest = new TestLocalSqliteDatabase(context);
			dbtest.openDataBase(debugTag);
			//Log.d("Insert: ", "Inserting .."); 
			//dbholder.addLocation(new LocationData(integer_id, genre, photo_link, name_el, latitude, longtitude));
			  //for (PlacesData td : placesDataArray){
		        	//dbtest.addTestData(td);
		      //}
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
			if (flag.equals("events")){
				//CalendarFragment c = new CalendarFragment();
				//c.SS();
				 ArrayList<PlacesData> currenteventslist = new ArrayList<PlacesData>();
				 currenteventslist = dbtest.getAllEvents("events", currentDate);
				 dbtest.close(debugTag);
			if (!language.equals("English")){ 
				 eventslistview.setAdapter(new EventsBaseAdapter("events", this, context, R.layout.places_basic_layout, currenteventslist,  current_latitude, current_longtitude, "task", true) );
			}
			else{
				eventslistview.setAdapter(new InEnglishEventsBaseAdapter("events", this, context, R.layout.places_basic_layout, currenteventslist,  current_latitude, current_longtitude, "task", true) );
			}
			}
        } 
        catch(JSONException e){
           e.printStackTrace();	
        } 
          
          
        
        
          
	  }
	   
	  
	 
}

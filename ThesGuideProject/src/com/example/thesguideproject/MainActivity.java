package com.example.thesguideproject;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class MainActivity extends ListActivity {

	private ProgressDialog pDialog;
	 
    // URL to get contacts JSON
    private static String url = "http://aetos.it.teithe.gr/~tsironis/json.php";
	
    String myname = "Tsironis";
    String myname2 = "Matoulas";
    String name3 = "thomas";
    String name4 = "time compensated";
    String name5 = "name5";
	
 // JSON Node names
    private static final String TAG_MOUSEIA = "mouseia";
    private static final String TAG_ID = "id";
    private static final String TAG_LINK = "link";
    private static final String TAG_PHONE = "tel";
    
    int kalase = 666;
    
    // contacts JSONArray
    JSONArray museum = null;
 
    // Hashmap for ListView
    ArrayList<HashMap<String, String>> mouseiaList;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mouseiaList = new ArrayList<HashMap<String, String>>();
		 
        ListView lv = getListView();
        
     // Calling async task to get json
        new GetMouseia().execute();
		
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
        
        
        
        Button mapButton = (Button) findViewById(R.id.mapButton);
        
        mapButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent myIntent = new Intent(MainActivity.this, MapTestActivity.class);
				startActivity(myIntent);
			}
		 });
        
        
        
        
        
	}

	 /**
     * Async task class to get json by making HTTP call
     * */
    private class GetMouseia extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
 
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
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
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, mouseiaList,
                    R.layout.list_item, new String[] { TAG_ID, TAG_LINK, TAG_PHONE }, new int[] 
                    								 { R.id.kodikos, R.id.link, R.id.number });
 
            setListAdapter(adapter);
            
        }
 
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}

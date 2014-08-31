package com.example.thesguideproject;

import java.util.ArrayList;
import java.util.List;

import com.example.adapters.SearchAdapter;
import com.example.fragmentClasses.MenuFragment;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.tasks.BitmapTask;
import com.example.thesguideproject.R;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesListFragmentActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

	private ActionBar mActionBar;
	private SearchView searchView;
	private MenuFragment menuFragment;
	private Fragment fragment;
	private FragmentTransaction fragmentTransaction;
	TextView hiddentv;
	private Cursor allDisplayImageLinkcursor;
	private BitmapTask imgFetcher;
	private ProgressDialog progressDialog; 
	private static final String debugTag = "PlacesListFragmentActivity";
	private String name;
	private String url;
	
	public PlacesListFragmentActivity(){}
	
	private TestLocalSqliteDatabase t = new TestLocalSqliteDatabase(this);
	//TestLocalSqliteDatabase t1 = new TestLocalSqliteDatabase(this);
	
	private ArrayList<String> items = new ArrayList<String>();
	
	MenuFragment m = new MenuFragment();
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeslistfragmentactivityfragment);
		t.openDataBase(debugTag);
		
		
		
		imgFetcher = new BitmapTask(this);
		
		//testDB.createDataBase();
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled()){
			//t.openDataBase();
			allDisplayImageLinkcursor = t.getAllPhotoDisplayImageLink(); 	
			
			
			
			if (allDisplayImageLinkcursor.moveToFirst()){
				do{
					 name = allDisplayImageLinkcursor.getString(allDisplayImageLinkcursor.getColumnIndex("_id"));
					 url = allDisplayImageLinkcursor.getString(allDisplayImageLinkcursor.getColumnIndex("photo_link"));
					// Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
					 
					 if (url.equals("")){
						 //testDB.close(); 
						 //s.add(url);
					 }
					 else{
						 
						Bitmap b = imgFetcher.loadImage(this, url, getApplicationContext(), name);	 
						//if (b != null){
							//break;
					//	} 
					//	else{
							imgFetcher.loadImage(this, url, getApplicationContext(), name);
				//		}
					   
						
					     //testDB.close();		     
					 } 
				}while(allDisplayImageLinkcursor.moveToNext());
			}
		}
		else{
			
		}
		
		if (isNetworkConnected()){
		
		new LoadViewTask().execute();  
		}
		
		
		mActionBar= getSupportActionBar();
		mActionBar.setDisplayShowTitleEnabled(false);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		//searchView = (SearchView) findViewById(R.id.action_search);	
		menuFragment = new MenuFragment();
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		hiddentv = (TextView) findViewById(R.id.hiddentv);
		
		
		if (savedInstanceState == null){
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.containermenu, menuFragment);
			t.close(debugTag);
			//fragmentTransaction.addToBackStack("menu");
			fragmentTransaction.commit();
		}
	
	}

	
	int y;
	 
	
	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}
	
	
	
	
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
	 if (keyCode==KeyEvent.KEYCODE_BACK){	
		//int y = t.getAuxiliaryVariableI();
	
		//FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
		int fragments = getSupportFragmentManager().getBackStackEntryCount();
		if (fragments == 1){
			//int backStackId = getSupportFragmentManager().getBackStackEntryAt(1).getId();
			//getSupportFragmentManager().popBackStack("d", 0);
			//getSupportFragmentManager().popBackStack();
			getSupportFragmentManager().popBackStack();
			Toast.makeText(getApplicationContext(), "Fragments in back stack are =>" + fragments, Toast.LENGTH_SHORT).show();
		}
		else if (fragments >2){
			//getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			int id = getSupportFragmentManager().getBackStackEntryCount();
			String backStackId = getSupportFragmentManager().getBackStackEntryAt(1).getName();
			//for (int i=0; i<id-2; i++){
				//getSupportFragmentManager().popBackStack(backStackId, 0);
				//getSupportFragmentManager().popBackStack();
			//}
			
			//String backStackId = getSupportFragmentManager().getBackStackEntryAt(1).getName();
			//if (backStackId.equals("mus")){
				//getSupportFragmentManager().popBackStack("mus", FragmentManager.POP_BACK_STACK_INCLUSIVE);
			//}
			//else{
				//getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			//}
			Toast.makeText(getApplicationContext(), "Fragments in back stack are =>" + fragments, Toast.LENGTH_SHORT).show();
			Toast.makeText(getApplicationContext(), "Fragments in back stack position 1 =>" + backStackId, Toast.LENGTH_SHORT).show();
		}
		//else if(fragments > 1){
		//	int backStackId = getSupportFragmentManager().getBackStackEntryAt(1).getId();
		//	getSupportFragmentManager().popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		//}
		
		// TODO Auto-generated method stub
		/*if (keyCode == event.KEYCODE_BACK && y != 0){
			
			t.insertValueForIAuxiliaryVariable(0);
			//t.close(); 
			String s = Integer.toString(i);
			Toast.makeText(getApplication(), s+ "pressed!", Toast.LENGTH_SHORT).show();
			Log.d(this.getClass().getName(), "back button pressed");
			hiddentv.setText("you are in!");
			//t.close();
			fragment = new MenuFragment();
			FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction().replace(R.id.containermenu, fragment);
			ft2.addToBackStack(null);
			ft2.commit();
		}
		else {
			fragment = new MenuFragment();
			FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction().replace(R.id.containermenu, fragment);
			ft2.commit();
			//t.close();
			Log.d(this.getClass().getName(), "NO back button pressed");
		}*/
		
	 }
		return super.onKeyDown(keyCode, event);
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
				
		//Find the search item
		MenuItem searchItem = menu.findItem(R.id.action_search);
		
		//Find the path item
		MenuItem pathItem = menu.findItem(R.id.action_path);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
		//Retrieve the SearchView
		searchView  = (SearchView) MenuItemCompat.getActionView(searchItem);
		//searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		searchView.setIconifiedByDefault(false);
		/*searchView.setOnQueryTextListener(new OnQueryTextListener(){

			@Override
			public boolean onQueryTextChange(String query) {
				// TODO Auto-generated method stub
				loadData(query);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});*/
        searchView.setOnQueryTextListener(this);
        //searchView.setOnCloseListener(this);
        
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_path:
        	Intent i = new Intent(PlacesListFragmentActivity.this, FindPathFragmentActivity.class);
        	startActivity(i);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
	}

	private void loadData(String query){
		
		items.clear();
		
		Log.i("Query =>", query);
		String columns[] = new String[] {"_id", "nameel_lower"};
		Object[] temp = new Object[] { 0, "default" };
		
		MatrixCursor cursor = new MatrixCursor(columns);
		
		Cursor c = t.searchByPlaceName(query);
		
	try{
		if (c == null){
			Log.i("Message Matched =>", "false");
		}
		else{
			//Log.i("Message Matched =>", "true");
			if (c.moveToFirst()){
				do{
					String s = c.getString(c.getColumnIndex("name_el"));
					Log.i("Cursor contents =>", s);
					items.add(s);
				}
				while(c.moveToNext());
			}
		}
	}
	finally
	{
		c.close();
	}
		
		for (int i=0; i<items.size(); i++){
			temp[0] = i;
			temp[1] = items.get(i);
			
			cursor.addRow(temp);
		}
		
		//t.setSuggestionPressedField("true");
		searchView.setSuggestionsAdapter(new SearchAdapter(this, cursor, items));
		
	}

	
	
	//@Override
//	public boolean onQueryTextChange(String newText) {
	//	showResults(newText + "*");
	//	return false;
	//}

	
	
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		//t.close(debugTag);
	}
	
	@Override
	public boolean onQueryTextChange(String query) {
		// TODO Auto-generated method stub
		loadData(query);
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		loadData(query);
		return true;
	}



	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	
 /*public static class TestListFragment extends Fragment{
	 
	 public TestListFragment(){}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.splash_screen_fragment, container, false);
		//Button b = (Button) rootView.findViewById(R.id.testbut);
		return rootView;
	}
	 

	 
 }*/
	
	 private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
	    { 
		 
		 
		  @Override  
	        protected void onPreExecute()  
	        {  
	            //Create a new progress dialog  
	            progressDialog = new ProgressDialog(PlacesListFragmentActivity.this);  
	            //Set the progress dialog to display a horizontal progress bar  
	            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	            //Set the dialog title to 'Loading...'  
	            progressDialog.setTitle("Loading...");  
	            //Set the dialog message to 'Loading application View, please wait...'  
	            progressDialog.setMessage("Loading application View, please wait...");  
	            //This dialog can't be canceled by pressing the back key  
	            progressDialog.setCancelable(false);  
	            //This dialog isn't indeterminate  
	            progressDialog.setIndeterminate(false);  
	            //The maximum number of items is 100  
	            progressDialog.setMax(100);  
	            //Set the current progress to zero  
	            progressDialog.setProgress(0);  
	            //Display the progress dialog  
	            progressDialog.show();  
	        }  
		  
		  //The code to be executed in a background thread.  
	        @Override  
	        protected Void doInBackground(Void... params)  
	        {  
	            /* This is just a code that delays the thread execution 4 times, 
	             * during 850 milliseconds and updates the current progress. This 
	             * is where the code that is going to be executed on a background 
	             * thread must be placed. 
	             */  
	            try  
	            {  
	                //Get the current thread's token  
	                synchronized (this)  
	                {  
	                    //Initialize an integer (that will act as a counter) to zero  
	                    int counter = 0;  
	                    //While the counter is smaller than four  
	                    while(counter <= 4)  
	                    {  
	                        //Wait 850 milliseconds  
	                        this.wait(350);  
	                        //Increment the counter  
	                        counter++;  
	                        //Set the current progress.  
	                        //This value is going to be passed to the onProgressUpdate() method.  
	                        publishProgress(counter*25);  
	                    }  
	                }  
	            }  
	            catch (InterruptedException e)  
	            {  
	                e.printStackTrace();  
	            }  
	            return null;  
	        }
	        
	        
	        
	        @Override  
	        protected void onProgressUpdate(Integer... values)  
	        {  
	            //set the current progress of the progress dialog  
	            progressDialog.setProgress(values[0]);  
	        }  
	  
	        //after executing the code in the thread  
	        @Override  
	        protected void onPostExecute(Void result)  
	        {  
	            //close the progress dialog  
	            progressDialog.dismiss();  
	            }
		 
		 
	    }
}

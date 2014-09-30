package com.example.thesguideproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.example.adapters.InEnglishSearchAdapter;
import com.example.adapters.SearchAdapter;
import com.example.fragmentClasses.MenuFragment;
import com.example.fragmentClasses.NoInternetConnectionFragment;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.storage.InternalStorage;
import com.example.tasks.BitmapTask;
import com.example.thesguideproject.R;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

 public class PlacesListFragmentActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

	private ActionBar mActionBar;
	private SearchView searchView;
	private MenuFragment menuFragment;
	private FragmentTransaction fragmentTransaction;
	private Cursor allDisplayImageLinkcursor;
	private ProgressDialog progressDialog; 
	private static final String debugTag = "PlacesListFragmentActivity";
	private String name;
	private String url;
	private String language;
	private BitmapTask imgFetcher = new BitmapTask(this);
	
	public PlacesListFragmentActivity(){}
	
	private MenuItem searchItem;
	private TestLocalSqliteDatabase t = new TestLocalSqliteDatabase(this);
	//TestLocalSqliteDatabase t1 = new TestLocalSqliteDatabase(this);
	
	private ArrayList<String> items = new ArrayList<String>();
	private boolean imagessavedFlag;
	MenuFragment m = new MenuFragment();
	
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeslistfragmentactivityfragment);
		
		Bundle extras = getIntent().getExtras();
		language = extras.getString("language");
		t.openDataBase(debugTag);
		
		//imgFetcher = new BitmapTask(this);
		
		//testDB.createDataBase();
		mActionBar= getSupportActionBar();
		mActionBar.setBackgroundDrawable(null);
		mActionBar.setHomeButtonEnabled(false);
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setIcon(R.drawable.ic_launcher);
		mActionBar.setDisplayShowTitleEnabled(false);
		
		
		//searchView = (SearchView) findViewById(R.id.action_search);	
	
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
						imgFetcher.loadImage(this, url, getApplicationContext(), name);     
					 } 
				}while(allDisplayImageLinkcursor.moveToNext());
			}
			imagessavedFlag = true;
		}else{
			imagessavedFlag = false;
		}
		
		if (savedInstanceState == null){
			Bundle langbundle = new Bundle();
			langbundle.putBoolean("imagessavedFlag", imagessavedFlag);
			langbundle.putString("language", language);
			menuFragment = new MenuFragment();
			menuFragment.setArguments(langbundle);
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.containermenu, menuFragment);
			t.close(debugTag);
			//fragmentTransaction.addToBackStack("menu");
			fragmentTransaction.commit();
		}
		
				if (isNetworkConnected()){
					new LoadViewTask().execute();  
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
	
	
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  
		
	 if (keyCode==KeyEvent.KEYCODE_BACK){	
	
		 LinearLayout menulinearlayout = (LinearLayout) findViewById(R.id.linearlayout1);
		 formIsValid(menulinearlayout);
		 
		
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
			//Toast.makeText(getApplicationContext(), "Fragments in back stack are =>" + fragments, Toast.LENGTH_SHORT).show();
			//Toast.makeText(getApplicationContext(), "Fragments in back stack position 1 =>" + backStackId, Toast.LENGTH_SHORT).show();
		}	
	  
	 }   
		return super.onKeyDown(keyCode, event);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
				
		//Find the search item
		searchItem = menu.findItem(R.id.action_search);
		//Find the path item
		MenuItem pathItem = menu.findItem(R.id.action_path);
		//Find the close item
		MenuItem closeItem = menu.findItem(R.id.close);
		if (language.equals("English")){
			closeItem.setTitle("Nearby");
		}else{
			closeItem.setTitle("Κοντά");
		}
		
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
		//Retrieve the SearchView
		searchView  = (SearchView) MenuItemCompat.getActionView(searchItem);
		//searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		if (language.equals("English")){
			searchView.setQueryHint("Place...");
		}else{
			searchView.setQueryHint("Τοποθεσία...");
		}
		searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus){
					MenuItemCompat.collapseActionView(searchItem);
					searchView.setQuery("", false);
				}	
			}
		});
        //searchView.setOnCloseListener(this);
        
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_path:
        	Intent i = new Intent(PlacesListFragmentActivity.this, FindPathFragmentActivity.class);
        	i.putExtra("imagessavedFlag", imagessavedFlag);
        	i.putExtra("language", language);
        	startActivity(i);
        	return true;
        case R.id.close:
        	Intent closeIntent = new Intent(PlacesListFragmentActivity.this, CloseExpandableListFragmentActivity.class);
        	closeIntent.putExtra("imagessavedFlag", imagessavedFlag);
        	closeIntent.putExtra("language", language);
        	startActivity(closeIntent);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
	}

	private void loadData(String query){
		
		items.clear();
		
	 if(language.equals("English")){
		 String pattern = "^[A-Za-z0-9. ]+$";
			if (query.matches(pattern)){
						String columns[] = new String[] {"_id", "name_en"};
						Object[] temp = new Object[] { 0, "default" };

						MatrixCursor cursor = new MatrixCursor(columns);
						Cursor c = t.searchByPlaceNameEn(query);

						try{
							if (c == null){
								Log.i("Message Matched =>", "false");
							}
							else{
								if (c.moveToFirst()){
									do{
										String s = c.getString(c.getColumnIndex("name_en"));
										//Log.i("Cursor contents =>", s);
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

						//String lang = "Latin";
						searchView.setSuggestionsAdapter(new InEnglishSearchAdapter(this, cursor, items, searchItem, imagessavedFlag));
						
				}
				else{
						Log.i("Query =>", query);
						String columns[] = new String[] {"_id", "name_en"};
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
										String s = c.getString(c.getColumnIndex("name_en"));
										//Log.i("Cursor contents =>", s);
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
						//String lang = "Greek";	
						searchView.setSuggestionsAdapter(new InEnglishSearchAdapter(this, cursor, items, searchItem, imagessavedFlag));
				}
	 }	
	 else{	
		String pattern = "^[A-Za-z0-9. ]+$";
		if (query.matches(pattern)){
					String columns[] = new String[] {"_id", "name_en"};
					Object[] temp = new Object[] { 0, "default" };

					MatrixCursor cursor = new MatrixCursor(columns);
					Cursor c = t.searchByPlaceNameEn(query);

					try{
						if (c == null){
							Log.i("Message Matched =>", "false");
						}
						else{
							if (c.moveToFirst()){
								do{
									String s = c.getString(c.getColumnIndex("name_el"));
									//Log.i("Cursor contents =>", s);
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

					String lang = "Latin";
					searchView.setSuggestionsAdapter(new SearchAdapter(this, cursor, items, lang, searchItem, imagessavedFlag));
			}
			else{
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
									//Log.i("Cursor contents =>", s);
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
					String lang = "Greek";	
					searchView.setSuggestionsAdapter(new SearchAdapter(this, cursor, items, lang, searchItem, imagessavedFlag));
			}
	 }
	}

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
		//searchView.onActionViewCollapsed();
		loadData(query);
		return true;
	}



	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean formIsValid(LinearLayout layout) {
		for (int i=0; i<layout.getChildCount(); i++){
			View v = layout.getChildAt(i);
				if (v instanceof Button){
					v.getBackground().setAlpha(255);
				}
		}	
		return true;
	}
	
	 private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
	    { 
		  @Override  
	        protected void onPreExecute()  
	        {  
	            //Create a new progress dialog  
	            progressDialog = new ProgressDialog(PlacesListFragmentActivity.this);  
	            //Set the progress dialog to display a horizontal progress bar  
	            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	           
	           if (language.equals("English")){ 
	        	   //Set the dialog title to 'Loading...'  
	        	   progressDialog.setTitle("Loading...");  
	        	   //Set the dialog message to 'Loading application View, please wait...'  
	        	   progressDialog.setMessage("Loading application View, please wait...");
	           }else{
	        	   progressDialog.setTitle("Φορτώνει...");
	        	   progressDialog.setMessage("Φόρτωση περιβάλλοντος εφαρμογής, παρακαλώ περιμένετε...");
	           }
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

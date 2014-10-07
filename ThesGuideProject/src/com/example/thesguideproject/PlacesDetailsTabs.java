package com.example.thesguideproject;


import java.util.ArrayList;

import com.example.adapters.InEnglishSearchAdapter;
import com.example.adapters.SearchAdapter;
import com.example.adapters.TabsPagerAdapter;
import com.example.fragmentClasses.GoogleMapFragment;
import com.example.fragmentClasses.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.example.fragmentClasses.KatalogFragment;
import com.example.fragmentClasses.NoInternetConnectionFragment;
import com.example.fragmentClasses.PhotoGridViewFragment;
import com.example.fragmentClasses.InfoFragment;
import com.example.fragmentClasses.ExhibitionFragment;
import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PlacesDetailsTabs extends ActionBarActivity implements OnGoogleMapFragmentListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener{

	// Google Map
    private GoogleMap googleMap;
    
    LatLng myPosition;
    private GPSTracker gps;
    private GoogleMap mUIGoogleMap;
    private ProgressDialog simpleWaitDialog;
    private String button_pressed;
    private String placenameEl;
    private String nameLower;
    private String description_info;
    private String telephone;
    private String link;
    private String fbLink;
    private String email;
    private String exhibition;
    private String menu;
    private String photoLink1;
    private String photoLink2;
    private String photoLink3;
    private String photoLink4;
    private TabsPagerAdapter mAdapter;
    private double doublelatitude;
    private double doublelongtitude;
    private double doubleCurrentLatitude;
    private double doubleCurrentLongtitude;
    private String latitude;
    private String longtitude;
    private String current_latitude;
    private String current_longtitude;
    private static final String debugTag = "PlacesDetailsTabs";
    private TestLocalSqliteDatabase testDB;
  	private ViewPager viewPager;
    private TabsPagerAdapter tabsPagerAdapter;
    private SearchView searchView;
    private String language;
    private  Bundle exhibitionBundle = new Bundle();
    private ArrayList<String> items = new ArrayList<String>();
    
    private ActionBar mActionBar;
    private MenuItem searchItem;
    private boolean imagessavedFlag;
    private String displaycurrentPoint;
    
	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		mUIGoogleMap = map;
	}
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testlayout);
		mActionBar = getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setIcon(R.drawable.ic_launcher);
		mActionBar.setDisplayShowTitleEnabled(false);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		Intent i = getIntent();
		
		displaycurrentPoint = i.getStringExtra("displaycurrentPoint");
		imagessavedFlag = i.getExtras().getBoolean("imagessavedFlag");
		language = i.getStringExtra("language");
		button_pressed = i.getStringExtra("button_pressed_text");
		placenameEl = i.getStringExtra("placeNameEl");
		nameLower = i.getStringExtra("placenameEllower");
		description_info = i.getStringExtra("desc_info");
		telephone = i.getStringExtra("telephone");
		link = i.getStringExtra("link");
		fbLink = i.getStringExtra("fbLink");
		email = i.getStringExtra("email");
		exhibition = i.getStringExtra("exhibition");
		menu = i.getStringExtra("menu");
		photoLink1 = i.getStringExtra("photoLink1");
		photoLink2 = i.getStringExtra("photoLink2");
		photoLink3 = i.getStringExtra("photoLink3");
		photoLink4 = i.getStringExtra("photoLink4");
		latitude = i.getStringExtra("latitude");
		longtitude = i.getStringExtra("longtitude");
		current_latitude = i.getStringExtra("current latitude");
		current_longtitude = i.getStringExtra("current longtitude");
		//Toast.makeText(getApplicationContext(), current_latitude + " " + current_longtitude, Toast.LENGTH_SHORT).show();
		
		
		doublelatitude = Double.parseDouble(latitude);
		doublelongtitude = Double.parseDouble(longtitude);
		
		doubleCurrentLatitude = Double.parseDouble(current_latitude);
		doubleCurrentLongtitude = Double.parseDouble(current_longtitude);
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setClipToPadding(false);

		//actionBar = getSupportActionBar();
		//mAdapter = new TabsPagerAdapter(this, viewPager, name, doublelatitude, doublelongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
		
		viewPager.setAdapter(mAdapter);
        //actionBar.setHomeButtonEnabled(false);
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);  
		mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        //this.tabsPagerAdapter = new TabsPagerAdapter(this, viewPager, name, doublelatitude, doublelongtitude, doubleCurrentLatitude, doubleCurrentLongtitude);
        this.tabsPagerAdapter = new TabsPagerAdapter(this, viewPager, mActionBar);
        tabsPagerAdapter.createHistory();
        
        Bundle infoBundle = new Bundle();
        infoBundle.putString("language", language);
        infoBundle.putString("place_nameEl_info", placenameEl);
        infoBundle.putString("Headername", nameLower);
        infoBundle.putString("desc_info", description_info);
        infoBundle.putString("telephone", telephone);
        infoBundle.putString("link", link);
        infoBundle.putString("fbLink", fbLink);
        infoBundle.putString("email", email);
        infoBundle.putString("button_pressed", button_pressed);
       // tabsPagerAdapter.addTab(actionBar.newTab().setText("Info"), InfoFragment.class, infoBundle);
       if (language.equals("Greek")){
    	   tabsPagerAdapter.addTab(mActionBar.newTab().setText("Πληροφοριες"), InfoFragment.class, infoBundle);
       }else{
    	   tabsPagerAdapter.addTab(mActionBar.newTab().setText("Info"), InfoFragment.class, infoBundle);
       }
       
       Bundle menuBundle = new Bundle();
       menuBundle.putString("language", language);
       menuBundle.putString("menu", menu);
       if (!menu.equals("null") && !(menu.length() == 3)){ 
       		if (language.equals("Greek")){
       				tabsPagerAdapter.addTab(mActionBar.newTab().setText("Καταλογος"), KatalogFragment.class, menuBundle);
       		}else{
       			    tabsPagerAdapter.addTab(mActionBar.newTab().setText("Menu"), KatalogFragment.class, menuBundle);
       		}
       }	
       
        exhibitionBundle = new Bundle();
        exhibitionBundle.putString("exhibition", exhibition);
        exhibitionBundle.putString("language", language);
        //tabsPagerAdapter.addTab(actionBar.newTab().setText("Exhibition"), ExhibitionFragment.class, exhibitionBundle);
        if (!exhibition.equals("null") && !(exhibition.length() == 10)){
        	if (language.equals("Greek")){
        		tabsPagerAdapter.addTab(mActionBar.newTab().setText("Εκθεσεις"), ExhibitionFragment.class, exhibitionBundle);
        	}else{
        		tabsPagerAdapter.addTab(mActionBar.newTab().setText("Exhibition"), ExhibitionFragment.class, exhibitionBundle);
        	}
        }
        
        testDB = new TestLocalSqliteDatabase(this);
        testDB.openDataBase(debugTag);
        
        DisplayMetrics metrics = new DisplayMetrics();
     		getWindowManager().getDefaultDisplay().getMetrics(metrics);
     		
     		int scr_height = metrics.heightPixels;
     		int scr_width = metrics.widthPixels;
     		String s_height = Integer.toString(scr_height);
     		String s_width = Integer.toString(scr_width);
     		Log.i("SCRENN HEIGHT => ", s_height);
     		Log.i("SCRENN WIDTH => ", s_width);
        
     //   if (button_pressed.equals("museums")){
        	
        	//ArrayList<Photo>  photoLinksArray = testDB.getPhotoLinksArray(placenameEl);
     	 if (language.equals("English")){
     		String[] photoLinkStringArray1 = testDB.getPhotoLinksArrayFromNameEn(placenameEl);
     		int list_length1 = photoLinkStringArray1.length;
     				if (!photoLinkStringArray1[0].equals("null")){
     						String s = Integer.toString(list_length1);
        	
     						Log.i("PHOTO LIST RETURN FROM DATABASE =>", s);
  	
     						//Toast.makeText(getApplicationContext(), "Button pressed text =>" + " " + button_pressed, Toast.LENGTH_SHORT).show();
     						Bundle photoBundle = new Bundle();
     						photoBundle.putSerializable("linksList", photoLinkStringArray1);
     						photoBundle.putInt("Screen Height", scr_height);
     						photoBundle.putInt("Screen Width", scr_width);
     						photoBundle.putString("language", language);
     						//tabsPagerAdapter.addTab(actionBar.newTab().setText("Photo tab"), PhotoGridViewFragment.class, photoBundle);
     						tabsPagerAdapter.addTab(mActionBar.newTab().setText("Photo tab"), PhotoGridViewFragment.class, photoBundle);
     		        }
     	 }			
     	 else{	
     		String[] photoLinkStringArray = testDB.getPhotoLinksArray(placenameEl);
        	int list_length = photoLinkStringArray.length;
        			if (!photoLinkStringArray[0].equals("null")){
        					String s = Integer.toString(list_length);
        	
        					Log.i("PHOTO LIST RETURN FROM DATABASE =>", s);
  	
        					//Toast.makeText(getApplicationContext(), "Button pressed text =>" + " " + button_pressed, Toast.LENGTH_SHORT).show();
        					Bundle photoBundle = new Bundle();
        					photoBundle.putSerializable("linksList", photoLinkStringArray);
        					photoBundle.putInt("Screen Height", scr_height);
        					photoBundle.putInt("Screen Width", scr_width);
        					photoBundle.putString("language", language);
        					//tabsPagerAdapter.addTab(actionBar.newTab().setText("Photo tab"), PhotoGridViewFragment.class, photoBundle);
        					tabsPagerAdapter.addTab(mActionBar.newTab().setText("Φωτογραφιες"), PhotoGridViewFragment.class, photoBundle);
        			}	
     	 }
        
     	  Bundle onmapBundle = new Bundle();
          onmapBundle.putString("language", language);
          onmapBundle.putDouble("doubleCurrentLatitude", doubleCurrentLatitude);
          onmapBundle.putDouble("doubleCurrentLongtitude", doubleCurrentLongtitude);
          onmapBundle.putDouble("doublePlaceLatitude", doublelatitude);
          onmapBundle.putDouble("doublePlaceLongtitude", doublelongtitude);
          onmapBundle.putString("displaycurrentPoint", displaycurrentPoint);
          onmapBundle.putString("place_nameEl_info", placenameEl);
     	 
          WifiManager wifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
          	if (wifi.isWifiEnabled()){
			
          		 if (language.equals("Greek")){
                 	tabsPagerAdapter.addTab(mActionBar.newTab().setText("Στο χαρτη"), GoogleMapFragment.class, onmapBundle);
                 }else{
                 	tabsPagerAdapter.addTab(mActionBar.newTab().setText("OnMap"), GoogleMapFragment.class, onmapBundle);
                 }
		    
          	}else{
          		 if (language.equals("Greek")){
          			tabsPagerAdapter.addTab(mActionBar.newTab().setText("Στο χαρτη"), NoInternetConnectionFragment.class, onmapBundle);
          		 }
          		 else{
          			tabsPagerAdapter.addTab(mActionBar.newTab().setText("OnMap"), NoInternetConnectionFragment.class, onmapBundle); 
          		 }
          	}
     
      
        //tabsPagerAdapter.addTab(actionBar.newTab().setText("OnMap"), OnMapFragment.class, onmapBundle);
        //tabsPagerAdapter.addTab(actionBar.newTab().setText("OnMap"), GoogleMapFragment.class, onmapBundle);
       
        	
      //  tabsPagerAdapter.replace(2, ExhibitionFragment.class, exhibitionBundle);
        
      /*  ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			
			@Override
			public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
				
				
			}
			
			@Override
			public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
				
				if (tab.getPosition() == 2){
					Toast.makeText(getApplicationContext(), "On Map Tab Pressed!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" 
							+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doublelatitude + "," + doublelongtitude));
							startActivity(intent);
				}
				viewPager.setCurrentItem(tab.getPosition());
			}
			
			@Override
			public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
				
				if (tab.getPosition() == 2){
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" 
							+ doubleCurrentLatitude + "," + doubleCurrentLongtitude + "&daddr=" + doublelatitude + "," + doublelongtitude));
							startActivity(intent);
				}
				viewPager.setCurrentItem(tab.getPosition());
			}
		};*/
 
		//Add new Tabs
	//	actionBar.addTab(actionBar.newTab().setText("Info").setTabListener(tabListener));
	//	actionBar.addTab(actionBar.newTab().setText("MenuTab").setTabListener(tabListener));
	//	actionBar.addTab(actionBar.newTab().setText("OnMap").setTabListener(tabListener));
		

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}

	public boolean onCreateOptionsMenu(Menu menu){
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
		
		//Find the search item
		searchItem = menu.findItem(R.id.action_search);
		
		//Retrieve the SearchView
		searchView  = (SearchView) MenuItemCompat.getActionView(searchItem);	
		
		searchView.setIconifiedByDefault(false);
		if (language.equals("English")){
			searchView.setQueryHint("Place...");
		}else{
			searchView.setQueryHint("Τοποθεσία...");
		}
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
        
        
		return super.onCreateOptionsMenu(menu);
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	    case R.id.action_path:
        	Intent i = new Intent(PlacesDetailsTabs.this, FindPathFragmentActivity.class);
        	i.putExtra("imagessavedFlag", imagessavedFlag);
        	i.putExtra("language", language);
        	startActivity(i);
        	return true;
        case R.id.close:
        	Intent closeIntent = new Intent(PlacesDetailsTabs.this, CloseExpandableListFragmentActivity.class);
        	closeIntent.putExtra("imagessavedFlag", imagessavedFlag);
        	closeIntent.putExtra("language", language);
        	startActivity(closeIntent);
        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	public boolean onClose() {
		// TODO Auto-generated method stub
		return false;
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
	
private void loadData(String query){
		
		items.clear();
		
	 if(language.equals("English")){
		 String pattern = "^[A-Za-z0-9. ]+$";
			if (query.matches(pattern)){
						String columns[] = new String[] {"_id", "name_en"};
						Object[] temp = new Object[] { 0, "default" };

						MatrixCursor cursor = new MatrixCursor(columns);
						Cursor c = testDB.searchByPlaceNameEn(query);

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
						Cursor c = testDB.searchByPlaceName(query);
			
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
					Cursor c = testDB.searchByPlaceNameEn(query);

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
					Cursor c = testDB.searchByPlaceName(query);
		
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
	
}

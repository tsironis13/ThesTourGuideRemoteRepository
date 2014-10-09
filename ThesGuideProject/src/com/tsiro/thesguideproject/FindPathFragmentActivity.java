package com.tsiro.thesguideproject;

import java.util.ArrayList;

import com.example.thesguideproject.R;
import com.google.android.gms.maps.GoogleMap;
import com.tsiro.adapters.InEnglishSearchAdapter;
import com.tsiro.adapters.SearchAdapter;
import com.tsiro.fragmentClasses.SettingsMapFragment;
import com.tsiro.fragmentClasses.GoogleMapFragment.OnGoogleMapFragmentListener;
import com.tsiro.sqlHelper.TestLocalSqliteDatabase;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

@TargetApi(Build.VERSION_CODES.ECLAIR) 
public class FindPathFragmentActivity extends ActionBarActivity implements OnGoogleMapFragmentListener, OnQueryTextListener{

	private SettingsMapFragment settingsMapFragment;
	private FragmentTransaction fragmentTransaction;
	private GoogleMap mUIGoogleMap;
	private String language;
	private ActionBar mActionBar;
	private SearchView searchView;
	private MenuItem searchItem;
	private ArrayList<String> items = new ArrayList<String>();
	private TestLocalSqliteDatabase testDB;
	private static final String debugTag = "FindPathFragmentActivity";
	private boolean imagessavedFlag;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpathfragmentactivity);	

		testDB = new TestLocalSqliteDatabase(this);
        testDB.openDataBase(debugTag);
		
		Bundle extras = getIntent().getExtras();
		language = extras.getString("language");
		imagessavedFlag = extras.getBoolean("imagessavedFlag");
		
		mActionBar = getSupportActionBar();
		mActionBar.setBackgroundDrawable(null);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setIcon(R.drawable.ic_launcher);
		mActionBar.setDisplayShowTitleEnabled(false);
		
		settingsMapFragment = new SettingsMapFragment();
		Bundle langbundle = new Bundle();
		langbundle.putString("language", language);
		if (savedInstanceState == null){
			settingsMapFragment.setArguments(langbundle);
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.containersettings, settingsMapFragment);
			fragmentTransaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
				
		//Find the search item
		searchItem = menu.findItem(R.id.action_search);
		//Find the path item
		MenuItem pathItem = menu.findItem(R.id.action_path);
		pathItem.setVisible(false);
		//this.invalidateOptionsMenu();
		//Find the close item
		MenuItem closeItem = menu.findItem(R.id.close);
		if (language.equals("English")){
			closeItem.setTitle("Nearby");
		}else{
			closeItem.setTitle("Κοντά");
		}
		
		
		//SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
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
        case R.id.home:
        	/*Intent upIntent = new Intent(this, PlacesListFragmentActivity.class);
        	//upIntent.putExtra("language", language);
        	startActivity(upIntent);
        	finish();*/
        	return true;
        case R.id.close:
        	Intent closeIntent = new Intent(FindPathFragmentActivity.this, CloseExpandableListFragmentActivity.class);
        	closeIntent.putExtra("language", language);
        	startActivity(closeIntent);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
	}
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		
		int id = getSupportFragmentManager().getBackStackEntryCount();
		String sid = Integer.toString(id);
		Log.i("back stack entry count =>", sid);
		
		//Log.i("back stack entry at position 0 =>", backStackId);
		for (int i=0; i<id; i++){
				String backStackId = getSupportFragmentManager().getBackStackEntryAt(i).getName();
		   if (backStackId.equals("toandfrom") || backStackId.equals("popup")){
			   getSupportFragmentManager().popBackStack();
		   }
		}
		
		//ToAndFromFragment t = new ToAndFromFragment();
		//fragmentTransaction = getSupportFragmentManager().beginTransaction().remove(t);
		//fragmentTransaction.commit();
	}



	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		mUIGoogleMap = map;
	}

	@Override
	public boolean onQueryTextChange(String query) {
		// TODO Auto-generated method stub
		loadData(query);
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		loadData(query);
		return false;
	}
	
	//@Override
	///protected void onListItemClick(ListView l, View v, int position, long id) {
		//super.onListItemClick(l, v, position, id);
		//String item = (String) getListAdapter().getItem(position);
		//startingpointtv.setText(item);
		//setListAdapter(null);
		//categoryrb.setChecked(false);
	//}
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

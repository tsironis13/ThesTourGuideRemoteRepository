package com.tsiro.thesguideproject;

import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tsiro.thesguideproject.R;
import com.tsiro.adapters.InEnglishSearchAdapter;
import com.tsiro.adapters.SearchAdapter;
import com.tsiro.fragmentClasses.SearchPlaceResultListFragment;
import com.tsiro.sqlHelper.TestLocalSqliteDatabase;

public class SearchPlaceResutlActivity extends ActionBarActivity implements OnQueryTextListener{

	private ActionBar mActionBar;
	private SearchView searchView;
	private SearchPlaceResultListFragment searchPlaceResultListFragment;
	private String language;
	private TestLocalSqliteDatabase t;
	private ArrayList<String> items = new ArrayList<String>();
	private MenuItem searchItem;
	private static final String debugTag = "SearchPlaceResutlActivity";
	private boolean imagessavedFlag;
	
	public SearchPlaceResutlActivity(){}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_place_result);
		
		Intent intent = getIntent();
		String nameEl = intent.getStringExtra("PlaceName");
	    language = intent.getStringExtra("language");
		imagessavedFlag = intent.getExtras().getBoolean("imagessavedFlag");
		t = TestLocalSqliteDatabase.getInstance(this);
		t.openDataBase(debugTag);
		
		mActionBar= getSupportActionBar();
		mActionBar.setBackgroundDrawable(null);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setIcon(R.drawable.ic_launcher);
		mActionBar.setDisplayShowTitleEnabled(false);
		
		if (savedInstanceState == null) { 
			Bundle langbundle = new Bundle();
			langbundle.putString("language", language);
			langbundle.putBoolean("imagessavedFlag", imagessavedFlag);
			searchPlaceResultListFragment = new SearchPlaceResultListFragment(nameEl);
			searchPlaceResultListFragment.setArguments(langbundle);
			getSupportFragmentManager().beginTransaction() .add(R.id.searchresultlist, searchPlaceResultListFragment).commit(); 
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
		//Find the close item
		MenuItem closeItem = menu.findItem(R.id.close);
		if (language.equals("English")){
			closeItem.setTitle("Nearby");
		}else{
			closeItem.setTitle("�����");
		}
		
		
		//SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		
		//Retrieve the SearchView
		searchView  = (SearchView) MenuItemCompat.getActionView(searchItem);
		//searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		if (language.equals("English")){
			searchView.setQueryHint("Place...");
		}else{
			searchView.setQueryHint("���������...");
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
        	Toast.makeText(getApplicationContext(), "Back!", Toast.LENGTH_SHORT).show();
        	return true;
        case R.id.action_path:
        	Intent i = new Intent(SearchPlaceResutlActivity.this, FindPathFragmentActivity.class);
        	i.putExtra("imagessavedFlag", imagessavedFlag);
        	i.putExtra("language", language);
        	startActivity(i);
        	return true;
        case R.id.close:
        	Intent closeIntent = new Intent(SearchPlaceResutlActivity.this, CloseExpandableListFragmentActivity.class);
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
		t.close(debugTag);
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

	
	
}

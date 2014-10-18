package com.tsiro.thesguideproject;

import java.util.ArrayList;
import com.tsiro.thesguideproject.R;
import com.tsiro.adapters.InEnglishSearchAdapter;
import com.tsiro.adapters.SearchAdapter;
import com.tsiro.fragmentClasses.CloseExpandableListFragment;
import com.tsiro.sqlHelper.TestLocalSqliteDatabase;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CloseExpandableListFragmentActivity extends ActionBarActivity implements OnQueryTextListener{

	private CloseExpandableListFragment closeExpListFragment;
	private FragmentTransaction fragmentTransaction;
	private String language;
	private ActionBar mActionBar;
	private SearchView searchView;
	private MenuItem searchItem;
	private ArrayList<String> items = new ArrayList<String>();
	private TestLocalSqliteDatabase testDB;
	private static final String debugTag = "CloseExpandableListFragmentActivity";
	private boolean imagessavedFlag;
	private Button hiddenrefreshbutton;
	private TextView hiddenresfreshtv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.closeexplistfragmentactivity);
		
		hiddenrefreshbutton = (Button) findViewById(R.id.hiddenrefreshbutton);
		hiddenresfreshtv = (TextView) findViewById(R.id.hiddenresfreshtv);
		//testDB = new TestLocalSqliteDatabase(this);
		testDB = TestLocalSqliteDatabase.getInstance(this);
        testDB.openDataBase(debugTag);
        
		mActionBar = getSupportActionBar();
		mActionBar.setBackgroundDrawable(null);
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(true);
		mActionBar.setIcon(R.drawable.ic_launcher);
		mActionBar.setDisplayShowTitleEnabled(false);
		
		Bundle extras = getIntent().getExtras();
		language = extras.getString("language");
		imagessavedFlag = extras.getBoolean("imagessavedFlag");
		
		closeExpListFragment = new CloseExpandableListFragment();
		Bundle langbundle = new Bundle();
		langbundle.putString("language", language);
	 if (isNetworkConnected()){	
		if (savedInstanceState == null){
			hiddenrefreshbutton.setVisibility(View.GONE);
			hiddenresfreshtv.setVisibility(View.GONE);
			closeExpListFragment.setArguments(langbundle);
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.close, closeExpListFragment);
			fragmentTransaction.commit();
		}
	 }
	 else{
			if (!language.equals("English")){
				hiddenrefreshbutton.setText("Παρακαλώ Ξαναπροσπάθησε");
				hiddenresfreshtv.setText("Χωρίς σύνδεση στο Διαδίκτυο");
			}
		 hiddenresfreshtv.setVisibility(View.VISIBLE);
		 hiddenrefreshbutton.setVisibility(View.VISIBLE);
		 hiddenrefreshbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isNetworkConnected()){
					hiddenresfreshtv.setVisibility(View.GONE);
					hiddenrefreshbutton.setVisibility(View.GONE);
					closeExpListFragment = new CloseExpandableListFragment();
					Bundle langbundle = new Bundle();
					langbundle.putString("language", language);
					closeExpListFragment.setArguments(langbundle);
					fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.close, closeExpListFragment);
					fragmentTransaction.commit();
				}
			}
		});
	 }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
				
		//Find the search item
		searchItem = menu.findItem(R.id.action_search);
		//Find the path item
		//MenuItem pathItem = menu.findItem(R.id.action_path);
		//this.invalidateOptionsMenu();
		//Find the close item
		MenuItem closeItem = menu.findItem(R.id.close);
		closeItem.setVisible(false);
		
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
        case R.id.action_path:
        	Intent i = new Intent(CloseExpandableListFragmentActivity.this, FindPathFragmentActivity.class);
        	i.putExtra("language", language);
        	startActivity(i);
        	return true;
        default:
        	return super.onOptionsItemSelected(item);
        }
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
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}

	 private boolean isNetworkConnected() {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo ni = cm.getActiveNetworkInfo();
			if (ni == null) {
				// There are no active networks.
				return false;
			} else
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

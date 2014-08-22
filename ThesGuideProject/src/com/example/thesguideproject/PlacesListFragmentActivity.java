package com.example.thesguideproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.example.fragmentClasses.DisplayImageFragment;
import com.example.fragmentClasses.ListPlacesFragment;
import com.example.fragmentClasses.MenuFragment;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;
import com.example.thesguideproject.R.menu;

import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesListFragmentActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

	private ActionBar mActionBar;
	private SearchView searchView;
	private ListView list;
	private Stack<Fragment> fragmentStack;
	private MenuFragment menuFragment;
	private Fragment fragment;
	private Fragment fragment2;
	private ListPlacesFragment listPlacesFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	TextView hiddentv;
	
	
	public PlacesListFragmentActivity(){}
	
	TestLocalSqliteDatabase t = new TestLocalSqliteDatabase(this);
	//TestLocalSqliteDatabase t1 = new TestLocalSqliteDatabase(this);
	
	private List<String> items = new ArrayList<String>();; 
	
	MenuFragment m = new MenuFragment();
	int i;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.placeslistfragmentactivityfragment);
	
		
		//items.add("antionio");
		//items.add("john");
		//items.add("peny");
		
		mActionBar= getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		
		//searchView = (SearchView) findViewById(R.id.action_search);
		
        this.list = (ListView) findViewById(R.id.list);
		
		menuFragment = new MenuFragment();
		
		//fragmentStack = new Stack<Fragment>();
		
		mActionBar = getSupportActionBar();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		hiddentv = (TextView) findViewById(R.id.hiddentv);
		
		
		if (savedInstanceState == null){
			fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.containermenu, menuFragment);
			t.close();
			fragmentTransaction.commit();
		}
	
	}

	
	int y;
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
			
		int y = t.getAuxiliaryVariableI();
	
		// TODO Auto-generated method stub
		if (keyCode == event.KEYCODE_BACK && y != 0){
			
			t.insertValueForIAuxiliaryVariable(0);
			t.close(); 
			String s = Integer.toString(i);
			Toast.makeText(getApplication(), s+ "pressed!", Toast.LENGTH_SHORT).show();
			Log.d(this.getClass().getName(), "back button pressed");
			hiddentv.setText("you are in!");
			fragment2 = new MenuFragment();
			FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction().replace(R.id.containermenu, fragment2);
			ft2.addToBackStack(null);
			ft2.commit();
		}
		else {
			fragment2 = new MenuFragment();
			FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction().replace(R.id.containermenu, fragment2);
			ft2.commit();
			Log.d(this.getClass().getName(), "NO back button pressed");
		}
		return super.onKeyDown(keyCode, event);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//Inflate the menu
		getMenuInflater().inflate(R.menu.main, menu);
				
		//Find the search item
		MenuItem searchItem = menu.findItem(R.id.action_search);
		
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
	
	
	private void loadData(String query){
		
		items.clear();
		
		Log.i("Query =>", query);
		String columns[] = new String[] {"_id", "name_el"};
		Object[] temp = new Object[] { 0, "default" };
		
		MatrixCursor cursor = new MatrixCursor(columns);
		
		Cursor c = t.searchByPlaceName(query);
		
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
		
		for (int i=0; i<items.size(); i++){
			temp[0] = i;
			temp[1] = items.get(i);
			
			cursor.addRow(temp);
		}
		
		//t.setSuggestionPressedField("true");
		searchView.setSuggestionsAdapter(new ExampleAdapter(this, cursor, items));
		
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
		//t.close();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getApplicationContext(), "ON RESUME !!!!!!!!!", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onClose() {
		showResults("");
		return false;
	}
	
	private void showResults(String query) {
		Cursor cursor = t.searchByPlaceName((query != null ? query.toString() : "@@@@"));
		
		if (cursor == null){
			
		}
		else{
			// Specify the columns we want to display in the result
			String columns[] = new String[] {"name_el"};
			
			// Specify the Corresponding layout elements where we want the columns to go
			int[] to = new int[] {R.id.nameElinfo};
			
			// Create a simple cursor adapter for the definitions and apply them to the ListView
			//SimpleCursorAdapter place = new SimpleCursorAdapter(this,R.layout.placeresult, cursor, columns, to);
            //this.list.setAdapter(place);
		}
		
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
	
	
}

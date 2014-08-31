package com.example.fragmentClasses;

import java.util.ArrayList;
import java.util.Locale;

import com.example.adapters.DisarableLocationCursorAdapter;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SettingsMapFragment extends ListFragment{

	private static EditText disarableLocationEditText;
	private static EditText disarabledestLocationEditText;
	private static ListView listView;
	private static TextView startingpointtv;
	private static TextView destinationpointtv;
	private RadioButton currentpositionrb;
	private RadioButton categoryrb;
	private RadioButton selectdestinationcategoryrd;
	private ArrayList<String> items = new ArrayList<String>();
	private TestLocalSqliteDatabase testDB;
	private static final String debugTag = "FindPathActivity";
	private static int SPLASH_TIME_OUT = 5000;
	private ArrayList<String> categoryPlacesList;
	private String flag;
	private FragmentTransaction fragmentTransaction;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.settingsmapfragment, container, false);	
		
		//InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    
		
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase(debugTag);
		categoryPlacesList = new ArrayList<String>();
		
		//listView = (ListView) findViewById(R.id.list);
		
		startingpointtv = (TextView) view.findViewById(R.id.startingpointtv);
		destinationpointtv = (TextView) view.findViewById(R.id.destinationpointtv);
		disarableLocationEditText = (EditText) view.findViewById(R.id.pickyourdisarablelocationet);
		disarabledestLocationEditText = (EditText) view.findViewById(R.id.pickyourdisarabledestlocationet);
		currentpositionrb = (RadioButton) view.findViewById(R.id.currentpositionrd);
		categoryrb = (RadioButton) view.findViewById(R.id.selectcategoryrd);
		selectdestinationcategoryrd = (RadioButton) view.findViewById(R.id.selectdestinationcategoryrd);
		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(disarableLocationEditText, InputMethodManager.SHOW_IMPLICIT);
		InputMethodManager imm2 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm2.showSoftInput(disarabledestLocationEditText, InputMethodManager.SHOW_IMPLICIT);
	
		
		return view;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		listView = getListView();
		
		destinationpointtv.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (startingpointtv.getText().length()> 1){
				GoogleMapFragment g = new GoogleMapFragment();
				Bundle onmapBundle = new Bundle();
		        onmapBundle.putDouble("doubleCurrentLatitude", 233.34);
		        onmapBundle.putDouble("doubleCurrentLongtitude", 2311.45);
		        onmapBundle.putDouble("doublePlaceLatitude", 232.45);
		        onmapBundle.putDouble("doublePlaceLongtitude", 23222.11);
		        g.setArguments(onmapBundle);
		        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containermapview, g);
		        //fragmentTransaction.addToBackStack(null);
		        fragmentTransaction.commit();}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		});
		
		startingpointtv.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (destinationpointtv.getText().length() > 1){
				
				GoogleMapFragment g = new GoogleMapFragment();
				Bundle onmapBundle = new Bundle();
		        onmapBundle.putDouble("doubleCurrentLatitude", 233.34);
		        onmapBundle.putDouble("doubleCurrentLongtitude", 2311.45);
		        onmapBundle.putDouble("doublePlaceLatitude", 232.45);
		        onmapBundle.putDouble("doublePlaceLongtitude", 23222.11);
		        g.setArguments(onmapBundle);
		        fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containermapview, g);
		        //fragmentTransaction.addToBackStack(null);
		        fragmentTransaction.commit();}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		});
		
		
		
		disarabledestLocationEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				flag = "destinationPoint";
			    Editable getEditableText = disarabledestLocationEditText.getText();
			    String getStringText = getEditableText.toString();
				Log.i("GET TEXT FROM EDIT TEXT =>",  getStringText);
				loadData(getStringText, flag);
			}	
		});
		
		
		disarableLocationEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {
				//listView.setVisibility(View.GONE);
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				flag = "startingPoint";
			    Editable getEditableText = disarableLocationEditText.getText();
			    String getStringText = getEditableText.toString();
				Log.i("GET TEXT FROM EDIT TEXT =>",  getStringText);
				loadData(getStringText, flag);
			}
		});
		
		
		
		
		currentpositionrb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true){
					startingpointtv.setText("current location");
				
					
					
					//setListAdapter(null);
				}
			}
		});
		
		selectdestinationcategoryrd.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				registerForContextMenu(buttonView); 
				getActivity().openContextMenu(buttonView);	
			}
			
		});
		
		categoryrb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true){
					registerForContextMenu(buttonView); 
					getActivity().openContextMenu(buttonView);	
				}
			}	
		});
	}

	private void loadData(String getStringText, String flag) {
		items.clear();
		
		//char ch = getStringText.charAt(0);
		
		String pattern = "^[A-Za-z0-9. ]+$";
		if (getStringText.matches(pattern)){
			if (getStringText.length()>2){	
				
				String columns[] = new String[] {"_id", "name_en"};
				Object[] temp = new Object[] { 0, "default" };
	
				MatrixCursor cursor = new MatrixCursor(columns);
	
	
				Cursor c = testDB.searchByPlaceNameEn(getStringText);
	
				try{
					if (c == null){
					Log.i("Message Matched =>", "false");
				}
				else{
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
	
				String lang = "Latin";
				listView.setAdapter(new DisarableLocationCursorAdapter(getActivity(), cursor, items, flag, lang));

			}
		else{
				listView.setAdapter(null);
			}
		}
		else{
			Toast.makeText(getActivity(), "Greek input", Toast.LENGTH_SHORT).show();
		
	
		
			if (getStringText.length()>2){	
		
					String columns[] = new String[] {"_id", "nameel_lower"};
					Object[] temp = new Object[] { 0, "default" };
		
					MatrixCursor cursor = new MatrixCursor(columns);
		
		
					Cursor c = testDB.searchByPlaceName(getStringText);
		
					try{
						if (c == null){
						Log.i("Message Matched =>", "false");
					}
					else{
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
					String lang= "Greek";
					listView.setAdapter(new DisarableLocationCursorAdapter(getActivity(), cursor, items, flag, lang));
	
				}
			else{
					listView.setAdapter(null);
				}
   
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.selectcategoryrd){
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.all_places_menu, menu);
		}
		else{
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.destination_menu, menu);
		}
	}

	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.mouseiadest:
			Bundle selectPlaceListdestFragmentBundle = new Bundle();
			selectPlaceListdestFragmentBundle.putString("genre", "museums");
			selectPlaceListdestFragmentBundle.putString("flag", "destination");
			SelectByCategoryPlacesListFragment selectByCategoryPlaceListdestFragment = new SelectByCategoryPlacesListFragment();
			selectByCategoryPlaceListdestFragment.setArguments(selectPlaceListdestFragmentBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containermapview, selectByCategoryPlaceListdestFragment);
			//fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		break;
		case R.id.mouseia:
			Bundle selectPlaceListFragmentBundle = new Bundle();
			selectPlaceListFragmentBundle.putString("genre", "museums");
			selectPlaceListFragmentBundle.putString("flag", "startingpoint");
			SelectByCategoryPlacesListFragment selectByCategoryPlaceListFragment = new SelectByCategoryPlacesListFragment();
			selectByCategoryPlaceListFragment.setArguments(selectPlaceListFragmentBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containermapview, selectByCategoryPlaceListFragment);
			//fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			/*Cursor mc = testDB.getSpecificPlaceData("museums");
			if (mc.moveToFirst()){
				do{
					String s = mc.getString(mc.getColumnIndex("name_el"));
					categoryPlacesList.add(s);
				}while(mc.moveToNext());
			}*/
			
			//ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryPlacesList);
			//setListAdapter(mAdapter);
			
			
		break;
		case R.id.nosokomeia:
			Cursor nc = testDB.getSpecificPlaceData("hospitals");
			if (nc.moveToFirst()){
				do{
					String s = nc.getString(nc.getColumnIndex("name_el"));
					categoryPlacesList.add(s);
				}while(nc.moveToNext());
			}
			//ArrayAdapter<String> nAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categoryPlacesList);
			//setListAdapter(nAdapter);
		break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}
	
	
	public static void s(String text, String flag){
		if (flag.equals("startingpoint")){
			startingpointtv.setVisibility(View.VISIBLE);
			startingpointtv.setText(text);
		}
		else{
			destinationpointtv.setVisibility(View.VISIBLE);
			destinationpointtv.setText(text);
		}
	} 
	
	
	
	
	 public static void setStartingPointTextViewText(String text){
	       listView.setAdapter(null);
	       //disarableLocationEditText.setInputType(0);
	       //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		   startingpointtv.setVisibility(View.VISIBLE);
		   startingpointtv.setText(text);
		   disarableLocationEditText.setText("");
	    }
	 
	 public static void setDestinantionPointTextViewText(String text){
	        listView.setAdapter(null);
	        //disarableLocationEditText.setInputType(0);
	        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	       destinationpointtv.setVisibility(View.VISIBLE);
	       destinationpointtv.setText(text);
	 	   disarabledestLocationEditText.setText("");
	 	 
	     }
	
	

}

package com.example.fragmentClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.adapters.DisarableLocationCursorAdapter;
import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class SettingsMapFragment extends ListFragment implements DialogInterface.OnClickListener {

	private static EditText disarableLocationEditText;
	private static EditText disarabledestLocationEditText;
	private static ListView listView;
	private static TextView startingpointtv;
	private static TextView destinationpointtv;
	private static TextView startpointlabeltv;
	private static TextView fromtv;
	private static TextView totv;
	private CheckBox currentpositioncb;
	private CheckBox currentpositiondestcb;
	private Button selectcategoryb;
	private Button selectdestinationcategoryb;
	private ArrayList<String> items = new ArrayList<String>();
	private TestLocalSqliteDatabase testDB;
	private static final String debugTag = "FindPathActivity";
	//private static int SPLASH_TIME_OUT = 5000;
	private ArrayList<String> categoryPlacesList;
	private String flag;
	private FragmentTransaction fragmentTransaction;
	private double startlatitude;
	private double startlongtitude;
	private double destlatitude;
	private double destlongtitude;
	private double startlatitude2;
	private double startlongtitude2;
	private double destlatitude2;
	private double destlongtitude2;
	private GPSTracker gps;
	private static String language;
	ArrayAdapter<String> dataAdapter = null;
	List<String> locationsList;
	
	String startpointcontent;
	String destpointcontent;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.settingsmapfragment, container, false);	
		language = getArguments().getString("language");
		//InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
	    
		
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase(debugTag);
		categoryPlacesList = new ArrayList<String>();
		
		//listView = (ListView) findViewById(R.id.list);
		startpointlabeltv = (TextView) view.findViewById(R.id.startpointlabeltv);
		destinationpointtv = (TextView) view.findViewById(R.id.destinationpointlabeltv);
		currentpositioncb = (CheckBox) view.findViewById(R.id.currentpositioncb);
		selectcategoryb = (Button) view.findViewById(R.id.selectcategoryb);
		disarableLocationEditText = (EditText) view.findViewById(R.id.pickyourdisarablelocationet);
		disarabledestLocationEditText = (EditText) view.findViewById(R.id.pickyourdisarabledestlocationet);
		currentpositiondestcb = (CheckBox) view.findViewById(R.id.currentpositiondestcb);
		selectdestinationcategoryb = (Button) view.findViewById(R.id.selectdestcategoryb);
		fromtv = (TextView) view.findViewById(R.id.fromtv);
		totv = (TextView) view.findViewById(R.id.totv);
	if (language.equals("Greek")){
		 startpointlabeltv.setText("Αφετηρία");
		 currentpositioncb.setText("Τρέχουσα θέση");
		 selectcategoryb.setText("Κατηγορία");
		 disarableLocationEditText.setHint("Ψάξε τοποθεσία");
		 destinationpointtv.setText("Προορισμός");
		 currentpositiondestcb.setText("Τρέχουσα θέση");
		 selectdestinationcategoryb.setText("Κατηγορία");
		 disarabledestLocationEditText.setHint("Ψάξε τοποθεσία");
		// fromtv.setText("Από");
		// totv.setText("Προς");
	 }
	 else{
		 startpointlabeltv.setText("Starting Point");
		 currentpositioncb.setText("Current location");
		 selectcategoryb.setText("Pick category");
		 disarableLocationEditText.setHint("Search location");
		 destinationpointtv.setText("Destination");
		 currentpositiondestcb.setText("Current location");
		 selectdestinationcategoryb.setText("Pick category");
		 disarabledestLocationEditText.setHint("Search location");
		 //fromtv.setText("From");
		 //totv.setText("To");
	 }
		
		startingpointtv = (TextView) view.findViewById(R.id.startingpointtv);
		destinationpointtv = (TextView) view.findViewById(R.id.destinationpointtv);
		
		//selectdestinationcategoryrd = (RadioButton) view.findViewById(R.id.selectdestinationcategoryrd);
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
		 locationsList = new ArrayList<String>();
		
		Cursor allplaces = testDB.getAllPlaces();
		if (allplaces.moveToFirst()){
			do{
				String name = allplaces.getString(allplaces.getColumnIndex("name_el"));
				locationsList.add(name);
			}while(allplaces.moveToNext());
		}
		
		
		  
		  dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, locationsList); 
		  
		  listView.setAdapter(dataAdapter);
		  listView.setTextFilterEnabled(true);
		
		destinationpointtv.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				String startpointtvcontent = startingpointtv.getText().toString();
				int ls = startpointtvcontent.length();
				String sls = Integer.toString(ls);
				Log.i("starting point tv characters =>", sls);
				
				String destv = destinationpointtv.getText().toString();
				int ld = destv.length();
				String sld = Integer.toString(ld);
				Log.i("starting point tv characters =>", sld);
			
				if (ls > 5){
					startpointcontent = startpointtvcontent.substring(6, ls);
				}
				if (ld > 5){
					destpointcontent = destv.substring(6, ld);
				}
			 if (!language.equals("English")){
				 
				// Toast.makeText(getActivity(), startpointcontent, Toast.LENGTH_SHORT).show();
				//	Toast.makeText(getActivity(), destpointcontent, Toast.LENGTH_SHORT).show();
			 }	
				
			 if (!currentpositioncb.isChecked() && !currentpositiondestcb.isChecked()){
					totv.setText("");
				}
			 
				boolean flag = false;
				//Log.i("start contents =>", startpointcontent);
				Log.i("dest contents =>", destpointcontent);
				if (ls > 5 && !startpointcontent.toString().equals(destpointcontent)){
					flag = true;
					Toast.makeText(getActivity(), "contents not equal", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(), "contents equal", Toast.LENGTH_SHORT).show();
					
				}
		
				if (startingpointtv.getText().length()> 0 &&  flag == true ){
					
					if (startpointcontent.toString().equals("current location") || startpointcontent.toString().equals("Τρέχουσα θέση")){
						gps = new GPSTracker(getActivity());
						
						if (gps.canGetLocation()){
							startlatitude = gps.getLatitude();
							startlongtitude = gps.getLongitude();
						}
						else
						{
				            gps.showSettingsAlert();
				        }
					}
					else{
						Cursor startcursor = testDB.getPlaceByNameEl(startpointcontent.toString());
							if (startcursor.moveToFirst()){
								do{
									startlatitude = startcursor.getDouble(startcursor.getColumnIndex("latitude"));
									startlongtitude = startcursor.getDouble(startcursor.getColumnIndex("longtitude"));
								}while(startcursor.moveToNext());
							}
					}
					
					
					if (destpointcontent.toString().equals("current location") || destpointcontent.toString().equals("Τρέχουσα θέση")){
						gps = new GPSTracker(getActivity());
						
						if (gps.canGetLocation()){
							destlatitude = gps.getLatitude();
							destlongtitude = gps.getLongitude();
						}
						else
						{
				            gps.showSettingsAlert();
				        }
					}else{
						Cursor destcursor = testDB.getPlaceByNameEl(destpointcontent.toString());
						if (destcursor.moveToFirst()){
							do{
								destlatitude = destcursor.getDouble(destcursor.getColumnIndex("latitude"));
					       		destlongtitude = destcursor.getDouble(destcursor.getColumnIndex("longtitude"));
							}while(destcursor.moveToNext());
						}
					}
					
					if (totv.getText().toString().equals("")){
						
					}else{	
						ToAndFromFragment t = new ToAndFromFragment();
						Bundle locations_langBundle = new Bundle();
						locations_langBundle.putString("language", language);
						locations_langBundle.putString("fromlocation", startingpointtv.getText().toString());
						locations_langBundle.putString("tolocation", destinationpointtv.getText().toString());
						t.setArguments(locations_langBundle);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containertv, t);
						fragmentTransaction.addToBackStack("t");
						fragmentTransaction.commit();
					
						GoogleMapFragment g = new GoogleMapFragment();
						Bundle onmapBundle = new Bundle();
						onmapBundle.putString("language", language);
						onmapBundle.putDouble("doubleCurrentLatitude", startlatitude);
						onmapBundle.putDouble("doubleCurrentLongtitude", startlongtitude);
						onmapBundle.putDouble("doublePlaceLatitude", destlatitude);
						onmapBundle.putDouble("doublePlaceLongtitude", destlongtitude);
						g.setArguments(onmapBundle);
						testDB.close(debugTag);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containersettings, g);
						fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
				  }
				}	
				else if (startingpointtv.getText().length() == 0){
					
				}
				else {
					AlertDialog ald = new AlertDialog.Builder(getActivity())
					.setMessage("Destination must differ from Starting Point")
					//.setNeutralButton("Cancel", this)
					//.setPositiveButton("OK", this)
					.create();
					ald.show();	
				}
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {}
		});
		
		startingpointtv.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				String destv = destinationpointtv.getText().toString();
				int ld = destv.length();
				String sld = Integer.toString(ld);
				Log.i("starting point tv characters =>", sld);
				
				String startpointtvcontent = startingpointtv.getText().toString();
				int ls = startpointtvcontent.length();
				String sls = Integer.toString(ls);
				Log.i("starting point tv characters =>", sls);
				
				if (ls > 5){
					startpointcontent = startpointtvcontent.substring(6, ls);
				}
			    if (ld > 5){
			    	destpointcontent = destv.substring(6, ld);
				}
				
			    if (!currentpositioncb.isChecked() && !currentpositiondestcb.isChecked()){
					fromtv.setText("");
				}	
			    
				boolean flag = false;
				if (ld > 5 && !startpointcontent.toString().equals(destpointcontent)){
					flag = true;
					Toast.makeText(getActivity(), "contents not equal", Toast.LENGTH_SHORT).show();
				}else if (destinationpointtv.getText().toString().equals("")){
					flag = false;
				}
				else{
					Toast.makeText(getActivity(), "contents equal", Toast.LENGTH_SHORT).show();	
				}
			
				if (destinationpointtv.getText().length() > 0 && flag == true){
					
					
					if (destpointcontent.toString().equals("current location") || destpointcontent.toString().equals("Τρέχουσα θέση")){
						gps = new GPSTracker(getActivity());
						
						if (gps.canGetLocation()){
							destlatitude2 = gps.getLatitude();
							destlongtitude2 = gps.getLongitude();
						}
						else
						{
				            gps.showSettingsAlert();
				        }
					}
					else{
						Cursor startcursor = testDB.getPlaceByNameEl(destpointcontent.toString());
						if (startcursor.moveToFirst()){
							do{
								destlatitude2 = startcursor.getDouble(startcursor.getColumnIndex("latitude"));
						   		destlongtitude2 = startcursor.getDouble(startcursor.getColumnIndex("longtitude"));	
							}while(startcursor.moveToNext());
						}
					}
					
					if (startpointcontent.toString().equals("current location") || startpointcontent.toString().equals("Τρέχουσα θέση")){
						gps = new GPSTracker(getActivity());
						
						if (gps.canGetLocation()){
							startlatitude2 = gps.getLatitude();
							startlongtitude2 = gps.getLongitude();
						}
						else
						{
				            gps.showSettingsAlert();
				        }
					}else{ 
						Cursor destcursor = testDB.getPlaceByNameEl(startpointcontent.toString());
							if (destcursor.moveToFirst()){
								do{
									startlatitude2 = destcursor.getDouble(destcursor.getColumnIndex("latitude"));
									startlongtitude2 = destcursor.getDouble(destcursor.getColumnIndex("longtitude"));	
								}while(destcursor.moveToNext());
							}
					}
						
					if (fromtv.getText().toString().equals("")){
						
					}else{	
						ToAndFromFragment t = new ToAndFromFragment();
						Bundle locations_langBundle = new Bundle();
						locations_langBundle.putString("language", language);
						locations_langBundle.putString("fromlocation", startingpointtv.getText().toString());
						locations_langBundle.putString("tolocation", destinationpointtv.getText().toString());
						t.setArguments(locations_langBundle);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containertv, t);
						fragmentTransaction.addToBackStack("t");
						fragmentTransaction.commit();
					
						GoogleMapFragment g = new GoogleMapFragment();
						Bundle onmapBundle = new Bundle();
						onmapBundle.putString("language", language);
						onmapBundle.putDouble("doubleCurrentLatitude", startlatitude2);
						onmapBundle.putDouble("doubleCurrentLongtitude", startlongtitude2);
						onmapBundle.putDouble("doublePlaceLatitude", destlatitude2);
						onmapBundle.putDouble("doublePlaceLongtitude", destlongtitude2);
						g.setArguments(onmapBundle);
						testDB.close(debugTag);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containersettings, g);
						fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
					}
				}
				else if (destinationpointtv.getText().length() == 0){
					
				}
				else{
					AlertDialog ald = new AlertDialog.Builder(getActivity())
					.setMessage("Starting Point must differ from Destination")
					//.setNeutralButton("Cancel", this)
					//.setPositiveButton("OK", this)
					.create();
					ald.show();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {}
			
		});
		
		disarabledestLocationEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String s1 = s.toString();
				
				 listView.setAdapter(dataAdapter);
				Log.i("char sequence =>", s1);
				if (s.length() <1){
					listView.setAdapter(null);
				}else{	
					dataAdapter.getFilter().filter(s.toString());
					listView.setVisibility(View.VISIBLE);
				}
				
				
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						if (language.equals("English")){
							destinationpointtv.setText("To: " + ((TextView) view).getText());
							listView.setAdapter(null);
							disarabledestLocationEditText.setText("");
						}
						else{
							destinationpointtv.setText("Προς: " + ((TextView) view).getText());
							listView.setAdapter(null);
							disarabledestLocationEditText.setText("");
						}
					}
				});
			}	
		});
		
		disarableLocationEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				
				
				String s1 = s.toString();
				
				 listView.setAdapter(dataAdapter);
		
				Log.i("char sequence =>", s1);
				if (s.length() <1){
					listView.setAdapter(null);
				}else{	
					dataAdapter.getFilter().filter(s.toString());
					listView.setVisibility(View.VISIBLE);
				}
				
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						if (language.equals("English")){
							startingpointtv.setText("From: " + ((TextView) view).getText());
							listView.setAdapter(null);
							disarableLocationEditText.setText("");
						}
						else{
							startingpointtv.setText("Από:  " + ((TextView) view).getText());
							listView.setAdapter(null);
							disarableLocationEditText.setText("");
						}
					}
				});
			}
		});
		
		currentpositiondestcb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true){
					destinationpointtv.setVisibility(View.VISIBLE);
					if (language.equals("English")){
						totv.setText("current location");
						destinationpointtv.setText("To: current location");
					}else{
						//totv.setText("Τρέχουσα θέση");
						destinationpointtv.setText("Προς: Τρέχουσα θέση");
					}
				}else{
					destinationpointtv.setText("");
					//destinationpointtv.setVisibility(View.INVISIBLE);
					totv.setText("");
				}
			}
		});
		
		currentpositioncb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true){
					startingpointtv.setVisibility(View.VISIBLE);
					if (language.equals("English")){
						fromtv.setText("current location");
						startingpointtv.setText("From: current location");
					}else{
					    //fromtv.setText("Τρέχουσα θέση");
						startingpointtv.setText("Από:  Τρέχουσα θέση");
					}
				}else{
					startingpointtv.setText("");
					//startingpointtv.setVisibility(View.INVISIBLE);
					fromtv.setText("");
				}
			  }
		});
		 
		 	
		selectdestinationcategoryb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				currentpositiondestcb.setChecked(false);
				registerForContextMenu(v); 
				getActivity().openContextMenu(v);		
			}
		});
		
		selectcategoryb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				currentpositioncb.setChecked(false);
				registerForContextMenu(v); 
				getActivity().openContextMenu(v);	
			}
		});
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.selectcategoryb){
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
	    List<String> list = new ArrayList<String>();
			
			Cursor specificPlacecursor = testDB.getSpecificPlaceData("museums");
			if (specificPlacecursor.moveToFirst()){
				do{
					String name = specificPlacecursor.getString(specificPlacecursor.getColumnIndex("name_el"));
					list.add(name);
				}while(specificPlacecursor.moveToNext());
			}
			  
			  ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			  listView.setVisibility(View.VISIBLE);
			  listView.setAdapter(dataAdapter1);
			
			  listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text = ((TextView) view).getText().toString();
						if (language.equals("English")){
							destinationpointtv.setText("To: " + text);
							totv.setText(text);
							//listView.setAdapter(null);
							disarabledestLocationEditText.setText("");
						}
						else{
							destinationpointtv.setText("Προς: " + text);
							totv.setText(text);
							//listView.setAdapter(null);
							disarabledestLocationEditText.setText("");
						}
					}
			});
		break;
		case R.id.mouseia:
			
			List<String> list1 = new ArrayList<String>();
			
			Cursor specificPlacecursor1 = testDB.getSpecificPlaceData("museums");
			if (specificPlacecursor1.moveToFirst()){
				do{
					String name = specificPlacecursor1.getString(specificPlacecursor1.getColumnIndex("name_el"));
					list1.add(name);
				}while(specificPlacecursor1.moveToNext());
			}
			  
			 ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.countries, list1); 
			  listView.setVisibility(View.VISIBLE);
			  listView.setAdapter(dataAdapter2);
			
			  listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						if (language.equals("English")){
							startingpointtv.setText("From: " + text1);
							fromtv.setText(text1);
							//listView.setAdapter(null);
							disarableLocationEditText.setText("");
						}
						else{
							startingpointtv.setText("Από:  " + text1);
							fromtv.setText(text1);
							//listView.setAdapter(null);
							disarableLocationEditText.setText("");
						}
					}
			});
			  
			//Bundle selectPlaceListFragmentBundle = new Bundle();
			//selectPlaceListFragmentBundle.putString("genre", "museums");
			//selectPlaceListFragmentBundle.putString("flag", "startingpoint");
			//SelectByCategoryPlacesListFragment selectByCategoryPlaceListFragment = new SelectByCategoryPlacesListFragment();
			//selectByCategoryPlaceListFragment.setArguments(selectPlaceListFragmentBundle);
			//fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containermapview, selectByCategoryPlaceListFragment);
			//fragmentTransaction.addToBackStack(null);
			//fragmentTransaction.commit();
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
	
	/*
	public static void s(String text, String flag){
		if (flag.equals("startingpoint")){
			if (language.equals("English")){
				startingpointtv.setVisibility(View.VISIBLE);
				fromtv.setText(text);
				startingpointtv.setText("From: " + text);
			}else{
				startingpointtv.setVisibility(View.VISIBLE);
				fromtv.setText(text);
				startingpointtv.setText("Από: " + text);
			}
		}
		else{
			if (language.equals("English")){
				destinationpointtv.setVisibility(View.VISIBLE);
				totv.setText(text);
				destinationpointtv.setText("To: " + text);
			}else{
				destinationpointtv.setVisibility(View.VISIBLE);
				totv.setText(text);
				destinationpointtv.setText("Προς: " + text);
			}
			
		}
	} 
	
	
	
	
	 public static void setStartingPointTextViewText(String text){
	       listView.setAdapter(null);
	       //disarableLocationEditText.setInputType(0);
	       //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		  if (language.equals("English")){
			  startingpointtv.setVisibility(View.VISIBLE);
			  fromtv.setText(text);
			  startingpointtv.setText("From: " + text);
			  disarableLocationEditText.setText("");
		  }else{ 
			  startingpointtv.setVisibility(View.VISIBLE);
			  fromtv.setText(text);
			  startingpointtv.setText("Από: " + text);
			  disarableLocationEditText.setText("");
		  }
	    }
	 
	 public static void setDestinantionPointTextViewText(String text){
	        listView.setAdapter(null);
	        //disarableLocationEditText.setInputType(0);
	        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	     if (language.equals("English")){
	    	 destinationpointtv.setVisibility(View.VISIBLE);
	    	 totv.setText(text);
		     destinationpointtv.setText("To: " + text);
		 	 disarabledestLocationEditText.setText("");
	     }else{  
	    	 destinationpointtv.setVisibility(View.VISIBLE);
	    	 totv.setText(text);
	    	 destinationpointtv.setText("Προς: " + text);
	    	 disarabledestLocationEditText.setText("");
	     }
	 	   
	 }*/

	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	
	

}

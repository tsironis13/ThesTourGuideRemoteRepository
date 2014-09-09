package com.example.fragmentClasses;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;
import com.example.thesguideproject.SearchPlaceResutlActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CloseExpandableListFragment extends Fragment{

	private static final String debugTag = "CloseExpandableListFragment";
	private TextView messagetv;
	public String language;
	private ExpandableListView explvlist;
	private ArrayList<String> listDataHeader;
	private HashMap<String, ArrayList<String>> listDataChild;
	private Double curlatitude = 40.639431;
	private Double curlongtitude = 22.937125;
	private final double min_distance = 2;
	private TestLocalSqliteDatabase testDB;
	//private Cursor cursor;
	private Cursor cursor;
	private String name;
	private HashMap<String, Double> museumsDistances;
	private HashMap<String, Double> hospitalsDistances;
	private HashMap<String, Double> sightsDistances;
	private HashMap<String, Double> foodDistances;
	private HashMap<String, Double> churchDistances;
	private HashMap<String, Double> nightlifeDistances;
	private HashMap<String, Double> barrestDistances;
	private HashMap<String, Double> restDistances;
	private HashMap<String, Double> intercDistances;
	private HashMap<String, Double> seafDistances;
	private HashMap<String, Double> byzDistances;
	private HashMap<String, Double> basDistances;
	private HashMap<String, Double> paleoDistances;
	private HashMap<String, Double> macDistances;
	private HashMap<String, Double> barsDistances;
	private HashMap<String, Double> clubsDistances;
	private HashMap<String, Double> pubsDistances;
	private int sum_museums = 0;
	private int sum_hospitals = 0;
	private int sum_sights = 0;
	private int sum_food = 0;
	private int sum_church = 0;
	private int sum_nightlife = 0;
	private int sum_seafood;
	private int sum_intcuis;
	private int sum_barrest;
	private int sum_rest;
	private int sum_byz;
	private int sum_bas;
	private int sum_pal;
	private int sum_mac;
	private int sum_clubs;
	private int sum_bars;
	private int sum_pubs;
	private GPSTracker gps;
	//private double curlatitude;
	//private double curlongtitude;
	ArrayList<String> oncalllist;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.expandablelistfragment, container, false);
		language = getArguments().getString("language");
		
		gps = new GPSTracker(getActivity());
		
		if (gps.canGetLocation()){
			curlatitude = gps.getLatitude();
			curlongtitude = gps.getLongitude();
		}
		else
		{
	        gps.showSettingsAlert();
	    }
		
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase(debugTag);
		
		museumsDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificPlaceData("museums");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
				
				if (distanceInKm <= min_distance){
					museumsDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		hospitalsDistances = new HashMap<String, Double>();
		oncalllist = new ArrayList<String>();
		cursor = testDB.getSpecificPlaceData("hospitals");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				String oncall = this.cursor.getString(this.cursor.getColumnIndex("menu"));
				
					if (oncall.equals("yes")){
						oncalllist.add(name);
					}
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					hospitalsDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		sightsDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificPlaceData("sightseeings");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					sightsDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		foodDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificPlaceData("food");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					foodDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		churchDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificPlaceData("church");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
				
				if (distanceInKm <= min_distance){
					churchDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		nightlifeDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificPlaceData("nightlife");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
				
				if (distanceInKm <= min_distance){
					nightlifeDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		barrestDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("bar-restaurant");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
				
				if (distanceInKm <= min_distance){
					barrestDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		restDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("restaurants");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					restDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		intercDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("intcuisine");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					intercDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		seafDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("seafood");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					seafDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		byzDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("Byzantine");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
		
				if (distanceInKm <= min_distance){
					byzDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		basDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("Basiliki");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
		
				if (distanceInKm <= min_distance){
					basDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		paleoDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("PaleoChristian");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
		
				if (distanceInKm <= min_distance){
					paleoDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		macDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("Macedonian");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					macDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		clubsDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("clubs");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					clubsDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		barsDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("bars");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					barsDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		pubsDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificChurchData("pubs");
		if (cursor.moveToFirst()){
			do{
			  if (language.equals("English")){	
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
			  }else{
				 name = this.cursor.getString(this.cursor.getColumnIndex("name_el")); 
			  }
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
			
				if (distanceInKm <= min_distance){
					pubsDistances.put(name, distanceInKm);
					//Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		messagetv = (TextView) view.findViewById(R.id.messageclosetv);
		
		if (language.equals("English")){
			messagetv.setText("Places with distance less than 2km");
		}else{
			messagetv.setText("Μέρη με απάσταση μικρότερη απο 2χμ");
		}
		
		 explvlist = (ExpandableListView) view.findViewById(R.id.explistview);
		 
		 //preparing list data
	     prepareListData();
		 
         explvlist.setAdapter(new ParentLevelListAdapter(listDataHeader, listDataChild));

		return view;
	}


	private void prepareListData(){
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, ArrayList<String>>();
		
		// Adding child data
		if (language.equals("English")){
			listDataHeader.add("Sightseeings");
			listDataHeader.add("Museums");
			listDataHeader.add("Hospitals");
			listDataHeader.add("Nightlife");
			listDataHeader.add("Food");
			listDataHeader.add("Churches");
			
        }else{
        	listDataHeader.add("Αξιοθέατα");
			listDataHeader.add("Μουσεία");
			listDataHeader.add("Νοσοκομεία");
			listDataHeader.add("Νυχτερινή Ζωή");
			listDataHeader.add("Φαγητό");
			listDataHeader.add("Εκκλησίες");
        }
		
		if (language.equals("English") || language.equals("Greek")){
			// Adding child data
			ArrayList<String> sightseeings = new ArrayList<String>();
	        Iterator sightsIterator = sightsDistances.keySet().iterator();
	        while(sightsIterator.hasNext()) {
	        	String key=(String) sightsIterator.next();
	            Double value=(Double) sightsDistances.get(key);
	        	
	            sightseeings.add(key);
	            sum_sights = sum_sights + 1;
	        } 	    
			
	        ArrayList<String> museums = new ArrayList<String>();
	        Iterator museumsIterator = museumsDistances.keySet().iterator();
	        while(museumsIterator.hasNext()) {
	        	String key=(String) museumsIterator.next();
	            Double value=(Double) museumsDistances.get(key);
	        	Log.i("distance mus => ", value.toString());
	        	museums.add(key);
	            sum_museums = sum_museums + 1;
	        	
	        }
	        
	        ArrayList<String> hospitals = new ArrayList<String>();
	        Iterator hospitalsIterator = hospitalsDistances.keySet().iterator();
	        while(hospitalsIterator.hasNext()) {
	        	String key=(String) hospitalsIterator.next();
	            Double value=(Double) hospitalsDistances.get(key);
	        	
	            hospitals.add(key);
	            sum_hospitals = sum_hospitals + 1;
	        } 	    
	        
	        //ArrayList<String> hospitals = new ArrayList<String>();
	        Iterator foodIterator = foodDistances.keySet().iterator();
	        while(foodIterator.hasNext()) {
	        	String key=(String) foodIterator.next();
	            Double value=(Double) foodDistances.get(key);
	        	
	           // hospitals.add(key);
	            sum_food = sum_food + 1;
	        } 	
	        
	        Iterator churchIterator = churchDistances.keySet().iterator();
	        while(churchIterator.hasNext()) {
	        	String key=(String) churchIterator.next();
	            Double value=(Double) churchDistances.get(key);
	        	
	           // hospitals.add(key);
	            sum_church = sum_church + 1;
	        } 	
	        
	        Iterator nightlifeIterator = nightlifeDistances.keySet().iterator();
	        while(nightlifeIterator.hasNext()) {
	        	String key=(String) nightlifeIterator.next();
	            Double value=(Double) nightlifeDistances.get(key);
	        	
	           // hospitals.add(key);
	            sum_nightlife = sum_nightlife + 1;
	        } 	
	    
	        listDataChild.put(listDataHeader.get(0), sightseeings); // Header, Child data
	        listDataChild.put(listDataHeader.get(1), museums);
	        listDataChild.put(listDataHeader.get(2), hospitals);
	        //listDataChild.put(listDataHeader.get(2), nightlife);
	        
		}else{
			
		}
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		explvlist.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {           
				String item = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
			 if (language.equals("English")){	
				Intent intent = new Intent(getActivity(), SearchPlaceResutlActivity.class);
				intent.putExtra("PlaceName", item);
				intent.putExtra("language", "English");
				startActivity(intent);
			 }else{
				 Intent intent = new Intent(getActivity(), SearchPlaceResutlActivity.class);
				 intent.putExtra("PlaceName", item);
				 intent.putExtra("language", "Greek");
				 startActivity(intent); 
			 }	
				/*Toast.makeText(getActivity(),listDataHeader.get(groupPosition) + " : "
                                + listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT)
                        .show(); */
				return false;
			}			
		});
	}

	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}



	public class ParentLevelListAdapter extends BaseExpandableListAdapter {

		private ArrayList<String> listDataHeader; // header titles
		private HashMap<String, ArrayList<String>> listDataChild;
		
		private ArrayList<String> listSecondLevelHeader;
		private HashMap<String, ArrayList<String>> listSecondLevelDataChild;
		
		public ParentLevelListAdapter(ArrayList<String> listDataHeader,  HashMap<String, ArrayList<String>> listChildData){
			this.listDataHeader = listDataHeader;
			this.listDataChild = listChildData;
		}
         @Override
         public Object getChild(int groupPosition, int childPosition) {
                return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
         }

         @Override
         public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
         }

       
		@Override
         public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        	 
        	 
        	 String headerTitle = (String) getGroup(groupPosition);		
        	// Toast.makeText(getActivity(), headerTitle, Toast.LENGTH_SHORT).show();
        	 listSecondLevelHeader = new ArrayList<String>();
        	 listSecondLevelDataChild = new HashMap<String, ArrayList<String>>();
        	 
        	 if (headerTitle.equals("Nightlife") || headerTitle.equals("Νυχτερινή Ζωή")){
        		 //String childText = (String) getChild(groupPosition, childPosition);
        		 if (language.equals("English")){
        			 listSecondLevelHeader.add("Bars");
        			 listSecondLevelHeader.add("Clubs");
        			 listSecondLevelHeader.add("Pubs");
        			 
        			  ArrayList<String> bars = new ArrayList<String>();
        			  sum_bars = 0;
          			  Iterator barsIterator = barsDistances.keySet().iterator();
          			  while(barsIterator.hasNext()) {
         	        	String key=(String) barsIterator.next();
         	            Double value=(Double) barsDistances.get(key);
         	        	
         	            bars.add(key);
         	            sum_bars = sum_bars + 1;
          			  } 
        			  
        			  ArrayList<String> clubs = new ArrayList<String>();
        			  sum_clubs = 0;
          			  Iterator clubsIterator = clubsDistances.keySet().iterator();
          			  while(clubsIterator.hasNext()) {
         	        	String key=(String) clubsIterator.next();
         	            Double value=(Double) clubsDistances.get(key);
         	        	
         	            clubs.add(key);
         	            sum_clubs = sum_clubs + 1;
          			  } 
        			 
        			  ArrayList<String> pubs = new ArrayList<String>();
        			  sum_pubs = 0;
          			  Iterator pubsIterator = pubsDistances.keySet().iterator();
          			  while(pubsIterator.hasNext()) {
         	        	String key=(String) pubsIterator.next();
         	            Double value=(Double) pubsDistances.get(key);
         	        	
         	            pubs.add(key);
         	            sum_pubs = sum_pubs + 1;
          			  } 
        			 
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(0), bars); // Header, Child data
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(1), clubs);
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(2), pubs);
        		 }
        		 else{
        			 listSecondLevelHeader.add("Μπαρ");
        			 listSecondLevelHeader.add("Κλαμπ");
        			 listSecondLevelHeader.add("Μπυραρίες");
        			 
        			  ArrayList<String> bars = new ArrayList<String>();
        			  sum_bars = 0;
          			  Iterator barsIterator = barsDistances.keySet().iterator();
          			  while(barsIterator.hasNext()) {
         	        	String key=(String) barsIterator.next();
         	            Double value=(Double) barsDistances.get(key);
         	        	
         	            bars.add(key);
         	            sum_bars = sum_bars + 1;
          			  } 
        			  
        			  ArrayList<String> clubs = new ArrayList<String>();
        			  sum_clubs = 0;
          			  Iterator clubsIterator = clubsDistances.keySet().iterator();
          			  while(clubsIterator.hasNext()) {
         	        	String key=(String) clubsIterator.next();
         	            Double value=(Double) clubsDistances.get(key);
         	        	
         	            clubs.add(key);
         	            sum_clubs = sum_clubs + 1;
          			  } 
        			 
        			  ArrayList<String> pubs = new ArrayList<String>();
        			  sum_pubs = 0;
          			  Iterator pubsIterator = pubsDistances.keySet().iterator();
          			  while(pubsIterator.hasNext()) {
         	        	String key=(String) pubsIterator.next();
         	            Double value=(Double) pubsDistances.get(key);
         	        	
         	            pubs.add(key);
         	            sum_pubs = sum_pubs + 1;
          			  } 
        			 
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(0), bars); // Header, Child data
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(1), clubs);
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(2), pubs);
        		 }
        		 
        		 CustExpListview SecondLevelexplv = new CustExpListview(getActivity());
                 SecondLevelexplv.setAdapter(new SecondLevelAdapter(listSecondLevelHeader, listSecondLevelDataChild));
                 //SecondLevelexplv.setGroupIndicator(null);
                 return SecondLevelexplv;
        	
        	 }	 
        	else if (headerTitle.equals("Food") || headerTitle.equals("Φαγητό")){
        		if (language.equals("English")){
        			listSecondLevelHeader.add("Bar-Restaurants");
       			 	listSecondLevelHeader.add("Restaurants");
       			 	listSecondLevelHeader.add("International Cuisine");
       			    listSecondLevelHeader.add("Seafood");
       			    
       			  ArrayList<String> barrest = new ArrayList<String>();
       			  sum_barrest = 0;
       			  Iterator barrestIterator = barrestDistances.keySet().iterator();
       			  while(barrestIterator.hasNext()) {
      	        	String key=(String) barrestIterator.next();
      	            Double value=(Double) barrestDistances.get(key);
      	        	
      	            barrest.add(key);
      	            sum_barrest = sum_barrest + 1;
       			  } 
       			  
       			  ArrayList<String> rest = new ArrayList<String>();
      			  sum_rest = 0;
      			  Iterator restIterator = restDistances.keySet().iterator();
      			  while(restIterator.hasNext()) {
     	        	String key=(String) restIterator.next();
     	            Double value=(Double) restDistances.get(key);
     	        	
     	            rest.add(key);
     	            sum_rest = sum_rest + 1;
      			  } 	
      			  
      			  ArrayList<String> interc = new ArrayList<String>();
      			  sum_intcuis = 0;
       			  Iterator intIterator = intercDistances.keySet().iterator();
      			  while(intIterator.hasNext()) {
     	        	String key=(String) intIterator.next();
     	            Double value=(Double) intercDistances.get(key);
     	        	
     	            interc.add(key);
     	            sum_intcuis = sum_intcuis + 1;
      			  } 	
      			  
      			  ArrayList<String> seaf = new ArrayList<String>();
      			  sum_seafood = 0; 
      			  Iterator seafIterator = seafDistances.keySet().iterator();
      			  while(seafIterator.hasNext()) {
     	        	String key=(String) seafIterator.next();
     	            Double value=(Double) seafDistances.get(key);
     	        	
     	            seaf.add(key);
     	            sum_seafood = sum_seafood + 1;
      			  } 	
       			   
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(0), barrest);
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(1), rest);
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(2), interc);
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(3), seaf);
       			    
        		}else{
        			listSecondLevelHeader.add("Μπαρ-Ρεστοράν");
       			 	listSecondLevelHeader.add("Ρεστοράν");
       			 	listSecondLevelHeader.add("Διεθνής Κουζίνα");
       			    listSecondLevelHeader.add("Ψαροταβέρνες");
       			    
       			 ArrayList<String> barrest = new ArrayList<String>();
      			  sum_barrest = 0;
      			  Iterator barrestIterator = barrestDistances.keySet().iterator();
      			  while(barrestIterator.hasNext()) {
     	        	String key=(String) barrestIterator.next();
     	            Double value=(Double) barrestDistances.get(key);
     	        	
     	            barrest.add(key);
     	            sum_barrest = sum_barrest + 1;
      			  } 
      			  
      			  ArrayList<String> rest = new ArrayList<String>();
     			  sum_rest = 0;
     			  Iterator restIterator = restDistances.keySet().iterator();
     			  while(restIterator.hasNext()) {
    	        	String key=(String) restIterator.next();
    	            Double value=(Double) restDistances.get(key);
    	        	
    	            rest.add(key);
    	            sum_rest = sum_rest + 1;
     			  } 	
     			  
     			  ArrayList<String> interc = new ArrayList<String>();
     			  sum_intcuis = 0;
      			  Iterator intIterator = intercDistances.keySet().iterator();
     			  while(intIterator.hasNext()) {
    	        	String key=(String) intIterator.next();
    	            Double value=(Double) intercDistances.get(key);
    	        	
    	            interc.add(key);
    	            sum_intcuis = sum_intcuis + 1;
     			  } 	
     			  
     			  ArrayList<String> seaf = new ArrayList<String>();
     			  sum_seafood = 0; 
     			  Iterator seafIterator = seafDistances.keySet().iterator();
     			  while(seafIterator.hasNext()) {
    	        	String key=(String) seafIterator.next();
    	            Double value=(Double) seafDistances.get(key);
    	        	
    	            seaf.add(key);
    	            sum_seafood = sum_seafood + 1;
     			  } 	
      			   
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(0), barrest);
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(1), rest);
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(2), interc);
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(3), seaf);
        		}
        		
        		CustExpListview SecondLevelexplv = new CustExpListview(getActivity());
                SecondLevelexplv.setAdapter(new SecondLevelAdapter(listSecondLevelHeader, listSecondLevelDataChild));
                //SecondLevelexplv.setGroupIndicator(null);
                return SecondLevelexplv;
        	}
        	else if (headerTitle.equals("Churches") || headerTitle.equals("Εκκλησίες")){
        		if (language.equals("English")){
        			listSecondLevelHeader.add("Byzantine");
       			 	listSecondLevelHeader.add("Basiliki");
       			 	listSecondLevelHeader.add("PaleoChristian");
       			    listSecondLevelHeader.add("Macedonian");
       			    
       			  ArrayList<String> byz = new ArrayList<String>();
       			  sum_byz = 0;
       			  Iterator byzIterator = byzDistances.keySet().iterator();
       			  while(byzIterator.hasNext()) {
      	        	String key=(String) byzIterator.next();
      	            Double value=(Double) byzDistances.get(key);
      	        	
      	            byz.add(key);
      	            sum_byz = sum_byz + 1;
       			  } 
       			  
       			  ArrayList<String> bas = new ArrayList<String>();
      			  sum_bas = 0;
      			  Iterator basIterator = basDistances.keySet().iterator();
      			  while(basIterator.hasNext()) {
     	        	String key=(String) basIterator.next();
     	            Double value=(Double) basDistances.get(key);
     	        	
     	            bas.add(key);
     	            sum_bas = sum_bas + 1;
      			  } 	
      			  
      			  ArrayList<String> pal = new ArrayList<String>();
      			  sum_pal = 0;
       			  Iterator palIterator = paleoDistances.keySet().iterator();
      			  while(palIterator.hasNext()) {
     	        	String key=(String) palIterator.next();
     	            Double value=(Double) paleoDistances.get(key);
     	        	
     	            pal.add(key);
     	            sum_pal = sum_pal + 1;
      			  } 	
      			  
      			  ArrayList<String> mac = new ArrayList<String>();
      			  sum_mac = 0; 
      			  Iterator macIterator = macDistances.keySet().iterator();
      			  while(macIterator.hasNext()) {
     	        	String key=(String) macIterator.next();
     	            Double value=(Double) macDistances.get(key);
     	        	
     	            mac.add(key);
     	            sum_mac = sum_mac + 1;
      			  } 	
       			   
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(0), byz);
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(1), bas);
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(2), pal);
       			listSecondLevelDataChild.put(listSecondLevelHeader.get(3), mac);
       			    
        		}else{
        			listSecondLevelHeader.add("Βυζαντινές");
       			 	listSecondLevelHeader.add("Βασιλικές");
       			 	listSecondLevelHeader.add("Παλεό-Χριστιανικές");
       			    listSecondLevelHeader.add("Μακεδονικές");
       			    
       			 ArrayList<String> byz = new ArrayList<String>();
      			  sum_byz = 0;
      			  Iterator byzIterator = byzDistances.keySet().iterator();
      			  while(byzIterator.hasNext()) {
     	        	String key=(String) byzIterator.next();
     	            Double value=(Double) byzDistances.get(key);
     	        	
     	            byz.add(key);
     	            sum_byz = sum_byz + 1;
      			  } 
      			  
      			  ArrayList<String> bas = new ArrayList<String>();
     			  sum_bas = 0;
     			  Iterator basIterator = basDistances.keySet().iterator();
     			  while(basIterator.hasNext()) {
    	        	String key=(String) basIterator.next();
    	            Double value=(Double) basDistances.get(key);
    	        	
    	            bas.add(key);
    	            sum_bas = sum_bas + 1;
     			  } 	
     			  
     			  ArrayList<String> pal = new ArrayList<String>();
     			  sum_pal = 0;
      			  Iterator palIterator = paleoDistances.keySet().iterator();
     			  while(palIterator.hasNext()) {
    	        	String key=(String) palIterator.next();
    	            Double value=(Double) paleoDistances.get(key);
    	        	
    	            pal.add(key);
    	            sum_pal = sum_pal + 1;
     			  } 	
     			  
     			  ArrayList<String> mac = new ArrayList<String>();
     			  sum_mac = 0; 
     			  Iterator macIterator = macDistances.keySet().iterator();
     			  while(macIterator.hasNext()) {
    	        	String key=(String) macIterator.next();
    	            Double value=(Double) macDistances.get(key);
    	        	
    	            mac.add(key);
    	            sum_mac = sum_mac + 1;
     			  } 	
      			   
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(0), byz);
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(1), bas);
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(2), pal);
      			listSecondLevelDataChild.put(listSecondLevelHeader.get(3), mac);
        		}
        		
        		CustExpListview SecondLevelexplv = new CustExpListview(getActivity());
                SecondLevelexplv.setAdapter(new SecondLevelAdapter(listSecondLevelHeader, listSecondLevelDataChild));
                //SecondLevelexplv.setGroupIndicator(null);
                return SecondLevelexplv;
        	} 
            else{
            	 LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 convertView = infalInflater.inflate(R.layout.listitem, null); 
            	
            	  ArrayList<String> list = new ArrayList<String>();
            	
        		  String childText = (String) getChild(groupPosition, childPosition);
        		  list.add(childText);
        		  Log.i("list element =>", childText);
        		  
        		  TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
         	      txtListChild.setText(childText);
        		  
        		  for (int i=0; i<oncalllist.size(); i++){
        			  for (int j=0; j<list.size(); j++){
        				  if (oncalllist.get(i).equals(list.get(j))){
        					  Log.i("on-call =>", list.get(j).toString());
        					  //Toast.makeText(getActivity(), "on-call", Toast.LENGTH_SHORT).show();
        					  txtListChild.setTextColor(Color.RED);
        				  }
        			  }
        		  }
        		 
        		 

        	     
        	     return convertView;
        	 }          
         }
        
         @Override
         public int getChildrenCount(int groupPosition) {
        	 String headerTitle = (String) getGroup(groupPosition);
        	 if (headerTitle.equals("Nightlife") || headerTitle.equals("Νυχτερινή Ζωή")){
        		 return 1;
        	 }
        	 else if (headerTitle.equals("Food") || headerTitle.equals("Φαγητό")){
        		 return 1;
        	 }
        	 else if (headerTitle.equals("Churches") || headerTitle.equals("Εκκλησίες")){
        		 return 1;
        	 }
        	 else{
                return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();}
        	// return 3;
         }

         @Override
         public Object getGroup(int groupPosition) {
                return this.listDataHeader.get(groupPosition);
         }

         @Override
         public int getGroupCount() {
                return this.listDataHeader.size();
         }

         @Override
         public long getGroupId(int groupPosition) {
                return groupPosition;
         }

         @Override
         public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                //TextView tv = new TextView(getActivity()); 
                String headerTitle = (String) getGroup(groupPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.listgroup, null);
                }
                TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
                lblListHeader.setTypeface(null, Typeface.BOLD);
                lblListHeader.setText(headerTitle);
         
                TextView sumtv = (TextView) convertView.findViewById(R.id.sumtv);
                if (headerTitle.equals("Museums") || headerTitle.equals("Μουσεία")){
                	String string_museums = Integer.toString(sum_museums);
                	sumtv.setText(string_museums);
                }
                else if (headerTitle.equals("Hospitals") || headerTitle.equals("Νοσοκομεία")){
                	String string_hospitals = Integer.toString(sum_hospitals);
                	sumtv.setText(string_hospitals);
                }
                else if (headerTitle.equals("Sightseeings") || headerTitle.equals("Αξιοθέατα")){
                	String string_sights = Integer.toString(sum_sights);
                	sumtv.setText(string_sights);
                }
                else if (headerTitle.equals("Food") || headerTitle.equals("Φαγητό")){
                	String string_food = Integer.toString(sum_food);
                	sumtv.setText(string_food);
                }
                else if (headerTitle.equals("Churches") || headerTitle.equals("Εκκλησίες")){
                	String string_church = Integer.toString(sum_church);
                	sumtv.setText(string_church);
                }
                else if (headerTitle.equals("Nightlife") || headerTitle.equals("Νυχτερινή Ζωή")){
                	String string_nightlife = Integer.toString(sum_nightlife);
                	sumtv.setText(string_nightlife);
                }
                
                return convertView;
                //tv.setText("      FirstLevel");
                //tv.setTextColor(Color.BLACK);
                //tv.setTextSize(20);
                //tv.setBackgroundColor(Color.BLUE);
                //tv.setPadding(10, 7, 7, 7);

       
         }

         @Override
         public boolean hasStableIds() {
                return true;
         }

         @Override
         public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
         }

  }

	 public static class CustExpListview extends ExpandableListView {

         int intGroupPosition, intChildPosition, intGroupid;
         
         public CustExpListview(Context context) {
             super(context);
            
         }

         protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(3000, MeasureSpec.AT_MOST);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         }

  }
	 
	 public class SecondLevelAdapter extends BaseExpandableListAdapter {

		 private ArrayList<String> listSecondLevelHeader;
		 private HashMap<String, ArrayList<String>> listSecondLevelDataChild;
		 private int i = 0;
		 
		 public SecondLevelAdapter(ArrayList<String> listSecondLevelHeader, HashMap<String, ArrayList<String>> listSecondLevelDataChild){
			 this.listSecondLevelHeader = listSecondLevelHeader;
			 this.listSecondLevelDataChild = listSecondLevelDataChild;
		 }
		 
         @Override
         public Object getChild(int groupPosition, int childPosition) {
        	 return this.listSecondLevelDataChild.get(this.listSecondLevelHeader.get(groupPosition)).get(childPosition);
         }

         @Override
         public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
         }

         @Override
         public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        	 String childText = (String) getChild(groupPosition, childPosition);
   		  Log.i("Child Text =>", childText);
   		// if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.listitem, null);
          //  }
   		 TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
   	     txtListChild.setText(childText);
   	     
   	     return convertView;
                /*TextView tv = new TextView(getActivity());
                tv.setText("child");
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(20);
                tv.setPadding(15, 5, 5, 5);
                tv.setBackgroundColor(Color.YELLOW);
                tv.setLayoutParams(new ListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                return tv;*/
         }

         @Override
         public int getChildrenCount(int groupPosition) {
                //return 5;
        	 return this.listSecondLevelDataChild.get(this.listSecondLevelHeader.get(groupPosition))
                     .size();
         }

         @Override
         public Object getGroup(int groupPosition) {
                return this.listSecondLevelHeader.get(groupPosition);
         }

         @Override
         public int getGroupCount() {
                return this.listSecondLevelHeader.size();
         }

         @Override
         public long getGroupId(int groupPosition) {
                return groupPosition;
         }

         @Override
         public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        	 
        	 String headerTitle = (String) getGroup(groupPosition);
             if (convertView == null) {
                 LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 convertView = infalInflater.inflate(R.layout.secondlevellistgroup, null);
             }
             TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
             lblListHeader.setTypeface(null, Typeface.BOLD);
             lblListHeader.setText(headerTitle);
      
             TextView seclevelsumtv = (TextView) convertView.findViewById(R.id.secondlevelsumtv);
             if (headerTitle.equals("Bar-Restaurants")|| headerTitle.equals("Μπαρ-Ρεστοράν")){
             	String string_barrest = Integer.toString(sum_barrest);
             	seclevelsumtv.setText(string_barrest);
             }
             else if (headerTitle.equals("Restaurants") || headerTitle.equals("Ρεστοράν")){
            	 String string_rest = Integer.toString(sum_rest);
              	 seclevelsumtv.setText(string_rest);
             }
             else if (headerTitle.equals("International Cuisine") || headerTitle.equals("Διεθνής Κουζίνα")){
            	 String string_inter = Integer.toString(sum_intcuis);
              	 seclevelsumtv.setText(string_inter);
             }
             else if (headerTitle.equals("Seafood") || headerTitle.equals("Ψαροταβέρνες")){
            	 String string_seaf = Integer.toString(sum_seafood);
              	 seclevelsumtv.setText(string_seaf);
             }
             else if (headerTitle.equals("Byzantine") || headerTitle.equals("Βυζαντινές")){
            	 String string_byz = Integer.toString(sum_byz);
              	 seclevelsumtv.setText(string_byz);
             }
             else if (headerTitle.equals("Basiliki") || headerTitle.equals("Βασιλικές")){
            	 String string_bas = Integer.toString(sum_bas);
              	 seclevelsumtv.setText(string_bas);
             }
             else if (headerTitle.equals("PaleoChristian") || headerTitle.equals("Παλεό-Χριστιανικές")){
            	 String string_pal = Integer.toString(sum_pal);
              	 seclevelsumtv.setText(string_pal);
             }
             else if (headerTitle.equals("Macedonian") || headerTitle.equals("Μακεδονικές")){
            	 String string_mac = Integer.toString(sum_mac);
              	 seclevelsumtv.setText(string_mac);
             }
             else if (headerTitle.equals("Bars") || headerTitle.equals("Μπαρ")){
            	 String string_bars = Integer.toString(sum_bars);
              	 seclevelsumtv.setText(string_bars);
             }
             else if (headerTitle.equals("Clubs") || headerTitle.equals("Κλαμπ")){
            	 String string_clubs = Integer.toString(sum_clubs);
              	 seclevelsumtv.setText(string_clubs);
             }
             else if (headerTitle.equals("Pubs") || headerTitle.equals("Μπυραρίες")){
            	 String string_pubs = Integer.toString(sum_pubs);
              	 seclevelsumtv.setText(string_pubs);
             }
             
             return convertView;
        	 
               /* TextView tv = new TextView(getActivity());
                tv.setText("      Second Level");
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(20);
                tv.setPadding(12, 7, 7, 7);
                tv.setBackgroundColor(Color.WHITE);

                return tv;*/
         }

         @Override
         public boolean hasStableIds() {
               return true;
         }
         
         @Override
         public boolean isChildSelectable(int groupPosition, int childPosition) {
        	 i++;
        	 String s = Integer.toString(i);
        	 Log.i("aux variable =>", s);
        	 String item = listSecondLevelDataChild.get(listSecondLevelHeader.get(groupPosition)).get(childPosition);
        if (i == 1){	
        	if (language.equals("English")){
        		Intent intent = new Intent(getActivity(), SearchPlaceResutlActivity.class);
        		intent.putExtra("PlaceName", item);
        		intent.putExtra("language", "English");
        		startActivity(intent);
			}else{
				Intent intent = new Intent(getActivity(), SearchPlaceResutlActivity.class);
				intent.putExtra("PlaceName", item);
				intent.putExtra("language", "Greek");
				startActivity(intent);
			}
			 
        }else{
        i = 0;}
             return true;
         }



  }



}

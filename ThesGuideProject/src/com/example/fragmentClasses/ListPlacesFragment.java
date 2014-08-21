package com.example.fragmentClasses;

import java.io.IOException;

import com.example.adapters.PLacesDataListCursorAdapter;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.tasks.BitmapTask;
import com.example.thesguideproject.R;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

@SuppressLint("ValidFragment") 
public class ListPlacesFragment extends ListFragment{

	private Spinner nightLifeSpinner;
	private Button museumsButton;
	private Button sightseeings;
	private Button churchesButton;
	private ListView listExample;
	private BitmapTask imgFetcher = new BitmapTask(getActivity());
	private LayoutInflater layoutInflator;
	private Cursor specificPlacecursor;
	private Cursor allDisplayImageLinkcursor;
	private String[] columns;
	private int[] to;
	//private String genre;
	private double current_latitude;
	private double current_longtitude;
	private String button_pressed;
	private ActionBar mActionBar;
	private SearchView searchView;
	TestLocalSqliteDatabase testDB ;
	TestLocalSqliteDatabase testDB1 ;
	private Fragment fragment;
	private FragmentTransaction fragmentTransaction;
	private String genre;
	
	String palcChr = "paleoChristian";

	
	public ListPlacesFragment(String museums, double current_latitude, double current_longtitude) {
		// TODO Auto-generated constructor stub
		this.genre = museums;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
	}
	
	
	//public static ListPlacesFragment newInstance(String g){
		
		//Bundle bundle = new Bundle();
		//bundle.putString("genre", g);
		///listplacesFrag.setArguments(bundle);
		
		//return listplacesFrag;
		
//	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.list_fragment, container, false);
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase();
		//String s = getArguments().getString("genre");
		allDisplayImageLinkcursor = testDB.getAllPhotoDisplayImageLink(); 
		 if (allDisplayImageLinkcursor.moveToFirst()){
			 do {
				 String name = this.allDisplayImageLinkcursor.getString(this.allDisplayImageLinkcursor.getColumnIndex("_id"));
				 String url = allDisplayImageLinkcursor.getString(this.allDisplayImageLinkcursor.getColumnIndex("photo_link"));
				 this.imgFetcher.loadImage(this, url, getActivity(), name);
			 }while (allDisplayImageLinkcursor.moveToNext()); 
		 }
		//button_pressed = "church";
		if (genre.equals("museums")){
			
		    HelperMethodDependingOnButtonClick(genre);
		    //setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		    setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		    
		    
		   // Toast.makeText(getActivity(), "You clicked ListPlacesFragment!!", Toast.LENGTH_SHORT).show();
		}   
		else{
			
			//testDB1.openDataBase();
			HelperMethodDependingOnChurchMenuItemButtonClick(genre);
			//Toast.makeText(getActivity(), "You clicked ListPlacesFragment and Churches in specific!!", Toast.LENGTH_SHORT).show();
			setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
			
		}
		
		return view;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close();
		//testDB1.close();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}


	public void HelperMethodDependingOnButtonClick(String genre){
		
		try{
		specificPlacecursor = testDB.getSpecificPlaceData(genre);
		
			
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info", "latitude", "longtitude"};
		
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
		}
		finally{
			//specificPlacecursor.close();
			//
		}
		//specificPlacecursor.close();
		
	}
	
	
	public void HelperMethodDependingOnChurchMenuItemButtonClick(String subcategory){
		//testDB.openDataBase();
		specificPlacecursor = testDB.getSpecificChurchData(subcategory);
		
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info"};
		
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
		//testDB.close();
	} 

	private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, BitmapTask imgFetcher, double current_latitude, double current_longtitude){
		setListAdapter(new PLacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, this.imgFetcher, current_latitude, current_longtitude) );
	}


	//private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, double current_latitude, double current_longtitude){
	//	setListAdapter(new PLacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude) );
//	}
	//public static ListPlacesFragment instantiate(FragmentActivity activity, Class<ListPlacesFragment> class1, Bundle bundle) {
		// TODO Auto-generated method stub
	//	return null;
	//}
	
}

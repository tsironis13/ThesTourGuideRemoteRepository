package com.example.fragmentClasses;

import com.example.adapters.InEnglishPlacesDataListCursorAdapter;
import com.example.adapters.PlacesDataListCursorAdapter;
import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.tasks.BitmapTask;
import com.example.thesguideproject.R;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("ValidFragment") 
public class SearchPlaceResultListFragment extends ListFragment{

	private GPSTracker gps;
	private double current_latitude;
	private double current_longtitude;
	private String nameEl;
	private TestLocalSqliteDatabase testDB;
	private Cursor getPlaceImageLink;
	private BitmapTask imgFetcher = new BitmapTask(getActivity());
	private Cursor specificPlacecursor;
	private String[] columns;
	private int[] to;
	private ListView listExample;
	private static final String debugTag = "SearchPlaceResultListFragment";
	private boolean imagessavedFlag;
	
	public SearchPlaceResultListFragment(String nameEl){
		this.nameEl = nameEl;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.search_result_list_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		String language = getArguments().getString("language");
		imagessavedFlag = getArguments().getBoolean("imagessavedFlag");
		
		gps = new GPSTracker(getActivity());
		
		if (gps.canGetLocation()){
			 current_latitude = gps.getLatitude();
	         current_longtitude = gps.getLongitude();
		}
		else
		{
	        gps.showSettingsAlert();
	    }
		
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase(debugTag);
		/*getPlaceImageLink = testDB.getPlacePhotoDisplayImageLink(nameEl);
			if (getPlaceImageLink.moveToFirst()){
				do{
					 String name = this.getPlaceImageLink.getString(this.getPlaceImageLink.getColumnIndex("_id"));
					 String url = getPlaceImageLink.getString(this.getPlaceImageLink.getColumnIndex("photo_link"));
					 if (url.equals("")){
						 testDB.close();
					 }
					 else{
					     this.imgFetcher.loadImage(this, url, getActivity(), name);
					     testDB.close();
					 }
				}while(getPlaceImageLink.moveToNext());
			}*/
		if (language.equals("English")){
			InEnglishHelperMethodDependingOnSearchQuery(nameEl);
			inEnglishSetAdapterFromSpecificCursor(nameEl, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		}else{
			HelperMethodDependingOnSearchQuery(nameEl);
			setAdapterFromSpecificCursor(nameEl, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		}	
	}

	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}

	public void InEnglishHelperMethodDependingOnSearchQuery(String nameEn){
		try{
			specificPlacecursor = testDB.getPlaceByNameEn(nameEn);
			// the desired columns to be bound
			columns = new String[] {"_id", "name_en", "photo_link", "info", "latitude", "longtitude"};
						
			// the XML defined views which the data will be bound to
			to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
			}
			finally{
				//specificPlacecursor.close();
			}
	}
	
	
	public void HelperMethodDependingOnSearchQuery(String nameEl){
		try{
			specificPlacecursor = testDB.getPlaceByNameEl(nameEl);
			
			// the desired columns to be bound
			columns = new String[] {"_id", "name_el", "photo_link", "info", "latitude", "longtitude"};
			
			// the XML defined views which the data will be bound to
			to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
			}
			finally{
				//specificPlacecursor.close();
				//
			}
	}
	
	private void inEnglishSetAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, BitmapTask imgFetcher, double current_latitude, double current_longtitude){
		setListAdapter(new InEnglishPlacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude, imagessavedFlag) );
	}
	
	private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, BitmapTask imgFetcher, double current_latitude, double current_longtitude){
		setListAdapter(new PlacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude, imagessavedFlag) );
	}
	
	
}

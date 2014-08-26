package com.example.fragmentClasses;

import java.util.ArrayList;

import com.example.adapters.PLacesDataListCursorAdapter;
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
import android.widget.Toast;

@SuppressLint("ValidFragment") 
public class ListPlacesFragment extends ListFragment{

	private ListView listExample;
	private BitmapTask imgFetcher = new BitmapTask(getActivity());
	private Cursor specificPlacecursor;
	private Cursor allDisplayImageLinkcursor;
	private String[] columns;
	private int[] to;
	//private String genre;
	private double current_latitude;
	private double current_longtitude;
	private TestLocalSqliteDatabase testDB;
	private String genre;
	private String subcategory;
	private static final String debugTag = "ListPlacesFragment";
	
	//genre is also NameEl
	public ListPlacesFragment(String genre, String subcategory, double current_latitude, double current_longtitude) {
		this.genre = genre;
		this.subcategory = subcategory;
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
		testDB.openDataBase(debugTag);
		//String s = getArguments().getString("genre");
		//allDisplayImageLinkcursor = testDB.getAllPhotoDisplayImageLink(); 
		//imageLinkNotNullList = new ArrayList<String>();
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		//button_pressed = "church";
		if (genre.equals("museums")){
			
		    HelperMethodDependingOnButtonClick(genre);
		    //setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		    setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
		    
		    
		   // Toast.makeText(getActivity(), "You clicked ListPlacesFragment!!", Toast.LENGTH_SHORT).show();
		}  
		else if (genre.equals("hospitals")){
			HelperMethodDependingOnButtonClick(genre);
		    //setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
		    setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
		}
		
		else {
			Toast.makeText(getActivity(), genre.toString(), Toast.LENGTH_SHORT).show();
			//testDB1.openDataBase();
			HelperMethodDependingOnMenuItemButtonClick(subcategory);
			//HelperMethodDependingOnSearchQuery(genre);
			//Toast.makeText(getActivity(), "You clicked ListPlacesFragment and Churches in specific!!", Toast.LENGTH_SHORT).show();
			setAdapterFromSpecificCursor(subcategory, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		testDB.close(debugTag);
	}

	public void HelperMethodDependingOnSearchQuery(String nameEl){
		specificPlacecursor = testDB.getPlaceByNameEl(nameEl);
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info", "latitude", "longtitude"};
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};		
	}
	
	public void HelperMethodDependingOnButtonClick(String genre){
		specificPlacecursor = testDB.getSpecificPlaceData(genre);
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info", "latitude", "longtitude"};
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};	
	}
	
	public void HelperMethodDependingOnMenuItemButtonClick(String subcategory){
		specificPlacecursor = testDB.getSpecificChurchData(subcategory);
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info"};
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};
	} 

	private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, double current_latitude, double current_longtitude){
		setListAdapter(new PLacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude) );
	}


	//private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, double current_latitude, double current_longtitude){
	//	setListAdapter(new PLacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude) );
//	}
	//public static ListPlacesFragment instantiate(FragmentActivity activity, Class<ListPlacesFragment> class1, Bundle bundle) {
		// TODO Auto-generated method stub
	//	return null;
	//}
	
}

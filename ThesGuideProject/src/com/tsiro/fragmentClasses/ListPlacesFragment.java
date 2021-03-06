package com.tsiro.fragmentClasses;

import java.util.ArrayList;
import com.tsiro.thesguideproject.R;
import com.tsiro.adapters.InEnglishPlacesDataListCursorAdapter;
import com.tsiro.adapters.PlacesDataListCursorAdapter;
import com.tsiro.locationData.PlacesData;
import com.tsiro.sqlHelper.TestLocalSqliteDatabase;
import com.tsiro.tasks.BitmapTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment") 
public class ListPlacesFragment extends ListFragment{

	private ListView listExample;
	private BitmapTask imgFetcher; 
	private Cursor specificPlacecursor;
	private String[] columns;
	private int[] to;
	//private String genre;
	private double current_latitude;
	private double current_longtitude;
	private TestLocalSqliteDatabase testDB;
	private String genre;
	private String subcategory;
	private static final String debugTag = "ListPlacesFragment";
	private String language;
	private String date;
	private String flag;
	private boolean imagessavedFlag;
	private Context context;
	private InEnglishPlacesDataListCursorAdapter englishAdapter;
	private View view;
	
	public ListPlacesFragment(){}
	
	//genre is also NameEl
	public ListPlacesFragment(String genre, String subcategory, double current_latitude, double current_longtitude) {
		this.genre = genre;
		this.subcategory = subcategory;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
	}
	
	public ListPlacesFragment(String genre, String subcategory, double current_latitude, double current_longtitude, String date, String flag) {
		this.genre = genre;
		this.subcategory = subcategory;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		this.date = date;
		this.flag = flag;
	}
	
	//public static ListPlacesFragment newInstance(String g){
		
		//Bundle bundle = new Bundle();
		//bundle.putString("genre", g);
		///listplacesFrag.setArguments(bundle);
		
		//return listplacesFrag;
		
//	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.list_fragment, container, false);	
		language = getArguments().getString("language");
		imagessavedFlag = getArguments().getBoolean("imagessavedFlag");
		//testDB = new TestLocalSqliteDatabase(getActivity());
		testDB = TestLocalSqliteDatabase.getInstance(getActivity());
		//testDB.openDataBase(debugTag);
		
		//String s = getArguments().getString("genre");
		//allDisplayImageLinkcursor = testDB.getAllPhotoDisplayImageLink(); 
		//imageLinkNotNullList = new ArrayList<String>();
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		context = getActivity().getApplicationContext();
		imgFetcher = new BitmapTask(context);
		//button_pressed = "church";
		if (genre.equals("events")){
				//HelperMethodDependingOnCurrentEvents(genre, date);
		}
		else if (genre.equals("museums")){
			
			if (language.equals("English")){
				HelperMethodDependingOnButtonClick(genre);
				inEnglishSetAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}
			else{
				HelperMethodDependingOnButtonClick(genre);
				//setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
				setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}
		    
		   // Toast.makeText(getActivity(), "You clicked ListPlacesFragment!!", Toast.LENGTH_SHORT).show();
		} 
		else if (genre.equals("sightseeings")){
			if (language.equals("English")){
				HelperMethodDependingOnButtonClick(genre);
				inEnglishSetAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}
			else{
				HelperMethodDependingOnButtonClick(genre);
				//setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
				setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}
		}
		else if (genre.equals("hospitals")){
			
			if (language.equals("English")){
				HelperMethodDependingOnButtonClick(genre);
				inEnglishSetAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}
			else{
				HelperMethodDependingOnButtonClick(genre);
				//setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, imgFetcher, current_latitude, current_longtitude);
				setAdapterFromSpecificCursor(genre, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}
		}
		
		else {
			
			if (language.equals("English")){
				HelperMethodDependingOnMenuItemButtonClick(subcategory);
				inEnglishSetAdapterFromSpecificCursor(subcategory, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}else{
			//Toast.makeText(getActivity(), genre.toString(), Toast.LENGTH_SHORT).show();
			//testDB1.openDataBase();
			HelperMethodDependingOnMenuItemButtonClick(subcategory);
			//HelperMethodDependingOnSearchQuery(genre);
			//Toast.makeText(getActivity(), "You clicked ListPlacesFragment and Churches in specific!!", Toast.LENGTH_SHORT).show();
			setAdapterFromSpecificCursor(subcategory, listExample, specificPlacecursor, columns, to, current_latitude, current_longtitude);
			}
		}
	}

	
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Toast.makeText(getActivity(), "Fragment paused!", Toast.LENGTH_SHORT).show();
		setListAdapter(null);
		englishAdapter.notifyDataSetChanged();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//testDB.close(debugTag);
		//setListAdapter(null);
		//Toast.makeText(getActivity(), "Fragment destroyed!", Toast.LENGTH_SHORT).show();
		//setListAdapter(null);
		//englishAdapter.notifyDataSetChanged();
	}

	public void HelperMethodDependingOnSearchQuery(String nameEl){
		specificPlacecursor = testDB.getPlaceByNameEl(nameEl);
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "photo_link", "info", "latitude", "longtitude"};
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv, R.id.locationImage};		
	}
	
	public static class MyViewHolder{
		public TextView placeNametv, nameEllower, latitudetv, longtitudetv, distance,desc_infohiddentv, menuhiddentv, 
		telhiddentv, linkhiddentv, fbLinkhiddentv, emailhiddentv, exhibitionhiddentv;
		public ImageView icon;
		public Button infoButton;
    	public PlacesData locations;
}
	
	/*public void HelperMethodDependingOnCurrentEvents(String genre, String date){
		//specificPlacecursor = testDB.getAllEvents(genre);
		
		if (flag.equals("oncreate")){
			currenteventslist = testDB.getAllEvents("events", date);
		}
		else{
			currenteventslist = testDB.getEventsOnCalendarClick("events", date);
		}
	
		if (language.equals("Greek")){
			//setListAdapter(new EventsBaseAdapter(genre, this, getActivity(), R.layout.places_basic_layout, currenteventslist,  current_latitude, current_longtitude));
		}else{
			setListAdapter(new InEnglishEventsBaseAdapter(genre, this, getActivity(), R.layout.places_basic_layout, currenteventslist,  current_latitude, current_longtitude));
		}
	}*/
	
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
		setListAdapter(new PlacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude, imagessavedFlag) );
	}

	
	
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		//Toast.makeText(getActivity(), "View destroyed!", Toast.LENGTH_SHORT).show();
		view = null;
	}
	
	private void inEnglishSetAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, double current_latitude, double current_longtitude){
		englishAdapter = new InEnglishPlacesDataListCursorAdapter(button_pressed, this, getActivity().getApplicationContext(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude, imagessavedFlag);
		setListAdapter(englishAdapter);
		englishAdapter.notifyDataSetChanged();
	}
	//private void setAdapterFromSpecificCursor(String button_pressed, ListView listExample, Cursor cursor, String[] columns, int[] to, double current_latitude, double current_longtitude){
	//	setListAdapter(new PLacesDataListCursorAdapter(button_pressed, this, getActivity(),  R.layout.places_basic_layout, cursor, columns, to, current_latitude, current_longtitude) );
//	}
	//public static ListPlacesFragment instantiate(FragmentActivity activity, Class<ListPlacesFragment> class1, Bundle bundle) {
		// TODO Auto-generated method stub
	//	return null;
	//}
	
}

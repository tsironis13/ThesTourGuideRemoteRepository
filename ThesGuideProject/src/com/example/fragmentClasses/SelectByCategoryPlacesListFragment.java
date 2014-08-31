package com.example.fragmentClasses;

import com.example.adapters.PLacesDataListCursorAdapter;
import com.example.adapters.SelectLocationSimpleCursorAdapter;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SelectByCategoryPlacesListFragment extends ListFragment{

	private ListView listView;
	private String genre;
	private String flag;
	private static final String debugTag = "SelectByCategoryPlacesListFragment";
	private TestLocalSqliteDatabase testDB;
	private Cursor specificPlacecursor;
	private String[] columns;
	private int[] to;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.selectbycategoryplaceslistfragment, container, false);
		return view;
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase(debugTag);
		
		flag = getArguments().getString("flag");
		genre = getArguments().getString("genre");
		HelperMethodDependingOnButtonClick(genre);
		setAdapterFromSpecificCursor(flag, genre, listView, specificPlacecursor, columns, to);
	}
	
	
	public void HelperMethodDependingOnButtonClick(String genre){
		specificPlacecursor = testDB.getSpecificPlaceData(genre);
		// the desired columns to be bound
		columns = new String[] {"_id", "name_el", "latitude", "longtitude"};
		// the XML defined views which the data will be bound to
		to = new int[] {R.id.locationName, R.id.placeNametv };	
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}
	
	private void setAdapterFromSpecificCursor(String flag, String genre, ListView listExample, Cursor cursor, String[] columns, int[] to){
		setListAdapter(new SelectLocationSimpleCursorAdapter(flag, genre, this, getActivity(),  R.layout.disarablelocation_layout, cursor, columns, to) );
	}
	
}

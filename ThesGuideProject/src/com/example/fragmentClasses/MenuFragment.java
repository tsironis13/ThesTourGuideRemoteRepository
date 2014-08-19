package com.example.fragmentClasses;

import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.CursorAdapterExample;
import com.example.thesguideproject.PlacesListFragmentActivity;
import com.example.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MenuFragment extends Fragment{
	
	private Button churhesButton;
	private Button clearButton;
	private Button museumsButton;
	private String genre;
	private String subcategory;
	private double current_latitude;
	private double current_longtitude;
	private Fragment fragment;
	private FragmentTransaction fragmentTransaction;
	private GPSTracker gps;
	private TestLocalSqliteDatabase t;
	private TestLocalSqliteDatabase t1;
	public MenuFragment(){}
    private int i=0;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.menu_fragment, container, false);
		
		churhesButton = (Button) view.findViewById(R.id.churchesbutton);
		clearButton = (Button) view.findViewById(R.id.clearDataDBbutton);
		museumsButton = (Button) view.findViewById(R.id.museumsbutton);
		
		return view; 
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		gps = new GPSTracker(getActivity());
		
		if (gps.canGetLocation()){
			 current_latitude = gps.getLatitude();
             current_longtitude = gps.getLongitude();
		}
		else
		{
            gps.showSettingsAlert();
        }
		
		fragment = new DisplayImageFragment();
		fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, fragment);
		//fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		
		museumsButton.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {
				t = new TestLocalSqliteDatabase(getActivity());
				t.openDataBase();
				i = t.getAuxiliaryVariableI();
				if (i != 10)
					{
				  t.insertValueForIAuxiliaryVariable(10);
				  t.close();
					}
				else{
					t.close();
				}
				genre = "museums";
				ListPlacesFragment listMuseumsFragment = new ListPlacesFragment(genre, current_latitude, current_longtitude);
				//fragmentTransaction = getFragmentManager().beginTransaction().remove(fragment);
				//fragmentTransaction.commit();
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMuseumsFragment);
				//fragmentTransaction.addToBackStack(null);
				fragmentTransaction.commit();
			}
		});
		
		churhesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				t1 = new TestLocalSqliteDatabase(getActivity());
				i = t1.getAuxiliaryVariableI();
				if (i != 10)
				{
				t1.insertValueForIAuxiliaryVariable(10);
				t1.close();
				}
				else{
					t1.close();
				}
				registerForContextMenu(v); 
				getActivity().openContextMenu(v);
				
				
			}
		});
		
		clearButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//fragment = new DisplayImageFragment();
				//fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, fragment);
				//fragmentTransaction.commit();
			}
		});
	}

	String palcChr = "PaleoChristian";
	String bizan = "Byzantine";
	String basiliki = "Basiliki";
    String macedon = "Macedonian";
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		 case R.id.paleochristianikes:
			 subcategory = palcChr;
			 ListPlacesFragment listPchrFragment = new ListPlacesFragment(subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listPchrFragment);
			 fragmentTransaction.commit();
			 break;
		 case R.id.bizantines:
			 subcategory = bizan;
			 ListPlacesFragment listBizanFragment = new ListPlacesFragment(subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBizanFragment);
			 fragmentTransaction.commit();
			 break;
		 case R.id.basiliki:
			 subcategory = basiliki;
			 ListPlacesFragment listBasilFragment = new ListPlacesFragment(subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBasilFragment);
			 fragmentTransaction.commit();
			 break;
		 case R.id.macedonian:
			 subcategory = macedon;
			 ListPlacesFragment listMacFragment = new ListPlacesFragment(subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMacFragment);
			 fragmentTransaction.commit();
			 break;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		MenuInflater menuInflater = getActivity().getMenuInflater();
		menuInflater.inflate(R.menu.categ_churchmenu, menu);
	}
	
	

}

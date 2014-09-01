package com.example.fragmentClasses;

import com.example.myLocation.GPSTracker;
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


public class MenuFragment extends Fragment {
	
	private Button churhesButton;
	private Button museumsButton;
	private Button hospitalsButton;
	private Button nightlifeButton;
	private Button foodButton;
	private String genre;
	private String subcategory;
	private double current_latitude;
	private double current_longtitude;
	private Fragment fragment;
	private FragmentTransaction fragmentTransaction;
	private GPSTracker gps;
	//private TestLocalSqliteDatabase t;
	//private int i=0;
	
	public MenuFragment(){}
	
	
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_fragment, container, false);
		
		String language = getArguments().getString("language");
		
		foodButton = (Button) view.findViewById(R.id.foodButton);
		nightlifeButton = (Button) view.findViewById(R.id.nightlifebutton);
		churhesButton = (Button) view.findViewById(R.id.churchesbutton);
		museumsButton = (Button) view.findViewById(R.id.museumsbutton);
		hospitalsButton = (Button) view.findViewById(R.id.hospitalsbutton);
		
		if (language.equals("Greek")){
			foodButton.setText("Φαγητό");
			nightlifeButton.setText("Νυχτεριρή Ζωή");
			churhesButton.setText("Εκκλησίες");
			museumsButton.setText("Μουσεία");
			hospitalsButton.setText("Νοσοκομεία");
		}
		
			
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
		fragmentTransaction.addToBackStack("d");
		fragmentTransaction.commit();
		
		
			//t2 = new TestLocalSqliteDatabase(getActivity());
			//t2.openDataBase();
		    //String s = t2.getSuggestionPressedFieldValue();
		   // Log.i("Suggestion Pressed =>", s);
		  //  t2.close();
		
		//if (s.equals("true")){
			//genre = this.nameEl;
			//ListPlacesFragment listMuseumsFragment = new ListPlacesFragment(genre, current_latitude, current_longtitude);
			//fragmentTransaction = getFragmentManager().beginTransaction().remove(fragment);
			//fragmentTransaction.commit();
			//fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMuseumsFragment);
			//fragmentTransaction.addToBackStack(null);
			//fragmentTransaction.commit();
		//}
		foodButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				registerForContextMenu(v); 
				getActivity().openContextMenu(v);	
			}
		});
		
		
		nightlifeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//t2 = new TestLocalSqliteDatabase(getActivity());
				//t2.openDataBase();
				//i = t2.getAuxiliaryVariableI();
				//if (i != 10)
				//	{
				//  t2.insertValueForIAuxiliaryVariable(10);
				//  t2.close();
				//	}
				//else{
				//	t2.close();
				//}
				registerForContextMenu(v); 
				getActivity().openContextMenu(v);
			}
		});
		
		museumsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genre = "museums";
				ListPlacesFragment listMuseumsFragment = new ListPlacesFragment(genre, "", current_latitude, current_longtitude);
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMuseumsFragment);
				int fragments = getFragmentManager().getBackStackEntryCount();
				if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				  	if (backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
				  	{
				  		getFragmentManager().popBackStack(backStackId,0);
				  	}
				        getFragmentManager().popBackStack();
				}
				fragmentTransaction.addToBackStack("mus");
				fragmentTransaction.commit();	
			}
		});
		
		
		
		
		
		churhesButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				registerForContextMenu(v); 
				getActivity().openContextMenu(v);	
			}
		});
		
		
		hospitalsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genre = "hospitals";
				ListPlacesFragment listMuseumsFragment = new ListPlacesFragment(genre, "", current_latitude, current_longtitude);
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMuseumsFragment);
				int fragments = getFragmentManager().getBackStackEntryCount();
				if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
				fragmentTransaction.addToBackStack("hos");
				
				fragmentTransaction.commit();
			}
		});
		
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		String genre;
		int fragments = getFragmentManager().getBackStackEntryCount();
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.barrestaurants:
			genre ="food";
			subcategory = "bar-restaurant";
			ListPlacesFragment listBarRestFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBarRestFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("food");
			fragmentTransaction.commit();
	   		break;
		case R.id.restaurants:
			genre ="food";
			subcategory = "restaurants";
			ListPlacesFragment listRestFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listRestFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("food");
			fragmentTransaction.commit();
			break;
		case R.id.intercuisine:
			genre ="food";
			subcategory = "intercuisine";
			ListPlacesFragment listInterCoisFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listInterCoisFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("food");
			fragmentTransaction.commit();
			break;
		case R.id.seafood:
			genre ="food";
			subcategory = "seafood";
			ListPlacesFragment listSeafoodFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listSeafoodFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("food");
			fragmentTransaction.commit();
			break;
		case R.id.bars:
			genre = "nightlife";
			subcategory = "bars";
			ListPlacesFragment listBarsFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBarsFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("nig");
			fragmentTransaction.commit();
			break;
		case R.id.clubs:
			genre = "nightlife";
			subcategory = "clubs";
			ListPlacesFragment listClubsFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listClubsFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("nig");
			fragmentTransaction.commit();
			break;
		case R.id.mpouzoukia:
			genre = "nightlife";
			subcategory = "mpouzoukia";
			ListPlacesFragment listMpouzoukiaFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMpouzoukiaFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("nig");
			fragmentTransaction.commit();
			break;
		case R.id.pubs:
			genre = "nightlife";
			subcategory = "pubs";
			ListPlacesFragment listPubsFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listPubsFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("nig");
			fragmentTransaction.commit();
			break;
		 case R.id.paleochristianikes:
			 genre = "church";
			 subcategory = "PaleoChristian";
			 ListPlacesFragment listPchrFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listPchrFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			 fragmentTransaction.addToBackStack("ch");
			 fragmentTransaction.commit();
			 break;
		 case R.id.bizantines:
			 genre = "church";
			 subcategory = "Byzantine";
			 ListPlacesFragment listBizanFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBizanFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			 fragmentTransaction.addToBackStack("ch");
			 fragmentTransaction.commit();
			 break;
		 case R.id.basiliki:
			 genre = "church";
			 subcategory = "Basiliki";
			 ListPlacesFragment listBasilFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBasilFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			 fragmentTransaction.addToBackStack("ch");
			 fragmentTransaction.commit();
			 break;
		 case R.id.macedonian:
			 genre = "church";
			 subcategory = "Macedonian";
			 ListPlacesFragment listMacFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMacFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			 fragmentTransaction.addToBackStack("ch");
			 fragmentTransaction.commit();
			 break;
		}
		return super.onContextItemSelected(item);
	}

	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.churchesbutton){
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.categ_churchmenu, menu);
		}
		else if (v.getId() == R.id.nightlifebutton){
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.nightlife_menu, menu);
		}
		else if (v.getId() == R.id.foodButton){
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.food_menu, menu);
		}
		
	}
	

}

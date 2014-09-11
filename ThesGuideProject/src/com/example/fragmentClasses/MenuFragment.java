package com.example.fragmentClasses;

import com.example.myLocation.GPSTracker;
import com.example.thesguideproject.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
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
	private Button sightseeingsButton;
	private Button eventsButton;
	private String genre;
	private String subcategory;
	private double current_latitude;
	private double current_longtitude;
	private Fragment fragment;
	private FragmentTransaction fragmentTransaction;
	private GPSTracker gps;
	private String language;
	//private TestLocalSqliteDatabase t;
	//private int i=0;
	
	public MenuFragment(){}
	
	
   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.menu_fragment, container, false);
		
		language = getArguments().getString("language");
		
		eventsButton = (Button) view.findViewById(R.id.eventsbutton);
		sightseeingsButton = (Button) view.findViewById(R.id.sightseeingsbutton);
		foodButton = (Button) view.findViewById(R.id.foodButton);
		nightlifeButton = (Button) view.findViewById(R.id.nightlifebutton);
		churhesButton = (Button) view.findViewById(R.id.churchesbutton);
		museumsButton = (Button) view.findViewById(R.id.museumsbutton);
		hospitalsButton = (Button) view.findViewById(R.id.hospitalsbutton);
		
		if (language.equals("Greek")){
			sightseeingsButton.setText("Αξιοθέατα");
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
		
		Bundle langbundle = new Bundle();
		langbundle.putString("language", language);
		fragment = new DisplayImageFragment();
		fragment.setArguments(langbundle);
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
		eventsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle langBundle = new Bundle();
				langBundle.putString("language", language);
				CalendarFragment calendarFragment = new CalendarFragment();
				calendarFragment.setArguments(langBundle);
				fragmentTransaction = getFragmentManager().beginTransaction().add(R.id.containerlist, calendarFragment);
				int fragments = getFragmentManager().getBackStackEntryCount();
				if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				  	if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
				  	{
				  		getFragmentManager().popBackStack(backStackId,0);
				  	}
				        getFragmentManager().popBackStack();
				}
				fragmentTransaction.addToBackStack("events");
				fragmentTransaction.commit();	
			}
		});
		
		foodButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				registerForContextMenu(v); 
				getActivity().openContextMenu(v);	
			}
		});
		
		sightseeingsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				genre = "sightseeings";
				Bundle langBundle = new Bundle();
				langBundle.putString("language", language);
				ListPlacesFragment listMuseumsFragment = new ListPlacesFragment(genre, "", current_latitude, current_longtitude);
				listMuseumsFragment.setArguments(langBundle);
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMuseumsFragment);
				int fragments = getFragmentManager().getBackStackEntryCount();
				if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				  	if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
				  	{
				  		getFragmentManager().popBackStack(backStackId,0);
				  	}
				        getFragmentManager().popBackStack();
				}
				fragmentTransaction.addToBackStack("sig");
				fragmentTransaction.commit();	
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
				Bundle langBundle = new Bundle();
				langBundle.putString("language", language);
				ListPlacesFragment listMuseumsFragment = new ListPlacesFragment(genre, "", current_latitude, current_longtitude);
				listMuseumsFragment.setArguments(langBundle);
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMuseumsFragment);
				int fragments = getFragmentManager().getBackStackEntryCount();
				if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				  	if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
				Bundle langBundle = new Bundle();
				langBundle.putString("language", language);
				ListPlacesFragment listMuseumsFragment = new ListPlacesFragment(genre, "", current_latitude, current_longtitude);
				listMuseumsFragment.setArguments(langBundle);
				fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMuseumsFragment);
				int fragments = getFragmentManager().getBackStackEntryCount();
				if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			Bundle langBundle = new Bundle();
			langBundle.putString("language", language);
			subcategory = "bar-restaurant";
			ListPlacesFragment listBarRestFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listBarRestFragment.setArguments(langBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBarRestFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			Bundle langrestBundle = new Bundle();
			langrestBundle.putString("language", language);
			subcategory = "restaurants";
			ListPlacesFragment listRestFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listRestFragment.setArguments(langrestBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listRestFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			Bundle langintBundle = new Bundle();
			langintBundle.putString("language", language);
			subcategory = "intcuisine";
			ListPlacesFragment listInterCoisFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listInterCoisFragment.setArguments(langintBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listInterCoisFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			Bundle langseafBundle = new Bundle();
			langseafBundle.putString("language", language);
			subcategory = "seafood";
			ListPlacesFragment listSeafoodFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listSeafoodFragment.setArguments(langseafBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listSeafoodFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			Bundle langbarsBundle = new Bundle();
			langbarsBundle.putString("language", language);
			subcategory = "bars";
			ListPlacesFragment listBarsFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listBarsFragment.setArguments(langbarsBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBarsFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			Bundle langclubsBundle = new Bundle();
			langclubsBundle.putString("language", language);
			subcategory = "clubs";
			ListPlacesFragment listClubsFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listClubsFragment.setArguments(langclubsBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listClubsFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
					{
						getFragmentManager().popBackStack(backStackId,0);
					}
						getFragmentManager().popBackStack();
				}
			fragmentTransaction.addToBackStack("nig");
			fragmentTransaction.commit();
			break;
		/*case R.id.mpouzoukia:
			genre = "nightlife";
			Bundle langmpouzBundle = new Bundle();
			langmpouzBundle.putString("language", language);
			subcategory = "mpouzoukia";
			ListPlacesFragment listMpouzoukiaFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listMpouzoukiaFragment.setArguments(langmpouzBundle);
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
			break;*/
		case R.id.pubs:
			genre = "nightlife";
			Bundle langnightBundle = new Bundle();
			langnightBundle.putString("language", language);
			subcategory = "pubs";
			ListPlacesFragment listPubsFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			listPubsFragment.setArguments(langnightBundle);
			fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listPubsFragment);
			if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			 Bundle langpaleoBundle = new Bundle();
			 langpaleoBundle.putString("language", language);
			 subcategory = "PaleoChristian";
			 ListPlacesFragment listPchrFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 listPchrFragment.setArguments(langpaleoBundle);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listPchrFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			 Bundle langbizBundle = new Bundle();
			 langbizBundle.putString("language", language);
			 subcategory = "Byzantine";
			 ListPlacesFragment listBizanFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 listBizanFragment.setArguments(langbizBundle);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBizanFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			 Bundle langbasilBundle = new Bundle();
			 langbasilBundle.putString("language", language);
			 subcategory = "Basiliki";
			 ListPlacesFragment listBasilFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 listBasilFragment.setArguments(langbasilBundle);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listBasilFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			 Bundle langmacBundle = new Bundle();
			 langmacBundle.putString("language", language);
			 subcategory = "Macedonian";
			 ListPlacesFragment listMacFragment = new ListPlacesFragment(genre, subcategory, current_latitude, current_longtitude);
			 listMacFragment.setArguments(langmacBundle);
			 fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerlist, listMacFragment);
			 if (fragments > 1){
				  String backStackId = getFragmentManager().getBackStackEntryAt(1).getName();
				
					if (backStackId.equals("events") || backStackId.equals("sig") || backStackId.equals("food") || backStackId.equals("hos") || backStackId.equals("mus") || backStackId.equals("nig") || backStackId.equals("ch"))
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
			MenuItem macitem = menu.findItem(R.id.macedonian);
			MenuItem palitem = menu.findItem(R.id.paleochristianikes);
			MenuItem byzitem = menu.findItem(R.id.bizantines);
			MenuItem basitem = menu.findItem(R.id.basiliki);
			if (language.equals("Greek")){
				macitem.setTitle("Μακεδονικές");
				palitem.setTitle("Παλεό-Χριστιανικές");
				byzitem.setTitle("Βυζαντινές");
				basitem.setTitle("Βασιλικές");
			}	
			else{
				macitem.setTitle("Macedonian");
				palitem.setTitle("Old-Christian");
				byzitem.setTitle("Byzantine");
				basitem.setTitle("Basilica");
			}
		}
		else if (v.getId() == R.id.nightlifebutton){
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.nightlife_menu, menu);
			MenuItem clubsitem = menu.findItem(R.id.clubs);
			MenuItem pubsitem = menu.findItem(R.id.pubs);
			MenuItem barsitem = menu.findItem(R.id.bars);
			if (language.equals("Greek")){
				clubsitem.setTitle("Κλαμπ");
				pubsitem.setTitle("Μπυραρίες");
				barsitem.setTitle("Μπαρ");
			}	
			else{
				clubsitem.setTitle("Club");
				pubsitem.setTitle("Pub");
				barsitem.setTitle("Bar");	
			}
		}
		else if (v.getId() == R.id.foodButton){
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.food_menu, menu);
			MenuItem barrestitem = menu.findItem(R.id.barrestaurants);
			MenuItem restitem = menu.findItem(R.id.restaurants);
			MenuItem seafitem = menu.findItem(R.id.seafood);
			MenuItem intercuiitem = menu.findItem(R.id.intercuisine);
			if (language.equals("Greek")){
				barrestitem.setTitle("Μπαρ-Ρεστοράν");
				restitem.setTitle("Ρεστοράν");
				seafitem.setTitle("Ψαροταβέρνες");
				intercuiitem.setTitle("Διεθνής Κουζίνα");
			}	
			else{
				barrestitem.setTitle("Bar-Restaurant");
				restitem.setTitle("Restaurant");
				seafitem.setTitle("Seafood");
				intercuiitem.setTitle("International Cuisine");
			}
		}
		
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
			super.onPrepareOptionsMenu(menu);
			
			
	}
	

	
	
}

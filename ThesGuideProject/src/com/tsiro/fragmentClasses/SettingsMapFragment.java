package com.tsiro.fragmentClasses;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.tsiro.thesguideproject.R;
import com.tsiro.adapters.DisarableLocationCursorAdapter;
import com.tsiro.adapters.InEnglishSearchAdapter;
import com.tsiro.adapters.SearchAdapter;
import com.tsiro.adapters.SettingsListAdapter;
import com.tsiro.adapters.SettingsListAdapterEnglish;
import com.tsiro.myLocation.GPSTracker;
import com.tsiro.sqlHelper.TestLocalSqliteDatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.wifi.WifiManager;
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

	//private static LinearLayout settinglinearlayout;
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
	private ArrayAdapter<String> dataAdapter = null;
	private String startpointcontent;
	private String destpointcontent;
	private String startpointtvcontent;
	private String destv;
	private String sls;
	private String sld;
	private int ls;
	private int ld;
	private List<String> list;
	private ArrayAdapter<String> dataCategoryAdapter = null;
	private String editTextFocused;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.settingsmapfragment, container, false);	
		language = getArguments().getString("language");
	    
		//testDB = new TestLocalSqliteDatabase(getActivity());
		testDB = TestLocalSqliteDatabase.getInstance(getActivity());
		testDB.openDataBase(debugTag);
		categoryPlacesList = new ArrayList<String>();
		
		//settinglinearlayout = (LinearLayout) view.findViewById(R.id.settingslinearlayout);
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
		 startpointlabeltv.setText("��������");
		 currentpositioncb.setText("�������� ����");
		 selectcategoryb.setText("���������");
		 disarableLocationEditText.setHint("���� ���������");
		 destinationpointtv.setText("����������");
		 currentpositiondestcb.setText("�������� ����");
		 selectdestinationcategoryb.setText("���������");
		 disarabledestLocationEditText.setHint("���� ���������");
		// fromtv.setText("���");
		// totv.setText("����");
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
			
		return view;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		listView = getListView();
		
		destinationpointtv.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				startpointtvcontent = startingpointtv.getText().toString();
				ls = startpointtvcontent.length();
				sls = Integer.toString(ls);
				Log.i("starting point tv characters =>", sls);
				
				destv = destinationpointtv.getText().toString();
				ld = destv.length();
				sld = Integer.toString(ld);
				Log.i("destination point tv characters =>", sld);
			
				if (ls > 5){
					startpointcontent = startpointtvcontent.substring(6, ls);
				}
				if (ld > 5){
					destpointcontent = destv.substring(6, ld);
				}else{
					destpointcontent = "";
				}
				
				boolean flag = false;
				//Log.i("start contents =>", startpointcontent);
				Log.i("start contents =>", startpointtvcontent);
				Log.i("dest contents =>", destpointcontent);
				if (ls > 5 && !startpointcontent.toString().equals(destpointcontent)){
					flag = true;
					//Toast.makeText(getActivity(), "contents not equal", Toast.LENGTH_SHORT).show();
				}else{
					//Toast.makeText(getActivity(), "contents equal", Toast.LENGTH_SHORT).show();
					
				}
		
				if (startingpointtv.getText().length()> 0 && destinationpointtv.getText().length()> 0 && flag == true ){
					
					if (startpointcontent.toString().equals("Current location") || startpointcontent.toString().equals("�������� ����")){
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
					
						if (!language.equals("English")){	
							Cursor startcursor = testDB.getPlaceByNameEl(startpointcontent.toString());
								if (startcursor.moveToFirst()){
									do{
										startlatitude = startcursor.getDouble(startcursor.getColumnIndex("latitude"));
										startlongtitude = startcursor.getDouble(startcursor.getColumnIndex("longtitude"));
									}while(startcursor.moveToNext());
								}
						}else{
							Cursor startcursor = testDB.getPlaceByNameEn(startpointcontent.toString());
							if (startcursor.moveToFirst()){
								do{
									startlatitude = startcursor.getDouble(startcursor.getColumnIndex("latitude"));
									startlongtitude = startcursor.getDouble(startcursor.getColumnIndex("longtitude"));
								}while(startcursor.moveToNext());
							}
						}
					}
					
					
					if (destpointcontent.toString().equals("Current location") || destpointcontent.toString().equals("�������� ����")){
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
						
						if (!language.equals("English")){	
							Cursor destcursor = testDB.getPlaceByNameEl(destpointcontent.toString());
							if (destcursor.moveToFirst()){
								do{
									destlatitude = destcursor.getDouble(destcursor.getColumnIndex("latitude"));
					       			destlongtitude = destcursor.getDouble(destcursor.getColumnIndex("longtitude"));
								}while(destcursor.moveToNext());
							}
						}else{
							Cursor destcursor = testDB.getPlaceByNameEn(destpointcontent.toString());
							if (destcursor.moveToFirst()){
								do{
									destlatitude = destcursor.getDouble(destcursor.getColumnIndex("latitude"));
					       			destlongtitude = destcursor.getDouble(destcursor.getColumnIndex("longtitude"));
								}while(destcursor.moveToNext());
							}
						}
					}
					
						ToAndFromFragment toandfrom = new ToAndFromFragment();
						Bundle locations_langBundle = new Bundle();
						locations_langBundle.putString("language", language);
						locations_langBundle.putString("fromlocation", startingpointtv.getText().toString());
						locations_langBundle.putString("tolocation", destinationpointtv.getText().toString());
						toandfrom.setArguments(locations_langBundle);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containertv, toandfrom);
						fragmentTransaction.addToBackStack("toandfrom");
						fragmentTransaction.commit();
					
						GoogleMapFragment g = new GoogleMapFragment();
						Bundle onmapBundle = new Bundle();
						onmapBundle.putString("language", language);
						onmapBundle.putDouble("doubleCurrentLatitude", startlatitude);
						onmapBundle.putDouble("doubleCurrentLongtitude", startlongtitude);
						onmapBundle.putDouble("doublePlaceLatitude", destlatitude);
						onmapBundle.putDouble("doublePlaceLongtitude", destlongtitude);
						onmapBundle.putString("displaycurrentPoint", "no");
				        onmapBundle.putString("place_nameEl_info", "null");
						g.setArguments(onmapBundle);
						testDB.close(debugTag);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containersettings, g);
						fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();
				}	
				else if (startingpointtv.getText().length() == 0 || destinationpointtv.getText().length() == 0){
					
				}
				else {
					
				 if (language.equals("English")){	
					 AlertDialog ald = new AlertDialog.Builder(getActivity())
					.setMessage("Destination must differ from Starting Point")
					.create();
					ald.show();	
				 }
				 else{
					 AlertDialog ald = new AlertDialog.Builder(getActivity())
					 .setMessage("� ���������� ������ �� �������� ��� ��� ��������")
					 .create();
					 ald.show();	
				 }
					//.setNeutralButton("Cancel", this)
					//.setPositiveButton("OK", this)
					
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
				}else{
					startpointcontent = ""; 
				}
		
			    if (ld > 5){
			    	destpointcontent = destv.substring(6, ld);
				}
				
				boolean flag = false;
				if (ld > 5 && !startpointcontent.toString().equals(destpointcontent)){
					flag = true;
					//Toast.makeText(getActivity(), "contents not equal", Toast.LENGTH_SHORT).show();
				}else if (destinationpointtv.getText().toString().equals("")){
					flag = false;
				}
				else{
					//Toast.makeText(getActivity(), "contents equal", Toast.LENGTH_SHORT).show();	
				}
			
				if (destinationpointtv.getText().length() > 0 && startingpointtv.getText().length() > 0 && flag == true){
					
					
					if (destpointcontent.toString().equals("Current location") || destpointcontent.toString().equals("�������� ����")){
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
						if (!language.equals("English")){
							Cursor startcursor = testDB.getPlaceByNameEl(destpointcontent.toString());
								if (startcursor.moveToFirst()){
									do{
										destlatitude2 = startcursor.getDouble(startcursor.getColumnIndex("latitude"));
										destlongtitude2 = startcursor.getDouble(startcursor.getColumnIndex("longtitude"));	
									}while(startcursor.moveToNext());
								}
						}else{
							Cursor startcursor = testDB.getPlaceByNameEn(destpointcontent.toString());
							if (startcursor.moveToFirst()){
								do{
									destlatitude2 = startcursor.getDouble(startcursor.getColumnIndex("latitude"));
									destlongtitude2 = startcursor.getDouble(startcursor.getColumnIndex("longtitude"));	
								}while(startcursor.moveToNext());
							}
						}
					}
					
					if (startpointcontent.toString().equals("Current location") || startpointcontent.toString().equals("�������� ����")){
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
					
					 if (!language.equals("English")){	
						Cursor destcursor = testDB.getPlaceByNameEl(startpointcontent.toString());
							if (destcursor.moveToFirst()){
								do{
									startlatitude2 = destcursor.getDouble(destcursor.getColumnIndex("latitude"));
									startlongtitude2 = destcursor.getDouble(destcursor.getColumnIndex("longtitude"));	
								}while(destcursor.moveToNext());
							}
					}else{
						Cursor startcursor = testDB.getPlaceByNameEn(startpointcontent.toString());
						if (startcursor.moveToFirst()){
							do{
								startlatitude2 = startcursor.getDouble(startcursor.getColumnIndex("latitude"));
								startlongtitude2 = startcursor.getDouble(startcursor.getColumnIndex("longtitude"));	
							}while(startcursor.moveToNext());
						}
					}
				}	
						ToAndFromFragment toandfrom = new ToAndFromFragment();
						Bundle locations_langBundle = new Bundle();
						locations_langBundle.putString("language", language);
						locations_langBundle.putString("fromlocation", startingpointtv.getText().toString());
						locations_langBundle.putString("tolocation", destinationpointtv.getText().toString());
						toandfrom.setArguments(locations_langBundle);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containertv, toandfrom);
						fragmentTransaction.addToBackStack("toandfrom");
						fragmentTransaction.commit();
					
					
						GoogleMapFragment g = new GoogleMapFragment();
						Bundle onmapBundle = new Bundle();
						onmapBundle.putString("language", language);
						onmapBundle.putDouble("doubleCurrentLatitude", startlatitude2);
						onmapBundle.putDouble("doubleCurrentLongtitude", startlongtitude2);
						onmapBundle.putDouble("doublePlaceLatitude", destlatitude2);
						onmapBundle.putDouble("doublePlaceLongtitude", destlongtitude2);
						onmapBundle.putString("displaycurrentPoint", "no");
				        onmapBundle.putString("place_nameEl_info", "null");
						g.setArguments(onmapBundle);
						testDB.close(debugTag);
						fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containersettings, g);
						fragmentTransaction.addToBackStack(null);
						fragmentTransaction.commit();

				}
				else if (destinationpointtv.getText().length() == 0 || startingpointtv.getText().length() == 0){
					
				}
				else{
					 if (language.equals("English")){	
						AlertDialog ald = new AlertDialog.Builder(getActivity())
						.setMessage("Starting Point must differ from Destination")
						.create();
						ald.show();	
					 }
					 else{
						 AlertDialog ald = new AlertDialog.Builder(getActivity())
						 .setMessage("� �������� ������ �� �������� ��� ��� ���������")
						 .create();
						 ald.show();	
					 }
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
				if (currentpositiondestcb.isChecked() || currentpositioncb.isChecked()){
					currentpositiondestcb.setChecked(false);
				}
				
				String charSequence = s.toString();
				if (s.length() < 2 && s.length() > 0){
					listView.setAdapter(null);
					int id = getFragmentManager().getBackStackEntryCount();
					 
					for (int i=0; i<id; i++){
							String backStackId = getFragmentManager().getBackStackEntryAt(i).getName();
						 if (backStackId.equals("popup")){
							 	getFragmentManager().popBackStack();
						 }
					}
				}else if (s.length() > 1){
					String edittextfocued = "destinationlocation";
					
					PopUpFragment popup = new PopUpFragment();
					Bundle b = new Bundle();
					b.putString("key", charSequence);
					b.putString("language", language);
					b.putString("edittextfocued", edittextfocued);
					
					popup.setArguments(b);
					fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.popupcontainer, popup);
					fragmentTransaction.addToBackStack("popup");
					fragmentTransaction.commit();
			   }
			}	
		});
		
		disarableLocationEditText.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable s) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (currentpositiondestcb.isChecked() || currentpositioncb.isChecked()){
					currentpositioncb.setChecked(false);
				}
				
				String charSequence = s.toString();
				if (s.length() < 2 && s.length() > 0){
					listView.setAdapter(null);
					int id = getFragmentManager().getBackStackEntryCount();
			
					for (int i=0; i<id; i++){
							String backStackId = getFragmentManager().getBackStackEntryAt(i).getName();
						 if (backStackId.equals("popup")){
								getFragmentManager().popBackStack();
						 }
					}
				}else if (s.length() > 1){
					String edittextfocued = "startinglocation";
					
					PopUpFragment popup = new PopUpFragment();
					Bundle b = new Bundle();
					b.putString("key", charSequence);
					b.putString("language", language);
					b.putString("edittextfocued", edittextfocued);
					
					popup.setArguments(b);
					fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.popupcontainer, popup);
					fragmentTransaction.addToBackStack("popup");
					fragmentTransaction.commit();
				}	
			}
		});
		
		currentpositiondestcb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked == true){
					destinationpointtv.setVisibility(View.VISIBLE);
					if (language.equals("English")){
						//totv.setText("current location");
						destinationpointtv.setText("To:   Current location");
					}else{
						//totv.setText("�������� ����");
						destinationpointtv.setText("����: �������� ����");
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
						//fromtv.setText("current location");
						startingpointtv.setText("From: Current location");
					}else{
					    //fromtv.setText("�������� ����");
						startingpointtv.setText("���:  �������� ����");
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
			MenuItem sightsitem = menu.findItem(R.id.axiotheata);
			MenuItem barrestitem = menu.findItem(R.id.barrest);
			MenuItem restitem = menu.findItem(R.id.rest);
			MenuItem intcuisitem = menu.findItem(R.id.intcuis);
			MenuItem seafooditem = menu.findItem(R.id.seafood);
			MenuItem barsitem = menu.findItem(R.id.bars);
			MenuItem clubsitem = menu.findItem(R.id.clubs);
			MenuItem pubsitem = menu.findItem(R.id.pubs); 
			MenuItem mouseiaitem = menu.findItem(R.id.mouseia);
			MenuItem ekkitem = menu.findItem(R.id.ekklisies);
			MenuItem nosokomeiaitem = menu.findItem(R.id.nosokomeia);
			if (language.equals("Greek")){
				//eventsitem.setTitle("����������");
				sightsitem.setTitle("���������");
				barrestitem.setTitle("����-��������");
				restitem.setTitle("��������");
				intcuisitem.setTitle("������� �������");
				seafooditem.setTitle("������������");
				barsitem.setTitle("����");
				clubsitem.setTitle("�����");
				pubsitem.setTitle("���������");
				mouseiaitem.setTitle("�������");
				ekkitem.setTitle("���������");
				nosokomeiaitem.setTitle("����������");
			}
		}
		else{
			MenuInflater menuInflater = getActivity().getMenuInflater();
			menuInflater.inflate(R.menu.destination_menu, menu);
			MenuItem sightsitem = menu.findItem(R.id.axiotheatad);
			MenuItem barrestitem = menu.findItem(R.id.barrestd);
			MenuItem restitem = menu.findItem(R.id.restd);
			MenuItem intcuisitem = menu.findItem(R.id.intcuisd);
			MenuItem seafooditem = menu.findItem(R.id.seafoodd);
			MenuItem barsitem = menu.findItem(R.id.barsd);
			MenuItem clubsitem = menu.findItem(R.id.clubsd);
			MenuItem pubsitem = menu.findItem(R.id.pubsd); 
			MenuItem mouseiaitem = menu.findItem(R.id.mouseiad);
			MenuItem ekkitem = menu.findItem(R.id.ekklisiesd);
			MenuItem nosokomeiaitem = menu.findItem(R.id.nosokomeiad);
			if (language.equals("Greek")){
				sightsitem.setTitle("���������");
				barrestitem.setTitle("����-��������");
				restitem.setTitle("��������");
				intcuisitem.setTitle("������� �������");
				seafooditem.setTitle("������������");
				barsitem.setTitle("����");
				clubsitem.setTitle("�����");
				pubsitem.setTitle("���������");
				mouseiaitem.setTitle("�������");
				ekkitem.setTitle("���������");
				nosokomeiaitem.setTitle("����������");
			}
		}
	}

	
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.mouseiad:
			list = new ArrayList<String>();
			returnCategoryCursor("museums", list);
			  
			  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			  listView.setVisibility(View.VISIBLE);
			  listView.setAdapter(dataCategoryAdapter);
			
			  listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text = ((TextView) view).getText().toString();
						onListItemClickDestination(text);
					}
			});
		break;
		case R.id.restd:
			list = new ArrayList<String>();
			returnSubCategory("restaurants", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.barrestd:
			list = new ArrayList<String>();
			returnSubCategory("bar-restaurant", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.seafoodd:
			list = new ArrayList<String>();
			returnSubCategory("seafood", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.intcuisd:
			list = new ArrayList<String>();
			returnSubCategory("intcuisine", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.barsd:
			list = new ArrayList<String>();
			returnSubCategory("bars", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.clubsd:
			list = new ArrayList<String>();
			returnSubCategory("clubs", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.pubsd:
			list = new ArrayList<String>();
			returnSubCategory("pubs", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		/*case R.id.eventsd:
			list = new ArrayList<String>();
			returnCategoryCursor("events", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;*/
		case R.id.axiotheatad:
			list = new ArrayList<String>();
			returnCategoryCursor("sightseeings", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.ekklisiesd:
			list = new ArrayList<String>();
			returnCategoryCursor("church", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.nosokomeiad:
			list = new ArrayList<String>();
			returnCategoryCursor("hospitals", list);
				  
				  dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
				  listView.setVisibility(View.VISIBLE);
				  listView.setAdapter(dataCategoryAdapter);
				
				  listView.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
								    // When clicked, show a toast with the TextView text
							String text = ((TextView) view).getText().toString();
							onListItemClickDestination(text);
						}
				});
			break;
		case R.id.mouseia:
			list = new ArrayList<String>();
			returnCategoryCursor("museums", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.nosokomeia:
			list = new ArrayList<String>();
			returnCategoryCursor("hospitals", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		/*case R.id.events:
			list = new ArrayList<String>();
			returnCategoryCursor("events", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;*/
		case R.id.axiotheata:
			list = new ArrayList<String>();
			returnCategoryCursor("sightseeings", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.ekklisies:
			list = new ArrayList<String>();
			returnCategoryCursor("church", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.barrest:
			list = new ArrayList<String>();
			returnSubCategory("bar-restaurant", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.rest:
			list = new ArrayList<String>();
			returnSubCategory("restaurants", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.intcuis:
			list = new ArrayList<String>();
			returnSubCategory("intcuisine", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.seafood:
			list = new ArrayList<String>();
			returnSubCategory("seafood", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.bars:
			list = new ArrayList<String>();
			returnSubCategory("bars", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.clubs:
			list = new ArrayList<String>();
			returnSubCategory("clubs", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;
		case R.id.pubs:
			list = new ArrayList<String>();
			returnSubCategory("pubs", list);
			  
			dataCategoryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.countries, list); 
			listView.setVisibility(View.VISIBLE);
			listView.setAdapter(dataCategoryAdapter);
			
			listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
							    // When clicked, show a toast with the TextView text
						String text1 = ((TextView) view).getText().toString();
						onListItemClickStarting(text1);
					}
			});
		break;	
		}
		return super.onContextItemSelected(item);
	}

	public void onListItemClickStarting(String text1){
		if (language.equals("English")){
			startingpointtv.setText("From: " + text1);
			fromtv.setText(text1);
			listView.setAdapter(null);
			disarableLocationEditText.setText("");
		}
		else{
			startingpointtv.setText("���:  " + text1);
			fromtv.setText(text1);
			listView.setAdapter(null);
			disarableLocationEditText.setText("");
		}
	}
	
	public void onListItemClickDestination(String text){
		if (language.equals("English")){
			destinationpointtv.setText("To:   " + text);
			totv.setText(text);
			listView.setAdapter(null);
			disarabledestLocationEditText.setText("");
		}
		else{
			destinationpointtv.setText("����: " + text);
			totv.setText(text);
			listView.setAdapter(null);
			disarabledestLocationEditText.setText("");
		}
	}
	
	public Cursor returnCategoryCursor(String category, List<String> list){
		Cursor cursor = testDB.getSpecificPlaceData(category);
		String name;
		if (cursor.moveToFirst()){
			do{
			   if (!language.equals("English")){	
				   name = cursor.getString(cursor.getColumnIndex("name_el"));
			   }
			   else{
				   name = cursor.getString(cursor.getColumnIndex("name_en"));
			   }
				list.add(name);
			}while(cursor.moveToNext());
		}
		return cursor;
	}
	
	public Cursor returnSubCategory(String subcategory, List<String> list){
		Cursor cursor = testDB.getSpecificChurchData(subcategory);
		String name;
		if (cursor.moveToFirst()){
			do{
			   if (!language.equals("English")){	
				   name = cursor.getString(cursor.getColumnIndex("name_el"));
			   }
			   else{
				   name = cursor.getString(cursor.getColumnIndex("name_en"));
			   }
				list.add(name);
			}while(cursor.moveToNext());
		}
		return cursor;
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}
	
	
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}			
}

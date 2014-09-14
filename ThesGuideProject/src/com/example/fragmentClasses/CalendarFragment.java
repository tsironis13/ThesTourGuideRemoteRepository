package com.example.fragmentClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.example.adapters.CalendarAdapter;
import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarFragment extends Fragment{

	private double current_latitude;
	private double current_longtitude;
	private GPSTracker gps;
	private String genre;
	private SimpleDateFormat df;
	private SimpleDateFormat dfmonth;
	private SimpleDateFormat dfyear;
	private String currentDate;
	private GridView gridview;
	private TextView title;
	private TextView eventslabeltv;
	private TextView mondaytv;
	private TextView tuesdaytv;
	private TextView wednesdaytv;
	private TextView thursdaytv;
	private TextView fridaytv;
	private TextView saturdaytv;
	private TextView sundaytv;
	public GregorianCalendar month, itemmonth;// calendar instances.
	public CalendarAdapter adapter;// adapter instance
	public Handler handler;// for grabbing some event values for showing the dot
							// marker.
	public ArrayList<String> items; // container to store calendar items which
									// needs showing the event marker
    private String language;
	private String flag;
	private String displayedmonth;
	private String displayedday;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
		
		View view = inflater.inflate(R.layout.calendar, container, false);	
		language = getArguments().getString("language");
		
		Locale.setDefault(Locale.getDefault());
		month = (GregorianCalendar) GregorianCalendar.getInstance();
			
		df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		dfmonth = new SimpleDateFormat("MM", Locale.getDefault());
		dfyear = new SimpleDateFormat("yyyy", Locale.getDefault());
		currentDate = df.format(month.getTime());
		//Toast.makeText(getActivity(), currentDate, Toast.LENGTH_SHORT).show();
		Log.i("current date", currentDate);
		
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();
		adapter = new CalendarAdapter(getActivity(), month);

		gridview = (GridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		eventslabeltv = (TextView) view.findViewById(R.id.eventslabeltv);
		title = (TextView) view.findViewById(R.id.title);
		mondaytv = (TextView) view.findViewById(R.id.tvmonday);
		tuesdaytv = (TextView) view.findViewById(R.id.tvtuesday);
		wednesdaytv = (TextView) view.findViewById(R.id.tvwednesday);
		thursdaytv = (TextView) view.findViewById(R.id.tvthursday);
		fridaytv = (TextView) view.findViewById(R.id.tvfriday);
		saturdaytv = (TextView) view.findViewById(R.id.tvsaturday);
		sundaytv = (TextView) view.findViewById(R.id.tvsunday);
		
	if (!language.equals("English")){	
		
		String mo = dfmonth.format(month.getTime());
		String year = dfyear.format(month.getTime());
		String month = getMonth(mo);
		title.setText(month + " " + year);
		
		eventslabeltv.setText("Εκδηλώσεις σήμερα");
		mondaytv.setText("Δευ");
		tuesdaytv.setText("Τρι");
		wednesdaytv.setText("Τετ");
		thursdaytv.setText("Πεμ");
		fridaytv.setText("Παρ");
		saturdaytv.setText("Σαβ");
		sundaytv.setText("Κυρ");
	}else{	
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
		eventslabeltv.setText("Events today");
	}
		RelativeLayout previous = (RelativeLayout) view.findViewById(R.id.previous);

		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	
				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString.get(position);
				Log.i("selected date =>", selectedGridDate);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*", "");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				flag = "onclick";
				genre = "events";
				Bundle langBundle = new Bundle();
				langBundle.putString("language", language);
			if (language.equals("English")){	
				String year = selectedGridDate.substring(0, 4);
				displayedmonth = selectedGridDate.substring(5, 7);
	            displayedday = selectedGridDate.substring(8, 10);
				eventslabeltv.setText("Events: " + displayedday + "-" + displayedmonth + "-" + year);
			}
			else{
				String year = selectedGridDate.substring(0, 4);
				displayedmonth = selectedGridDate.substring(5, 7);
	            displayedday = selectedGridDate.substring(8, 10);
				eventslabeltv.setText("Εκδηλώσεις: " + displayedday + "-" + displayedmonth + "-" + year);
			}
				ListPlacesFragment listEventsFragment = new ListPlacesFragment(genre, "", current_latitude, current_longtitude, selectedGridDate, flag);
				listEventsFragment.setArguments(langBundle);
				FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerdetails, listEventsFragment);
				fragmentTransaction.commit();
				showToast(selectedGridDate);

			
				
			}
		});
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
		
		flag = "oncreate";
		genre = "events";
		Bundle langBundle = new Bundle();
		langBundle.putString("language", language);
		ListPlacesFragment listEventsFragment = new ListPlacesFragment(genre, "", current_latitude, current_longtitude, currentDate, flag);
		listEventsFragment.setArguments(langBundle);
		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.containerdetails, listEventsFragment);
		fragmentTransaction.commit();
	}




	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1), month.getActualMinimum(GregorianCalendar.MONTH), 1);
			
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1), month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH, month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	protected void showToast(String string) {
		Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
	}

	public void refreshCalendar() {
		adapter.refreshDays();
		adapter.notifyDataSetChanged();
		handler.post(calendarUpdater); // generate some calendar items

			if (!language.equals("English")){	
				String mo = dfmonth.format(month.getTime());
				String year = dfyear.format(month.getTime());
				String month = getMonth(mo);
				title.setText(month + " " + year);			
			}else{	
				title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
			}
	}

	public Runnable calendarUpdater = new Runnable() {
		@Override
		public void run() {
			items.clear();

			// Print dates of the current week
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
			String itemvalue;
			for (int i = 0; i < 7; i++) {
				itemvalue = df.format(itemmonth.getTime());
				itemmonth.add(GregorianCalendar.DATE, 1);
				items.add("2012-09-12");
				items.add("2012-10-07");
				items.add("2012-10-15");
				items.add("2012-10-20");
				items.add("2012-11-30");
				items.add("2012-11-28");
			}

			adapter.setItems(items);
			adapter.notifyDataSetChanged();
		}
	};

	public String getMonth(String mo){
		if (mo.equals("10")){
			return "Οκτώβριος"; 
		}
		else if (mo.equals("11")){
			return "Νοέμβριος";
		}
		else if (mo.equals("12")){
			return "Δεκέμβριος";
		}
		else if (mo.equals("01")){
			return "Ιανουάριος";
		}
		else if (mo.equals("02")){
			return "Φεβρουάριος";
		}
		else if (mo.equals("03")){
			return "Μάρτιος";
		}
		else if (mo.equals("04")){
			return "Απρίλιος";
		}
		else if (mo.equals("05")){
			return "Μάιος";
		}
		else if (mo.equals("06")){
			return "Ιούνιος";
		}
		else if (mo.equals("07")){
			return "Ιούλιος";
		}
		else if (mo.equals("08")){
			return "Αύγουστος";
		}
		else {
			return "Σεπτέμβριος";
		}
	}
	
	
	
}



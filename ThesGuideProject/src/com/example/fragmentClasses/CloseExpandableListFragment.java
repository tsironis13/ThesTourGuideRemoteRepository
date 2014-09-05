package com.example.fragmentClasses;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.example.myLocation.GPSTracker;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CloseExpandableListFragment extends Fragment{

	private static final String debugTag = "CloseExpandableListFragment";
	private TextView messagetv;
	public String language;
	private ExpandableListView explvlist;
	private ArrayList<String> listDataHeader;
	private HashMap<String, ArrayList<String>> listDataChild;
	private Double curlatitude = 40.639431;
	private Double curlongtitude = 22.937125;
	private final int min_distance = 2;
	private TestLocalSqliteDatabase testDB;
	private Cursor cursor;
	private HashMap<String, Double> placesDistances;
	int sum=0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.expandablelistfragment, container, false);
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase(debugTag);
		
		placesDistances = new HashMap<String, Double>();
		cursor = testDB.getSpecificPlaceData("museums");
		if (cursor.moveToFirst()){
			do{
				String name = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
				Double latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
				Double longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
				
				double apostasi = GPSTracker.getDistance(this.curlatitude, this.curlongtitude, latitude, longtitude);
				double distanceInKm = apostasi/1000;
				DecimalFormat df = new DecimalFormat("#.##");
				String s = Double.toString(distanceInKm);
				
				if (distanceInKm <= min_distance){
					placesDistances.put(name, distanceInKm);
					Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
				}
				
			}while(cursor.moveToNext());
		}
		
		
		messagetv = (TextView) view.findViewById(R.id.messageclosetv);
		language = getArguments().getString("language");
		if (language.equals("English")){
			messagetv.setText("Places with distance less than 2km");
		}else{
			messagetv.setText("Μέρη με απάσταση μικρότερη απο 2χμ");
		}
		
		 explvlist = (ExpandableListView) view.findViewById(R.id.explistview);
		 
		 //preparing list data
	     prepareListData();
		 
         explvlist.setAdapter(new ParentLevelListAdapter(listDataHeader, listDataChild));

		return view;
	}

	private void prepareListData(){
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, ArrayList<String>>();
		
		// Adding child data
		if (language.equals("English")){
			listDataHeader.add("Sightseeings");
			listDataHeader.add("Museums");
			listDataHeader.add("Hospitals");
			listDataHeader.add("Nightlife");
			
        }else{
        	listDataHeader.add("Αξιοθέατα");
			listDataHeader.add("Μουσεία");
			listDataHeader.add("Νυχτερινή Ζωή");
			//listDataHeader.add("Νοσοκομεία");
        }
		
		if (language.equals("English")){
			// Adding child data
			ArrayList<String> sightseeings = new ArrayList<String>();
			sightseeings.add("leukos pyrgos");
			sightseeings.add("kamara");
			
		
	        ArrayList<String> museums = new ArrayList<String>();
	        Iterator myVeryOwnIterator = placesDistances.keySet().iterator();
	        while(myVeryOwnIterator.hasNext()) {
	        	String key=(String)myVeryOwnIterator.next();
	            Double value=(Double)placesDistances.get(key);
	        	
	        	museums.add(key);
	            sum = sum + 1;
	        	
	        }
	       // museums.add("arxaiologiko");
	       // museums.add("byzantino");
	       // museums.add("noisis");
	            
	        ArrayList<String> hospitals = new ArrayList<String>();
	        hospitals.add("papageorgiou");
	        hospitals.add("papanikolaou");
	        hospitals.add("genimatas");
	        
	        listDataChild.put(listDataHeader.get(0), sightseeings); // Header, Child data
	        listDataChild.put(listDataHeader.get(1), museums);
	        listDataChild.put(listDataHeader.get(2), hospitals);
	        //listDataChild.put(listDataHeader.get(2), nightlife);
	        
		}else{
			
		}
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		explvlist.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {           
				Toast.makeText(
                        getActivity(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                        listDataHeader.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT)
                        .show();
				return false;
			}			
		});
	}

	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}



	public class ParentLevelListAdapter extends BaseExpandableListAdapter {

		private ArrayList<String> listDataHeader; // header titles
		private HashMap<String, ArrayList<String>> listDataChild;
		
		private ArrayList<String> listSecondLevelHeader;
		private HashMap<String, ArrayList<String>> listSecondLevelDataChild;
		
		public ParentLevelListAdapter(ArrayList<String> listDataHeader,  HashMap<String, ArrayList<String>> listChildData){
			this.listDataHeader = listDataHeader;
			this.listDataChild = listChildData;
		}
         @Override
         public Object getChild(int groupPosition, int childPosition) {
                return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
         }

         @Override
         public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
         }

         @Override
         public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        	 
        	 
        	 String headerTitle = (String) getGroup(groupPosition);		
        	// Toast.makeText(getActivity(), headerTitle, Toast.LENGTH_SHORT).show();
        	 listSecondLevelHeader = new ArrayList<String>();
        	 listSecondLevelDataChild = new HashMap<String, ArrayList<String>>();
        	 
        	 if (headerTitle.equals("Nightlife")){
        		 //String childText = (String) getChild(groupPosition, childPosition);
        		 if (language.equals("English")){
        			 listSecondLevelHeader.add("bars");
        			 listSecondLevelHeader.add("clubs");
        			 listSecondLevelHeader.add("pubs");
        			 
        			 ArrayList<String> bars = new ArrayList<String>();
        			 bars.add("bar1");
        			 bars.add("bar2");
        			 
        			 ArrayList<String> clubs = new ArrayList<String>();
        			 clubs.add("club1");
        			 clubs.add("club2");
        			 
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(0), bars); // Header, Child data
        			 listSecondLevelDataChild.put(listSecondLevelHeader.get(1), clubs);
        		 }
        		 
                CustExpListview SecondLevelexplv = new CustExpListview(getActivity());
                SecondLevelexplv.setAdapter(new SecondLevelAdapter(listSecondLevelHeader, listSecondLevelDataChild));
                //SecondLevelexplv.setGroupIndicator(null);
                return SecondLevelexplv;
        	 }else{
        		  String childText = (String) getChild(groupPosition, childPosition);
        		  Log.i("Child Text =>", childText);
        		// if (convertView == null) {
                     LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                     convertView = infalInflater.inflate(R.layout.listitem, null);
               //  }
        		 TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        	     txtListChild.setText(childText);
        	     
        	     return convertView;
        	 }
                
         }

         @Override
         public int getChildrenCount(int groupPosition) {
        	 String headerTitle = (String) getGroup(groupPosition);
        	 if (headerTitle.equals("Nightlife")){
        		 return 1;
        	 }else{
                return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();}
        	// return 3;
         }

         @Override
         public Object getGroup(int groupPosition) {
                return this.listDataHeader.get(groupPosition);
         }

         @Override
         public int getGroupCount() {
                return this.listDataHeader.size();
         }

         @Override
         public long getGroupId(int groupPosition) {
                return groupPosition;
         }

         @Override
         public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                //TextView tv = new TextView(getActivity()); 
                String headerTitle = (String) getGroup(groupPosition);
                if (convertView == null) {
                    LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = infalInflater.inflate(R.layout.listgroup, null);
                }
                TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
                lblListHeader.setTypeface(null, Typeface.BOLD);
                lblListHeader.setText(headerTitle);
         
                TextView sumtv = (TextView) convertView.findViewById(R.id.sumtv);
                if (headerTitle.equals("Museums")){
                	String s = Integer.toString(sum);
                	sumtv.setText(s);
                }
                else{
                	sumtv.setText("15");
                }
                
                return convertView;
                //tv.setText("      FirstLevel");
                //tv.setTextColor(Color.BLACK);
                //tv.setTextSize(20);
                //tv.setBackgroundColor(Color.BLUE);
                //tv.setPadding(10, 7, 7, 7);

       
         }

         @Override
         public boolean hasStableIds() {
                return true;
         }

         @Override
         public boolean isChildSelectable(int groupPosition, int childPosition) {
                return true;
         }

  }

	 public static class CustExpListview extends ExpandableListView {

         int intGroupPosition, intChildPosition, intGroupid;
         
         public CustExpListview(Context context) {
             super(context);
            
         }

         protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(600, MeasureSpec.AT_MOST);
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(800, MeasureSpec.AT_MOST);
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         }

  }
	 
	 public class SecondLevelAdapter extends BaseExpandableListAdapter {

		 private ArrayList<String> listSecondLevelHeader;
		 private HashMap<String, ArrayList<String>> listSecondLevelDataChild;
		 
		 public SecondLevelAdapter(ArrayList<String> listSecondLevelHeader, HashMap<String, ArrayList<String>> listSecondLevelDataChild){
			 this.listSecondLevelHeader = listSecondLevelHeader;
			 this.listSecondLevelDataChild = listSecondLevelDataChild;
		 }
		 
         @Override
         public Object getChild(int groupPosition, int childPosition) {
        	 return this.listSecondLevelDataChild.get(this.listSecondLevelHeader.get(groupPosition)).get(childPosition);
         }

         @Override
         public long getChildId(int groupPosition, int childPosition) {
                return childPosition;
         }

         @Override
         public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        	 String childText = (String) getChild(groupPosition, childPosition);
   		  Log.i("Child Text =>", childText);
   		// if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.listitem, null);
          //  }
   		 TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
   	     txtListChild.setText(childText);
   	     
   	     return convertView;
                /*TextView tv = new TextView(getActivity());
                tv.setText("child");
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(20);
                tv.setPadding(15, 5, 5, 5);
                tv.setBackgroundColor(Color.YELLOW);
                tv.setLayoutParams(new ListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

                return tv;*/
         }

         @Override
         public int getChildrenCount(int groupPosition) {
                //return 5;
        	 return this.listSecondLevelDataChild.get(this.listSecondLevelHeader.get(groupPosition))
                     .size();
         }

         @Override
         public Object getGroup(int groupPosition) {
                return this.listSecondLevelHeader.get(groupPosition);
         }

         @Override
         public int getGroupCount() {
                return this.listSecondLevelHeader.size();
         }

         @Override
         public long getGroupId(int groupPosition) {
                return groupPosition;
         }

         @Override
         public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        	 
        	 String headerTitle = (String) getGroup(groupPosition);
             if (convertView == null) {
                 LayoutInflater infalInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 convertView = infalInflater.inflate(R.layout.secondlevellistgroup, null);
             }
             TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
             lblListHeader.setTypeface(null, Typeface.BOLD);
             lblListHeader.setText(headerTitle);
      
             return convertView;
        	 
               /* TextView tv = new TextView(getActivity());
                tv.setText("      Second Level");
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(20);
                tv.setPadding(12, 7, 7, 7);
                tv.setBackgroundColor(Color.WHITE);

                return tv;*/
         }

         @Override
         public boolean hasStableIds() {
               return true;
         }

         @Override
         public boolean isChildSelectable(int groupPosition, int childPosition) {
        	 Toast.makeText(getActivity(), listSecondLevelDataChild.get(
        			 listSecondLevelHeader.get(groupPosition)).get(
                     childPosition), Toast.LENGTH_SHORT).show();
                return true;
         }



  }



}

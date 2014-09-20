package com.example.fragmentClasses;

import java.util.ArrayList;

import com.example.adapters.SettingsListAdapter;
import com.example.adapters.SettingsListAdapterEnglish;
import com.example.sqlHelper.TestLocalSqliteDatabase;
import com.example.thesguideproject.R;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PopUpFragment extends Fragment{

	private TextView helpingtv;
	private static TextView startingpointtv;
	private static TextView destinationpointtv;
	private static EditText disarableLocationEditText;
	private static EditText disarabledestLocationEditText;
	private TestLocalSqliteDatabase testDB;
	private ListView androidlist;
	private ArrayList<String> items = new ArrayList<String>();
	private static final String debugTag = "PopUpFragment";
	private String language;
	private String edittextfocued;
	private String charSequence;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.popuplayout, container, false);
		
		testDB = new TestLocalSqliteDatabase(getActivity());
		testDB.openDataBase(debugTag);
		helpingtv = (TextView) view.findViewById(R.id.helpingtv);
		startingpointtv = (TextView) getActivity().findViewById(R.id.startingpointtv);
		destinationpointtv = (TextView) getActivity().findViewById(R.id.destinationpointtv);
		disarableLocationEditText = (EditText) getActivity().findViewById(R.id.pickyourdisarablelocationet);
		disarabledestLocationEditText = (EditText) getActivity().findViewById(R.id.pickyourdisarabledestlocationet);
		
		edittextfocued = getArguments().getString("edittextfocued");
		charSequence = getArguments().getString("key");
		language = getArguments().getString("language");
		androidlist = (ListView) view.findViewById(R.id.androilist);
		
		if (charSequence.length() > 1){
			//helpingtv.setVisibility(View.VISIBLE);
			helpingtv.setText(charSequence);
		}	
		
		if (edittextfocued.equals("destinationlocation")){
			loadData(charSequence.toString(), "null", "dest");
		}else{
			loadData(charSequence.toString(), "start", "null");
		}	
		return view;
	}
	
	
	private void loadData(String charsequence, String start, String dest){
		androidlist.setVisibility(View.VISIBLE);
		items.clear();
		
		 if(language.equals("English")){
			 String pattern = "^[A-Za-z0-9. ]+$";
				if (charsequence.matches(pattern)){
							String columns[] = new String[] {"_id", "name_en"};
							Object[] temp = new Object[] { 0, "default" };

							MatrixCursor cursor = new MatrixCursor(columns);
							Cursor c = testDB.searchByPlaceNameEn(charsequence);

							try{
								if (c == null){
									Log.i("Message Matched =>", "false");
								}
								else{
									if (c.moveToFirst()){
										do{
											String s = c.getString(c.getColumnIndex("name_en"));
											//Log.i("Cursor contents =>", s);
											items.add(s);
										}
										while(c.moveToNext());
									}
								}
							}
							finally
							{
								c.close();
							}

							for (int i=0; i<items.size(); i++){
								temp[0] = i;
								temp[1] = items.get(i);
								cursor.addRow(temp);
							}

							//String lang = "Latin";
							testDB.close(debugTag);
							androidlist.setAdapter(new SettingsListAdapterEnglish(start, dest, getActivity(), cursor, items, destinationpointtv, startingpointtv, androidlist, disarabledestLocationEditText, disarableLocationEditText, helpingtv));
							
					}
					else{
							Log.i("Query =>", charsequence);
							String columns[] = new String[] {"_id", "name_en"};
							Object[] temp = new Object[] { 0, "default" };
				
							MatrixCursor cursor = new MatrixCursor(columns);
							Cursor c = testDB.searchByPlaceName(charsequence);
				
							try{
								if (c == null){
									Log.i("Message Matched =>", "false");
								}
								else{
									//Log.i("Message Matched =>", "true");
									if (c.moveToFirst()){
										do{
											String s = c.getString(c.getColumnIndex("name_en"));
											//Log.i("Cursor contents =>", s);
											items.add(s);
										}
										while(c.moveToNext());
									}
								}
							}
							finally
							{
								c.close();
							}
				
							for (int i=0; i<items.size(); i++){
								temp[0] = i;
								temp[1] = items.get(i);
								cursor.addRow(temp);
							}
							//t.setSuggestionPressedField("true");
							//String lang = "Greek";	
							testDB.close(debugTag);
							androidlist.setAdapter(new SettingsListAdapterEnglish(start, dest, getActivity(), cursor, items, destinationpointtv, startingpointtv, androidlist, disarabledestLocationEditText, disarableLocationEditText, helpingtv));
					 }
				} else{	
						String pattern = "^[A-Za-z0-9. ]+$";
						if (charsequence.matches(pattern)){
									String columns[] = new String[] {"_id", "nameel_lower"};
									Object[] temp = new Object[] { 0, "default" };

									MatrixCursor cursor = new MatrixCursor(columns);
									Cursor c = testDB.searchByPlaceNameEn(charsequence);

									try{
										if (c == null){
											Log.i("Message Matched =>", "false");
										}
										else{
											if (c.moveToFirst()){
												do{
													String s = c.getString(c.getColumnIndex("name_el"));
													//Log.i("Cursor contents =>", s);
													items.add(s);
												}
												while(c.moveToNext());
											}
										}
									}
									finally
									{
										c.close();
									}

									for (int i=0; i<items.size(); i++){
										temp[0] = i;
										temp[1] = items.get(i);
										cursor.addRow(temp);
									}

									testDB.close(debugTag);
									androidlist.setAdapter(new SettingsListAdapter(start, dest, getActivity(), cursor, items, destinationpointtv, startingpointtv, androidlist, disarabledestLocationEditText, disarableLocationEditText, helpingtv));
							}
							else{
									Log.i("Query =>", charsequence);
									String columns[] = new String[] {"_id", "nameel_lower"};
									Object[] temp = new Object[] { 0, "default" };
						
									MatrixCursor cursor = new MatrixCursor(columns);
									Cursor c = testDB.searchByPlaceName(charsequence);
						
									try{
										if (c == null){
											Log.i("Message Matched =>", "false");
										}
										else{
											//Log.i("Message Matched =>", "true");
											if (c.moveToFirst()){
												do{
													String s = c.getString(c.getColumnIndex("name_el"));
													//Log.i("Cursor contents =>", s);
													items.add(s);
												}
												while(c.moveToNext());
											}
										}
									}
									finally
									{
										c.close();
									}
						
									for (int i=0; i<items.size(); i++){
										temp[0] = i;
										temp[1] = items.get(i);
										cursor.addRow(temp);
									}
									testDB.close(debugTag);
									androidlist.setAdapter(new SettingsListAdapter(start, dest, getActivity(), cursor, items, destinationpointtv, startingpointtv, androidlist, disarabledestLocationEditText, disarableLocationEditText, helpingtv));
							}
					 }
		 }


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		testDB.close(debugTag);
		super.onDestroy();
	}	
	

}

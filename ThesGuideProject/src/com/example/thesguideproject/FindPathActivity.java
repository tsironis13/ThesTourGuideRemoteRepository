package com.example.thesguideproject;

import java.util.ArrayList;

import com.example.adapters.DisarableLocationCursorAdapter;
import com.example.sqlHelper.TestLocalSqliteDatabase;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ECLAIR) public class FindPathActivity extends Activity{

	private static EditText disarableLocationEditText;
	private static ListView listView;
	private static TextView startingpointtv;
	private ArrayList<String> items = new ArrayList<String>();
	private TestLocalSqliteDatabase testDB = new TestLocalSqliteDatabase(this);
	private static final String debugTag = "FindPathActivity";
	private static int SPLASH_TIME_OUT = 5000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.findpath);
		
		listView = (ListView) findViewById(R.id.list);
		
		if (listView.isPressed()){
			 
		}
		
		startingpointtv = (TextView) findViewById(R.id.startingpointtv);
		
		if (startingpointtv.getText().length() > 1){
			//listView.setAdapter(null);
		}
		
		disarableLocationEditText = (EditText) findViewById(R.id.pickyourdisarablelocationet);
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(disarableLocationEditText, InputMethodManager.SHOW_IMPLICIT);
		disarableLocationEditText.addTextChangedListener(new TextWatcher(){

		
			
			@Override
			public void afterTextChanged(Editable s) {
				//listView.setVisibility(View.GONE);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			    Editable getEditableText = disarableLocationEditText.getText();
			    String getStringText = getEditableText.toString();
				Log.i("GET TEXT FROM EDIT TEXT =>",  getStringText);
				loadData(getStringText);
			}
		});
		
		
		
	
	
	}
	

	private void loadData(String getStringText) {
		items.clear();
		
	 if (getStringText.length()>2){	
		
		String columns[] = new String[] {"_id", "name_el"};
		Object[] temp = new Object[] { 0, "default" };
		
		MatrixCursor cursor = new MatrixCursor(columns);
		
		testDB.openDataBase(debugTag);
		Cursor c = testDB.searchByPlaceName(getStringText);
		
		try{
			if (c == null){
				Log.i("Message Matched =>", "false");
			}
			else{
				if (c.moveToFirst()){
					do{
						String s = c.getString(c.getColumnIndex("name_el"));
						Log.i("Cursor contents =>", s);
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
		
		listView.setAdapter(new DisarableLocationCursorAdapter(this, cursor, items));
	
	 }
	 else{
		listView.setAdapter(null);
	 }
   }
	
   	
   	
	
   @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		testDB.close(debugTag);
	}

    public void setStartingPointTextViewText(String text){
       listView.setAdapter(null);
       //disarableLocationEditText.setInputType(0);
       //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	   startingpointtv.setVisibility(View.VISIBLE);
	   startingpointtv.setText(text);
	   disarableLocationEditText.setText("");
	 
    }


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		 listView.setAdapter(null);
		super.onBackPressed();
		
	}


    



	
	
	
}

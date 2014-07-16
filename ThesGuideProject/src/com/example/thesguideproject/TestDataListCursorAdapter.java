package com.example.thesguideproject;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestDataListCursorAdapter extends SimpleCursorAdapter {

	private CursorAdapterExample activity;
	private LayoutInflater layoutInflater;
	private Context context;
	private int layout;
	
	
	public TestDataListCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
		super(context, layout, c, from, to);
		this.context = context;
		this.layout = layout;
		// TODO Auto-generated constructor stub
	}

	public View newView(Context context, Cursor cursor, ViewGroup parent){
		Cursor c = getCursor();
		 
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);
 
        int nameCol = c.getColumnIndex("_id");
        int surnameCol = c.getColumnIndex("surname");
 
        String name = c.getString(nameCol);
        String surname = c.getString(surnameCol);
        
        /**
         * Next set the name of the entry.
         */    
        TextView name_text = (TextView) v.findViewById(R.id.name_entry);
        TextView surname_text = (TextView) v.findViewById(R.id.surname_entry);
        if (surname_text != null && name_text != null) {
        	name_text.setText(name);
            surname_text.setText(surname);
        }
 
        return v;
	}
	
	@Override
    public void bindView(View v, Context context, Cursor c) {
 
        int surnameCol = c.getColumnIndex("surname");
 
        String surname = c.getString(surnameCol);
 
        /**
         * Next set the name of the entry.
         */    
        TextView surname_text = (TextView) v.findViewById(R.id.surname_entry);
        if (surname_text != null) {
            surname_text.setText(surname);
        }
    }

	


	
	
}

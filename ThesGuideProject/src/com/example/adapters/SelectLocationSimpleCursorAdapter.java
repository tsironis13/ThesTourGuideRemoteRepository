package com.example.adapters;


import com.example.fragmentClasses.SelectByCategoryPlacesListFragment;
import com.example.fragmentClasses.SettingsMapFragment;
import com.example.thesguideproject.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SelectLocationSimpleCursorAdapter extends SimpleCursorAdapter implements OnClickListener{
	
	private String genre;
	private String flag;
	private SelectByCategoryPlacesListFragment selPlaceListFragment;
	private Context context;
	private int layout;
	private Cursor cursor;
	
	
	public SelectLocationSimpleCursorAdapter(String flag, String genre, SelectByCategoryPlacesListFragment selPlaceListFragment, Context context, int layout, Cursor cursor, String[] from, int[] to){
		super(context, layout, cursor, from, to);
		this.flag = flag;
		this.genre = genre;
		this.selPlaceListFragment = selPlaceListFragment;
		this.context = context;
		this.layout = layout;
		this.cursor = cursor;
	}
	
	private static class ViewHolder{
		TextView placeNametv;
		
		ViewHolder(View v){
			placeNametv = (TextView) v.findViewById(R.id.placeNametv);
		}
	}

	@Override
	public View getView(int pos, View inView, ViewGroup parent) {
		View v = inView;
		ViewHolder viewHolder;
		
		if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder(v);
            viewHolder.placeNametv = (TextView) v.findViewById(R.id.placeNametv);
            viewHolder.placeNametv.setTag(viewHolder);
            v.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		
		this.cursor.moveToPosition(pos);
		String placeNameEl = this.cursor.getString(this.cursor.getColumnIndex("name_el"));
		
		viewHolder.placeNametv.setText(placeNameEl);
		viewHolder.placeNametv.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View view) {
		ViewHolder vH = (ViewHolder) view.getTag();
		CharSequence s = vH.placeNametv.getText();
		String s1 = s.toString();
		//SettingsMapFragment.s(s1,flag);
		//Toast.makeText(context, s1, Toast.LENGTH_SHORT).show();
		//settingsMapFragment.setStartingPointTextViewText(s1);
		
	}
	
	
	
	
	
	
	

}

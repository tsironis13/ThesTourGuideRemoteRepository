package com.example.adapters;

import java.util.List;

import com.example.thesguideproject.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SettingsListAdapterEnglish extends CursorAdapter implements OnClickListener{

	private List<String> items;
	private Context context; 
	private Cursor cursor;	
	private String placeNameEl;
	private TextView startpointtv;
	private TextView destpointtv;
	private ListView listview;
	private EditText disarableLocationEditText;
	private EditText disarabledestLocationEditText;
	private String start;
	private String dest;
	
	public SettingsListAdapterEnglish(String start, String dest, Context context, Cursor cursor, List<String> items, TextView destpointtv, TextView startpointtv, ListView listview, EditText disarabledestLocationEditText, EditText disarableLocationEditText) {
		super(context, cursor, false);
		this.start = start;
		this.dest = dest;
		this.items = items;
		this.context = context;
		this.cursor = cursor;
		this.startpointtv = startpointtv;
		this.destpointtv = destpointtv;
		this.listview = listview;
		this.disarabledestLocationEditText = disarabledestLocationEditText;
		this.disarableLocationEditText = disarableLocationEditText;
		// TODO Auto-generated constructor stub
	}
	
	private static class ViewHolder{
		TextView t;
		
		ViewHolder(View v){
			//item = (Button) v.findViewById(R.id.text);
			t = (TextView) v.findViewById(R.id.tttt);
		}
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		//text.setText((CharSequence) items.get(cursor.getPosition()));
		

	}

	@Override
	public View getView(int position, View inView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = inView;
		ViewHolder viewHolder;
		if (v == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 
	        v = inflater.inflate(R.layout.countries, parent, false);
	        viewHolder = new ViewHolder(v);
	        //viewHolder.item = (Button) v.findViewById(R.id.text);
	        viewHolder.t = (TextView) v.findViewById(R.id.tttt);
	        viewHolder.t.setTag(viewHolder);
	        v.setTag(viewHolder); 
		}
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		
		this.cursor.moveToPosition(position);
	    placeNameEl = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
	
		viewHolder.t.setText(placeNameEl);
		viewHolder.t.setOnClickListener(this);
		
		return v;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ViewHolder vH = (ViewHolder) v.getTag();
		InputMethodManager imm = (InputMethodManager) this.context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
		CharSequence s = vH.t.getText();
		String s1 = s.toString();
		
	if (start.equals("start") && dest.equals("null")){	
		startpointtv.setText("From: "+s1);
		this.disarableLocationEditText.setText("");
		this.listview.setAdapter(null);
	
		
	}
	else if (start.equals("null") && dest.equals("dest")){
		destpointtv.setText("To:   "+s1);
		this.disarabledestLocationEditText.setText("");
		this.listview.setAdapter(null);
	}
	
		//MenuFragment m = new MenuFragment(s1);
		//PlacesListFragmentActivity p = new PlacesListFragmentActivity();
		//p.setPlaceSearchFragment();
		//m.startFragment();
		//Intent intent = new Intent(context, StartActivityFromFragment.class);
		//context.startActivity(intent);
		
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

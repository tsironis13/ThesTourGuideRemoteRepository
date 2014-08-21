package com.example.thesguideproject;

import java.util.List;











import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ExampleAdapter extends CursorAdapter implements OnClickListener{

	private List items;
	private Context context; 
    private TextView t;
    private Button text;
	private Cursor cursor;		
    
	public ExampleAdapter(Context context, Cursor cursor, List items) {
		super(context, cursor, false);
		this.items = items;
		this.context = context;
		this.cursor = cursor;
		// TODO Auto-generated constructor stub
	}
    
	private static class ViewHolder{
		Button item;
		TextView t;
		
		ViewHolder(View v){
			//item = (Button) v.findViewById(R.id.text);
			t = (TextView) v.findViewById(R.id.t);
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
			 
	        v = inflater.inflate(R.layout.item, parent, false);
	        viewHolder = new ViewHolder(v);
	        //viewHolder.item = (Button) v.findViewById(R.id.text);
	        viewHolder.t = (TextView) v.findViewById(R.id.t);
	        viewHolder.t.setTag(viewHolder);
	        v.setTag(viewHolder); 
		}
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		
		this.cursor.moveToPosition(position);
		String placeNameEl = this.cursor.getString(this.cursor.getColumnIndex("name_el"));
		
		viewHolder.t.setText(placeNameEl);
		viewHolder.t.setOnClickListener(this);
		
		return v;
	}


	/*@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
        View view = inflater.inflate(R.layout.item, parent, false);
 
        text = (TextView) view.findViewById(R.id.text);
 
        return view;
	}*/

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ViewHolder vH = (ViewHolder) v.getTag();
		
		CharSequence s = vH.t.getText();
		String s1 = s.toString();
		Log.i("TextView Clicked =>", s1);
		Intent intent = new Intent(context, StartActivityFromFragment.class);
		context.startActivity(intent);
		
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}

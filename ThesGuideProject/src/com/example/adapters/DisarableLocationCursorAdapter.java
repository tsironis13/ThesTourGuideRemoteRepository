package com.example.adapters;

import java.util.List;

import com.example.thesguideproject.FindPathActivity;
import com.example.thesguideproject.R;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisarableLocationCursorAdapter extends CursorAdapter implements OnClickListener{

	private List items;
	private Context context; 
	private Cursor cursor;
	private FindPathActivity findPathActivity = new FindPathActivity();
	
	public DisarableLocationCursorAdapter(Context context, Cursor cursor, List items){
		super(context, cursor, false);
		this.items = items;
		this.context = context;
		this.cursor = cursor;
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
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
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
		        v.setTag(viewHolder); }
		        else{
					viewHolder = (ViewHolder) v.getTag();
				}
				
				this.cursor.moveToPosition(position);
				String placeNameEl = this.cursor.getString(this.cursor.getColumnIndex("name_el"));
				
				viewHolder.t.setText(placeNameEl);
				viewHolder.t.setOnClickListener(this);
				
				return v;
	}


	

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}




	@Override
	public void onClick(View v) {
		ViewHolder vH = (ViewHolder) v.getTag();
		CharSequence s = vH.t.getText();
		String s1 = s.toString();
		Log.i("TextView Clicked =>", s1);
		//Object pos = (Object) v.getTag();
		//items.remove(pos);
		//this.notifyDataSetChanged();
		findPathActivity.setStartingPointTextViewText(s1);
		//FindPathActivity.setStartingPointTextViewText(s1);	
	}
	
}

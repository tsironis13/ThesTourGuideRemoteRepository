package com.example.adapters;

import java.util.ArrayList;

import com.example.fragmentClasses.ListPlacesFragment;
import com.example.fragmentClasses.ListPlacesFragment.MyViewHolder;
import com.example.locationData.PlacesData;
import com.example.thesguideproject.R;




import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventsBaseAdapter extends BaseAdapter{

	private ArrayList<PlacesData> locations;
	private LayoutInflater layoutInflater;
	private ListPlacesFragment fragment;
	private Context context;
	private int layout;
	
	public EventsBaseAdapter(ListPlacesFragment fragment, Context context, int layout, ArrayList<PlacesData> locations){
		this.fragment = fragment;
		this.context = context;
		this.layout = layout;
		//this.layoutInflater = layoutInflater;
		this.locations = locations;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.locations.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder holder;
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(layout, parent, false);
			holder = new MyViewHolder();
			holder.placeNametv = (TextView) convertView.findViewById(R.id.placeNametv);
			convertView.setTag(holder);
		}
		else{
			holder = (MyViewHolder) convertView.getTag();
		}
		
		PlacesData location = locations.get(position);
		holder.locations = location;
		holder.placeNametv.setText(location.getNameEl());
		
		
		return convertView;
	}

}

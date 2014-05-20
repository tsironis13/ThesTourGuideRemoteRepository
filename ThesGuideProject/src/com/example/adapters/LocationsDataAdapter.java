package com.example.adapters;

import java.util.ArrayList;

import com.example.locationData.LocationData;
import com.example.tasks.ImageTask;
import com.example.thesguideproject.ActBarTest;
import com.example.thesguideproject.ActBarTest.MyViewHolder;
import com.example.thesguideproject.R;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationsDataAdapter extends BaseAdapter{

	private static String debugTag = "LocationsDataAdapter";
	private ActBarTest activity;
	private ImageTask imgFetcher;
	private LayoutInflater layoutInflater;
	private ArrayList<LocationData> locations;
	
	public LocationsDataAdapter(ActBarTest a, ImageTask i,  LayoutInflater l, ArrayList<LocationData> data){
		this.activity = a;
		this.imgFetcher = i;
		this.layoutInflater = l;
		this.locations = data;
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
	public long getItemId(int pos) {
		// TODO Auto-generated method stub
		return pos;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewHolder holder;
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.places_basic_layout, parent, false);
			holder = new MyViewHolder();
			holder.genre = (TextView) convertView.findViewById(R.id.locationName);
			holder.icon = (ImageView) convertView.findViewById(R.id.locationImage);
			holder.nameEl = (TextView)convertView.findViewById(R.id.nameEl);
			convertView.setTag(holder);
		}
		else{
			holder = (MyViewHolder) convertView.getTag();
		}
   		
   		LocationData location = locations.get(pos);
   		holder.locations = location;
   		holder.genre.setText(location.getGenre());
   		holder.nameEl.setText(location.getNameEl());
   		if(location.getPhotoLink() != null) {
   			holder.icon.setTag(location.getPhotoLink());
   			Drawable dr = imgFetcher.loadImage(this, holder.icon);
   			if(dr != null) {
   				holder.icon.setImageDrawable(dr);
   			}
   		} else {
   			holder.icon.setImageResource(R.drawable.filler_icon);
   		}
		
		return convertView;
	}

}

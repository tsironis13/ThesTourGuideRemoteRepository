package com.example.adapters;

import java.util.ArrayList;

import com.example.locationData.LocationData;
import com.example.tasks.ImageTask;
import com.example.thesguideproject.ActBarTest;
import com.example.thesguideproject.ActBarTest.MyViewHolder;
import com.example.thesguideproject.R;
import com.example.thesguideproject.Test;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LocationsDataAdapter extends BaseAdapter implements OnClickListener{

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
			holder.nameEl = (TextView) convertView.findViewById(R.id.nameEl);
			//holder.latitude = (TextView) convertView.findViewById(R.id.latitudetv);
			//holder.relLay = (RelativeLayout) convertView.findViewById(R.id.relativeLayout);
			holder.detailsButton = (Button) convertView.findViewById(R.id.details_button);
			holder.latitude = (TextView) convertView.findViewById(R.id.latitudetv);
			holder.longtitude = (TextView) convertView.findViewById(R.id.longtitudetv);
			holder.detailsButton.setTag(holder);
			convertView.setTag(holder);
		}
		else{
			holder = (MyViewHolder) convertView.getTag();
		}
   		
   		LocationData location = locations.get(pos);
   		holder.locations = location;
   		holder.genre.setText(location.getGenre());
   		holder.nameEl.setText(location.getNameEl());
   		//holder.relLay.setOnLongClickListener(this);
   		//holder.latitude.setText(location.getLatitude());
   		holder.detailsButton.setOnClickListener(this);
   		
   		
   		//String lat = location.getLatitude();
   		//Log.d(debugTag, "LATITUDE IS : " + lat);
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
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		MyViewHolder holder = (MyViewHolder) v.getTag();
		Intent intent = new Intent(this.activity, Test.class);
		//String s = holder.locations.getGenre();
		intent.putExtra("nameEl", holder.locations.getNameEl());
		intent.putExtra("latitude", holder.locations.getLatitude());
		intent.putExtra("longtitude", holder.locations.getLongtitude());
		this.activity.startActivity(intent);
		
	}
	

}

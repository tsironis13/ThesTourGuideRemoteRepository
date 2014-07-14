package com.example.adapters;

import java.util.ArrayList;

import com.example.locationData.LocationData;
import com.example.locationData.TestData;
import com.example.tasks.ImageTask;
import com.example.thesguideproject.MainLayoutActivity;
import com.example.thesguideproject.R;
import com.example.thesguideproject.ActBarTest.MyViewHolder;
import com.example.thesguideproject.MainLayoutActivity.MyTestViewHolder;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TestDataAdapter extends BaseAdapter {

	private MainLayoutActivity activity;
	private LayoutInflater layoutInflater;
	private ArrayList<TestData> testData;
	private ImageTask imgFetcher;
	
	public TestDataAdapter(MainLayoutActivity a, ImageTask i, LayoutInflater l, ArrayList<TestData> data){
		this.activity = a;
		this.imgFetcher = i;
		this.layoutInflater = l;
		this.testData = data;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.testData.size();
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
		MyTestViewHolder testHolder;
		 
		if(convertView == null){
			convertView = layoutInflater.inflate(R.layout.places_basic_layout, parent, false);
			testHolder = new MyTestViewHolder();
			testHolder.name = (TextView) convertView.findViewById(R.id.nameEl);
			testHolder.surname = (TextView) convertView.findViewById(R.id.locationName);
			testHolder.icon = (ImageView) convertView.findViewById(R.id.locationImage);
			testHolder.latitude = (TextView) convertView.findViewById(R.id.latitudetv);
			convertView.setTag(testHolder);
		}else{
			testHolder = (MyTestViewHolder) convertView.getTag();
		}		
		
		TestData test = testData.get(pos);
   		testHolder.testData = test;
   		testHolder.name.setText(test.getName());
   		testHolder.surname.setText(test.getSurname());
   		testHolder.latitude.setText(test.getType());
   		
   		if(test.getImageLink() != null) {
   			testHolder.icon.setTag(test.getImageLink());
   			Drawable dr = imgFetcher.loadImage(this, testHolder.icon);
   			if(dr != null) {
   				testHolder.icon.setImageDrawable(dr);
   			}
   		} else {
   			testHolder.icon.setImageResource(R.drawable.filler_icon);
   		}
   		
   		
		
   		return convertView;
	}

}

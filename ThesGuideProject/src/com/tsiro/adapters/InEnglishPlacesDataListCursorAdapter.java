package com.tsiro.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.tsiro.thesguideproject.R;
import com.tsiro.fragmentClasses.ListPlacesFragment;
import com.tsiro.fragmentClasses.SearchPlaceResultListFragment;
import com.tsiro.locationData.PlacesData;
import com.tsiro.myLocation.GPSTracker;
import com.tsiro.storage.InternalStorage;
import com.tsiro.tasks.BitmapTask;
import com.tsiro.thesguideproject.PlacesDetailsTabs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InEnglishPlacesDataListCursorAdapter extends SimpleCursorAdapter implements OnClickListener{

	private ListPlacesFragment activity;
	private SearchPlaceResultListFragment searchPlaceResultListFragment;
	private BitmapTask imgFetcher;
	private Context context;
	private int layout;
	private Cursor cursor;
	private double current_latitude;
	private double current_longtitude;
	private String button_pressed;
	private Boolean imagessavedFlag;
	private Bitmap bitmap;
	private String fontcolor="";
	private Bitmap bit;
	private LayoutInflater mLayoutInflater;
	
	GPSTracker gps;
	ArrayList<PlacesData> placesDataArray = new ArrayList<PlacesData>();
	
	
	public InEnglishPlacesDataListCursorAdapter(String button_pressed, SearchPlaceResultListFragment searchPlaceResultListFragment, Context context, int layout, Cursor cursor, String[] from, int[] to, double current_latitude, double current_longtitude, boolean imagessavedFlag, String fontcolor) {
		super(context, layout, cursor, from, to);
		this.button_pressed = button_pressed;
		this.searchPlaceResultListFragment = searchPlaceResultListFragment;
		this.context = context.getApplicationContext();
		this.layout = layout;
		this.cursor = cursor;
		//this.imgFetcher = i;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		this.imagessavedFlag = imagessavedFlag;
		this.fontcolor = fontcolor;
		// TODO Auto-generated constructor stub
	}
	
	public InEnglishPlacesDataListCursorAdapter(String button_pressed, ListPlacesFragment activity, Context context, int layout, Cursor cursor, String[] from, int[] to, double current_latitude, double current_longtitude, boolean imagessavedFlag) {
		super(context, layout, cursor, from, to);
		this.button_pressed = button_pressed;
		this.activity = activity;
		this.context = context;
		this.layout = layout;
		this.cursor = cursor;
		//this.imgFetcher = i;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		this.imagessavedFlag = imagessavedFlag;
		// TODO Auto-generated constructor stub
	}
	/*
	@SuppressWarnings("deprecation")
	public PLacesDataListCursorAdapter(String button_pressed, CursorAdapterExample activity, Context context, int layout, Cursor c, String[] from, int[] to, BitmapTask i, double current_latitude, double current_longtitude) {
		super(context, layout, c, from, to);
		this.button_pressed = button_pressed;
		this.activity = activity;
		this.context = context;
		this.layout = layout;
		this.c = c;
		this.imgFetcher = i;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		// TODO Auto-generated constructor stub
	}*/
	
	static class ViewHolder{
			public TextView nametv, placeNametv, distance, latitudetv, longtitudetv, desc_infohiddentv, menuhiddentv,
			telhiddentv, linkhiddentv, fbLinkhiddentv, emailhiddentv, exhibitionhiddentv,
			photoLink1hiddentv, photoLink2hiddentv, photoLink3hiddentv, photoLink4hiddentv;
			public ImageView icon;
			public Button infoButton;
			
			public ViewHolder(View v){
				desc_infohiddentv = (TextView) v.findViewById(R.id.descinfohiddentv);
				menuhiddentv = (TextView) v.findViewById(R.id.menuhiddentv);
				telhiddentv = (TextView) v.findViewById(R.id.telhiddentv);
				linkhiddentv = (TextView) v.findViewById(R.id.linkhiddentv);
				fbLinkhiddentv = (TextView) v.findViewById(R.id.fbLinkhiddentv);
				emailhiddentv = (TextView) v.findViewById(R.id.emailhiddentv);
				exhibitionhiddentv = (TextView) v.findViewById(R.id.exhibitionhiddentv);
				photoLink1hiddentv = (TextView) v.findViewById(R.id.photoLink1hiddentv);
				photoLink2hiddentv = (TextView) v.findViewById(R.id.photoLink2hiddentv);
				photoLink3hiddentv = (TextView) v.findViewById(R.id.photoLink3hiddentv);
				photoLink4hiddentv = (TextView) v.findViewById(R.id.photoLink4hiddentv);
				nametv = (TextView) v.findViewById(R.id.locationName);
				placeNametv = (TextView) v.findViewById(R.id.placeNametv);
				latitudetv = (TextView) v.findViewById(R.id.latitudetv);
				longtitudetv = (TextView) v.findViewById(R.id.longtitudetv);
				distance = (TextView) v.findViewById(R.id.distance);
				icon = (ImageView) v.findViewById(R.id.locationImage);
				infoButton = (Button) v.findViewById(R.id.info_button);
			}
	}
	
	public View getView(int pos, View inView, ViewGroup parent){
		//ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		//NetworkInfo ni = cm.getActiveNetworkInfo();
		//imgFetcher = new BitmapTask(context.getApplicationContext());
		View v = inView;
		ViewHolder viewHolder = null;
		if (v == null) {
            //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = inflater.inflate(R.layout.places_basic_layout, parent, false);
			mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = mLayoutInflater.inflate(R.layout.places_basic_layout, parent, false);
            viewHolder = new ViewHolder(v);
            /*viewHolder.desc_infohiddentv = (TextView) v.findViewById(R.id.descinfohiddentv);
            viewHolder.menuhiddentv = (TextView) v.findViewById(R.id.menuhiddentv);
            viewHolder.telhiddentv = (TextView) v.findViewById(R.id.telhiddentv);
            viewHolder.linkhiddentv = (TextView) v.findViewById(R.id.linkhiddentv);
            viewHolder.fbLinkhiddentv = (TextView) v.findViewById(R.id.fbLinkhiddentv);
            viewHolder.emailhiddentv = (TextView) v.findViewById(R.id.emailhiddentv);
            viewHolder.exhibitionhiddentv = (TextView) v.findViewById(R.id.exhibitionhiddentv);
            viewHolder.photoLink1hiddentv = (TextView) v.findViewById(R.id.photoLink1hiddentv);
            viewHolder.photoLink2hiddentv = (TextView) v.findViewById(R.id.photoLink2hiddentv);
            viewHolder.photoLink3hiddentv = (TextView) v.findViewById(R.id.photoLink3hiddentv);
            viewHolder.photoLink4hiddentv = (TextView) v.findViewById(R.id.photoLink4hiddentv);
            viewHolder.nametv = (TextView) v.findViewById(R.id.locationName);
            viewHolder.placeNametv = (TextView) v.findViewById(R.id.placeNametv);
            viewHolder.latitudetv = (TextView) v.findViewById(R.id.latitudetv);
            viewHolder.longtitudetv = (TextView) v.findViewById(R.id.longtitudetv);
            viewHolder.distance = (TextView) v.findViewById(R.id.distance);
            viewHolder.icon = (ImageView) v.findViewById(R.id.locationImage);
            viewHolder.infoButton = (Button) v.findViewById(R.id.info_button);*/
            viewHolder.infoButton.setTag(viewHolder);
            v.setTag(viewHolder);   
        }
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		this.cursor.moveToPosition(pos);
		String name = this.cursor.getString(this.cursor.getColumnIndex("_id"));
		int integer_id = Integer.parseInt(name);
		String placeNameEl = this.cursor.getString(this.cursor.getColumnIndex("name_en"));
		String image_link = this.cursor.getString(this.cursor.getColumnIndex("photo_link"));
		double place_latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
		String str_placelatitude = Double.toString(place_latitude);
		double place_longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
		String str_placelongtitude = Double.toString(place_longtitude);
		String descInfo = this.cursor.getString(this.cursor.getColumnIndex("info_en"));
		String tel = this.cursor.getString(this.cursor.getColumnIndex("tel"));
		String link = this.cursor.getString(this.cursor.getColumnIndex("link"));
		String fbLink = this.cursor.getString(this.cursor.getColumnIndex("fb_link"));
		String email = this.cursor.getString(this.cursor.getColumnIndex("email"));
		String exhibition = this.cursor.getString(this.cursor.getColumnIndex("exhibition_en"));
		String menu = this.cursor.getString(this.cursor.getColumnIndex("menu_en"));
		String link1 = this.cursor.getString(this.cursor.getColumnIndex("link1"));
		String link2 = this.cursor.getString(this.cursor.getColumnIndex("link2"));
		String link3 = this.cursor.getString(this.cursor.getColumnIndex("link3"));
		String link4 = this.cursor.getString(this.cursor.getColumnIndex("link4"));
		
		//placesDataArray.add(new PlacesData(integer_id, surname, "", "", final_latitude, final_longtitude, image_link, ""));
		
		double apostasi = GPSTracker.getDistance(this.current_latitude, this.current_longtitude, place_latitude, place_longtitude);
		double distanceInKm = apostasi/1000;
		DecimalFormat df = new DecimalFormat("#.##");
		String dx=df.format(distanceInKm);
		//String str_distanceInKm = Double.toString(distanceInKm);
		
		//bindView(v, context, cursor);
		//InternalStorage i = new InternalStorage();
		//String path = "/data/data/com.example.thesguideproject/app_imageDir";
		/*bit = i.loadImageFromStorage(path, name);
		
		if(!image_link .equals("")) {
			viewHolder.icon.setTag(image_link);
   			//Drawable dr = imgFetcher.loadImage(this, viewHolder.icon);
			//BitmapTask bit = new BitmapTask(this);
			RelativeLayout.LayoutParams placenametvparams = (RelativeLayout.LayoutParams) viewHolder.placeNametv.getLayoutParams();
			placenametvparams.setMargins(5, 3, 0, 0);
			viewHolder.placeNametv.setLayoutParams(placenametvparams);
			
			RelativeLayout.LayoutParams distancetvparams = (RelativeLayout.LayoutParams) viewHolder.distance.getLayoutParams();
			distancetvparams.setMargins(5, 20, 0, 0);
			viewHolder.distance.setLayoutParams(distancetvparams);
			
			if(imagessavedFlag == true) {
   				//viewHolder.icon.setImageDrawable(dr);
   				bitmap = imgFetcher.loadImage(this, viewHolder.icon, context.getApplicationContext(), name);
   				viewHolder.icon.setImageBitmap(bitmap);
   			}
   			else if (ni == null && bit == null){
   				viewHolder.icon.setBackgroundResource(R.drawable.thess_icon);
   			}
   			else if (ni == null && bit != null){
   				//viewHolder.icon.setImageBitmap(bit);
   				//bitmap = imgFetcher.loadImage(this, viewHolder.icon, context, name);
   				viewHolder.icon.setImageBitmap(bit);
   			}
   			else if (ni != null && imagessavedFlag == false){
   				//imgFetcher.loadImage(image_link, context, name);
   				Bitmap bitm = imgFetcher.loadImage(this, viewHolder.icon, context.getApplicationContext(), name);
   				viewHolder.icon.setImageBitmap(bitm);
   				viewHolder.icon.invalidate();
   			}
   		} else {
   			//viewHolder.icon.setImageResource(R.drawable.filler_icon);
   			//RelativeLayout imgHolder = (RelativeLayout) v.findViewById(R.id.relativeLayout);
   			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
   			viewHolder.icon.setLayoutParams(params);
   		}*/
		
		viewHolder.nametv.setText(name);
		viewHolder.desc_infohiddentv.setText(descInfo);
		viewHolder.menuhiddentv.setText(menu);
		viewHolder.telhiddentv.setText(tel);
		viewHolder.linkhiddentv.setText(link);
		viewHolder.fbLinkhiddentv.setText(fbLink);
		viewHolder.emailhiddentv.setText(email);
		viewHolder.exhibitionhiddentv.setText(exhibition);
		viewHolder.photoLink1hiddentv.setText(link1);
		viewHolder.photoLink2hiddentv.setText(link2);
		viewHolder.photoLink3hiddentv.setText(link3);
		viewHolder.photoLink4hiddentv.setText(link4);
		viewHolder.placeNametv.setText(placeNameEl);
		if (fontcolor.equals("black")){	
			viewHolder.placeNametv.setText(placeNameEl);
			viewHolder.distance.setText(dx + " km");
			viewHolder.distance.setTextColor(Color.BLACK);
			viewHolder.distance.setTypeface(null, Typeface.BOLD);
			viewHolder.placeNametv.setTextColor(Color.BLACK);
			viewHolder.placeNametv.setTypeface(null, Typeface.BOLD);
		}
		else{
			viewHolder.placeNametv.setText(placeNameEl);
			viewHolder.distance.setText(dx + " km");
		}
		//viewHolder.surnametv.setTag(surname);
		viewHolder.latitudetv.setText(str_placelatitude);
		viewHolder.longtitudetv.setText(str_placelongtitude);
		viewHolder.infoButton.setOnClickListener(this);
		
		//InternalStorage intStorage = new InternalStorage();
		//String path = "/data/data/com.example.thesguideproject/app_imageDir";
		//Bitmap b = intStorage.loadImageFromStorage(path, name);
			
		/*if(image_link != null) {
			viewHolder.icon.setTag(image_link);
   			//Drawable dr = imgFetcher.loadImage(this, viewHolder.icon);
			//Bitmap bitmap = imgFetcher.loadImage(this, viewHolder.icon, context, name);
   			if(b != null) {
   				//viewHolder.icon.setImageDrawable(dr);
   				viewHolder.icon.setImageBitmap(b);
   			}
   		} else {
   			viewHolder.icon.setImageResource(R.drawable.filler_icon);
   		}*/
		
		/*if(image_link != null) {
			viewHolder.icon.setTag(image_link);
   			//Drawable dr = imgFetcher.loadImage(this, viewHolder.icon);
			Bitmap bitmap = imgFetcher.loadImage(this, viewHolder.icon, context, name);
   			if(bitmap != null) {
   				//viewHolder.icon.setImageDrawable(dr);
   				//viewHolder.icon.setImageBitmap(bitmap);
   			}
   		} else {
   			viewHolder.icon.setImageResource(R.drawable.filler_icon);
   		}*/
		//load(viewHolder.icon);
		
		return v;
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//TextView surnametv = (TextView) v.findViewById(R.id.nameEl);
		//Button pressed = (Button) v;
		//String button_pressed_text = pressed.getText().toString();
		Log.i("Button pressed text =>", " " + button_pressed);
		
		ViewHolder vH = (ViewHolder) v.getTag();
		//String url = (String) vH.surnametv.getTag();
		//Toast.makeText(this.context, url, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(context.getApplicationContext(), PlacesDetailsTabs.class);
		//Intent intent = new Intent(this.activity, PlacesListFragmentActivityTest.class);
		//intent.putExtra("nameEl", vH.surnametv.getTag().toString());
		String str_current_latitude = Double.toString(current_latitude);
		String str_current_longtitude = Double.toString(current_longtitude);
		intent.putExtra("language", "English");
		intent.putExtra("current latitude" , str_current_latitude);
		intent.putExtra("current longtitude", str_current_longtitude);
		intent.putExtra("placeNameEl", vH.placeNametv.getText());
		intent.putExtra("desc_info", vH.desc_infohiddentv.getText());
		intent.putExtra("menu", vH.menuhiddentv.getText());
		intent.putExtra("telephone", vH.telhiddentv.getText());
		intent.putExtra("link", vH.linkhiddentv.getText());
		intent.putExtra("fbLink", vH.fbLinkhiddentv.getText());
		intent.putExtra("email", vH.emailhiddentv.getText());
		intent.putExtra("exhibition", vH.exhibitionhiddentv.getText());
		intent.putExtra("photoLink1", vH.photoLink1hiddentv.getText());
		intent.putExtra("photoLink2", vH.photoLink2hiddentv.getText());
		intent.putExtra("photoLink3", vH.photoLink3hiddentv.getText());
		intent.putExtra("photoLink4", vH.photoLink4hiddentv.getText());
		intent.putExtra("latitude", vH.latitudetv.getText());
		intent.putExtra("longtitude", vH.longtitudetv.getText());
		intent.putExtra("button_pressed_text", button_pressed);
		intent.putExtra("displaycurrentPoint", "yes");
		//Toast.makeText(this.context, vH.photoLink1hiddentv.getText(), Toast.LENGTH_SHORT).show();
		//intent.putExtra("latitude", this.c.getDouble(this.c.getColumnIndex("latitude")));
		//intent.putExtra("longtitude", this.c.getDouble(this.c.getColumnIndex("longtitude")));
		if (this.activity == null){
			this.searchPlaceResultListFragment.startActivity(intent);
		}else{
			this.activity.startActivity(intent);
		}
	}

	
	
}

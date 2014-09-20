package com.example.adapters;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.example.fragmentClasses.ListPlacesFragment;
import com.example.fragmentClasses.SearchPlaceResultListFragment;
import com.example.locationData.PlacesData;
import com.example.myLocation.GPSTracker;
import com.example.storage.InternalStorage;
import com.example.tasks.BitmapTask;
import com.example.tasks.ImageTask;
import com.example.thesguideproject.CursorAdapterExample;
import com.example.thesguideproject.PlacesListFragmentActivity;
import com.example.thesguideproject.R;
import com.example.thesguideproject.PlacesDetailsTabs;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PlacesDataListCursorAdapter extends SimpleCursorAdapter implements OnClickListener {

	private ListPlacesFragment activity;
	private SearchPlaceResultListFragment searchPlaceResultListFragment;
	
	//private CursorAdapterExample activity;
	private LayoutInflater layoutInflater;
	private BitmapTask imgFetcher;
	private Context context;
	private SimpleCursorAdapter dataAdapter;
	private int layout;
	private BitmapTask bitTask;
	private Cursor cursor;
	private double current_latitude;
	private double current_longtitude;
	private String button_pressed;
	private ActionBar act;
	
	GPSTracker gps;
	//ArrayList<PlacesData> placesDataArray = new ArrayList<PlacesData>();
	
	/*public PLacesDataListCursorAdapter(String button_pressed, ListPlacesFragment activity, Context context, int layout, Cursor cursor, String[] from, int[] to, BitmapTask i, double current_latitude, double current_longtitude) {
		super(context, layout, cursor, from, to);
		this.button_pressed = button_pressed;
		this.activity = activity;
		this.context = context;
		this.layout = layout;
		this.cursor = cursor;
		this.imgFetcher = i;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		// TODO Auto-generated constructor stub
	}*/
	
	public PlacesDataListCursorAdapter(String button_pressed, SearchPlaceResultListFragment searchPlaceResultListFragment, Context context, int layout, Cursor cursor, String[] from, int[] to, double current_latitude, double current_longtitude) {
		super(context, layout, cursor, from, to);
		this.button_pressed = button_pressed;
		this.searchPlaceResultListFragment = searchPlaceResultListFragment;
		this.context = context;
		this.layout = layout;
		this.cursor = cursor;
		//this.imgFetcher = i;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		// TODO Auto-generated constructor stub
	}
	
	public PlacesDataListCursorAdapter(String button_pressed, ListPlacesFragment activity, Context context, int layout, Cursor cursor, String[] from, int[] to, double current_latitude, double current_longtitude) {
		super(context, layout, cursor, from, to);
		this.button_pressed = button_pressed;
		this.activity = activity;
		this.context = context;
		this.layout = layout;
		this.cursor = cursor;
		//this.imgFetcher = i;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
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
	
	private static class ViewHolder{
		TextView nametv, placeNametv, nameEllower,  distance, latitudetv, longtitudetv, desc_infohiddentv, 
		telhiddentv, linkhiddentv, fbLinkhiddentv, emailhiddentv, exhibitionhiddentv,
		photoLink1hiddentv, photoLink2hiddentv, photoLink3hiddentv, photoLink4hiddentv, hiddenoncalltv;
		ImageView icon;
		Button infoButton;
		Button oncallButton;
		
		ViewHolder(View v){
			desc_infohiddentv = (TextView) v.findViewById(R.id.descinfohiddentv);
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
			nameEllower = (TextView) v.findViewById(R.id.nameellower);
			latitudetv = (TextView) v.findViewById(R.id.latitudetv);
			longtitudetv = (TextView) v.findViewById(R.id.longtitudetv);
			distance = (TextView) v.findViewById(R.id.distance);
			icon = (ImageView) v.findViewById(R.id.locationImage);
			infoButton = (Button) v.findViewById(R.id.info_button);
			oncallButton = (Button) v.findViewById(R.id.oncallbutton);
			hiddenoncalltv = (TextView) v.findViewById(R.id.hiddenoncalltv);
		}
	}
	

	
	/*
	
	public View newView(Context context, Cursor cursor, ViewGroup parent){
		Cursor c = getCursor();
		 
        final LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(layout, parent, false);
    
        v.setTag(new ViewHolder(v));

        int nameCol = c.getColumnIndex("_id");
        int surnameCol = c.getColumnIndex("surname");
 
        String name = c.getString(nameCol);
        String surname = c.getString(surnameCol);
      
        //TextView name_text = (TextView) v.findViewById(R.id.name_entry);
        //TextView surname_text = (TextView) v.findViewById(R.id.surname_entry);
        TextView name_text = (TextView) v.findViewById(R.id.locationName);
        TextView surname_text = (TextView) v.findViewById(R.id.nameEl);
        if (surname_text != null && name_text != null) {
        	name_text.setText(name);
            surname_text.setText(surname);
        }
 
        return v;
	}*/
	
	
	
	public View getView(int pos, View inView, ViewGroup parent){
		imgFetcher = new BitmapTask(this);
		View v = inView;
		ViewHolder viewHolder;
		if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder(v);
            viewHolder.desc_infohiddentv = (TextView) v.findViewById(R.id.descinfohiddentv);
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
            viewHolder.nameEllower = (TextView) v.findViewById(R.id.nameellower);
            viewHolder.latitudetv = (TextView) v.findViewById(R.id.latitudetv);
            viewHolder.longtitudetv = (TextView) v.findViewById(R.id.longtitudetv);
            viewHolder.distance = (TextView) v.findViewById(R.id.distance);
            viewHolder.icon = (ImageView) v.findViewById(R.id.locationImage);
            viewHolder.hiddenoncalltv = (TextView) v.findViewById(R.id.hiddenoncalltv);
            viewHolder.infoButton = (Button) v.findViewById(R.id.info_button);
            viewHolder.oncallButton = (Button) v.findViewById(R.id.oncallbutton);
            viewHolder.oncallButton.setTag(viewHolder);
            viewHolder.infoButton.setTag(viewHolder);
            v.setTag(viewHolder);   
        }
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		this.cursor.moveToPosition(pos);
		String name = this.cursor.getString(this.cursor.getColumnIndex("_id"));
		int integer_id = Integer.parseInt(name);
		String placeNameEl = this.cursor.getString(this.cursor.getColumnIndex("name_el"));
		String nameElLower = this.cursor.getString(this.cursor.getColumnIndex("nameel_lower"));
		String image_link = this.cursor.getString(this.cursor.getColumnIndex("photo_link"));
		double place_latitude = this.cursor.getDouble(this.cursor.getColumnIndex("latitude"));
		String str_placelatitude = Double.toString(place_latitude);
		double place_longtitude = this.cursor.getDouble(this.cursor.getColumnIndex("longtitude"));
		String str_placelongtitude = Double.toString(place_longtitude);
		String descInfo = this.cursor.getString(this.cursor.getColumnIndex("info"));
		String tel = this.cursor.getString(this.cursor.getColumnIndex("tel"));
		String link = this.cursor.getString(this.cursor.getColumnIndex("link"));
		String fbLink = this.cursor.getString(this.cursor.getColumnIndex("fb_link"));
		String email = this.cursor.getString(this.cursor.getColumnIndex("email"));
		String exhibition = this.cursor.getString(this.cursor.getColumnIndex("exhibition"));
		String menu = this.cursor.getString(this.cursor.getColumnIndex("menu"));
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
		
		bindView(v, context, cursor);
		
		
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
			
			Bitmap bitmap = imgFetcher.loadImage(this, viewHolder.icon, context, name);
   			if(bitmap != null) {
   				//viewHolder.icon.setImageDrawable(dr);
   				viewHolder.icon.setImageBitmap(bitmap);
   			}
   		} else {
   			//viewHolder.icon.setImageResource(R.drawable.filler_icon);
   			RelativeLayout imgHolder = (RelativeLayout) v.findViewById(R.id.relativeLayout);
   			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(0, 0);
   			viewHolder.icon.setLayoutParams(params);
   		}
		
		//viewHolder.nametv.setText(name);
		viewHolder.desc_infohiddentv.setText(descInfo);
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
		viewHolder.nameEllower.setText(nameElLower);
		//viewHolder.surnametv.setTag(surname);
		viewHolder.latitudetv.setText(str_placelatitude);
		viewHolder.longtitudetv.setText(str_placelongtitude);
		viewHolder.distance.setText(dx + " ÷ëì");
		viewHolder.infoButton.setOnClickListener(this);
		viewHolder.hiddenoncalltv.setText(menu);
		if (viewHolder.hiddenoncalltv.getText().equals("yes")){
			viewHolder.oncallButton.setVisibility(View.VISIBLE);
		}
		else{
			viewHolder.oncallButton.setVisibility(View.INVISIBLE);
		}
		
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
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		super.bindView(view, context, cursor);
	}
	
	/*
	@Override
    public void bindView(View v, Context context, Cursor c) {
		
		int nameCol = c.getColumnIndex("_id");
        int surnameCol = c.getColumnIndex("surname");
        int image_link = c.getColumnIndex("image_link");
 
        String name = c.getString(nameCol);
        String surname = c.getString(surnameCol);
        String link = c.getString(image_link);
     
        int cursor_position = c.getPosition();
        String cur_position = Integer.toString(cursor_position);
        //Log.i("Cursor position => ", cur_position + " " + link);
        
        ViewHolder vh = (ViewHolder) v.getTag();
 
        //Bitmap bitmap = BitmapFactory.decodeResource(v.getContext().getResources(), R.drawable.android_icon);
        
       
        
        if (vh.nametv != null && vh.surnametv != null) {
            vh.nametv.setText(name);
            vh.surnametv.setText(surname);
            //vh.icon.setImageResource(R.drawable.android_icon);
           // vh.icon.setImageBitmap(bitmap);
        }
        
      if (link != null){
    	  vh.icon.setTag(c.getString(image_link));
    	  //new DownloadImageTask(vh.icon).execute(link);
    	 // vh.icon.setTag(link);
    	  //new DownloadImageTask(vh.icon).execute(link);
    	  load(vh.icon);
    	  
    	 BitmapTask bitTask = new BitmapTask(this);
    	 Bitmap bit = bitTask.loadBitmapImage(this, vh.icon);
    	 if(bit != null) {
    		 vh.icon.setImageBitmap(bit);
		}
		 else {
			vh.icon.setImageResource(R.drawable.filler_icon);
		}
    	 
      }

        
       
        if (link != null){
        	vh.icon.setTag(link);
        	//vh.icon.getTag();
        	Bitmap bitmap = bitTask.loadBitmapImage(this, vh.icon);
        	if (bitmap != null){
        		vh.icon.setImageBitmap(bitmap);
        		//((ImageView) vh.icon.getTag(R.id.locationImage)).setImageBitmap(bitmap);
        	}else
        	{
        		Log.i("Bitmap is loaded => ", "false");
        	}
        }
        
        
        
        //vh.icon.setTag(link);
			Drawable dr = imgFetcher.loadImage(this, (ImageView) vh.icon.getTag());
			if(dr != null) {
				vh.icon.setImageDrawable(dr);
			}
			else 
			{
			vh.icon.setImageResource(R.drawable.filler_icon);
		    }
        
          
        //TextView surname_text = (TextView) v.findViewById(R.id.surname_entry);
       
    }
	*/
	
	



	@Override
	public void setViewBinder(ViewBinder viewBinder) {
		// TODO Auto-generated method stub
		super.setViewBinder(viewBinder);
	}

	@Override
	public void setViewImage(ImageView imageView, String imageviewCursor) {
		// TODO Auto-generated method stub
		super.setViewImage(imageView, imageviewCursor);
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
		Intent intent = new Intent(context, PlacesDetailsTabs.class);
		//Intent intent = new Intent(this.activity, PlacesListFragmentActivityTest.class);
		//intent.putExtra("nameEl", vH.surnametv.getTag().toString());
		String str_current_latitude = Double.toString(current_latitude);
		String str_current_longtitude = Double.toString(current_longtitude);
		intent.putExtra("language", "Greek");
		intent.putExtra("current latitude" , str_current_latitude);
		intent.putExtra("current longtitude", str_current_longtitude);
		intent.putExtra("placenameEllower", vH.nameEllower.getText());
		intent.putExtra("placeNameEl", vH.placeNametv.getText());
		intent.putExtra("desc_info", vH.desc_infohiddentv.getText());
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

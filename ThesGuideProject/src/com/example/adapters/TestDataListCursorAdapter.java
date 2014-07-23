package com.example.adapters;

import java.io.InputStream;
import java.text.DecimalFormat;

import com.example.myLocation.GPSTracker;
import com.example.storage.InternalStorage;
import com.example.tasks.BitmapTask;
import com.example.tasks.ImageTask;
import com.example.thesguideproject.CursorAdapterExample;
import com.example.thesguideproject.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TestDataListCursorAdapter extends SimpleCursorAdapter {

	private CursorAdapterExample activity;
	private LayoutInflater layoutInflater;
	private BitmapTask imgFetcher;
	private Context context;
	private SimpleCursorAdapter dataAdapter;
	private int layout;
	private BitmapTask bitTask;
	private Cursor c;
	private double current_latitude;
	private double current_longtitude;
	
	GPSTracker gps;
	
	@SuppressWarnings("deprecation")
	public TestDataListCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, BitmapTask i, double current_latitude, double current_longtitude) {
		super(context, layout, c, from, to);
		this.context = context;
		this.layout = layout;
		this.c = c;
		this.imgFetcher = i;
		this.current_latitude = current_latitude;
		this.current_longtitude = current_longtitude;
		// TODO Auto-generated constructor stub
	}
	
	private class ViewHolder{
		TextView nametv, surnametv, distance;
		ImageView icon;
		
		ViewHolder(View v){
			nametv = (TextView) v.findViewById(R.id.locationName);
			surnametv = (TextView) v.findViewById(R.id.nameEl);
			distance = (TextView) v.findViewById(R.id.distance);
			icon = (ImageView) v.findViewById(R.id.locationImage);
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
		View v = inView;
		ViewHolder viewHolder;
		if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, parent, false);
            viewHolder = new ViewHolder(v);
            viewHolder.nametv = (TextView) v.findViewById(R.id.locationName);
            viewHolder.surnametv = (TextView) v.findViewById(R.id.nameEl);
            viewHolder.distance = (TextView) v.findViewById(R.id.distance);
            viewHolder.icon = (ImageView) v.findViewById(R.id.locationImage);
            v.setTag(viewHolder);   
        }
		else{
			viewHolder = (ViewHolder) v.getTag();
		}
		this.c.moveToPosition(pos);
		String name = this.c.getString(this.c.getColumnIndex("_id"));
		String surname = this.c.getString(this.c.getColumnIndex("name_el"));
		String image_link = this.c.getString(this.c.getColumnIndex("photo_link"));
		double final_latitude = this.c.getDouble(this.c.getColumnIndex("latitude"));
		double final_longtitude = this.c.getDouble(this.c.getColumnIndex("longtitude"));
		
		double apostasi = GPSTracker.getDistance(this.current_latitude, this.current_longtitude, final_latitude, final_longtitude);
		double distanceInKm = apostasi/1000;
		DecimalFormat df = new DecimalFormat("#.##");
		String dx=df.format(distanceInKm);
		//String str_distanceInKm = Double.toString(distanceInKm);
		
		viewHolder.nametv.setText(name);
		viewHolder.surnametv.setText(surname);
		viewHolder.distance.setText(dx + " km");
		
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
		
		if(image_link != null) {
			viewHolder.icon.setTag(image_link);
   			//Drawable dr = imgFetcher.loadImage(this, viewHolder.icon);
			Bitmap bitmap = imgFetcher.loadImage(this, viewHolder.icon, context, name);
   			if(bitmap != null) {
   				//viewHolder.icon.setImageDrawable(dr);
   				viewHolder.icon.setImageBitmap(bitmap);
   			}
   		} else {
   			viewHolder.icon.setImageResource(R.drawable.filler_icon);
   		}
		//load(viewHolder.icon);
		
		return v;
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
	 public void load(ImageView view){
	    	String url = (String) view.getTag();
	    	new DownloadImageTask(view).execute(url);
	    }

	 private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		    ImageView bmImage;
		    private String urldisplay;
		    //SimpleCursorAdapter curAdapter;

		    public DownloadImageTask(ImageView bmImage) {
		      this.bmImage = bmImage;
		    }
		    
		   

		    protected Bitmap doInBackground(String... urls) {
		        urldisplay = urls[0];
		        Bitmap mIcon11 = null;
		        try {
		            InputStream in = new java.net.URL(urldisplay).openStream();
		            mIcon11 = BitmapFactory.decodeStream(in);
		            in.close();
		        } catch (Exception e) {
		            Log.e("Error", e.getMessage());
		            e.printStackTrace();
		        }
		        return mIcon11;
		    }

		    protected void onPostExecute(Bitmap result) {
		    	bmImage.setImageBitmap(result);
		    	/* synchronized (this) {
		                imageCache.put(urldisplay, result);
		         }
		    	 simCursorAdapt.notifyDataSetChanged(); */
		    	//bmImage.setImageBitmap(result);
		    }
	}
	 
	
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

	
	
	

	
	
}

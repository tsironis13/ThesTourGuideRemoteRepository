package com.example.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.storage.InternalStorage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ImageView;

public class BitmapTask  {

	private static final String debugTag = "BitmapTask";
    private HashMap<String, Bitmap> imageCache;
    private static Bitmap DEFAULT_ICON = null;
    private SimpleCursorAdapter adapt;
    //private BaseAdapter adapt;
    private ArrayList<Bitmap> bitmapArray;
    
    InternalStorage intStorage = new InternalStorage();
    private String path = "/data/data/com.example.thesguideproject/app_imageDir";
    
    public BitmapTask(Context ctx)
    {
        imageCache = new HashMap<String, Bitmap>();
    }
    
    public Bitmap loadImage(SimpleCursorAdapter adapt, ImageView view, Context context, String name)
    {
        this.adapt = adapt;
        String url = (String) view.getTag();
        Bitmap b = intStorage.loadImageFromStorage(path, name);
        
        if (b != null)
        {
        	//Log.d(debugTag, "Image obtained from Cache!");
        	//Log.i("Url obtained: ", url);
            //return imageCache.get(url);
        	int sizeOfBitmap = intStorage.byteSizeOf(b);
        	String size = Integer.toString(sizeOfBitmap);
        	Log.i("Byte size of Bitmap => ", size + " " + name);
        	
        	return b;
        }
        else {
            new NestedImageTask(context, name).execute(url);
            Log.d(debugTag, "Image Fetched!!");
            Log.i("Image Fetched: ", url);
            return DEFAULT_ICON;
        }
    }
    
    
    
    
    /*
    public Drawable loadImage(BaseAdapter adapt, ImageView view)
    {
        this.adapt = adapt;
        String url = (String) view.getTag();
        if (imageCache.containsKey(url))
        {
        	Log.d(debugTag, "Image obtained from Cache!");
        	Log.i("Url obtained: ", url);
            return imageCache.get(url);
        }
        else {
            new NestedImageTask().execute(url);
            Log.d(debugTag, "Image Fetched!!");
            Log.i("Image Fetched: ", url);
            return DEFAULT_ICON;
        }
    }*/
    
    private class NestedImageTask extends AsyncTask<String, Void, Bitmap>
    {
        private String s_url;
        private Context context;
        private String name;
        
        ImageInternalStorage imgInSt = new ImageInternalStorage();
        
        public NestedImageTask(Context context, String name){
        	this.context = context;
        	this.name = name;
        }
        
       @Override
        protected Bitmap doInBackground(String... params) {
            s_url = params[0];
            InputStream istr;
            try {
                Log.d(debugTag, "Fetching: " + s_url);
                URL url = new URL(s_url);
                istr = url.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(istr);
                
             
                return bitmap;
                //imgInSt.saveToInternalSorage(bitmap, s_url);
                
            } catch (MalformedURLException e) {
                Log.d(debugTag, "Malformed: " + e.getMessage());
                throw new RuntimeException(e);
            } catch (IOException e)
            {
                Log.d(debugTag, "I/O : " + e.getMessage());
                throw new RuntimeException(e);
                
            }
           
        }
        
        
         @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            synchronized (this) {
                //imageCache.put(s_url, result);
                intStorage.saveToInternalSorage(result, context, name);
            }
            adapt.notifyDataSetChanged();
        }
        
        
	/*
        @Override
        protected Bitmap doInBackground(String... params){
        	 s_url = params[0];
             Bitmap bitmapImage = null;
             try {
                 InputStream in = new java.net.URL(s_url).openStream();
                 bitmapImage = BitmapFactory.decodeStream(in);
                 Log.i("Bitmap decoded => ", "successfully!!" + "\n" + s_url);
             } catch (Exception e) {
            	 Log.i("Bitmap decoded => ", "unsuccessfully!!" + "\n" + s_url);
                 e.printStackTrace();
             }
             return bitmapImage;
        }
        
        
        @Override
        protected void onPostExecute(Bitmap result){
        	super.onPostExecute(result);
        	synchronized(this){
        		
        		bitmapArray = new ArrayList<Bitmap>();
        		bitmapArray.add(result);
        		
        		//imgInSt.saveToInternalSorage(result, s_url);
        	}
        	
        	int size = bitmapArray.size();
        	String array_size = Integer.toString(size);
        	Log.i("BitmapArray size => ", array_size);
        	
        	adapt.notifyDataSetChanged();
        }
        */
       
        
    }
}
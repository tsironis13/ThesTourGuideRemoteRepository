package com.example.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;


public class InternalStorage {
	
	ArrayList<String> url_array = new ArrayList<String>();
	ArrayList<Bitmap> bitmap_array = new ArrayList<Bitmap>();
	private String path;
	private File directory;
	private File mypath;
	
	
	public void setPath(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
	
	@TargetApi(Build.VERSION_CODES.KITKAT) public static int byteSizeOf(Bitmap bitmap){
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	        return bitmap.getAllocationByteCount();
	    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
	        return bitmap.getByteCount();
	    } else {
	        return bitmap.getRowBytes() * bitmap.getHeight();
	    }
	}
	
	
	public String saveToInternalSorage(Bitmap bitmapImage, Context context, String name){
        ContextWrapper cw = new ContextWrapper(context);
         // path to /data/data/yourapp/app_data/imageDir
       directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
       mypath=new File(directory, name);

        FileOutputStream fos = null;
        try {           

            fos = new FileOutputStream(mypath);

       // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }
	
	
	public Bitmap loadImageFromStorage(String path, String name)
	{
		Bitmap b = null;
	    try 
	    {
	        File f=new File(path, name);
	        b = BitmapFactory.decodeStream(new FileInputStream(f));  
	    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }
	    return b;
	}
	/*
	private void loadImageFromStorage(String path)
	{
		
	    try {
	        File f=new File(path, "profile.jpg");
	        
	     for (int i=0; i<bitmap_array.size(); i++){
	    	 Bitmap b = bitmap_array.get(i);
	        b = BitmapFactory.decodeStream(new FileInputStream(f));
	            ImageView img=(ImageView)findViewById(R.id.imageViewWhereisStored);
	        img.setImageBitmap(b);
	     }
	    } 
	    catch (FileNotFoundException e) 
	    {
	        e.printStackTrace();
	    }

	}*/
	
}

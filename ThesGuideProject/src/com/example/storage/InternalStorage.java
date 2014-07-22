package com.example.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

       //Bitmap decodedBitmap = decodeBitmapFile(mypath);
       
       FileOutputStream fos = null;
        try {           

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 70, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }
	
	//decodes image and scales it to reduce memory consumption
	private Bitmap decodeBitmapFile(File f){
		
		Bitmap b = null;

		try{
        //Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;

		FileInputStream fis = new FileInputStream(f);
		BitmapFactory.decodeStream(fis, null, o);
		fis.close();

		int scale = 1;
		int IMAGE_MAX_SIZE = 70;
		
		if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
			scale = (int)Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE / 
			(double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

		//Decode with inSampleSize
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		fis = new FileInputStream(f);
		b = BitmapFactory.decodeStream(fis, null, o2);
		fis.close();
		
		} catch(FileNotFoundException e){
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
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

package com.example.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.util.Log;

public class ImageInternalStorage {

	private Context myContext;
		
	
	public boolean saveToInternalSorage(Bitmap bitmapImage, String s_url){
        //ContextWrapper cw = new ContextWrapper(myContext.getApplicationContext());
         // path to /data/data/yourapp/app_data/imageDir
        //File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        //File mypath=new File(directory, url);

		try {
			// Use the compress method on the Bitmap object to write image to
			// the OutputStream
			FileOutputStream fos = myContext.openFileOutput(s_url, Context.MODE_PRIVATE);

			// Writing the bitmap to the output stream
			bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			//Log.i("Saved into application => ", "TRUE");
			
			return true;
		} catch (Exception e) {
			//Log.i("Saved into application => ", "FALSE");
			
			return false;
		}
	}
    
	
	
}

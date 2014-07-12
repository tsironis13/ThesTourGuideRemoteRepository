package com.example.sqlHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.locationData.LocationData;
import com.example.locationData.TestData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHolder extends SQLiteOpenHelper{
		
	// All Static variables
    // Database Version
	private static int DATABASE_VERSION = 1;
	
	// Database Name
    private static final String DATABASE_NAME = "locationsManagerFor";
    
    private SQLiteDatabase myDataBase;
    
    // Locations table name
    private static final String TABLE_LOCATIONS = "locationsFor";
    
    //private static final String TEST_TABLE = "testTable";
   // private static final String TEST_KEY_ID = "id";
   // private static final String TEST_NAME = "name";
    
    // Locations Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name_el";
    private static final String KEY_GENRE = "genre";
	private static final String KEY_PHOTO_LINK = "photo_link";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGTITUDE = "longtitude";
    private static String flag;
    
    public DatabaseHolder(Context context){
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    	String s = Integer.toString(DATABASE_VERSION);
    	Log.d("DATABASE VERSION: ", s);
    }
    
    // Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME +
				" TEXT, " + KEY_GENRE + " TEXT, " + KEY_PHOTO_LINK + " TEXT, " + KEY_LATITUDE + " TEXT, " + KEY_LONGTITUDE + " TEXT" + ")";
		
		
		//String CREATE_TEST_TABLE = "CREATE TABLE " + TEST_TABLE + "(" + TEST_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEST_NAME +
		//		" TEXT, " + ")";
		
		//db.execSQL(CREATE_TEST_TABLE);
		db.execSQL(CREATE_LOCATIONS_TABLE);
			
		//db.execSQL("create table locations " +
			//      "(id integer primary key, name_el text,photo_link text,latitude text, longtitude text)");
	} 

	// Updating Tables
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        
        String oldversion = Integer.toString(oldVersion);
        Log.d("DATABASE OLD VERSION: ", oldversion);
        
        String newversion = Integer.toString(newVersion);
        //Log.d("DATABASE NEW VERSION: ", newVersion);
        // Create tables again
        onCreate(db);
	}

	/*
	public void addTestData(TestData td){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(TEST_KEY_ID, td.getId());
		values.put(TEST_NAME, td.getName());
		
		db.insert(TEST_TABLE, null, values);
		db.close();
	}*/
	
	// Adding new location
	public void addLocation(LocationData location) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_ID, location.getId());
		values.put(KEY_NAME, location.getNameEl());
		values.put(KEY_GENRE, location.getGenre());
		values.put(KEY_PHOTO_LINK, location.getPhotoLink());
		values.put(KEY_LATITUDE, location.getLatitude());
		values.put(KEY_LONGTITUDE, location.getLongtitude());
		
		//Inserting row
		db.insert(TABLE_LOCATIONS, null, values);
		db.close();
	}
	 
	//Clear Table if exists
	public void clearTableIfExists(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		try{
			db.delete(TABLE_LOCATIONS, null, null);
			flag = "true";
			Log.d("Table deleted successfully", flag);
		}
		catch(Exception e){
			flag = "false";
			Log.d("Table deleted successfully", flag);
		}
	}
	
	/*
	public void clearTestTableIfExists(){
		SQLiteDatabase db = this.getReadableDatabase();
		
		try{
			db.delete(TEST_TABLE, null, null);
			flag = "true";
			Log.d("Table deleted successfully", flag);
		}
		catch(Exception e){
			flag = "false";
			Log.d("Table deleted successfully", flag);
		}
	}*/
	
	
	// Getting single location
	public LocationData getLocation(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_LOCATIONS, new String[] {KEY_ID, KEY_NAME, KEY_GENRE, KEY_PHOTO_LINK, KEY_LONGTITUDE, KEY_LATITUDE}, 
				KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		LocationData location = new LocationData(Integer.parseInt(cursor.getString(0)),
	            cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
	    // return contact
	    return location;
		
		
	}
	
	
	
	 
	// Getting All Locations
	public ArrayList<LocationData> getAllLocations() {
		ArrayList<LocationData> locationList = new ArrayList<LocationData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        //int count = cursor.getCount();
		//String s = Integer.toString(count);
		//Log.d("Cursor row count: ", s);
		
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LocationData location = new LocationData();
                location.setId(Integer.parseInt(cursor.getString(0)));
                location.setNameEl(cursor.getString(1));
                location.setGenre(cursor.getString(2));
                location.setPhotoLink(cursor.getString(3));
                location.setLongtitude(cursor.getString(4));
                location.setLatitude(cursor.getString(5));
                // Adding contact to list
                locationList.add(location);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return locationList;
		
		
		
	}
	
	
	
	// Getting All Locations By Genre
		public ArrayList<LocationData> getAllLocationsByGenre(String genre) {
			ArrayList<LocationData> locationListByGenre = new ArrayList<LocationData>();
	        // Select All Query
	        String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS + " WHERE genre ='" + genre + "'";
	 
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	 
	        int count = cursor.getCount();
			String s = Integer.toString(count);
			Log.d("Cursor row count: ", s);
	        
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	                LocationData location = new LocationData();
	                location.setId(Integer.parseInt(cursor.getString(0)));
	                location.setNameEl(cursor.getString(1));
	                location.setGenre(cursor.getString(2));
	                location.setPhotoLink(cursor.getString(3));
	                location.setLongtitude(cursor.getString(4));
	                location.setLatitude(cursor.getString(5));
	                // Adding contact to list
	                locationListByGenre.add(location);
	            } while (cursor.moveToNext());
	        }
	 
	        //DATABASE_VERSION = 0;
	        // return contact list
	        return locationListByGenre;
			
			
			
		}
	
	 
	// Getting locations Count
	public int getLocationsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        // return count
        return cursor.getCount();
	}
	
	// Updating single location
	public int updateLocation(LocationData location) {
		SQLiteDatabase db = this.getWritableDatabase();
		 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, location.getNameEl());
        values.put(KEY_GENRE, location.getGenre());
        values.put(KEY_PHOTO_LINK, location.getPhotoLink());
        values.put(KEY_LATITUDE, location.getLatitude());
		values.put(KEY_LONGTITUDE, location.getLongtitude());
 
        // updating row
        return db.update(TABLE_LOCATIONS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(location.getId()) });
	}
	 
	// Deleting single location
	public void deleteLocation(LocationData location) {
		SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?",
                new String[] { String.valueOf(location.getId()) });
        db.close();
	}
	
	
	
	public Cursor getLocationsByGenre(String genre){
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOCATIONS + " WHERE genre='" + genre + "'", null);
		Log.v("test", cursor.toString());
		
		return cursor;
	}
	
	
	
	
}

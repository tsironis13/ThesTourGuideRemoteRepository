package com.example.sqlHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.locationData.LocationData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHolder extends SQLiteOpenHelper{

	// All Static variables
    // Database Version
	private static final int DATABASE_VERSION = 1;
	
	// Database Name
    private static final String DATABASE_NAME = "locationsManager";
    
    private SQLiteDatabase myDataBase;
    
    // Locations table name
    private static final String TABLE_LOCATIONS = "locations";
    
    // Locations Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name_el";
    private static final String KEY_GENRE = "genre";
	private static final String KEY_PHOTO_LINK = "photo_link";
	private static final String KEY_LATITUDE = "latitude";
	private static final String KEY_LONGTITUDE = "longtitude";
    
    
    public DatabaseHolder(Context context){
    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    // Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_LOCATIONS_TABLE = "CREATE TABLE " + TABLE_LOCATIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME +
				" TEXT, " + KEY_GENRE + " TEXT, " + KEY_PHOTO_LINK + " TEXT, " + KEY_LATITUDE + " TEXT, " + KEY_LONGTITUDE + " TEXT" + ")";
		
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
 
        // Create tables again
        onCreate(db);
	}

	
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

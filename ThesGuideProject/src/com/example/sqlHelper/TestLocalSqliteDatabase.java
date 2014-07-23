package com.example.sqlHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.example.locationData.LocationData;
import com.example.locationData.PlacesData;
import com.example.thesguideproject.MainLayoutActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class TestLocalSqliteDatabase extends SQLiteOpenHelper {

	private final Context myContext;
	//The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.example.thesguideproject/databases/";
	
	//private static String DB_NAME = "MyLocationsForThessTourGuideProject";
	private static String DB_NAME = "mydb";
	private static final String debugTag = "TestLocalSqliteDatabase";
	private SQLiteDatabase myDataBase;
	
	//private static final String TEST_TABLE = "testtable";    
	private static final String TEST_KEY_ID = "_id";
	private static final String TEST_NAME = "_name";
	private static final String TEST_SURNNAME = "surname";
	private static final String TEST_TYPE = "type";
	
	//private static String flag;
	//private static final String TABLE_RECORD = "TestTable"; 
	
	
	 /**
	  * Constructor
	  * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	  * @param context
	  */
	public TestLocalSqliteDatabase(Context context) {
	    super(context, DB_NAME, null, 1);
	    this.myContext = context;
	    //DB_PATH = "/data/data/" + myContext.getApplicationContext().getPackageName() + "/databases/";
	}
	
	
	 /**
	  * Creates a empty database on the system and rewrites it with your own database.
	  * */
	public void createDataBase() throws IOException{
	 
		boolean dbExist = checkDataBase();
	 
		if(dbExist){
			//do nothing - database already exist
			//MainLayoutActivity mainAct = new MainLayoutActivity();
			//mainAct.databaseExistsOrNot(true);
			Log.d(debugTag, "Database has been already created!");
		}else{
	 
		//By calling this method and empty database will be created into the default system path
		//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();
			Log.d(debugTag, "Database created successfully!");
			try {
	 			copyDataBase();
	        } catch (IOException e) {
	            throw new Error("Error copying database");
	       } 
	   } 
	 
	}
	
	
	
	 /**
	  * Check if the database already exist to avoid re-copying the file each time you open the application.
	  * @return true if it exists, false if it doesn't
	  */
	private boolean checkDataBase(){
	 
		//SQLiteDatabase checkDB = null;
		boolean checkDB = false;
	 
		//File dbFile = myContext.getDatabasePath(DB_NAME);
		//return dbFile.exists();
		
		try{
			File dbFile = myContext.getDatabasePath(DB_NAME);
			checkDB = dbFile.exists();
			//return dbFile.exists();
			//Toast.makeText(myContext, "Database already exists!", Toast.LENGTH_SHORT).show();
			
			//String myPath = DB_PATH + DB_NAME;
			//File dbfile = new File(myPath);
			//checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			
	    }catch(SQLiteException e){
	        //database does't exist yet.
	    	Toast.makeText(myContext, "Error!!, Database does not exist yet! ", Toast.LENGTH_SHORT).show();
	    }
	 /*
		if(checkDB != null){
	        checkDB.close();
	    }
	 */
		return checkDB;
	   //return checkDB != null ? true : false;
	}
	
	
	
	 /**
	  * Copies your database from your local assets-folder to the just created empty database in the
	  * system folder, from where it can be accessed and handled.
	  * This is done by transfering bytestream.
	  * */
	private void copyDataBase() throws IOException{
	    //Open your local db as the input stream
	    InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	    // Path to the just created empty db
	    String outFileName = DB_PATH + DB_NAME;
	 
	    //Open the empty db as the output stream
	    OutputStream myOutput = new FileOutputStream(outFileName);
	    //OutputStream myOutput = new FileOutputStream("/data/data/(com.example.thesguideproject)/databases   /(mydb).sqlite");
	    
	    //transfer bytes from the inputfile to the outputfile
	    byte[] buffer = new byte[1024];
	    int length;
	    
	    while ((length = myInput.read(buffer))>0){
	      myOutput.write(buffer, 0, length);
	    }
	 
	    //Close the streams
	    myOutput.flush();
	    myOutput.close();
	    myInput.close();
	 
	}
	
	
	 public void openDataBase() throws SQLException{
		//Open the database
		try
		{
			String myPath = DB_PATH + DB_NAME;
			myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			Log.d(debugTag, "Database opened successfully!");
			
			
			Cursor c=myDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table'",null);

			/*if (c.moveToFirst()) {
			    while ( !c.isAfterLast() ) {
			        Toast.makeText(myContext, "Table Name=> "+c.getString(0), Toast.LENGTH_SHORT).show();
			        c.moveToNext();
			    }
			}*/
			
			
		}
		catch(SQLiteException e)
		{
			Log.d(debugTag, "Failed to open Database!");
		}
		
     }
		 
	 @Override
	 public synchronized void close() {
		
		if(myDataBase != null)
		myDataBase.close();
		super.close();
	 }
	 
	 
	 public void getTableNames(){
		 Cursor c = myDataBase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

		 if (c.moveToFirst()) {
		     while ( !c.isAfterLast() ) {
		         Log.d(debugTag, "Table Name=> "+c.getString(0));
		         c.moveToNext();
		     }
		 }
	 }
	 
	 
	 public void clearTableIfExists(){
			SQLiteDatabase db = this.getReadableDatabase();
			
			try{
				db.delete("PlacesTable", null, null);
				//flag = "true";
				Log.d("Table deleted successfully", "true");
			}
			catch(Exception e){
				//flag = "false";
				Log.d("Table deleted successfully", "false");
			}
		}
	 
	 
	 //boolean testTableIfExistsFlag = false;
	 
	 public boolean checkDataTable(){
		 		 
		 	String selectQuery = "SELECT _id FROM PlacesTable ";
	        
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	 
	        //db.execSQL("INSERT INTO TestTable(id, name) VALUES(1, 'giannis')");
	        
	        int count = cursor.getCount();
			//String s = Integer.toString(count);
			
			if (count != 0)
				return true;
			else
				return false;
			
	 }
	 
	 public Cursor getAllTestData(){
		 SQLiteDatabase db = this.getReadableDatabase();
		 
		 String selectQuery = "SELECT _id, surname, image_link FROM Example ";
		 
		 Cursor cursor = db.rawQuery(selectQuery, null);
		 
		 return cursor;
	 }
	 
	 public Cursor getAllTestData(String genre){
		 SQLiteDatabase db = this.getReadableDatabase();
		 
		 String selectQuery = "SELECT * FROM PlacesTable WHERE genre = '" + genre + "'";
		 
		 Cursor cursor = db.rawQuery(selectQuery, null);
		 
		 return cursor;
	 }
	 
	 //ArrayList<TestData> getTestDataByName;
	 
	 public void getArrayListwithTestJsonData(ArrayList<PlacesData> td){
		 boolean returnflag = checkDataTable();
		 if (returnflag == true){
			 // do nothing
		 }else{
			 try{
			  for(int i=0; i<td.size(); i++){
					 PlacesData placesData = td.get(i);
					 
					 int id = placesData.getId();
					 String name_el = placesData.getNameEl();
					 String name_en = placesData.getNameEn();
					 String link = placesData.getLink();
					 double latitude = placesData.getLatitude();
					 double longtitude = placesData.getLongtitude();
					 String photo_link = placesData.getPhotoLink();
					 String genre = placesData.getGenre();
					 
					 SQLiteDatabase db = this.getWritableDatabase();
//db.execSQL("INSERT INTO TestTable(_id, name, surname, type) VALUES(" + id + ",'"  + name  +  ",'"  + surname  + ",'" + type + "')");
					 //db.execSQL("INSERT INTO TestTable(_id, name, surname, type) VALUES(1, 'giannis ' , 'tsironis ' , 'male ')");
					 //db.execSQL("INSERT INTO TestTable(_id, name, surname, type) VALUES(2, 'nikos ' , 'tsironis ' , 'male ')");
					 //db.execSQL("INSERT INTO TestTable(_id, name, surname, type) VALUES(3, 'aggelos ' , 'tsironis ' , 'male ')");
db.execSQL("INSERT INTO PlacesTable(_id, name_el, name_en, link, latitude, longtitude, photo_link, genre) VALUES('" + id + "','" + name_el + "','" + name_en + "','" + link + "','" + latitude + "','" + longtitude + "','" + photo_link + "','" + genre + "')");
				  }
			  Log.i("Data inserted into PlacesTable: ", "status => true");
			 }
			 catch(Exception e){
				 Log.i("Data inserted into PlacesTable: ", "status => false");
			 }
			  //getTestDataByName = getTestDataByName();
			  //MainLayoutActivity mainAct = new MainLayoutActivity();
			  //mainAct.setTestViewUsingBaseAdapter(getTestDataByName);
		 }
		
		  
		 
	 }
	 
	 /*
	 public ArrayList<TestData> getTestDataByName() {
			ArrayList<TestData> testDataByName = new ArrayList<TestData>();
	        // Select All Query
	        //String selectQuery = "SELECT * FROM TestTable ";
			String selectQuery = "SELECT _id, surname, image_link FROM Example ";
			
	        try{
	        	SQLiteDatabase db = this.getReadableDatabase();
	        	Cursor cursor = db.rawQuery(selectQuery, null);
	        	Log.i("Query results: ", " executed successfully!!!");
	        	int count = cursor.getCount();
	 			String s = Integer.toString(count);
	 			Log.d("Cursor row count: ", s);
	 	        
	 	        
	 			
	 			
	 			
	 			// looping through all rows and adding to list
	 	        if (cursor.moveToFirst()) {
	 	            do {
	 	                TestData td = new TestData();
	 	               // td.setId(cursor.getInt(cursor.getColumnIndex(TEST_KEY_ID)));
	 	                td.setName(cursor.getString(cursor.getColumnIndex("_id")));
	 	                td.setSurname(cursor.getString(cursor.getColumnIndex("surname")));
	 	                td.setImageLink(cursor.getString(cursor.getColumnIndex("image_link")));
	 	                //td.setType(cursor.getString(cursor.getColumnIndex(TEST_TYPE)));
	 	                //location.setId(Integer.parseInt(cursor.getString(0)));
	 	                //location.setNameEl(cursor.getString(1));
	 	                //location.setGenre(cursor.getString(2));
	 	                //location.setPhotoLink(cursor.getString(3));
	 	                //location.setLongtitude(cursor.getString(4));
	 	                //location.setLatitude(cursor.getString(5));
	 	                // Adding contact to list
	 	                testDataByName.add(td);
	 	            } while (cursor.moveToNext());
	 	            cursor.close();
	 	        }
	 	 
	        }
	        catch(Exception e)
	        {
	        	Log.i("Query results: ", " executed without results!");
	        }
	        
	        //db.execSQL("INSERT INTO TestTable(id, name) VALUES(1, 'giannis')");
	        
	       
	        //DATABASE_VERSION = 0;
	        // return contact list
	        return testDataByName;
	 }
	 
	 public Cursor selectNamesFromTestdb(){
		 Cursor c = myDataBase.rawQuery("Select name from TestTable ", null);
		 return c;
	 }*/
	 
	 @Override
	 public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		 //String CREATE_TEST_TABLE = "CREATE TABLE " + TEST_TABLE + "(" + TEST_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEST_NAME +
				//	" TEXT " + ")";
			
			//db.execSQL(CREATE_TEST_TABLE);
	 }

	/* public void addTestData(TestData td){
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(TEST_KEY_ID, td.getId());
			values.put(TEST_NAME, td.getName());
			
			db.insert(TEST_TABLE, null, values);
			db.close();
		}
	 
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
		}
	 */
	 @Override
	 public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	 }

}

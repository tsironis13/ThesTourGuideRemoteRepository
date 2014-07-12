package com.example.sqlHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.example.locationData.LocationData;
import com.example.locationData.TestData;

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

	//The Android's default system path of your application database.
	private static String DB_PATH = "/data/data/com.example.thesguideproject/databases/";
	
	//private static String DB_NAME = "MyLocationsForThessTourGuideProject";
	private static String DB_NAME = "mydb";
	private static final String debugTag = "TestLocalSqliteDatabase";
	private SQLiteDatabase myDataBase;
	 
	
	//private static final String TEST_TABLE = "testtable";    
	private static final String TEST_KEY_ID = "id";
	private static final String TEST_NAME = "name";
	
	private static String flag;
	//private static final String TABLE_RECORD = "TestTable"; 
	
	private final Context myContext;
	
	 /**
	  * Constructor
	  * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
	  * @param context
	  */
	public TestLocalSqliteDatabase(Context context) {
	    super(context, DB_NAME, null, 1);
	    this.myContext = context;
	}
	
	
	 /**
	  * Creates a empty database on the system and rewrites it with your own database.
	  * */
	public void createDataBase() throws IOException{
	 
		boolean dbExist = checkDataBase();
	 
		if(dbExist){
			//do nothing - database already exist
		}else{
	 
		//By calling this method and empty database will be created into the default system path
		//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();
			
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
	 
		SQLiteDatabase checkDB = null;
	 
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	    }catch(SQLiteException e){
	        //database does't exist yet.
	    }
	 
		if(checkDB != null){
	        checkDB.close();
	    }
	 
	   return checkDB != null ? true : false;
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
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
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
	 
	 
	 //boolean testTableIfExistsFlag = false;
	 
	 private boolean checkDataTable(){
		 		 
		 	String selectQuery = "SELECT name FROM TestTable ";
	        
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	 
	        //db.execSQL("INSERT INTO TestTable(id, name) VALUES(1, 'giannis')");
	        
	        int count = cursor.getCount();
			//String s = Integer.toString(count);
			
			if (count != 0) return true;
			else{
				return false;
			}
	 }
	 
	 
	 public void getArrayListwithTestJsonData(ArrayList<TestData> td){
		 boolean returnflag = checkDataTable();
		 if (returnflag){
			 // do nothing
		 }else{
			  for(int i=0; i<td.size(); i++){
					 TestData testData = td.get(i);
					 
					 int id = testData.getId();
					 String name = testData.getName();
					 
					 SQLiteDatabase db = this.getReadableDatabase();
					 db.execSQL("INSERT INTO TestTable(_id, name) VALUES(" + id + ",'" + name + "')");
				  }
		 }
		
		  
		 
	 }
	 
	 
	 
	 
	 public ArrayList<TestData> getTestDataByName() {
			ArrayList<TestData> testDataByName = new ArrayList<TestData>();
	        // Select All Query
	        String selectQuery = "SELECT name FROM TestTable ";
	        
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	 
	        //db.execSQL("INSERT INTO TestTable(id, name) VALUES(1, 'giannis')");
	        
	        int count = cursor.getCount();
			String s = Integer.toString(count);
			Log.d("Cursor row count: ", s);
	        
	        // looping through all rows and adding to list
	        if (cursor.moveToFirst()) {
	            do {
	                TestData td = new TestData();
	                td.setName(cursor.getString(0));
	                //location.setId(Integer.parseInt(cursor.getString(0)));
	                //location.setNameEl(cursor.getString(1));
	                //location.setGenre(cursor.getString(2));
	                //location.setPhotoLink(cursor.getString(3));
	                //location.setLongtitude(cursor.getString(4));
	                //location.setLatitude(cursor.getString(5));
	                // Adding contact to list
	                testDataByName.add(td);
	            } while (cursor.moveToNext());
	        }
	 
	        //DATABASE_VERSION = 0;
	        // return contact list
	        return testDataByName;
	 }
	 
	 public Cursor selectNamesFromTestdb(){
		 Cursor c = myDataBase.rawQuery("Select name from TestTable ", null);
		 return c;
	 }
	 
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

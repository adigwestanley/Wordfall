package com.lclass.wordwrangle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.lclass.actor.Level;



import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{

	//Initialize SQL Database
	SQLiteDatabase db;
	
	//Set up constants for the database handles
	private static String DB_PATH = "/data/data/com.lclass.wordwrangle/databases/";
	private static final String DB_NAME = "wordgame.db";
	private static final int DATABASE_VERSION = 2;
	
	
	//Set up context
	private Context myContext;
	
    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }
    
    public String getWord(int id)
    {
  	   String query = "Select Word_Wrangler.word from Word_Wrangler where id ='"+ id +"'";
  	
  	   Cursor cursor = db.rawQuery(query, new String[]{});
  	 
  	   String resultUser = null;
  	   if(cursor != null)
  	   {
  		   cursor.moveToFirst();
  		   resultUser = cursor.getString(0);
  		   System.out.print("resultUser----"+resultUser);
  	   }
  	   return resultUser;
    }
    
    public ArrayList<Level> getLevels()
    {
    	ArrayList<Level> levels = new ArrayList<Level>();
    	
    	String query = "Select word, numMoves, time, completed_level from Word_Wrangler";
    	
    	Cursor cursor = db.rawQuery(query, new String[]{});
    	
    	if (cursor.moveToFirst()) {
            do {
    		    levels.add(new Level(cursor.getString(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
    	
    	return levels;
    }
    
    public Level getSingleLevel(int level)
    {
    	Level l = null;
    	
    	String query = "Select word, numMoves, time from Word_Wrangler WHERE levelNumber = '" + level + "'";
    	
    	Cursor cursor = db.rawQuery(query, new String[]{});
    	
    	if (cursor.moveToFirst()) {
            do {
    		    l = new Level(cursor.getString(0), cursor.getInt(1), cursor.getInt(2));
            } while (cursor.moveToNext());
        }
    	
    	return l;
    }
    
    public void updateCompleted(int i)
    {
    	String query = "UPDATE Word_Wrangler SET completed_level = 1 WHERE levelNumber = '" + i + "'";
    	db.execSQL(query);
    	System.out.println("DB UPDATE CALED " + query);
    }
     
    
    
    
	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 
		if(newVersion > oldVersion){                    
	        try { 
	            copyDataBase(); 
	        } catch (IOException e) { 
	            throw new Error("Error upgrading database"); 
	        } 
        }
	}
	
	 public void OverwriteOld() throws IOException{
 		 //By calling this method and empty database will be created into the default system path
         //of your application so we are gonna be able to overwrite that database with our database.
     	 // this.getReadableDatabase();
 		    this.getReadableDatabase();
 		    this.getReadableDatabase().close();
 		   
 		   try {
    			   copyDataBase();
    		   } catch (IOException e) {
        		    throw new Error("Error copying database");
            }
   	
      }
	
    
    public void createDataBase() throws IOException{
		//By calling this method and empty database will be created into the default system path
        //of your application so we are gonna be able to overwrite that database with our database.
    	//this.getReadableDatabase();
    	//File dbFile= myContext.getDatabasePath(DB_PATH + DB_NAME);
    	File f = new File(DB_PATH);
    	if (!f.exists()) {
    	  f.mkdir();
    	}
    	
    	Boolean exists = checkDataBase();
		
    	if(!exists)//!dbFile.exists())
		{
		    this.getReadableDatabase();
		    //this.getReadableDatabase().close();
		   
		    try {
		       this.close();
   			   copyDataBase();
   		    } catch (IOException e) {
       		    throw new Error("Error copying database");
           }
		}
		else
		{
			this.getReadableDatabase();
			this.getReadableDatabase().close();
		}
  	
    }
    
    //check if the database exists
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
    	OutputStream myOutput = new FileOutputStream(outFileName); //this line throws error
    	
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
    	db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }
 
    @Override
	public synchronized void close() {
    	    if(db != null)
    		    db.close();
 
    	    super.close();
 
	}

   

}

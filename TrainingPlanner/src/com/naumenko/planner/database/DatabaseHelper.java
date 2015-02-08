package com.naumenko.planner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private boolean isDatabaseEmpty = false;
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "planner";
	private static final String DRILLS_CREATION_TABLE_QUERY = "CREATE TABLE" 
			+ " IF NOT EXISTS drills (id INTEGER PRIMARY KEY," 
			+ " name TEXT NOT NULL, description TEXT NOT NULL," 
			+ " picture TEXT NOT NULL, time TEXT NOT NULL," 
			+ " execution TEXT NOT NULL, effect TEXT NOT NULL,"
			+ " thumbnail TEXT NOT NULL)";
					
	private static final String DROP_DRILLS_TABLE = "DROP TABLE drills";
		
	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.e("DataAccessObject", "Creation database ");
		database.execSQL(DRILLS_CREATION_TABLE_QUERY);
		
		isDatabaseEmpty = true;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) { 
		db.execSQL(DROP_DRILLS_TABLE);
		db.execSQL(DRILLS_CREATION_TABLE_QUERY);
		
		db.close();
		isDatabaseEmpty = true;
	}
	
	public boolean isDatabaseEmpty(){
		return isDatabaseEmpty;
	}
}

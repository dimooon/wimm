package com.naumenko.wimm.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EntityTable {
	
	
	private static final String TAG = EntityTable.class.getSimpleName();

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(WIMM_QUERY.CREATE_DATABASE.getQueryString());
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(TAG,
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		
		db.execSQL(WIMM_QUERY.DROP_TABLE_IF_EXISTS.getQueryString());
	    onCreate(db);
	}
	
}

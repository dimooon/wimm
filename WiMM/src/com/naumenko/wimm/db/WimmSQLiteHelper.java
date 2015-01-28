package com.naumenko.wimm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WimmSQLiteHelper extends SQLiteOpenHelper{

	private static final String TAG = WimmSQLiteHelper.class.getSimpleName();
	
	public WimmSQLiteHelper(Context context) {
		super(context, 
				ENTITY_CONTRACT.DATABASE_NAME.getContractKey(), 
				null, 
				Integer.valueOf(ENTITY_CONTRACT.DATABASE_VERSION.getContractKey()));
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(WIMM_QUERY.CREATE_DATABASE.getQueryString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(TAG,
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		
		db.execSQL(WIMM_QUERY.DROP_TABLE_IF_EXISTS.getQueryString());
	    onCreate(db);
	}

}

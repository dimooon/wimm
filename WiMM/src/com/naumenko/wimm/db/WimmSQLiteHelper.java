package com.naumenko.wimm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WimmSQLiteHelper extends SQLiteOpenHelper{

	private static final String TAG = WimmSQLiteHelper.class.getSimpleName();
	
	private EntityTable entityTable;
	
	public WimmSQLiteHelper(Context context) {
		super(context, 
				ENTITY_CONTRACT.DATABASE_NAME.getContractKey(), 
				null, 
				Integer.valueOf(ENTITY_CONTRACT.DATABASE_VERSION.getContractKey()));
		
		entityTable = new EntityTable();
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		entityTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		entityTable.onUpgrade(db, oldVersion, newVersion);
	}

}

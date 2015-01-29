package com.naumenko.wimm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WimmSQLiteHelper extends SQLiteOpenHelper{

	public WimmSQLiteHelper(Context context) {
		super(context, 
				ENTITY_CONTRACT.DATABASE_NAME.getContractKey(), 
				null, 
				Integer.valueOf(ENTITY_CONTRACT.DATABASE_VERSION.getContractKey()));
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		EntityTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		EntityTable.onUpgrade(db, oldVersion, newVersion);
	}

}

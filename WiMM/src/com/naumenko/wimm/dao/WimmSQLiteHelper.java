package com.naumenko.wimm.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WimmSQLiteHelper extends SQLiteOpenHelper{

	private static final String TAG = WimmSQLiteHelper.class.getSimpleName();
	
	public enum ENTITY_CONTRACT{
		
		DATABASE_NAME("wimm_database"),
	
		DATABASE_VERSION("1"),
		
		TABLE_NAME("wimm_entity"),
		ID("_id"),
		NAME("wimm_entity_name"),
		DESCRIPTION("wimm_entity_description"),
		AMOUNT("wimm_entity_amount"),
		PAYMENT_TYPE("wimm_entity_payment_type"),
		DATE("wimm_entity_date")
		
		;
	
		private String contractKey;
		
		private ENTITY_CONTRACT(String contractKey) {
			this.contractKey = contractKey;
		}
		
		public String getContractKey(){
			return this.contractKey;
		}
		
	}
	
	private enum WIMM_QUERY{
		CREATE_DATABASE(
				"create table " + ENTITY_CONTRACT.TABLE_NAME.getContractKey() + 
				"(" + ENTITY_CONTRACT.ID.getContractKey() + " integer primary key autoincrement, " + 
					  ENTITY_CONTRACT.NAME.getContractKey()  + " text not null," +
					  ENTITY_CONTRACT.DESCRIPTION.getContractKey()  + " text," +
					  ENTITY_CONTRACT.AMOUNT.getContractKey()  + " REAL not null," +
					  ENTITY_CONTRACT.PAYMENT_TYPE.getContractKey()  + " INTEGER not null," +
					  ENTITY_CONTRACT.DATE.getContractKey()  + " INTEGER not null" +
				 ");"),
				 
		DROP_TABLE_IF_EXISTS("DROP TABLE IF EXISTS " + ENTITY_CONTRACT.TABLE_NAME);
		
		private String query;
		
		private WIMM_QUERY(String queryString) {
			this.query = queryString;
		}
		
		public String getQueryString(){
			return this.query;
		}
		
	}
	
	public WimmSQLiteHelper(Context context) {
		super(context, 
				ENTITY_CONTRACT.DATABASE_NAME.getContractKey(), 
				null, 
				Integer.valueOf(ENTITY_CONTRACT.DATABASE_VERSION.contractKey));
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

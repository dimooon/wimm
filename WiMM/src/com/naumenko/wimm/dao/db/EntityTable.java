package com.naumenko.wimm.dao.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.naumenko.wimm.dao.db.WimmSQLiteHelper.WIMM_QUERY;

public class EntityTable {
	
	
	private static final String TAG = EntityTable.class.getSimpleName();

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(WIMM_QUERY.CREATE_ENTITY_TABLE.getQueryString());
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(TAG,
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		
		db.execSQL(WIMM_QUERY.DROP_TABLE_ENTITY_IF_EXISTS.getQueryString());
	    onCreate(db);
	}
	
	public enum ENTITY_CONTRACT{
		
		CONTRACT(null),
		DATABASE_NAME("wimm_database"),

		DATABASE_VERSION("2"),
		
		TABLE_NAME("wimm_entity"),
		COLUMN_ID("_id",0),
		COLUMN_NAME("wimm_entity_name",1),
		COLUMN_DESCRIPTION("wimm_entity_description",2),
		COLUMN_AMOUNT("wimm_entity_amount",3),
		COLUMN_PAYMENT_TYPE("wimm_entity_payment_type",4),
		COLUMN_DATE("wimm_entity_date",5),
        COLUMN_LIST_ID("list_id",6);

		private String contractKey;
		private int index;
		
		private ENTITY_CONTRACT(String contractKey) {
			this.contractKey = contractKey;
		}
		
		private ENTITY_CONTRACT(String contractKey, int columnIndex) {
			this(contractKey);
			this.index = columnIndex;
		}
		
		public String getContractKey(){
			return this.contractKey;
		}
		
		public int getIndex(){
			return index;
		}
		
		public String[] getSelectionAllFields(){
			return new String[]{
				COLUMN_ID.getContractKey(),
				COLUMN_NAME.getContractKey(),
				COLUMN_DESCRIPTION.getContractKey(),
				COLUMN_AMOUNT.getContractKey(),
				COLUMN_PAYMENT_TYPE.getContractKey(),
				COLUMN_DATE.getContractKey(),
                COLUMN_LIST_ID.getContractKey()};
		}
	}
}
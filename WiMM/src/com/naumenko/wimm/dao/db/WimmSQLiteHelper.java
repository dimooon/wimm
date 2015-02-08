package com.naumenko.wimm.dao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.naumenko.wimm.dao.db.EntityTable.ENTITY_CONTRACT;

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
        ListTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		EntityTable.onUpgrade(db, oldVersion, newVersion);
        ListTable.onUpgrade(db, oldVersion, newVersion);
	}

	public enum WIMM_QUERY{
		
		CREATE_ENTITY_TABLE(
				"create table " + ENTITY_CONTRACT.TABLE_NAME.getContractKey() + 
				"(" + ENTITY_CONTRACT.COLUMN_ID.getContractKey() + " integer primary key autoincrement, " + 
					  ENTITY_CONTRACT.COLUMN_NAME.getContractKey()  + " text not null," +
					  ENTITY_CONTRACT.COLUMN_DESCRIPTION.getContractKey()  + " text," +
					  ENTITY_CONTRACT.COLUMN_AMOUNT.getContractKey()  + " REAL not null," +
					  ENTITY_CONTRACT.COLUMN_PAYMENT_TYPE.getContractKey()  + " INTEGER not null," +
					  ENTITY_CONTRACT.COLUMN_DATE.getContractKey()  + " INTEGER not null," +
                      ENTITY_CONTRACT.COLUMN_LIST_ID.getContractKey()  + " INTEGER" +
				 ");"),

        CREATE_LIST_TABLE(
                "create table " + ListTable.LIST_CONTRACT.TABLE_NAME.getContractKey() +
                        "(" + ListTable.LIST_CONTRACT.COLUMN_ID.getContractKey() + " integer primary key autoincrement, " +
                              ListTable.LIST_CONTRACT.COLUMN_NAME.getContractKey()  + " text not null" +
                        ");"),

        CREATE_UNNAMED_LIST(
                "INSERT INTO " + ListTable.LIST_CONTRACT.TABLE_NAME.getContractKey() +
                        "(" + ListTable.LIST_CONTRACT.COLUMN_NAME.getContractKey() +")" +
                 "VALUES (" + " 'Unnamed' " + ");"),

		DROP_TABLE_ENTITY_IF_EXISTS("DROP TABLE IF EXISTS " + ENTITY_CONTRACT.TABLE_NAME.getContractKey()),
        DROP_TABLE_LIST_IF_EXISTS("DROP TABLE IF EXISTS " + ListTable.LIST_CONTRACT.TABLE_NAME.getContractKey());

		private String query;
		
		private WIMM_QUERY(String queryString) {
			this.query = queryString;
		}
		
		public String getQueryString(){
			return this.query;
		}
	}
}
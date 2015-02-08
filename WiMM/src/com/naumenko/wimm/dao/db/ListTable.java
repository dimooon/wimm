package com.naumenko.wimm.dao.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.naumenko.wimm.dao.db.WimmSQLiteHelper.WIMM_QUERY;

public class ListTable {
	
	
	private static final String TAG = ListTable.class.getSimpleName();

	public static void onCreate(SQLiteDatabase db) {
		db.execSQL(WIMM_QUERY.CREATE_LIST_TABLE.getQueryString());
        db.execSQL(WIMM_QUERY.CREATE_UNNAMED_LIST.getQueryString());
	}

	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		Log.w(TAG,
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		
		db.execSQL(WIMM_QUERY.DROP_TABLE_LIST_IF_EXISTS.getQueryString());
	    onCreate(db);
	}
	
	public enum LIST_CONTRACT {

        CONTRACT(null),

        TABLE_NAME("wimm_entity_list"),
        COLUMN_ID("_id", 0),
        COLUMN_NAME("wimm_list_name", 1);

        private String contractKey;
        private int index;

        private LIST_CONTRACT(String contractKey) {
            this.contractKey = contractKey;
        }

        private LIST_CONTRACT(String contractKey, int columnIndex) {
            this(contractKey);
            this.index = columnIndex;
        }

        public String getContractKey() {
            return this.contractKey;
        }

        public int getIndex() {
            return index;
        }

        public String[] getSelectionAllFields() {
            return new String[]{
                    COLUMN_ID.getContractKey(),
                    COLUMN_NAME.getContractKey(),
            };
        }
    }
}
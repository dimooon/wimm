package com.naumenko.wimm.dao.provider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.naumenko.wimm.dao.db.EntityTable;
import com.naumenko.wimm.dao.db.WimmSQLiteHelper;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;

public class WimmContentProvider extends ContentProvider{

	private WimmSQLiteHelper databaseHelper; 
	
	@Override
	public boolean onCreate() {
		
		databaseHelper = new WimmSQLiteHelper(getContext());
		
		return false;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

	    SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

	    checkColumns(projection);

	    queryBuilder.setTables(EntityTable.ENTITY_CONTRACT.TABLE_NAME.getContractKey());

	    int uriType = CONTRACT.URIMatcher.match(uri);
	    switch (uriType) {
	    case CONTRACT.ENTITY_CODE:
	      break;
	    case CONTRACT.ENTITY_ID_CODE:
	    	
	      queryBuilder.appendWhere(EntityTable.ENTITY_CONTRACT.COLUMN_ID.getContractKey() + "=" + uri.getLastPathSegment());
	      break;
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }

	    SQLiteDatabase db = databaseHelper.getWritableDatabase();
	    
	    Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

	    cursor.setNotificationUri(getContext().getContentResolver(), uri);

	    return cursor;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		return 0;
	}
	
	@Override
	public String getType(Uri uri) {
		return null;
	}
	
	public static class CONTRACT {
		
		public static final String AUTHORITY = "com.naumenko.wimm";
		public static final String PATH = "wimm";
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);
	
		private static final UriMatcher URIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		private static final int ENTITY_CODE = 10;
		private static final int ENTITY_ID_CODE = 20;
		
		static {
		    URIMatcher.addURI(AUTHORITY, PATH, ENTITY_CODE);
		    URIMatcher.addURI(AUTHORITY, PATH + "/#", ENTITY_ID_CODE);
		}
		
	}
	
	private void checkColumns(String[] projection) {
	    String[] available = { 
	    
	    		EntityTable.ENTITY_CONTRACT.COLUMN_ID.getContractKey(),
	    		EntityTable.ENTITY_CONTRACT.COLUMN_NAME.getContractKey(),
	    		EntityTable.ENTITY_CONTRACT.COLUMN_DESCRIPTION.getContractKey(),
	    		EntityTable.ENTITY_CONTRACT.COLUMN_AMOUNT.getContractKey(),
	    		EntityTable.ENTITY_CONTRACT.COLUMN_PAYMENT_TYPE.getContractKey(),
	    		EntityTable.ENTITY_CONTRACT.COLUMN_DATE.getContractKey()};
	    
	    if (projection != null) {
	      HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
	      HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
	      
	      if (!availableColumns.containsAll(requestedColumns)) {
	        throw new IllegalArgumentException("Unknown columns in projection");
	      }
	   }
	}
	
}

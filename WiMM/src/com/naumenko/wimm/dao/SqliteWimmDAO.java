	package com.naumenko.wimm.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naumenko.wimm.dao.db.EntityTable.ENTITY_CONTRACT;
import com.naumenko.wimm.dao.db.WimmSQLiteHelper;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class SqliteWimmDAO implements WimmDAO{

	private SQLiteDatabase database;
	private WimmSQLiteHelper helper;
	
	public SqliteWimmDAO(Context context) {
		helper = new WimmSQLiteHelper(context);
	}
	
	@Override
	public long addEntity(WimmEntity entity) {
		
		open();
		
		ContentValues values = new ContentValues();
	    values.put(ENTITY_CONTRACT.COLUMN_NAME.getContractKey(), entity.getName());
	    values.put(ENTITY_CONTRACT.COLUMN_DESCRIPTION.getContractKey(), entity.getDescription());
	    values.put(ENTITY_CONTRACT.COLUMN_AMOUNT.getContractKey(), entity.getAmount());
	    values.put(ENTITY_CONTRACT.COLUMN_PAYMENT_TYPE.getContractKey(), entity.getType().getTypeRepresentation());
	    values.put(ENTITY_CONTRACT.COLUMN_DATE.getContractKey(), entity.getTimeInMs());
	    
	    long insertId = database.insert(ENTITY_CONTRACT.TABLE_NAME.getContractKey(), null, values);
	    
	    close();
	    
		return insertId;
	}

	@Override
	public boolean updateEntity(WimmEntity entity) {
		return false;
	}

	@Override
	public boolean deleteEntity(long id) {
		
		open();
		
	    int count = database.delete(
	    			ENTITY_CONTRACT.TABLE_NAME.getContractKey(), 
	    			ENTITY_CONTRACT.COLUMN_ID.getContractKey() + " = " + id, 
	    			null);
		
	    close();
	    
		return count > 0;
	}

	@Override
	public int clear() {
		
		int deletedCount; 
		
		open();
		
		deletedCount =  database.delete(ENTITY_CONTRACT.TABLE_NAME.getContractKey() , null, null);
		
		close();
		
		return deletedCount;
	}
	
	@Override
	public WimmEntity getEntity(long id) {
		
		open();
		
		Cursor cursor = database.query(
				ENTITY_CONTRACT.TABLE_NAME.getContractKey(),
				ENTITY_CONTRACT.CONTRACT.getSelectionAllFields(), 
				ENTITY_CONTRACT.COLUMN_ID.getContractKey() + " = " + id, 
				null,
		        null,
		        null,
		        null);
		cursor.moveToFirst();
		
		WimmEntity entity = new PaymentWimmEntity(cursor);
		cursor.close();
		
		close();
		
		return entity;
	}
	
	@Override
	public List<WimmEntity> getEntityList() {
		
		List<WimmEntity> entities = new ArrayList<WimmEntity>();

		open();
		
	    Cursor cursor = database.query(
	    		ENTITY_CONTRACT.TABLE_NAME.getContractKey(), 
	    		ENTITY_CONTRACT.CONTRACT.getSelectionAllFields(), 
	    		null, 
	    		null, 
	    		null, 
	    		null, 
	    		null);
	    
	    cursor.moveToFirst();
	    
	    while (!cursor.isAfterLast()) {
	      entities.add(cursorToEntity(cursor));
	      cursor.moveToNext();
	    }

	    cursor.close();
	    close();
	    
		return entities;
	}
	
	private void open(){
		database = helper.getWritableDatabase();
	}
	
	private void close(){
		helper.close();
	}
	
	private WimmEntity cursorToEntity(Cursor cursor){
		
		WimmEntity entity = new PaymentWimmEntity(cursor);
		
		return entity;
	}
	
}
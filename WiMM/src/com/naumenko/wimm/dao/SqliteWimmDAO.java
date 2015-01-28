	package com.naumenko.wimm.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.db.ENTITY_CONTRACT;
import com.naumenko.wimm.db.WimmSQLiteHelper;

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
	    values.put(ENTITY_CONTRACT.NAME.getContractKey(), entity.getName());
	    values.put(ENTITY_CONTRACT.DESCRIPTION.getContractKey(), entity.getDescription());
	    values.put(ENTITY_CONTRACT.AMOUNT.getContractKey(), entity.getAmount());
	    values.put(ENTITY_CONTRACT.PAYMENT_TYPE.getContractKey(), entity.getType().getTypeRepresentation());
	    values.put(ENTITY_CONTRACT.DATE.getContractKey(), entity.getTimeInMs());
	    
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
		
	    int count = database.delete(
	    			ENTITY_CONTRACT.TABLE_NAME.getContractKey(), 
	    			ENTITY_CONTRACT.ID.getContractKey() + " = " + id, 
	    			null);
		
		return count > 0;
	}

	@Override
	public WimmEntity getEntity(long id) {
		
		open();
		
		Cursor cursor = database.query(
				ENTITY_CONTRACT.TABLE_NAME.getContractKey(),
				ENTITY_CONTRACT.CONTRACT.getSelectionAllFields(), 
				ENTITY_CONTRACT.ID.getContractKey() + " = " + id, 
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
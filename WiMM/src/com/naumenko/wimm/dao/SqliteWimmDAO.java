	package com.naumenko.wimm.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.naumenko.wimm.dao.db.EntityTable.ENTITY_CONTRACT;
import com.naumenko.wimm.dao.db.ListTable.LIST_CONTRACT;
import com.naumenko.wimm.dao.db.EntityTable;
import com.naumenko.wimm.dao.db.ListTable;
import com.naumenko.wimm.dao.db.WimmSQLiteHelper;
import com.naumenko.wimm.dao.entity.PaymentList;
import com.naumenko.wimm.dao.entity.Payment;
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
		
	    long insertId = database.insert(ENTITY_CONTRACT.TABLE_NAME.getContractKey(), null, entity.getConvertedContentValues());
	    
	    close();
	    
		return insertId;
	}

	@Override
	public boolean updateEntity(WimmEntity entity) {
		
		open();
		
		int updatedRawCount = database.update(
				ENTITY_CONTRACT.TABLE_NAME.getContractKey(),
				entity.getConvertedContentValues(),
				ENTITY_CONTRACT.COLUMN_ID.getContractKey() + " = " + entity.getId(),
				null);
		
		close();
		
		return updatedRawCount > 0;
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
		deletedCount+=  database.delete(ListTable.LIST_CONTRACT.TABLE_NAME.getContractKey(), EntityTable.ENTITY_CONTRACT.COLUMN_ID.getContractKey() + "> 1", null);
		
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
		
		WimmEntity entity = new Payment(cursor);
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
	
	@Override
	public List<WimmEntity> getEntityList(long listId) {
		
		List<WimmEntity> entities = new ArrayList<WimmEntity>();

		open();
		
	    Cursor cursor = database.query(
	    		ENTITY_CONTRACT.TABLE_NAME.getContractKey(), 
	    		ENTITY_CONTRACT.CONTRACT.getSelectionAllFields(), 
	    		ENTITY_CONTRACT.COLUMN_LIST_ID.getContractKey() + " = " + listId, 
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
	
	@Override
	public ArrayList<PaymentList> getPaymentLists() {
		ArrayList<PaymentList> entityLists = new ArrayList<PaymentList>();

		open();
		
	    Cursor cursor = database.query(
	    		LIST_CONTRACT.TABLE_NAME.getContractKey(), 
	    		LIST_CONTRACT.CONTRACT.getSelectionAllFields(), 
	    		null, 
	    		null, 
	    		null, 
	    		null, 
	    		null);
	    
	    cursor.moveToFirst();
	    
	    while (!cursor.isAfterLast()) {
	    	entityLists.add(cursorToPamentList(cursor));
	      cursor.moveToNext();
	    }

	    cursor.close();
	    close();
	    
		return entityLists;
	}
	
	@Override
	public PaymentList getPaymentList(long list_id) {
		
		open();
		
		Cursor cursor = database.query(
				LIST_CONTRACT.TABLE_NAME.getContractKey(),
				LIST_CONTRACT.CONTRACT.getSelectionAllFields(), 
				LIST_CONTRACT.COLUMN_ID.getContractKey() + " = " + list_id, 
				null,
		        null,
		        null,
		        null);
		cursor.moveToFirst();
		
		PaymentList list = new PaymentList();
		list.cursorToWimmEntity(cursor);
		list.setEntities(getEntityList(list.getId()));
		cursor.close();
		
		close();
		
		return list;
		
	}
	
	@Override
	public long addPaymentList(PaymentList list) {
		open();
		
	    long insertId = database.insert(LIST_CONTRACT.TABLE_NAME.getContractKey(), null, list.getConvertedContentValues());
	    
	    for (WimmEntity entity : list.getEntities()) {
	    	entity.setListId(insertId);
			addEntity(entity);
		}
	    
	    close();
	    
		return insertId;
	}
	
	@Override
	public boolean deleteList(long id) {
		open();
		
	    int count = database.delete(
	    			LIST_CONTRACT.TABLE_NAME.getContractKey(), 
	    			LIST_CONTRACT.COLUMN_ID.getContractKey() + " = " + id, 
	    			null);
		
	    close();
	    
		return count > 0;
		
	}
	
	private void open(){
		database = helper.getWritableDatabase();
	}
	
	private void close(){
		helper.close();
	}
	
	private WimmEntity cursorToEntity(Cursor cursor){
		
		WimmEntity entity = new Payment(cursor);
		
		return entity;
	}
	
	private PaymentList cursorToPamentList(Cursor cursor){
		
		PaymentList paymentList = new PaymentList();
		paymentList.cursorToWimmEntity(cursor);
		paymentList.setEntities(getEntityList(paymentList.getId()));
		
		return paymentList;
	}

}
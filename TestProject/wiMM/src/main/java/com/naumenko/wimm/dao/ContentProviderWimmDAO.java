package com.naumenko.wimm.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.naumenko.wimm.dao.db.EntityTable;
import com.naumenko.wimm.dao.db.ListTable;
import com.naumenko.wimm.dao.db.EntityTable.ENTITY_CONTRACT;
import com.naumenko.wimm.dao.db.ListTable.LIST_CONTRACT;
import com.naumenko.wimm.dao.entity.PaymentList;
import com.naumenko.wimm.dao.entity.Payment;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.dao.provider.WimmContentProvider;

public class ContentProviderWimmDAO implements WimmDAO{
	
	private ContentResolver resolver;
	
	public ContentProviderWimmDAO(Context context) {
		
		resolver = context.getContentResolver();
		
	}
	
	@Override
	public long addEntity(WimmEntity entity) {
		
		Uri insergedUri = resolver.insert(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				entity.getConvertedContentValues());
		
		long insertedId = Long.parseLong(insergedUri.getLastPathSegment());
		
		return insertedId;
	}

	@Override
	public boolean updateEntity(WimmEntity entity) {
		
		int updateCount = resolver.update(
								WimmContentProvider.CONTRACT.CONTENT_URI, 
								entity.getConvertedContentValues(), 
								String.valueOf(entity.getId()), 
								null);
		return updateCount > 0;
	}

	@Override
	public boolean deleteEntity(long id) {
		
		int deleteCount = resolver.delete(
								WimmContentProvider.CONTRACT.CONTENT_URI, 
								String.valueOf(id), 
								null); 
		
		return deleteCount > 0;
	}

	@Override
	public WimmEntity getEntity(long id) {
		
		Cursor cursor = resolver.query(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				EntityTable.ENTITY_CONTRACT.CONTRACT.getSelectionAllFields(), 
				ENTITY_CONTRACT.COLUMN_ID.getContractKey() + " = " + String.valueOf(id), 
				null, 
				null);
		
		cursor.moveToFirst();
		
		WimmEntity entity = new Payment(cursor);
		
		return entity;
	}

	@Override
	public List<WimmEntity> getEntityList() {
		
		List<WimmEntity> entities = new ArrayList<WimmEntity>();
		
		Cursor cursor = resolver.query(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				null, 
				null, 
				null, 
				null);
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
		      entities.add(cursorToEntity(cursor));
		      cursor.moveToNext();
		}
		
		return entities;
	}

	@Override
	public ArrayList<PaymentList> getPaymentLists() {
		ArrayList<PaymentList> paymentLists = new ArrayList<PaymentList>();
		
		Cursor cursor = resolver.query(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				null, 
				null, 
				null, 
				ListTable.LIST_CONTRACT.TABLE_NAME.getContractKey());
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
			paymentLists.add(cursorToPamentList(cursor));
		    cursor.moveToNext();
		}
		
		return paymentLists;
	}
	
	@Override
	public List<WimmEntity> getEntityList(long listId) {
		
		List<WimmEntity> entities = new ArrayList<WimmEntity>();
		
		Cursor cursor = resolver.query(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				ENTITY_CONTRACT.CONTRACT.getSelectionAllFields(), 
				ENTITY_CONTRACT.COLUMN_LIST_ID.getContractKey() + " = " + String.valueOf(listId),
				null, 
				ENTITY_CONTRACT.TABLE_NAME.getContractKey());
		
		cursor.moveToFirst();
		
		while (!cursor.isAfterLast()) {
		      entities.add(cursorToEntity(cursor));
		      cursor.moveToNext();
		}
		
		return entities;

	}
	
	@Override
	public PaymentList getPaymentList(long list_id) {
		Cursor cursor = resolver.query(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				LIST_CONTRACT.CONTRACT.getSelectionAllFields(), 
				LIST_CONTRACT.COLUMN_ID.getContractKey() + " = " + String.valueOf(list_id), 
				null, 
				LIST_CONTRACT.TABLE_NAME.getContractKey());
		
		cursor.moveToFirst();
		
		PaymentList list = new PaymentList();
		list.cursorToWimmEntity(cursor);
		list.setEntities(getEntityList(list.getId()));
		
		return list;
	}
	
	@Override
	public long addPaymentList(PaymentList list) {
		return 0;
	}
	
	@Override
	public boolean deleteList(long id) {
		return false;
	}
	
	@Override
	public int clear() {
		
		int deleteCount = resolver.delete(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				null, 
				null); 
		
		return deleteCount;
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
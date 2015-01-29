package com.naumenko.wimm.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.naumenko.wimm.dao.db.EntityTable;
import com.naumenko.wimm.dao.db.EntityTable.ENTITY_CONTRACT;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
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
		
		WimmEntity entity = new PaymentWimmEntity(cursor);
		
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
	public int clear() {
		return 0;
	}
	
	private WimmEntity cursorToEntity(Cursor cursor){
		
		WimmEntity entity = new PaymentWimmEntity(cursor);
		
		return entity;
	}
}
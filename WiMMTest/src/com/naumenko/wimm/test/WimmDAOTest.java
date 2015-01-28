package com.naumenko.wimm.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;
import android.util.Log;

import com.naumenko.wimm.dao.SqliteWimmDAO;
import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.test.mock.StubEntity;

public class WimmDAOTest extends AndroidTestCase{
	
	private static final String TAG = WimmDAOTest.class.getSimpleName();
	private WimmDAO dataAccessObject;
	private WimmEntity entity;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		initDAO();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		freeDAO();
	}
	
	public void testPreConditions(){
		assertNotNull(dataAccessObject);
		assertNotNull(entity);
	}
	
	public void testAddEntity(){
		
		assertTrue(dataAccessObject.addEntity(entity));
		
	}
	
	public void testUpdateEntity(){
		
		assertTrue(dataAccessObject.updateEntity(entity));
		
	}
	
	public void testDeleteEntity(){
		assertTrue(dataAccessObject.deleteEntity(entity));
	}
	
	public void testGetEntityList(){
		
		dataAccessObject.addEntity(entity);
		
		ArrayList<WimmEntity> entityList;
		
		entityList = (ArrayList<WimmEntity>) dataAccessObject.getEntityList();
		assertNotNull(entityList);
		assertFalse(entityList.isEmpty());
		
		for (WimmEntity wimmEntity : entityList) {
			Log.e(TAG, wimmEntity.toString());
		}
	}
	
	private void initDAO(){
		dataAccessObject = new SqliteWimmDAO(getContext());
		
		entity = new StubEntity();
		
		entity.setName("potato");
		entity.setDescription("just a potato");
		entity.setAmount(15.56);
		entity.setPaymentType(PaymentType.PAY);
		entity.setDate(System.currentTimeMillis());
	}
	
	private void freeDAO(){
		dataAccessObject = null;
	}
}
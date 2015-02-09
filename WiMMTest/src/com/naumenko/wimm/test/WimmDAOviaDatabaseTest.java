package com.naumenko.wimm.test;

import java.util.ArrayList;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.naumenko.wimm.dao.SqliteWimmDAO;
import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.PaymentList;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class WimmDAOviaDatabaseTest extends AndroidTestCase{
	
	public static final int ALWAYS_EXISTING_UNNAMED_LIST = 1;
	public static final int NOT_EXISTING_LIST_ID = 2;
	
	private static final String TAG = WimmDAOviaDatabaseTest.class.getSimpleName();
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
		
		dataAccessObject.clear();
		
		freeDAO();
	}
	
	public void testPreConditions(){
		assertNotNull(dataAccessObject);
		assertNotNull(entity);
	}
	
	public void testAddEntity(){
		
		assertTrue(dataAccessObject.addEntity(entity) > 0);
		
	}
	
	public void testGetEntity(){
		
		long insertId = dataAccessObject.addEntity(entity);
		
		entity.setId(insertId);
		
		WimmEntity insertedBeforeEntity = dataAccessObject.getEntity(insertId);
		
		assertTrue(insertedBeforeEntity.isValid());
		assertEquals(entity, insertedBeforeEntity);
		
	}
	
	public void testUpdateEntity(){
		
		WimmEntity updatedEntity = new PaymentWimmEntity();
		
		long id = dataAccessObject.addEntity(entity);
		
		updatedEntity.setName("cucamber");
		
		updatedEntity.setId(id);
		
		updatedEntity.setDescription("just a potato");
		updatedEntity.setAmount(15.56);
		updatedEntity.setPaymentType(PaymentType.PAY);
		updatedEntity.setDate(System.currentTimeMillis());
		
		assertTrue(dataAccessObject.updateEntity(updatedEntity));
		
		WimmEntity updatedFromDatabase = dataAccessObject.getEntity(updatedEntity.getId());
		
		assertEquals(updatedEntity, updatedFromDatabase);
		
		entity.setId(id);
		
		assertNotSame(entity, updatedEntity);
		assertNotSame(entity, updatedFromDatabase);
	}
	
	public void testDeleteEntity(){
		
		long entityId = dataAccessObject.addEntity(entity);
		
		assertTrue(dataAccessObject.deleteEntity((entityId)));
	}
	
	public void testClear(){
		
		dataAccessObject.addEntity(entity);
		dataAccessObject.addEntity(entity);
		dataAccessObject.addEntity(entity);
		
		int deletedCount = dataAccessObject.clear();
		
		assertTrue(deletedCount > 2);
	}
	
	public void testGetEntityList(){
		
		dataAccessObject.addEntity(entity);
		
		ArrayList<WimmEntity> entityList;
		
		entityList = (ArrayList<WimmEntity>) dataAccessObject.getEntityList();
		assertNotNull(entityList);
		assertFalse(entityList.isEmpty());
		
	}
	
	public void testGetEntityListByListId(){
		
		dataAccessObject.addEntity(entity);
		
		ArrayList<WimmEntity> entityList;
		
		entityList = (ArrayList<WimmEntity>) dataAccessObject.getEntityList(ALWAYS_EXISTING_UNNAMED_LIST);
		assertNotNull(entityList);
		assertFalse(entityList.isEmpty());
		assertTrue(entityList.size() == 1);
	}
	
	public void testGetPaymentLists(){
		
		dataAccessObject.addEntity(entity);
		entity.setName("another");
		dataAccessObject.addEntity(entity);
		entity.setListId(ALWAYS_EXISTING_UNNAMED_LIST);
		dataAccessObject.addEntity(entity);
		
		
		
		entity.setListId(NOT_EXISTING_LIST_ID);
		dataAccessObject.addEntity(entity);
		
		ArrayList<PaymentList> paymentLists;
		
		paymentLists = dataAccessObject.getPaymentLists();
		assertNotNull(paymentLists);
		assertTrue(paymentLists.size() > 0);
		assertTrue(paymentLists.size() == 1);
		
		PaymentList unnamedList = paymentLists.get(0);
		
		Log.e(TAG, ""+unnamedList);
		
		assertNotNull(unnamedList);
		assertTrue(unnamedList.getId() == ALWAYS_EXISTING_UNNAMED_LIST);
		assertNotNull(unnamedList.getName());
		
		ArrayList<WimmEntity> entities = new ArrayList<WimmEntity>(unnamedList.getEntities());
		
		assertNotNull(entities);
		assertTrue(entities.size() > 0);
		assertTrue(entities.size() == 3);
		
		WimmEntity firstInserted = entities.get(0);
		assertTrue(firstInserted.getListId() == unnamedList.getId());
		assertEquals(firstInserted.getName(), "potato");
		
		WimmEntity secondInserted = entities.get(1);
		assertTrue(secondInserted.getListId() == unnamedList.getId());
		assertEquals(secondInserted.getName(), "another");
		
		WimmEntity thirdInserted = entities.get(1);
		assertTrue(thirdInserted.getListId() == unnamedList.getId());
		assertEquals(thirdInserted.getName(), "another");
		
	}
	
	public void testGetPaymentList(){
		Assert.fail();
	}
	
	public void testDeletePaymentList(){
		Assert.fail();
	}
	
	public void testUpdatePaymentList(){
		Assert.fail();
	}
	
	public void testAddPaymentList(){
		Assert.fail();
	}
	
	public void testAddPaymentToList(){
		Assert.fail();
	}
	
	public void testMovePaymentToList(){
		Assert.fail();
	}
	
	private void initDAO(){
		dataAccessObject = new SqliteWimmDAO(getContext());
		
		entity = new PaymentWimmEntity();
		
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
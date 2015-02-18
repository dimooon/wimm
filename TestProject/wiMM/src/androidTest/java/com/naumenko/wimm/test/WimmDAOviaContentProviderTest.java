package com.naumenko.wimm.test;

import java.util.ArrayList;

import junit.framework.Assert;
import android.test.AndroidTestCase;
import android.util.Log;

import com.naumenko.wimm.dao.ContentProviderWimmDAO;
import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.PaymentList;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.Payment;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class WimmDAOviaContentProviderTest extends AndroidTestCase{

	private static final String TAG = WimmDAOviaContentProviderTest.class.getSimpleName();
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
		
		int deletedCount = dataAccessObject.clear();
		
		Log.e(TAG, "deletedCount: "+deletedCount);
		
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
		
		WimmEntity insertedBeforeEntity = dataAccessObject.getEntity(entity.getId());
		
		assertTrue(insertedBeforeEntity.isValid());
		assertEquals(entity, insertedBeforeEntity);
		
	}
	
	public void testUpdateEntity(){
		
		long id  = dataAccessObject.addEntity(entity);
		
		assertTrue(id > 0);
		
		entity.setId(id);
		
		assertTrue(dataAccessObject.updateEntity(entity));
		
	}
	
	public void testDeleteEntity(){
		
		long entityId = dataAccessObject.addEntity(entity);
		
		assertTrue(dataAccessObject.deleteEntity((entityId)));
	}
	
	public void testGetEntityList(){
		
		dataAccessObject.addEntity(entity);
		
		ArrayList<WimmEntity> entityList;
		
		entityList = (ArrayList<WimmEntity>) dataAccessObject.getEntityList();
		assertNotNull(entityList);
		assertFalse(entityList.isEmpty());
		
		for (WimmEntity wimmEntity : entityList) {
			Log.e("TAG", ""+wimmEntity);
		}
		
	}
	
	public void testGetPaymentLists(){
		
		dataAccessObject.addEntity(entity);
		entity.setName("another");
		dataAccessObject.addEntity(entity);
		entity.setListId(WimmDAOviaDatabaseTest.ALWAYS_EXISTING_UNNAMED_LIST);
		dataAccessObject.addEntity(entity);
		
		entity.setListId(WimmDAOviaDatabaseTest.NOT_EXISTING_LIST_ID);
		dataAccessObject.addEntity(entity);
		
		ArrayList<PaymentList> paymentLists;
		
		paymentLists = dataAccessObject.getPaymentLists();
		assertNotNull(paymentLists);
		assertTrue(paymentLists.size() > 0);
		assertTrue(paymentLists.size() == 1);
		
		PaymentList unnamedList = paymentLists.get(0);
		
		Log.e(TAG, "unnamedList: "+unnamedList);
		
		assertNotNull(unnamedList);
		assertTrue(unnamedList.getId() == WimmDAOviaDatabaseTest.ALWAYS_EXISTING_UNNAMED_LIST);
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
		PaymentList defaultPaymentList = dataAccessObject.getPaymentList(WimmDAOviaDatabaseTest.ALWAYS_EXISTING_UNNAMED_LIST);
		
		assertNotNull(defaultPaymentList);
		assertTrue(defaultPaymentList.getId() == WimmDAOviaDatabaseTest.ALWAYS_EXISTING_UNNAMED_LIST);
	}
	
	public void testDeletePaymentList(){
		Assert.fail();
	}
	
	public void testAddPaymentList(){
		Assert.fail();
	}
	
	private void initDAO(){
		dataAccessObject = new ContentProviderWimmDAO(getContext());
		
		entity = new Payment();
		
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

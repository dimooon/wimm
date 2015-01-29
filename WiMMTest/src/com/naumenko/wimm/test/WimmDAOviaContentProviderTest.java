package com.naumenko.wimm.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;
import android.util.Log;

import com.naumenko.wimm.dao.ContentProviderWimmDAO;
import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class WimmDAOviaContentProviderTest extends AndroidTestCase{

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
	
	private void initDAO(){
		dataAccessObject = new ContentProviderWimmDAO(getContext());
		
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

package com.naumenko.wimm.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;

import com.naumenko.wimm.dao.SqliteWimmDAO;
import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class WimmDAOviaDatabaseTest extends AndroidTestCase{
	
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
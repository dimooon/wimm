package com.naumenko.wimm.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;

import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.test.mock.StubDAO;
import com.naumenko.wimm.test.mock.StubEntity;

public class WimmDAOTest extends AndroidTestCase{
	
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
		ArrayList<WimmEntity> entityList;
		
		entityList = (ArrayList<WimmEntity>) dataAccessObject.getEntityList();
		assertNotNull(entityList);
		assertFalse(entityList.isEmpty());
	}
	
	private void initDAO(){
		dataAccessObject = new StubDAO();
		
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
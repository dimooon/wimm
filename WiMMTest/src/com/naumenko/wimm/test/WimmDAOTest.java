package com.naumenko.wimm.test;

import java.util.ArrayList;

import android.test.AndroidTestCase;

import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.test.mock.StubDAO;
import com.naumenko.wimm.test.mock.StubEntity;

public class WimmDAOTest extends AndroidTestCase{
	
	private WimmDAO dataAccessObject;
	
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
		
		WimmEntity entity = new StubEntity();
		
		assertTrue(dataAccessObject.addEntity(entity));
	}
	
	public void testUpdateEntity(){
		WimmEntity entity = new StubEntity();
		
		assertTrue(dataAccessObject.updateEntity(entity));
	}
	
	public void testDeleteEntity(){
		WimmEntity entity = new StubEntity();
		
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
		
		dataAccessObject.setName();
		dataAccessObject.setDescription();
		dataAccessObject.setAmount();
		dataAccessObject.setPaymentType();
		dataAccessObject.setDate();
	}
	
	private void freeDAO(){
		dataAccessObject = null;
	}
}
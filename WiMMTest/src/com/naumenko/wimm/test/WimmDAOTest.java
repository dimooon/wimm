package com.naumenko.wimm.test;

import com.naumenko.wimm.dao.WimmDAO;

import junit.framework.Assert;
import android.test.AndroidTestCase;

public class WimmDAOTest extends AndroidTestCase{
	
	private WimmDAO dataAccessObject;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testPreConditions(){
		
	}
	
	public void testAddEntity(WimmEntity entity){
		Assert.fail();
	}
	
	public void testUpdateEntity(WimmEntity entity){
		Assert.fail();
	}
	
	public void testDeleteEntity(WimmEntity entity){
		Assert.fail();
	}
	
	public void testGetEntityList(){
		Assert.fail();
	}
	
}

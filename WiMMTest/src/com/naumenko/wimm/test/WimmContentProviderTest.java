package com.naumenko.wimm.test;

import junit.framework.Assert;
import android.test.ProviderTestCase2;

import com.naumenko.wimm.dao.provider.WimmContentProvider;

public class WimmContentProviderTest extends ProviderTestCase2<WimmContentProvider>{

	public WimmContentProviderTest() {
		super(WimmContentProvider.class, WimmContentProvider.CONTRACT.authority);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testPreConditions(){
		Assert.fail();
	}
		
	public void testQuery() {
		Assert.fail();
	}
	
	public void testInsert() {
		Assert.fail();
	}
	
	public void testDelete() {
		Assert.fail();
	}

	public void testUpdate() {
		Assert.fail();
	}
	
	public void testGetType() {
		Assert.fail();
	}
	
}
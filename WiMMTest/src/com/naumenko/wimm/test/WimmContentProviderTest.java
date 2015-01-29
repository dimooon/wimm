package com.naumenko.wimm.test;

import android.test.ProviderTestCase2;

import com.naumenko.wimm.dao.provider.WimmContentProvider;

public class WimmContentProviderTest extends ProviderTestCase2<WimmContentProvider>{

	public WimmContentProviderTest(Class<WimmContentProvider> providerClass, String providerAuthority) {
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
		
	}
		
	public void testQuery() {

	}
	
	public void testInsert() {
	}
	
	public void testDelete() {
		
	}

	public void testUpdate() {
		
	}
	
	public void testGetType() {
		
	}
	
}
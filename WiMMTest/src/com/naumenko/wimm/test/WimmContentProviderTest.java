package com.naumenko.wimm.test;

import junit.framework.Assert;
import android.database.Cursor;
import android.net.Uri;
import android.test.IsolatedContext;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import com.naumenko.wimm.dao.provider.WimmContentProvider;

public class WimmContentProviderTest extends ProviderTestCase2<WimmContentProvider>{

	private IsolatedContext context;
	private MockContentResolver resolver;
	
	public WimmContentProviderTest() {
		super(WimmContentProvider.class, WimmContentProvider.CONTRACT.AUTHORITY);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		context = getMockContext();
		resolver = getMockContentResolver();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		context = null;
		resolver = null;
	}

	public void testPreConditions(){
		assertNotNull(context);
		assertNotNull(resolver);
	}
		
	public void testQuery() {
		Cursor cursor = resolver.query(WimmContentProvider.CONTRACT.CONTENT_URI, null, null, null, null);
		
		assertNotNull(cursor);
		assertTrue(cursor.getColumnCount() > 0);
	}
	
	public void testInsert() {
		Uri uri =resolver.insert(WimmContentProvider.CONTRACT.CONTENT_URI, null);
		assertNotNull(uri);
	}
	
	public void testDelete() {
		
		Uri uri =resolver.insert(WimmContentProvider.CONTRACT.CONTENT_URI, null);
		
		int count =resolver.delete(uri, null, null);
		assertTrue(count > 0);
	}

	public void testUpdate() {
		int count = resolver.update(WimmContentProvider.CONTRACT.CONTENT_URI, null, null, null);
		
		assertTrue(count > 0);
	}
	
	public void testGetType() {
		Assert.fail();
	}
	
}
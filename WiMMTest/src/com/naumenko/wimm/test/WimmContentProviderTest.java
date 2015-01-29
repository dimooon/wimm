package com.naumenko.wimm.test;

import android.database.Cursor;
import android.net.Uri;
import android.test.IsolatedContext;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;
import android.util.Log;

import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.dao.provider.WimmContentProvider;

public class WimmContentProviderTest extends ProviderTestCase2<WimmContentProvider>{

	private static final String TAG = WimmContentProviderTest.class.getSimpleName();
	private IsolatedContext context;
	private MockContentResolver resolver;
	private PaymentWimmEntity entity;
	
	public WimmContentProviderTest() {
		super(WimmContentProvider.class, WimmContentProvider.CONTRACT.AUTHORITY);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		context = getMockContext();
		resolver = getMockContentResolver();
		
		entity = new PaymentWimmEntity();
		
		entity.setName("potato");
		entity.setDescription("just a potato");
		entity.setAmount(15.56);
		entity.setPaymentType(PaymentType.PAY);
		entity.setDate(System.currentTimeMillis());
		
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		context = null;
		resolver = null;
		entity = null;
	}

	public void testPreConditions(){
		assertNotNull(context);
		assertNotNull(resolver);
		assertNotNull(entity);
		assertNotNull(entity.getConvertedContentValues());
	}
		
	public void testQuery() {
		
		Uri uri = resolver.insert(WimmContentProvider.CONTRACT.CONTENT_URI, entity.getConvertedContentValues());
		
		assertNotNull(uri);
		
		long id = Long.valueOf(uri.getLastPathSegment());
		
		assertTrue(id > 0);
		
		Cursor cursor = resolver.query(WimmContentProvider.CONTRACT.CONTENT_URI, null, null, null, null);
		
		assertNotNull(cursor);
		assertTrue(cursor.getColumnCount() > 0);
		cursor.moveToFirst();
		assertTrue(!cursor.isAfterLast());
		
		printCursorData(cursor);
	}
	
	public void testInsert() {
		Uri uri =resolver.insert(WimmContentProvider.CONTRACT.CONTENT_URI, entity.getConvertedContentValues());
		assertNotNull(uri);
	}
	
	public void testDelete() {
		
		Uri uri =resolver.insert(WimmContentProvider.CONTRACT.CONTENT_URI, entity.getConvertedContentValues());
		
		int id = Integer.valueOf(uri.getLastPathSegment());
		
		assertTrue(id > 0);
		
		int count =resolver.delete(WimmContentProvider.CONTRACT.CONTENT_URI, uri.getLastPathSegment(), null);
		assertTrue(count > 0);
	}

	public void testUpdate() {
		
		Uri uri = resolver.insert(WimmContentProvider.CONTRACT.CONTENT_URI, entity.getConvertedContentValues());
		
		assertTrue(uri != null);		
		
		int id = Integer.valueOf(uri.getLastPathSegment());
		
		assertTrue(id > 0);
		
		WimmEntity updateEntity = new PaymentWimmEntity();
		
		updateEntity.setId(id);
		updateEntity.setName("rope");
		updateEntity.setDescription(entity.getDescription());
		updateEntity.setAmount(entity.getAmount());
		updateEntity.setPaymentType(entity.getType());
		updateEntity.setDate(entity.getTimeInMs());
		
		int count = resolver.update(
				WimmContentProvider.CONTRACT.CONTENT_URI, 
				updateEntity.getConvertedContentValues(), 
				String.valueOf(updateEntity.getId()), 
				null);
		
		assertTrue(count > 0);
		entity.setId(id);
		
		assertNotSame(entity, updateEntity);
	}
	
	public void testGetType() {
 		String type = resolver.getType(WimmContentProvider.CONTRACT.CONTENT_URI);
 		assertNotNull(type);
	}
	
	private void printCursorData(Cursor cursor){
		
		Log.e(TAG, "try to print cursor");
		
		cursor.moveToFirst();
	    
	    while (!cursor.isAfterLast()) {
	      Log.e(TAG, new PaymentWimmEntity(cursor).toString());
	      cursor.moveToNext();
	    }

	    cursor.close();
		
	}
	
}
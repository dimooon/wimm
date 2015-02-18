package com.naumenko.wimm.test;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.naumenko.wimm.dao.db.EntityTable;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.Payment;

public class WimmPaymentEntityTest extends AndroidTestCase{

	private Payment entity;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		entity = new Payment();
		entity.setName("testName");
		entity.setDescription("no desription");
		entity.setAmount(350.53);;
		entity.setPaymentType(PaymentType.PAY);
		entity.setDate(System.currentTimeMillis());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		entity = null;
	}
	
	public void testPreconditions(){
		assertNotNull(entity);
	}
	
	public void testConverToContentValues(){
		
		ContentValues entityInContentValues = entity.getConvertedContentValues();
		assertNotNull(entityInContentValues);
		
		String name = entityInContentValues.getAsString(EntityTable.ENTITY_CONTRACT.COLUMN_NAME.getContractKey());
		String description = entityInContentValues.getAsString(EntityTable.ENTITY_CONTRACT.COLUMN_DESCRIPTION.getContractKey());
		double amount =  entityInContentValues.getAsDouble(EntityTable.ENTITY_CONTRACT.COLUMN_AMOUNT.getContractKey());
		PaymentType paymentType = PaymentType.get(entityInContentValues.getAsString(EntityTable.ENTITY_CONTRACT.COLUMN_PAYMENT_TYPE.getContractKey()));
		long dateInMs = entityInContentValues.getAsLong(EntityTable.ENTITY_CONTRACT.COLUMN_DATE.getContractKey());
		
		assertTrue(entity.getName().equals(name));
		assertTrue(entity.getDescription().equals(description));
		assertTrue(entity.getAmount() == amount);
		assertTrue(entity.getType() == paymentType);
		assertTrue(entity.getTimeInMs() == dateInMs);
		
	}
	
}

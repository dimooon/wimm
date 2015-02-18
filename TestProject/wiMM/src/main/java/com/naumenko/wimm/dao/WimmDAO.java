package com.naumenko.wimm.dao;

import java.util.ArrayList;
import java.util.List;

import com.naumenko.wimm.dao.entity.PaymentList;
import com.naumenko.wimm.dao.entity.WimmEntity;

	
public interface WimmDAO {
	public long addEntity(WimmEntity entity);
	public boolean updateEntity(WimmEntity entity);
	public boolean deleteEntity(long id);
	public WimmEntity getEntity(long id);
	public List<WimmEntity> getEntityList();
	public List<WimmEntity> getEntityList(long listId);
	public int clear();
	
	public ArrayList<PaymentList> getPaymentLists();
	public PaymentList getPaymentList(long list_id);
	public long addPaymentList(PaymentList list);
	public boolean deleteList(long id);
}

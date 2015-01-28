package com.naumenko.wimm.dao;

import com.naumenko.wimm.dao.entity.WimmEntity;

public interface WimmDAO {
	
	public void addEntity(WimmEntity entity);
	public void updateEntity(WimmEntity entity);
	public void deleteEntity(WimmEntity entity);
	public void getEntityList();
	
}

package com.naumenko.wimm.dao;

import java.util.List;

import com.naumenko.wimm.dao.entity.WimmEntity;

	
public interface WimmDAO {
	public long addEntity(WimmEntity entity);
	public boolean updateEntity(WimmEntity entity);
	public boolean deleteEntity(long id);
	public WimmEntity getEntity(long id);
	public List<WimmEntity> getEntityList();
	public int clear();
}

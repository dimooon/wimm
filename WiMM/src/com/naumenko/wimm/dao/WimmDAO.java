package com.naumenko.wimm.dao;

import java.util.List;

import com.naumenko.wimm.dao.entity.WimmEntity;

public interface WimmDAO {
	
	public boolean addEntity(WimmEntity entity);
	public boolean updateEntity(WimmEntity entity);
	public boolean deleteEntity(WimmEntity entity);
	public List<WimmEntity> getEntityList();
	
}

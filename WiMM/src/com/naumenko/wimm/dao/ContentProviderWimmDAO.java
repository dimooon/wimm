package com.naumenko.wimm.dao;

import java.util.List;

import com.naumenko.wimm.dao.entity.WimmEntity;

public class ContentProviderWimmDAO implements WimmDAO{
	
	public ContentProviderWimmDAO() {
	
	}
	
	@Override
	public long addEntity(WimmEntity entity) {
		return 0;
	}

	@Override
	public boolean updateEntity(WimmEntity entity) {
		return false;
	}

	@Override
	public boolean deleteEntity(long id) {
		return false;
	}

	@Override
	public WimmEntity getEntity(long id) {
		return null;
	}

	@Override
	public List<WimmEntity> getEntityList() {
		return null;
	}

	@Override
	public int clear() {
		return 0;
	}

}

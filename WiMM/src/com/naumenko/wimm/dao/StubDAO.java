package com.naumenko.wimm.dao;

import java.util.List;

import com.naumenko.wimm.dao.entity.WimmEntity;

public class StubDAO implements WimmDAO{

	@Override
	public boolean addEntity(WimmEntity entity) {
		return false;
	}

	@Override
	public boolean updateEntity(WimmEntity entity) {
		return false;
	}

	@Override
	public boolean deleteEntity(WimmEntity entity) {
		return false;
	}

	@Override
	public List<WimmEntity> getEntityList() {
		return null;
	}


}

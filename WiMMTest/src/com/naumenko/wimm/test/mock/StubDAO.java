package com.naumenko.wimm.test.mock;

import java.util.ArrayList;
import java.util.List;

import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class StubDAO implements WimmDAO{

	@Override
	public boolean addEntity(WimmEntity entity) {
		return true;
	}

	@Override
	public boolean updateEntity(WimmEntity entity) {
		return true;
	}

	@Override
	public boolean deleteEntity(WimmEntity entity) {
		return true;
	}

	@Override
	public List<WimmEntity> getEntityList() {
		
		List<WimmEntity> entities = new ArrayList<WimmEntity>();
		entities.add(new StubEntity());
		
		return entities;
	}
}

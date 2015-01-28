package com.naumenko.wimm.test.mock;

import java.util.ArrayList;
import java.util.List;

import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class StubDAO implements WimmDAO{

	@Override
	public long addEntity(WimmEntity entity) {
		return 1;
	}

	@Override
	public boolean updateEntity(WimmEntity entity) {
		return true;
	}

	@Override
	public boolean deleteEntity(long id) {
		return true;
	}

	@Override
	public List<WimmEntity> getEntityList() {
		
		List<WimmEntity> entities = new ArrayList<WimmEntity>();
		entities.add(new StubEntity());
		
		return entities;
	}

	@Override
	public WimmEntity getEntity(long id) {
		return new StubEntity();
	}
}

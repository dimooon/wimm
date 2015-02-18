package com.naumenko.wimm.dao.entity.export;

import java.util.List;

import com.naumenko.wimm.dao.entity.WimmEntity;

public interface Export {
	public boolean export(List<WimmEntity> entities);
}

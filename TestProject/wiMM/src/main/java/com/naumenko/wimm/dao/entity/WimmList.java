package com.naumenko.wimm.dao.entity;

import com.naumenko.wimm.dao.entity.WimmEntity;

import java.util.List;

public interface WimmList extends CursorParselable, ContentValuesConvertable, XmlConvertable{

    public long getId();
    public void setId(long id);
    public String getName();
    public void setName(String name);

    public List<WimmEntity> getEntities();
    public void setEntities(List<WimmEntity> entities);

}
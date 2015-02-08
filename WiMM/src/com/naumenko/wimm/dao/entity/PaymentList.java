package com.naumenko.wimm.dao.entity;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class PaymentList implements WimmList{

    private long id;
    private String name;
    private ArrayList<WimmEntity> entities = new ArrayList<WimmEntity>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<WimmEntity> getEntities() {
        return this.entities;
    }

    @Override
    public void setEntities(List<WimmEntity> entities) {
        this.entities.clear();
        this.entities.addAll(entities);
    }

    @Override
    public ContentValues getConvertedContentValues() {
        return null;
    }

    @Override
    public void cursorToWimmEntity(Cursor cursor) {

    }

    @Override
    public boolean entityParsed() {
        return false;
    }

    @Override
    public String getConvertedXml() {
        return null;
    }
}
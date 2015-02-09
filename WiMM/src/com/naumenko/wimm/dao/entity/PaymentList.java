package com.naumenko.wimm.dao.entity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;

import com.naumenko.wimm.dao.db.ListTable.LIST_CONTRACT;

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
    	
    	setId(cursor.isNull(0) ? -1 : cursor.getLong(LIST_CONTRACT.COLUMN_ID.getIndex()));
		setName(cursor.isNull(1) ? null : cursor.getString(LIST_CONTRACT.COLUMN_NAME.getIndex()));
		
    }

    @Override
    public boolean entityParsed() {
        return false;
    }

    @Override
    public String getConvertedXml() {
        return null;
    }

	@Override
	public String toString() {
		return "PaymentList [id=" + id + ", name=" + name + ", entities=" +"\n"+ entities+"\n" + "]";
	}
    
}
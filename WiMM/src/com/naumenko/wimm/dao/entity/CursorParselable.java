package com.naumenko.wimm.dao.entity;

import android.database.Cursor;

public interface CursorParselable {
	
	public void cursorToWimmEntity(Cursor cursor);
	public boolean entityParsed();
}

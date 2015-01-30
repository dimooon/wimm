package com.naumenko.wimm;

import com.naumenko.wimm.dao.entity.WimmEntity;

import android.app.Application;

public class WimmApplication extends Application {
	
	private static WimmEntity selectedEntity;
	
	public static void setSelectedEntity(WimmEntity selected){
		WimmApplication.selectedEntity = selected;
	};
	
	public static WimmEntity getSelectedEntity(){
		return WimmApplication.selectedEntity;
	}
	
}

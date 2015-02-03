package com.naumenko.wimm;

import android.app.Application;
import android.content.Context;

import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.dao.entity.export.Export;
import com.naumenko.wimm.dao.entity.export.ExportManager;
import com.naumenko.wimm.fragment.WimmFragmentManager;

public class WimmApplication extends Application {
	
	private static Context context;
	private static WimmEntity selectedEntity;
	private static WimmFragmentManager manager;
	private static WimmDAO dao;
	private static ExportManager exportManager;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		context = getApplicationContext();
		exportManager = new ExportManager();
	}
	
	public static void setSelectedEntity(WimmEntity selected){
		WimmApplication.selectedEntity = selected;
	};
	
	public static WimmEntity getSelectedEntity(){
		return WimmApplication.selectedEntity;
	}
	
	public static Context getContext(){
		return WimmApplication.context;
	}
	
	public static void setFragmentManager(WimmFragmentManager manager){
		WimmApplication.manager = manager;
	}
	
	public static WimmFragmentManager getFragmentManager(){
		return WimmApplication.manager;
	}

	public static void setDAO(WimmDAO dao) {
		WimmApplication.dao = dao;
	}
	
	public static WimmDAO getDAO(){
		return WimmApplication.dao;
	}
	
	public static Export getExport(int which){		
		return exportManager.getExport(which);
	}
	
}
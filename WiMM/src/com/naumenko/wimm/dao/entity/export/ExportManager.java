package com.naumenko.wimm.dao.entity.export;

import java.util.ArrayList;
import java.util.List;

public class ExportManager {
	
	static List<Export> exportsList = new ArrayList<Export>();
	
	public static final int XML = 0;
	
	public ExportManager() {
		super();
	}
	
	static {
		exportsList.add(new XMLExport());
	}
	
	public Export getExport(int which){
		
		Export export = (which < exportsList.size()) ? exportsList.get(which) : null; 
		
		return export;
	}
	
}

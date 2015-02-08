package com.naumenko.planner.components;

import java.util.ArrayList;

public interface DrillPannelCallback {
	
	void notifyOk(ArrayList<Drill> drills,int maxProgress);
	void notifyClose();
}

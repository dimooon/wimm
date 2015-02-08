package com.naumenko.planner.database;

public interface LoaderObserver {

	void notifyStart();
	void notifyFinish();
	void notifyError(Exception ex);
	void notifyUnknownMetod(String methodName);
	void notifyImgLoadStart();
	void notifyImgLoadFinish();
}

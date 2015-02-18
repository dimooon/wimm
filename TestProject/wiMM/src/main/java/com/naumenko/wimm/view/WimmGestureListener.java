package com.naumenko.wimm.view;

import android.view.View;

public interface WimmGestureListener {
	public void onTouch(View view);
	public void onSwipeLeft(View view);
	public void onSwipeRight(View view);
}

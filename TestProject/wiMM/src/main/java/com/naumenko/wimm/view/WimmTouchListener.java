package com.naumenko.wimm.view;

import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class WimmTouchListener implements OnTouchListener	{
	
	private Activity activity;
	private WimmGestureListener listener;
	
	private int action_down_x = 0;
    private int action_up_x = 0;
    private int difference = 0;
	
	public WimmTouchListener(Activity activity,WimmGestureListener listener) {
		this.listener = listener;
		this.activity = activity;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		
		int action = event.getAction();

		boolean processed = (difference > -1 && difference <=3);
		
		Log.e("TAG", "processed: "+processed+ " differences: "+difference);
		
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			action_down_x = (int) event.getX();
			Log.d("action", "ACTION_DOWN - ");
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d("action", "ACTION_MOVE - ");
			action_up_x = (int) event.getX();
			difference = action_down_x - action_up_x;
			calcuateDifference(v);
			break;
		case MotionEvent.ACTION_UP:
			Log.d("action", "ACTION_UP - ");
			
			if(processed){
				listener.onTouch(v);
			}
			
			action_down_x = 0;
			action_up_x = 0;
			difference = 0;
			
			break;
		}
		return true;
	}
	
	private void calcuateDifference(final View view) {
		
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (difference > 75) {
					listener.onSwipeRight(view);
				}
				if (difference < -75) {
					listener.onSwipeLeft(view);
				}
			}
		});
		
	}
}
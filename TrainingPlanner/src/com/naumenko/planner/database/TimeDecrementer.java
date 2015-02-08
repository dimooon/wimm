package com.naumenko.planner.database;

import java.util.Stack;

import android.app.Activity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeDecrementer {

	private Stack<Long> timeStack;
	
	private long currentTime;
	private long mainTime;

	private boolean isRunning = false;

	private LinearLayout timePannel;
	private TextView timeTextView;
	
	private Activity activity;
	
	public TimeDecrementer(LinearLayout linearLayout, long ms, final Activity context) {
		timeStack = new Stack<Long>();
		
		timePannel = linearLayout;
		
		timeStack.push(ms);
		
		activity = context;
	}

	public void addTimer(long ms) {
		if (isRunning) {
			timeStack.push(currentTime * 1000);
			mainTime = System.currentTimeMillis() + ms;
		} else {
			timeStack.push(ms);
		}
	}

	public synchronized int removeTimer() {
		if (timeStack.size() == 0)
			return 0;

		timePannel.post(new Thread() {
			public void run() {
				timePannel.removeViewAt(timeStack.size());
			}
		});

		if (isRunning) {
			mainTime = System.currentTimeMillis() + timeStack.pop();
		} else {
			timeStack.pop();
		}

		return timeStack.size() + 1;
	}

	private String str;

	public void start() {
		if (isRunning == false) {
			isRunning = true;
			mainTime = System.currentTimeMillis() + timeStack.pop();

			new Thread() {
				public void run() {
					while (isRunning) {

						try {
							sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}

						currentTime = (mainTime - System.currentTimeMillis()) / 1000;
						if (currentTime <= 0) {
							int num = removeTimer();
							
							if (num == 0) {
								isRunning = false;
								
							}
						} else {
							str = currentTime / 10 +""+ currentTime % 10;
							timePannel.post(new Thread() {
								public void run() {
									timeTextView.setText(str);
								}
							});
						}
					}
				}
			}.start();
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void stop() {
		timeStack.push(currentTime);
		isRunning = false;
	}

}

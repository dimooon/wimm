package com.naumenko.planner.components;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.naumenko.planner.R;
import com.naumenko.planner.TrainingPlannerActivity;

public class TraningPannel extends LinearLayout {

	private HorizontialListView drillsListView;
	private DrillGridAdapter adpter;

	private Activity activity;
	private int max;
	private int id;

	public TraningPannel(Activity activity, final int id) {
		super(activity);
		this.activity = activity;
		this.id = id;
		inflate(activity, R.layout.training_plan, this);

		drillsListView = (HorizontialListView) findViewById(R.id.horizontalView);

		adpter = new DrillGridAdapter(activity, new ArrayList<Drill>());

		drillsListView.setAdapter(adpter);

		drillsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Toast.makeText(TraningPannel.this.activity,
						"tap = " + position, Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.imagePanImg).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						((TrainingPlannerActivity) TraningPannel.this.activity)
								.setId(TraningPannel.this.id);
					}
				});

	}

	public int getId() {
		return id;
	}

	public void setTrainingPlan(ArrayList<Drill> drills, int max) {
		adpter.addDrills(drills);
		this.max = max;

		((ProgressBar) findViewById(R.id.progressBar1)).setProgress(0);
		((TextView) findViewById(R.id.tBarMax)).setText(max + " min");

	}

	private Handler timerHandler;

	private int maxS = 100;
	private float current = 0;
	
	public void start() {
		
		final long currentTime = System.currentTimeMillis();
		
		timerHandler = new Handler();
		timerHandler.post(new Runnable() {

			@Override
			public void run() {
				while ((current/max * 60 * 1000) < maxS) {
					current += System.currentTimeMillis()/1000+currentTime/1000;
					Log.e("time", "current = "+(current/max * 60 * 1000)+" max = "+max+" l = "+currentTime/1000+ " p = "+ (System.currentTimeMillis()/1000-currentTime/1000));
					((ProgressBar) findViewById(R.id.progressBar1)).setProgress(((int)current/max * 60 * 1000));
				}
			}
		});
	}

	public void stop() {
		current = 100;
	}

}
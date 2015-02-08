package com.naumenko.planner.components;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.naumenko.planner.R;

public class DrillPannel extends LinearLayout {

	private ArrayList<Drill> drills;

	private GridView drillsGrid;
	private ListView drillsCheked;

	private DrillGridAdapter drillAdapter;
	private DrillGridAdapter drillChekedAdapter;
	private LinearLayout timePannel;

	private Activity activity;

	private DrillPannelCallback callBack;

	public DrillPannel(Activity activity,DrillPannelCallback callback, ArrayList<Drill> drills) {
		super(activity);
		this.drills = drills;
		this.activity = activity;
		this.callBack = callback;
		create();

	}

	private void create() {
		initPannel();
		createDrillPannel();
		setButtonListeners();
	}

	private void initPannel() {
		inflate(getContext(), R.layout.drill_grid, this);
	}

	private void createDrillPannel() {
		createDrillGrid();
		createDrillChekedList();
		createTimeBar();
	}

	private void createDrillGrid() {
		drillsGrid = (GridView) findViewById(R.id.drillGrid);
		drillAdapter = new DrillGridAdapter(activity, drills);
		prepareDrillGridListener();
		drillsGrid.setAdapter(drillAdapter);
	}

	private void createDrillChekedList() {
		drillsCheked = (ListView) findViewById(R.id.drillGridChekedList);
		drillChekedAdapter = new DrillGridAdapter(activity,
				new ArrayList<Drill>());
		prepareDrillChekedListListener();
		drillsCheked.setAdapter(drillChekedAdapter);
	}

	private void prepareDrillGridListener() {
		drillsGrid.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Drill selectedDrill = drillAdapter.getItem(position);

				if (timeProgress.getProgress() + Integer.valueOf(selectedDrill.getTime()) <= maxProgress)
					drillChekedAdapter.addDrill(selectedDrill);

				computeProgress(selectedDrill);
			}
		});
	}

	private void prepareDrillChekedListListener() {
		drillsCheked.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				drillChekedAdapter.removeDrill(position);
				dectProgress(drillAdapter.getItem(position));
			}
		});
	}

	private void computeProgress(Drill drill) {
		int currentProgress = timeProgress.getProgress();

		int newProgress = (int) (100 * Integer.valueOf(drill.getTime()) / maxProgress);

		if (newProgress + currentProgress > maxProgress) {
			newProgress = 100;
		} else {
			newProgress += currentProgress;
		}
		timeProgress.setProgress(newProgress);
	}

	private void dectProgress(Drill drill) {
		int newProg = timeProgress.getProgress();
		int a = (int) (newProg - Integer.valueOf(drill.getTime()));
		timeProgress.setProgress(a >= 0 ? a : 0);
	}

	ProgressBar timeProgress;
	TextView timeMax;

	private void createTimeBar() {
		timePannel = (LinearLayout) findViewById(R.id.timePannel);

		timeProgress = (ProgressBar) findViewById(R.id.timeProgress);
		timeProgress.setProgress(0);
		timeMax = (TextView) findViewById(R.id.timeBarMax);

		createTimeBarListener();
	}

	private void createTimeBarListener() {
		timePannel.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {

				createDialog();

			}
		});
	}

	private void createDialog() {
		final Dialog timeDialog = new Dialog(getContext());

		final LinearLayout dialogLayout = new LinearLayout(getContext());

		dialogLayout.inflate(getContext(),
				R.layout.drill_grid_time_pannel_dialog, dialogLayout);

		((Button) dialogLayout.findViewById(R.id.time_dialog_ok))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {

						timeMax.setText(((TextView) dialogLayout
								.findViewById(R.id.time_value)).getText()
								+ " min");
						String s = (((TextView) dialogLayout
								.findViewById(R.id.time_value)).getText())
								.toString();
						Log.e("S", "s = " + s);
						maxProgress = new Integer(s);

						timeDialog.dismiss();
					}
				});

		((Button) dialogLayout.findViewById(R.id.time_dialog_cancel))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						timeDialog.dismiss();
					}
				});
		timeDialog.setContentView(dialogLayout);
		timeDialog.show();
	}

	private int maxProgress = 100;

	private void setButtonListeners() {
		((Button) findViewById(R.id.gridOk))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						ok();
					}
				});

		((Button) findViewById(R.id.gridClear))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						clear();
					}
				});

		((Button) findViewById(R.id.gridCancel))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						cancel();
					}
				});
	}

	private void ok() {
		callBack.notifyOk(drillChekedAdapter.getDrills(),maxProgress);
	}

	private void cancel() {
		clear();
		callBack.notifyClose();

	}

	private void clear() {
		drillChekedAdapter.clear();
		timeProgress.setProgress(0);
	}

}
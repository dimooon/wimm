package com.naumenko.planner;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.naumenko.planner.components.Drill;
import com.naumenko.planner.components.DrillPannel;
import com.naumenko.planner.components.DrillPannelCallback;
import com.naumenko.planner.components.DrillsListAdapter;
import com.naumenko.planner.components.TraningPannel;
import com.naumenko.planner.database.DatabaseManager;
import com.naumenko.planner.database.Loader;
import com.naumenko.planner.database.LoaderObserver;

public class TrainingPlannerActivity extends Activity implements
		DrillPannelCallback {

	private Dialog dialog;
	private TraningPannel traningPannel;
	private ArrayList<Drill> drills;

	private ArrayList<Drill> selectedDrills = new ArrayList<Drill>();
	
	private ArrayList<TraningPannel> plansList = new ArrayList<TraningPannel>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initBase();
	}

	private void initBase() {
		final DatabaseManager db = DatabaseManager.getInstance();
		db.init(getApplicationContext());
		Loader l = new Loader(getApplicationContext());
		if (db.isEmpty()) {

			l.setObserver(new LoaderObserver() {

				@Override
				public void notifyUnknownMetod(String methodName) {
					Log.e("unMeth", "" + methodName);
				}

				@Override
				public void notifyStart() {
					Log.e("unMeth", "start");
				}

				@Override
				public void notifyImgLoadStart() {

				}

				@Override
				public void notifyImgLoadFinish() {

				}

				@Override
				public void notifyFinish() {

					Log.e("unMeth", "finish");
				}

				@Override
				public void notifyError(Exception ex) {
					Log.e("unMeth", "error");
				}
			});
			l.start();
		}

		if (l != null) {
			try {
				Log.i("WAIT", "WAITING FOR JOIN");
				l.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		drills = new ArrayList<Drill>();
		drills.addAll(db.getDrillList());
		createDrillListView();
		findViewById(R.id.launch).setVisibility(View.GONE);
		Log.e("Drills", "D = " + drills + " sel " + selectedDrills);
	}

	public void onList(View v) {
		findViewById(R.id.training_screen).setVisibility(View.GONE);
		findViewById(R.id.list_screen).setVisibility(View.VISIBLE);
	}

	public void onPlan(View v) {
		findViewById(R.id.training_screen).setVisibility(View.VISIBLE);
		findViewById(R.id.list_screen).setVisibility(View.GONE);
	}

	public void onCreateNewPlan(View v) {
		traningPannel = new TraningPannel(TrainingPlannerActivity.this,
				plansList.size());

		((LinearLayout) findViewById(R.id.insert)).addView(traningPannel);
		
		plansList.add(traningPannel);
		
		currentPlanId = traningPannel.getId();
		
		dialog = new Dialog(TrainingPlannerActivity.this, R.style.noTitleDialog);
		dialog.setContentView(new DrillPannel(TrainingPlannerActivity.this,
				TrainingPlannerActivity.this, drills));
		dialog.show();
	}

	public void notifyOk(ArrayList<Drill> drills,int max) {
		selectedDrills = drills;
		traningPannel.setTrainingPlan(selectedDrills,max);
		dialog.dismiss();
		startPlan();
	}

	public void notifyClose() {
		dialog.dismiss();
	}

	ListView lv;

	public void createDrillListView() {

		lv = ((ListView) findViewById(R.id.list_of_drills));

		lv.setAdapter(new DrillsListAdapter(this, drills));

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showDescriptionDialog(drills.get(arg2), arg2);
			}
		});

	}

	private void showDescriptionDialog(final Drill current, final int id) {

		final Dialog dialog = new Dialog(this, R.style.noTitleDialog);

		dialog.setContentView(R.layout.drill_description_screen);

		((TextView) dialog.findViewById(R.id.drill_desc_name)).setText(current
				.getName());
		((TextView) dialog.findViewById(R.id.drill_desc_desc)).setText(current
				.getDescription());
		((TextView) dialog.findViewById(R.id.drill_desc_effect))
				.setText(current.getEffect());
		((TextView) dialog.findViewById(R.id.drill_desc_execution))
				.setText(current.getExecution());
		((TextView) dialog.findViewById(R.id.drill_desc_time)).setText(current
				.getTime());

		for (int i = 0; i < current.getPicturesList().size(); i++) {

			ImageView iv = new ImageView(this);

			iv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.FILL_PARENT));

			Resources resources = getResources();
			try {
				iv.setImageDrawable(Drawable.createFromStream(
						resources.getAssets().open(
								"photo_description/"
										+ current.getPicturesList().get(i)),
						null));
			} catch (IOException e) {
				e.printStackTrace();
			}

			((LinearLayout) dialog.findViewById(R.id.drill_description_img))
					.addView(iv);
		}

		((ImageButton) dialog.findViewById(R.id.edit_btn))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						final Dialog d = new Dialog(
								TrainingPlannerActivity.this);
						d.setContentView(R.layout.drill_grid_time_pannel_dialog);
						d.findViewById(R.id.time_dialog_ok).setOnClickListener(
								new OnClickListener() {

									@Override
									public void onClick(View v) {

										((TextView) dialog
												.findViewById(R.id.drill_desc_time)).setText(((TextView) (d
												.findViewById(R.id.time_value)))
												.getText().toString());

										DatabaseManager
												.getInstance()
												.addMyTimePix(
														current.getId(),
														((TextView) (d
																.findViewById(R.id.time_value)))
																.getText()
																.toString());

										((DrillsListAdapter) lv.getAdapter())
												.updateList(DatabaseManager
														.getInstance()
														.getDrillList());
										d.dismiss();
									}
								});
						d.show();
					}
				});

		dialog.show();
	}

	private int currentPlanId;

	public void setId(int cp) {
		currentPlanId = cp;
	}
	
	private void startPlan(){
		plansList.get(currentPlanId).start();
	}
	private void stopPlan(){
		plansList.get(currentPlanId).stop();
	}
	
}
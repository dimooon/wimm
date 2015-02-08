package com.naumenko.planner.components;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naumenko.planner.R;

public class DrillsListAdapter extends BaseAdapter{
	private ArrayList<Drill> drills;
	private Activity activity;
	private Drill currentDrill;
	private ViewHolder viewHolder;

	public DrillsListAdapter(Activity activity, ArrayList<Drill> drills) {
		this.activity = activity;
		this.drills = new ArrayList<Drill>();
		this.drills.addAll(drills);
	}
	
	public void addDrill(Drill drill){
		drills.add(drill);
		
		notifyDataSetChanged();
	}
	
	public void addDrills(ArrayList<Drill> drillsList){
		drills.addAll(drillsList);
		
		notifyDataSetChanged();
	}
	
	public void removeDrill(int position){
		drills.remove(position);
		
		notifyDataSetChanged();
	}
	
	public void updateList(ArrayList<Drill> drills){
		this.drills.clear();
		this.drills.addAll(drills);
		
		notifyDataSetChanged();
	}
	
	public ArrayList<Drill> getDrills(){
		return drills;
	}
	
	public void clear(){
		drills.clear();
		
		notifyDataSetChanged();
	}
	
	public int getCount() {
		return drills.size();
	}

	public Drill getItem(int index) {
		return drills.get(index);
	}

	public long getItemId(int index) {
		return index;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder = null;
		currentDrill = drills.get(position);

		if (convertView == null) {
			convertView = ((LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
					.inflate(R.layout.drills_list_item, null);
			viewHolder = new ViewHolder(convertView);

			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		setItemData();
		
		return convertView;

	}

	private void setItemData() {
		Resources resources = activity.getResources();
		try {
			viewHolder.getImage().setImageDrawable(Drawable.createFromStream(resources.getAssets().open("photo_description/"+currentDrill.getPicturesList().get(0)), null));
		} catch (IOException e) {
			e.printStackTrace();
		}
		viewHolder.getTextName().setText(currentDrill.getName());
		viewHolder.getTextDescription().setText(currentDrill.getDescription());
		viewHolder.getTextTime().setText(currentDrill.getTime());
		viewHolder.getTextExecution().setText(currentDrill.getExecution());
		viewHolder.getTextEffect().setText(currentDrill.getEffect());
	}

	class ViewHolder {

		private ImageView image;
		private TextView textName;
		private TextView textTime;
		private TextView textDescription;
		private TextView textExecution;
		private TextView textEffect;
		
		public ViewHolder(View convertView) {
			image = (ImageView) convertView.findViewById(R.id.drill_image_big);
			textName = (TextView) convertView.findViewById(R.id.name);
			textTime = (TextView) convertView.findViewById(R.id.time);
			textDescription = (TextView) convertView.findViewById(R.id.description);
			textExecution = (TextView) convertView.findViewById(R.id.execution);
			textEffect = (TextView) convertView.findViewById(R.id.effect);
		
		}

		public ImageView getImage() {
			return image;
		}
		public TextView getTextName() {
			return textName;
		}

		public TextView getTextTime() {
			return textTime;
		}

		public TextView getTextDescription() {
			return textDescription;
		}

		public TextView getTextExecution() {
			return textExecution;
		}

		public TextView getTextEffect() {
			return textEffect;
		}
	}
	
}

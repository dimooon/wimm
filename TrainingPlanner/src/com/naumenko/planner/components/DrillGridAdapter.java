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

public class DrillGridAdapter extends BaseAdapter {

	private ArrayList<Drill> drills;
	private Activity activity;
	private Drill currentDrill;
	private ViewHolder viewHolder;

	public DrillGridAdapter(Activity activity, ArrayList<Drill> drills) {
		this.activity = activity;
		this.drills = drills;
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
					.inflate(R.layout.drill_grid_item, null);
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
			viewHolder.getImage().setImageDrawable(Drawable.createFromStream(resources.getAssets().open("drills_th/"+currentDrill.getThumbnail()), null));
		} catch (IOException e) {
			e.printStackTrace();
		}
		viewHolder.getText().setText(currentDrill.getName());
	}

	class ViewHolder {

		private ImageView image;
		private TextView text;

		public ViewHolder(View convertView) {
			image = (ImageView) convertView
					.findViewById(R.id.drillGridItemPicture);
			text = (TextView) convertView
					.findViewById(R.id.drillGridItemDescription);
		}

		public ImageView getImage() {
			return image;
		}

		public TextView getText() {
			return text;
		}
		
		
	}
	
}

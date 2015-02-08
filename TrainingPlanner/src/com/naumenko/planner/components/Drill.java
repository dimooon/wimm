package com.naumenko.planner.components;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;

public class Drill {
	
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String name;
	private String description;
	private String picture;
	private String time;
	private String execution;
	private String effect;
	private String thumbnail;
	private ArrayList<String> picturesList = new ArrayList<String>();

	@Override
	public String toString() {
		return "Drill [name=" + name + ", description=" + description
				+ ", picture=" + picture + ", time=" + time + ", execution="
				+ execution + ", effect=" + effect + ", thumbnail=" + thumbnail
				+ "]";
	}

	public Drill() {

	}

	public Drill(String name, String description, String picture, String time,
			String execution, String effect, String thumbnail,int id) {
		this.name = name;
		this.description = description;
		this.picture = picture;
		this.time = time;
		this.execution = execution;
		this.effect = effect;
		this.thumbnail = thumbnail;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getPicture() {
		return picture;
	}

	public String getTime() {
		return time;
	}

	public String getExecution() {
		return execution;
	}

	public String getEffect() {
		return effect;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setExecution(String execution) {
		this.execution = execution;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public ArrayList<String> getPicturesList() {
		parsePictures();
		Log.e("pic_L:", ""+picturesList);
		return picturesList;
	}

	private void parsePictures() {
		picturesList.clear();
		final String delims = ",";
		String[] pictures = picture.split(delims);
		picturesList.addAll(Arrays.asList(pictures));
	}
}

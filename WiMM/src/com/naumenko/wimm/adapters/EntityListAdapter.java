package com.naumenko.wimm.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class EntityListAdapter extends BaseAdapter{

	private ArrayList<WimmEntity> entities;
	private Activity context;
	
	public EntityListAdapter(Activity context, List<WimmEntity> createdItems) {
		this.entities = new ArrayList<WimmEntity>();
		this.entities.addAll(createdItems);
		
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return this.entities.size();
	}

	@Override
	public Object getItem(int position) {
		return this.entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.entities.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		EntityItemViewHolder viewHolder;
	    
		View view = convertView;
		
	    if(view==null){
	        
	        LayoutInflater inflater = this.context.getLayoutInflater();
	        view = inflater.inflate(R.layout.entities_list_item, parent, false);
	         
	        viewHolder = new EntityItemViewHolder();
	        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
	        viewHolder.title = (TextView) view.findViewById(R.id.firstLine);
	        viewHolder.descriprion = (TextView) view.findViewById(R.id.secondLine);
	         
	        view.setTag(viewHolder);
	         
	    }else{
	        viewHolder = (EntityItemViewHolder) view.getTag();
	    }
	     
	    WimmEntity entity = entities.get(position);
	     
	    if(entity != null) {
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
	    	String dateString = formatter.format(new Date(entity.getTimeInMs()));
	    	
	        viewHolder.icon.setImageResource(R.drawable.ic_launcher);
	        viewHolder.title.setText(this.context.getString(R.string.entity_list_item_title_pattern,entity.getName(),entity.getAmount()));
	        viewHolder.descriprion.setText(this.context.getString(R.string.entity_list_item_description_pattern,entity.getDescription(),dateString));
	    }
	    
	    return view;
	}
	
	private static class EntityItemViewHolder{
		ImageView icon;
		TextView title;
		TextView descriprion;
	}
}
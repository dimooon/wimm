package com.naumenko.wimm.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.WimmApplication;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.view.WimmGestureListener;
import com.naumenko.wimm.view.WimmTouchListener;

public class EntityListAdapter extends BaseAdapter implements WimmGestureListener{

	private ArrayList<WimmEntity> entities;
	private Activity activity;
	
	public EntityListAdapter(Activity context, List<WimmEntity> createdItems) {
		this.entities = new ArrayList<WimmEntity>();
		this.entities.addAll(createdItems);
		
		this.activity = context;
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
	
	public List<WimmEntity> getItems(){
		return this.entities;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final EntityItemViewHolder viewHolder;
	    
		View view = convertView;
		
	    if(view==null){
	        
	        LayoutInflater inflater = this.activity.getLayoutInflater();
	        view = inflater.inflate(R.layout.entities_list_item, parent, false);
	        
	        view.setTag(R.id.position,position);
	        
	        viewHolder = new EntityItemViewHolder();
	        viewHolder.init(view);
	        
	        view.setTag(R.id.holder,viewHolder);
	        
	        
	    }else{
	        viewHolder = (EntityItemViewHolder) view.getTag(R.id.holder);
	    }
	    
	    view.setOnTouchListener(new WimmTouchListener(activity, this));
	    
	    WimmEntity entity = entities.get(position);
	     
	    if(entity != null) {
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
	    	String dateString = formatter.format(new Date(entity.getTimeInMs()));
	    	
	        viewHolder.icon.setImageResource(R.drawable.ic_launcher);
	        viewHolder.title.setText(this.activity.getString(R.string.entity_list_item_title_pattern,entity.getName(),entity.getAmount()));
	        viewHolder.descriprion.setText(this.activity.getString(R.string.entity_list_item_description_pattern,entity.getDescription(),dateString));
	    }
	    
	    return view;
	}
	
	private class EntityItemViewHolder{
		ImageView icon;
		TextView title;
		TextView descriprion;
		Button buttonRemove;
		View content;
		
		private int position = -1;
		
		public void init(View view){
			
			position = (Integer) view.getTag(R.id.position);
			
			icon = (ImageView) view.findViewById(R.id.icon);
	        title = (TextView) view.findViewById(R.id.firstLine);
	        descriprion = (TextView) view.findViewById(R.id.secondLine);
	        buttonRemove = (Button) view.findViewById(R.id.entity_item_delete);
	        content = view.findViewById(R.id.entity_item_content);
	        
	        buttonRemove.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					WimmApplication.getDAO().deleteEntity(entities.get(position).getId());
					
					view.setVisibility(View.GONE);
					entities.remove(position);
					
					notifyDataSetChanged();
					content.setVisibility(View.VISIBLE);
					buttonRemove.setVisibility(View.GONE);
				}
			});
	        
		}
	}

	@Override
	public void onTouch(View view) {
		WimmApplication.setSelectedEntity((WimmEntity)getItem(Integer.valueOf(view.getTag(R.id.position).toString())));
		WimmApplication.getFragmentManager().startInfoFragment();
	}

	@Override
	public void onSwipeLeft(View view) {
		EntityItemViewHolder holder = (EntityItemViewHolder) view.getTag(R.id.holder);
		holder.buttonRemove.setVisibility(View.GONE);
		holder.content.setVisibility(View.VISIBLE);
	}

	@Override
	public void onSwipeRight(View view) {
		EntityItemViewHolder holder = (EntityItemViewHolder) view.getTag(R.id.holder);
		holder.buttonRemove.setVisibility(View.VISIBLE);
		holder.content.setVisibility(View.GONE);
		
	}
	
}
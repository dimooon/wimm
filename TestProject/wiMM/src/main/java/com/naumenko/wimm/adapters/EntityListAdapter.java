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
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.WimmApplication;
import com.naumenko.wimm.dao.entity.PaymentList;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.view.WimmGestureListener;
import com.naumenko.wimm.view.WimmTouchListener;

public class EntityListAdapter extends BaseExpandableListAdapter implements WimmGestureListener{

	private ArrayList<PaymentList> sectionHeader;
	
	private Activity activity;
	
	public EntityListAdapter(Activity context, List<PaymentList> paymentList) {
		this.sectionHeader = new ArrayList<PaymentList>();
		this.sectionHeader.addAll(paymentList);		
		
		this.activity = context;
	}
	
	public ArrayList<WimmEntity> getItems(){
		
		ArrayList<WimmEntity> entities = new ArrayList<WimmEntity>();
		
		for (PaymentList payments : sectionHeader) {
			entities.addAll(payments.getEntities());
		}
		
		return entities;
	}
	
	@Override
    public int getGroupCount() {
        return sectionHeader.size();
    }
	
	@Override
    public int getChildrenCount(int groupPosition) {
        return sectionHeader.get(groupPosition).getEntities().size();
    }
	
	 @Override
	    public Object getGroup(int groupPosition) {
	        return sectionHeader.get(groupPosition);
	    }
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
	    return sectionHeader.get(groupPosition).getEntities().get(childPosition);
	}
	
	@Override
	public long getGroupId(int groupPosition) {
	   return groupPosition;
	}
	
	@Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
	
	@Override
    public boolean hasStableIds() {
        return true;
    }
	
	 @Override
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		 
		 	View view = convertView;
		 	ListHeaderViewHolder holder;
		 	
	        if (view == null) {
	        	holder = new ListHeaderViewHolder();
	        	
	            LayoutInflater inflater = this.activity.getLayoutInflater();
	            view = inflater.inflate(R.layout.list_title_item, parent, false);
	            
	            holder.caption = (TextView) view.findViewById(R.id.list_title_name);
	            
		        view.setTag(holder);
	        }else{
	        	holder = (ListHeaderViewHolder) view.getTag();
	        }
	        
	        if (isExpanded){
	        	
	        }
	        else{
	        	
	        }

	        holder.caption.setText(sectionHeader.get(groupPosition).getName());

	        return view;

	    }
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		final EntityItemViewHolder viewHolder;
	    
		View view = convertView;
		
	    if(view==null){
	    	
	    	LayoutInflater inflater;
	    	viewHolder = new EntityItemViewHolder();
	    	
	    	
				inflater = this.activity.getLayoutInflater();
		        view = inflater.inflate(R.layout.entities_list_item, parent, false);
		        
		        view.setTag(R.id.position,childPosition);
		        view.setTag(R.id.group_position,groupPosition);
		        
		        viewHolder.init(view);
		        
		        view.setTag(R.id.holder,viewHolder);
		    
	    }else{
	        viewHolder = (EntityItemViewHolder) view.getTag(R.id.holder);
	    }
	    
	    view.setOnTouchListener(new WimmTouchListener(activity, this));
	    
	    WimmEntity entity = sectionHeader.get(groupPosition).getEntities().get(childPosition);
	     
	    if(entity != null) {
	    	
	    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
	    	String dateString = formatter.format(new Date(entity.getTimeInMs()));
	    	
	        viewHolder.icon.setImageResource(R.drawable.ic_launcher);
	        viewHolder.title.setText(this.activity.getString(R.string.entity_list_item_title_pattern,entity.getName(),entity.getAmount()));
	        viewHolder.descriprion.setText(this.activity.getString(R.string.entity_list_item_description_pattern,entity.getDescription(),dateString));
	    	
	    }
	    
	    return view;
	}
	
	private class ListHeaderViewHolder{
		TextView caption;
	}
	
	private class EntityItemViewHolder{
		ImageView icon;
		TextView title;
		TextView descriprion;
		Button buttonRemove;
		View content;
		
		TextView listTitleName;
		
		private int position = -1;
		private int listPosition = -1;
		
		public void init(View view){
			
			position = (Integer) view.getTag(R.id.position);
			listPosition = (Integer) view.getTag(R.id.group_position);
			
			icon = (ImageView) view.findViewById(R.id.icon);
	        title = (TextView) view.findViewById(R.id.firstLine);
	        descriprion = (TextView) view.findViewById(R.id.secondLine);
	        buttonRemove = (Button) view.findViewById(R.id.entity_item_delete);
	        content = view.findViewById(R.id.entity_item_content);
	        
	        buttonRemove.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					WimmApplication.getDAO().deleteEntity(
							sectionHeader.get(listPosition).getEntities().get(position).getId());
					
					view.setVisibility(View.GONE);
					
					sectionHeader.get(listPosition).getEntities().remove(position);
					
					notifyDataSetChanged();
					content.setVisibility(View.VISIBLE);
					buttonRemove.setVisibility(View.GONE);
				}
			});
	        
		}
	}

	@Override
	public void onTouch(View view) {
		WimmApplication.setSelectedEntity((WimmEntity)getChild(
				Integer.valueOf(view.getTag(R.id.group_position).toString()),
				Integer.valueOf(view.getTag(R.id.position).toString())));
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

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		return true;
	}
}
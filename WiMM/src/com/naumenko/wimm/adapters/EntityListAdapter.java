package com.naumenko.wimm.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naumenko.wimm.R;
import com.naumenko.wimm.dao.ContentProviderWimmDAO;
import com.naumenko.wimm.dao.db.EntityTable;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.dao.provider.WimmContentProvider;

public class EntityListAdapter extends BaseAdapter{

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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final EntityItemViewHolder viewHolder;
	    
		View view = convertView;
		
	    if(view==null){
	        
	        LayoutInflater inflater = this.activity.getLayoutInflater();
	        view = inflater.inflate(R.layout.entities_list_item, parent, false);
	         
	        viewHolder = new EntityItemViewHolder();
	        viewHolder.icon = (ImageView) view.findViewById(R.id.icon);
	        viewHolder.title = (TextView) view.findViewById(R.id.firstLine);
	        viewHolder.descriprion = (TextView) view.findViewById(R.id.secondLine);
	        viewHolder.buttonRemove = (Button) view.findViewById(R.id.entity_item_delete);
	        viewHolder.content = view.findViewById(R.id.entity_item_content);
	        
	        viewHolder.buttonRemove.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					
					activity.getContentResolver().delete(
							WimmContentProvider.CONTRACT.CONTENT_URI,
							String.valueOf(entities.get(position).getId()),
							null);
					
					view.setVisibility(View.GONE);
					entities.remove(position);
					Log.e("TAG", "position "+position);
					notifyDataSetChanged();
					viewHolder.content.setVisibility(View.VISIBLE);
					viewHolder.buttonRemove.setVisibility(View.GONE);
				}
			});
	        
	        
	        view.setTag(viewHolder);
	         
	    }else{
	        viewHolder = (EntityItemViewHolder) view.getTag();
	    }
	    
	    view.setOnTouchListener(new MyTouchListener());
	    
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
	
	private static class EntityItemViewHolder{
		ImageView icon;
		TextView title;
		TextView descriprion;
		Button buttonRemove;
		View content;
	}
	
	private int action_down_x = 0;
    private int action_up_x = 0;
    private int difference = 0;
	
	class MyTouchListener implements OnTouchListener
    {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			EntityItemViewHolder holder = (EntityItemViewHolder) v.getTag();
			int action = event.getAction();

			switch (action) {
			case MotionEvent.ACTION_DOWN:
				action_down_x = (int) event.getX();
				Log.d("action", "ACTION_DOWN - ");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d("action", "ACTION_MOVE - ");
				action_up_x = (int) event.getX();
				difference = action_down_x - action_up_x;
				calcuateDifference(holder);
				break;
			case MotionEvent.ACTION_UP:
				Log.d("action", "ACTION_UP - ");
				action_down_x = 0;
				action_up_x = 0;
				difference = 0;
				break;
			}
			return true;
		}
    }

	private void calcuateDifference(final EntityItemViewHolder holder) {
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				if (difference > 75) {
					holder.buttonRemove.setVisibility(View.VISIBLE);
					holder.content.setVisibility(View.GONE);
				}
				if (difference < -75) {
					holder.buttonRemove.setVisibility(View.GONE);
					holder.content.setVisibility(View.VISIBLE);
				}
			}
		});
	}
	
	
}
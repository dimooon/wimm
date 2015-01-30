package com.naumenko.wimm.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.WimmApplication;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class EntityPreviewFragment extends Fragment implements OnTouchListener{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
		return inflater.inflate(R.layout.entity_preview_fragment, container,false);
	}
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		WimmEntity entity = WimmApplication.getSelectedEntity();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	String dateString = formatter.format(new Date(entity.getTimeInMs()));
		
		((ImageView)getView().findViewById(R.id.icon)).setImageResource(R.drawable.ic_launcher);
		
		first = ((TextView)getView().findViewById(R.id.firstLine));
		first.setText(entity.getName());
		first.setOnTouchListener(this);
		
		second = ((TextView)getView().findViewById(R.id.secondLine));
		second.setText(String.valueOf(entity.getAmount()));
		second.setOnTouchListener(this);

		third = ((TextView)getView().findViewById(R.id.thirdLine));
		third.setText(entity.getType().name());
		third.setOnTouchListener(this);
		
		fourth = ((TextView)getView().findViewById(R.id.fourthLine));
		fourth.setText(dateString);
		fourth.setOnTouchListener(this);
		
		firstEdit = ((EditText)getView().findViewById(R.id.firstLine_edit));
		firstEdit.setText(entity.getName());
		firstEdit.setOnTouchListener(this);
		
		secondEdit = ((EditText)getView().findViewById(R.id.secondLine_edit));
		secondEdit.setText(String.valueOf(entity.getAmount()));
		secondEdit.setOnTouchListener(this);

		thirdEdit = ((EditText)getView().findViewById(R.id.thirdLine_edit));
		thirdEdit.setText(entity.getType().name());
		thirdEdit.setOnTouchListener(this);
		
		fourthEdir = ((EditText)getView().findViewById(R.id.fourthLine_edit));
		fourthEdir.setText(dateString);
		fourthEdir.setOnTouchListener(this);
		
		firstEdit.setEnabled(false);
		secondEdit.setEnabled(false);
		thirdEdit.setEnabled(false);
		fourthEdir.setEnabled(false);
		
		((TextView)getView().findViewById(R.id.entity_description_additional_info)).setText(entity.getDescription());
		
	}

	private int difference;
	private int action_down_x;
	private int action_up_x;
	private TextView first;
	private TextView second;
	private TextView third;
	private TextView fourth;
	private EditText firstEdit;
	private EditText secondEdit;
	private EditText thirdEdit;
	private EditText fourthEdir;
	

	View parent = null;
	View next = parent;
	boolean edit;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			parent = v;
			next = parent;
			
			edit = false;
			
			switch (v.getId()) {
			
			case R.id.firstLine:
				
				next = firstEdit;
				edit = true;
				break;

			case R.id.secondLine:
				
				next = secondEdit;
				edit = true;
				break;
				
			case R.id.thirdLine:
	
				next = thirdEdit;
				edit = true;
				break;
	
			case R.id.fourthLine:
	
				next = fourthEdir;
				edit = true;
				break;
				
			case R.id.firstLine_edit:
				
				next = first;
				edit = false;
				break;

			case R.id.secondLine_edit:
				
				next = second;
				edit = false;
				break;
				
			case R.id.thirdLine_edit:
	
				next = third;
				edit = false;
				break;
	
			case R.id.fourthLine_edit:
	
				next = fourth;
				edit = false;
				break;
			}
			
			int action = event.getAction();

			boolean processed = (difference > -1 && difference <=3);
			
			Log.e("TAG", "processed: "+processed+ " differences: "+difference);
			
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				action_down_x = (int) event.getX();
				Log.d("action", "ACTION_DOWN - ");
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d("action", "ACTION_MOVE - ");
				action_up_x = (int) event.getX();
				difference = action_down_x - action_up_x;
				calcuateDifference();
				break;
			case MotionEvent.ACTION_UP:
				Log.d("action", "ACTION_UP - ");
				
				if(processed){
					return false;
				}
				
				action_down_x = 0;
				action_up_x = 0;
				difference = 0;
				
				break;
			}
			return true;
		}
    

	private void calcuateDifference() {
		
		getActivity().runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if (difference < -75 && edit) {
					
					Log.e("TAG", "next: "+next.getClass().getSimpleName()+" parent: "+parent.getClass().getSimpleName());
					
					next.setVisibility(View.VISIBLE);
					parent.setVisibility(View.INVISIBLE);
					next.setEnabled(true);
					parent.setEnabled(false);
					
					((TextView)next).setText(((TextView)parent).getText());
					
			}
				if (difference > 75 && !edit) {
					
					next.setVisibility(View.VISIBLE);
					parent.setVisibility(View.INVISIBLE);
					next.setEnabled(true);
					parent.setEnabled(false);
					
					((TextView)next).setText(((TextView)parent).getText());
				}
			}
		});
		
	}
	
}

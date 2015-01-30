package com.naumenko.wimm.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.WimmApplication;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class EntityPreviewFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
		return inflater.inflate(R.layout.entity_preview_fragment, container,false);
	}
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		WimmEntity entity = WimmApplication.getSelectedEntity();
		WimmApplication.setSelectedEntity(null);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
    	String dateString = formatter.format(new Date(entity.getTimeInMs()));
		
		((ImageView)getView().findViewById(R.id.icon)).setImageResource(R.drawable.ic_launcher);
		
		((TextView)getView().findViewById(R.id.firstLine)).setText(entity.getName());
		((TextView)getView().findViewById(R.id.secondLine)).setText(entity.getAmount()+"  "+entity.getType()+" "+dateString);
		
		((TextView)getView().findViewById(R.id.entity_description_additional_info)).setText(entity.getDescription());
	}
	
}

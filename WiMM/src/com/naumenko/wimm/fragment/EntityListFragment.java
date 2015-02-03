package com.naumenko.wimm.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.adapters.EntityListAdapter;
import com.naumenko.wimm.dao.ContentProviderWimmDAO;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class EntityListFragment extends Fragment {

	private static final String TAG = EntityListFragment.class.getSimpleName();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_of_items_fragment, container,false);
	}
	
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		final ListView lv = (ListView) getView().findViewById(R.id.created_entity_list);
		
		List<WimmEntity> createdItems = new ArrayList<WimmEntity>();
		
		createdItems.addAll(new ContentProviderWimmDAO(getActivity()).getEntityList());
		
		lv.setAdapter(new EntityListAdapter(getActivity(), createdItems));
		
		double fullAmount = 0;
		
		for (WimmEntity wimmEntity : createdItems) {
			fullAmount+=wimmEntity.getAmount();
		}
		
		((TextView)getView().findViewById(R.id.fullAmount)).setText(String.valueOf(fullAmount));
		
		((Button)getView().findViewById(R.id.export)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				exportToXml(((EntityListAdapter)lv.getAdapter()).getItems());
			}
		});
		
	};
	
	
	private void exportToXml(List<WimmEntity> items){
		
		StringBuilder xmlBuilder = new StringBuilder();
		xmlBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xmlBuilder.append("<payment_list>");
		xmlBuilder.append("\n");
		for (WimmEntity wimmEntity : items) {
			xmlBuilder.append(wimmEntity.getConvertedXml());
			xmlBuilder.append("\n");
		}
		xmlBuilder.append("</payment_list>");
		
		storeOnSdCard(xmlBuilder.toString());
		
	}
	
	private void storeOnSdCard(Object data){
		
		final String MODE_WRITABLE = "w";
		
		ParcelFileDescriptor pfd = null;
		FileOutputStream fileOutputStream = null;
		
		try {
			
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy"); 
	    	String dateString = formatter.format(System.currentTimeMillis());
			
			String fileName = "Wimm_Export_"+dateString+".xml";
			
			File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/"+fileName);
			
			Log.e(TAG, ""+f.getAbsolutePath());
			
			f.createNewFile();
			
			fileOutputStream = new FileOutputStream(f);
			fileOutputStream.write(((String) data).getBytes());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(fileOutputStream!=null){
					fileOutputStream.close();
				}
				if(pfd!=null){
					pfd.close();	
				}
			} catch (IOException e) {
			}
		}	
	}
	
}

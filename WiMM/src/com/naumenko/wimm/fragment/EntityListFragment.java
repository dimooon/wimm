package com.naumenko.wimm.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.WimmApplication;
import com.naumenko.wimm.adapters.EntityListAdapter;
import com.naumenko.wimm.dao.entity.PaymentList;
import com.naumenko.wimm.dao.entity.WimmEntity;
import com.naumenko.wimm.dao.entity.export.ExportManager;

public class EntityListFragment extends Fragment {
	
	private EntityListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list_of_items_fragment, container,false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		initListView();
		updateFullAmountView();
		initExportButton();
	};
	
	private void initListView(){
		
		final ExpandableListView listOfPaymentEntities = (ExpandableListView) getView().findViewById(R.id.created_entity_list);
		
		List<PaymentList> createdItems = new ArrayList<PaymentList>();
		
		createdItems.addAll(WimmApplication.getDAO().getPaymentLists());
		
		Log.e("tag", ""+createdItems);
		
		adapter = new EntityListAdapter(getActivity(), createdItems);

		adapter.registerDataSetObserver(new DataSetObserver() {

			@Override
			public void onChanged() {
				super.onChanged();
				
				updateFullAmountView();
				
			}
			
		});

		listOfPaymentEntities.setAdapter(adapter);
		
	}
	
	private void updateFullAmountView(){
		double fullAmount = 0;
		
		for (WimmEntity wimmEntity : adapter.getItems()) {
			fullAmount+=wimmEntity.getAmount();
		}
		
		((TextView)getView().findViewById(R.id.fullAmount)).setText(String.valueOf(fullAmount));
	}
	
	private void initExportButton(){
		((Button)getView().findViewById(R.id.export)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				WimmApplication.getExport(ExportManager.XML).export((adapter.getItems()));
			}
		});
	}
}
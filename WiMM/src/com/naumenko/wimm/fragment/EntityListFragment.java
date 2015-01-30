package com.naumenko.wimm.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.naumenko.wimm.R;
import com.naumenko.wimm.WimmApplication;
import com.naumenko.wimm.adapters.EntityListAdapter;
import com.naumenko.wimm.dao.ContentProviderWimmDAO;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class EntityListFragment extends Fragment {

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
		
		
	};
	
	
	
}

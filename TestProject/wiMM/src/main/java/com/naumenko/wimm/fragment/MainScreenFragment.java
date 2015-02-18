package com.naumenko.wimm.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.naumenko.wimm.R;
import com.naumenko.wimm.WimmApplication;
import com.naumenko.wimm.adapters.RecyclerAdapter;
import com.naumenko.wimm.dao.entity.PaymentList;

import java.util.ArrayList;

public class MainScreenFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_fragment, container,false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		getView().findViewById(R.id.createEntityButton).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WimmApplication.getFragmentManager().startCreatingFragment();	
			}
		});;
		
		getView().findViewById(R.id.getListOfCreated).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				WimmApplication.getFragmentManager().startListFragment();	
			}
		});

        RecyclerView mRecyclerView = (RecyclerView) getView().findViewById(R.id.recicler_view);

        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<PaymentList> payments = WimmApplication.getDAO().getPaymentLists();

        RecyclerAdapter mAdapter = new RecyclerAdapter(payments);
        mRecyclerView.setAdapter(mAdapter);
	};
}
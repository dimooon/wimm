package com.naumenko.wimm.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.naumenko.wimm.R;

public class MainScreenFragment extends Fragment implements View.OnClickListener {

	private Button createButton;
	private Button listButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_fragment, container,false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		createButton = (Button) getView().findViewById(R.id.createEntityButton);
		listButton = (Button) getView().findViewById(R.id.getListOfCreated);
		
		createButton.setOnClickListener(this);
		listButton.setOnClickListener(this);
		
	};
	
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.createEntityButton:
			
			startCreatingFragment();
			
			
			break;
		case R.id.getListOfCreated:
			
			startListFragment();
			
			break;
		default:
			break;
		}
	}
	
	private void startCreatingFragment(){
		CreateEntityFragment newFragment = new CreateEntityFragment();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		transaction.replace(R.id.main_activity_root, newFragment);
		transaction.addToBackStack(null);

		transaction.commit();
	}

	private void startListFragment(){
		EntityListFragment newFragment = new EntityListFragment();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		transaction.replace(R.id.main_activity_root, newFragment);
		transaction.addToBackStack(null);

		transaction.commit();
	}
	
}
package com.naumenko.wimm.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.naumenko.wimm.R;

public class WimmFragmentManager {

	private Activity rootActivity;
	private FragmentManager manager;
	
	public WimmFragmentManager(Activity rootActivity) {
		this.rootActivity = rootActivity;
		
		this.manager = this.rootActivity.getFragmentManager();
	}
	
	public void addMainFragment(Bundle savedInstanceState){
		Bundle extras = this.rootActivity.getIntent().getExtras();
		
		if (extras != null) {
            return;
        }
		
        MainScreenFragment firstFragment = new MainScreenFragment();
        firstFragment.setArguments(extras);
        this.manager.beginTransaction().add(R.id.main_activity_root, firstFragment).commit();
	}
	
	public void startCreatingFragment(){
		replace(new CreateEntityFragment());
	}

	public void startListFragment(){
		replace(new EntityListFragment());
	}

	public void startInfoFragment(){
		replace(new EntityPreviewFragment());
	}
	
	private void replace(Fragment fragment){

		FragmentTransaction transaction = this.manager.beginTransaction();

		transaction.replace(R.id.main_activity_root, fragment);
		transaction.addToBackStack(null);

		transaction.commit();		
	}
	
	
}

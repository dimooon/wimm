package com.naumenko.wimm;

import com.naumenko.wimm.fragment.MainScreenFragment;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		addMainFragment(savedInstanceState);	
	}
	
	private void addMainFragment(Bundle savedInstanceState){
		if (savedInstanceState != null) {
            return;
        }

        MainScreenFragment firstFragment = new MainScreenFragment();
        
        firstFragment.setArguments(getIntent().getExtras());
        
        getFragmentManager().beginTransaction().add(R.id.main_activity_root, firstFragment).commit();
	}
	
}

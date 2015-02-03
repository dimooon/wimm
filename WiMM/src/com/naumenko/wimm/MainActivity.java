package com.naumenko.wimm;

import android.app.Activity;
import android.os.Bundle;

import com.naumenko.wimm.dao.ContentProviderWimmDAO;
import com.naumenko.wimm.dao.WimmDAO;
import com.naumenko.wimm.fragment.WimmFragmentManager;

public class MainActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		initFragmentManager();
		initDAO();
		
		WimmApplication.getFragmentManager().addMainFragment(savedInstanceState);
	}
	
	private void initFragmentManager(){
		WimmFragmentManager manager = new WimmFragmentManager(this);
		WimmApplication.setFragmentManager(manager);
	}
	private void initDAO(){
		WimmDAO dao = new ContentProviderWimmDAO(this);
		WimmApplication.setDAO(dao);
	}
}
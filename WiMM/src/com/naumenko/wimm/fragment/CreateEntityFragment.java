package com.naumenko.wimm.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.naumenko.wimm.R;
import com.naumenko.wimm.dao.ContentProviderWimmDAO;
import com.naumenko.wimm.dao.entity.PaymentType;
import com.naumenko.wimm.dao.entity.PaymentWimmEntity;
import com.naumenko.wimm.dao.entity.WimmEntity;

public class CreateEntityFragment extends Fragment implements View.OnClickListener, OnItemSelectedListener{
	
	private WimmEntity entity;
	private EditText name;
	private EditText description;
	private EditText amount;
	private Spinner type;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 	Bundle savedInstanceState) {
	
		return inflater.inflate(R.layout.create_entity_fragment, container,false);
	}
	public void onViewCreated(View view, Bundle savedInstanceState) {
		
		getView().findViewById(R.id.start_create_button).setOnClickListener(this);
		name = (EditText) getView().findViewById(R.id.columnt_name);
		description = (EditText) getView().findViewById(R.id.columnt_description);
		amount = (EditText) getView().findViewById(R.id.columnt_amount);
		type =  (Spinner) getView().findViewById(R.id.column_type);
		
		List<String> list = new ArrayList<String>();
		list.add(PaymentType.PAY.name());
		list.add(PaymentType.SALARY.name());
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
		
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(dataAdapter);
		type.setOnItemSelectedListener(this);
		
		entity = new PaymentWimmEntity();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_create_button:
			
			createEntity();
			
			break;
		default:
			break;
		}
	};
	
	private void createEntity(){
		
		if(name.getText().length() == 0){
			name.setError(Html.fromHtml("<font color='red'>Name cannot be empty</font>"));
			return;
		}
		if(amount.getText().length() == 0){
			amount.setError(Html.fromHtml("<font color='red'>Amount cannot be empty</font>"));
			return;
		}
		
		entity.setName(name.getText().toString());
		entity.setDescription(description.getText().toString());
		entity.setAmount(Double.parseDouble(amount.getText().toString()));
		entity.setDate(System.currentTimeMillis());
		
		new ContentProviderWimmDAO(getActivity()).addEntity(entity);
		
		getActivity().onBackPressed();
		
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		entity.setPaymentType(PaymentType.get(String.valueOf(position)));
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		entity.setPaymentType(PaymentType.PAY);
	}	
}

package com.naumenko.wimm.db;

public enum ENTITY_CONTRACT{
	
	CONTRACT(null),
	DATABASE_NAME("wimm_database"),

	DATABASE_VERSION("1"),
	
	TABLE_NAME("wimm_entity"),
	ID("_id"),
	NAME("wimm_entity_name"),
	DESCRIPTION("wimm_entity_description"),
	AMOUNT("wimm_entity_amount"),
	PAYMENT_TYPE("wimm_entity_payment_type"),
	DATE("wimm_entity_date");

	private String contractKey;
	
	private ENTITY_CONTRACT(String contractKey) {
		this.contractKey = contractKey;
	}
	
	public String getContractKey(){
		return this.contractKey;
	}
	
	public String[] getSelectionAllFields(){
		return new String[]{
			ID.getContractKey(),
			NAME.getContractKey(),
			DESCRIPTION.getContractKey(),
			AMOUNT.getContractKey(),
			PAYMENT_TYPE.getContractKey(),
			DATE.getContractKey()};
	}
	
}
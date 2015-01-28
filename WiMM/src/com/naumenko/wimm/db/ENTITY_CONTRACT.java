package com.naumenko.wimm.db;

public enum ENTITY_CONTRACT{
	
	CONTRACT(null),
	DATABASE_NAME("wimm_database"),

	DATABASE_VERSION("1"),
	
	TABLE_NAME("wimm_entity"),
	ID("_id",0),
	NAME("wimm_entity_name",1),
	DESCRIPTION("wimm_entity_description",2),
	AMOUNT("wimm_entity_amount",3),
	PAYMENT_TYPE("wimm_entity_payment_type",4),
	DATE("wimm_entity_date",5);

	private String contractKey;
	private int index;
	
	private ENTITY_CONTRACT(String contractKey) {
		this.contractKey = contractKey;
	}
	
	private ENTITY_CONTRACT(String contractKey, int columnIndex) {
		this(contractKey);
		this.index = columnIndex;
	}
	
	public String getContractKey(){
		return this.contractKey;
	}
	
	public int getIndex(){
		return index;
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
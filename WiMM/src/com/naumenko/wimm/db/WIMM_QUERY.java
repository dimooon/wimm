package com.naumenko.wimm.db;

public enum WIMM_QUERY{
	CREATE_DATABASE(
			"create table " + ENTITY_CONTRACT.TABLE_NAME.getContractKey() + 
			"(" + ENTITY_CONTRACT.ID.getContractKey() + " integer primary key autoincrement, " + 
				  ENTITY_CONTRACT.NAME.getContractKey()  + " text not null," +
				  ENTITY_CONTRACT.DESCRIPTION.getContractKey()  + " text," +
				  ENTITY_CONTRACT.AMOUNT.getContractKey()  + " REAL not null," +
				  ENTITY_CONTRACT.PAYMENT_TYPE.getContractKey()  + " INTEGER not null," +
				  ENTITY_CONTRACT.DATE.getContractKey()  + " INTEGER not null" +
			 ");"),
			 
	DROP_TABLE_IF_EXISTS("DROP TABLE IF EXISTS " + ENTITY_CONTRACT.TABLE_NAME);
	
	private String query;
	
	private WIMM_QUERY(String queryString) {
		this.query = queryString;
	}
	
	public String getQueryString(){
		return this.query;
	}
	
}
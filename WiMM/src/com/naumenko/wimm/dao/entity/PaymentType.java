package com.naumenko.wimm.dao.entity;

public enum PaymentType {
	
	PAY("0"),SALARY("1");
	
	private PaymentType(String typeRepresentation) {
		this.typeRepresentation = typeRepresentation;
	}
	
	private String typeRepresentation;
	
	public String getTypeRepresentation(){
		return typeRepresentation;
	}
	
}

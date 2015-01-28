package com.naumenko.wimm.dao.entity;

import java.util.HashMap;
import java.util.Map;

public enum PaymentType {
	
	PAY("0"),SALARY("1");
	
	private PaymentType(String typeRepresentation) {
		this.typeRepresentation = typeRepresentation;
	}
	
	private String typeRepresentation;
	
	public String getTypeRepresentation(){
		return typeRepresentation;
	}
	
	private static final Map<String, PaymentType> lookup = new HashMap<String, PaymentType>();
    
	static {
        for (PaymentType d : PaymentType.values())
            lookup.put(d.getTypeRepresentation(), d);
    }
	
	public static PaymentType get(String typeRepresentation) {
        return lookup.get(typeRepresentation);
    }
}

package com.naumenko.wimm.dao.entity;

public interface WimmEntity {

	public void setName(String name);
	public void setDescription(String description);
	public void setAmount(double amount);
	public void setPaymentType(PaymentType type);
	public void setDate(Long timeInMs);

	public String getName();
	public String getDescription();
	public double getAmount();
	public PaymentType getType();
	public Long getTimeInMs();
	
}
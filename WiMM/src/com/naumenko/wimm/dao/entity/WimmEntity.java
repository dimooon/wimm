package com.naumenko.wimm.dao.entity;

public interface WimmEntity {

	public long getId();
	public void setName(String name);
	public void setDescription(String description);
	public void setAmount(double amount);
	public void setPaymentType(PaymentType type);
	public void setDate(Long timeInMs);

	public void setId(long id);
	public String getName();
	public String getDescription();
	public double getAmount();
	public PaymentType getType();
	public Long getTimeInMs();
	
	public String toString();
}
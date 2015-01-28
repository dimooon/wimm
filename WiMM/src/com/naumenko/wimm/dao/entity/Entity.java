package com.naumenko.wimm.dao.entity;

public class Entity implements WimmEntity{
	
	protected String name;
	protected String description;
	protected double amount;
	protected PaymentType type;
	protected Long timeInMs;
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public PaymentType getType() {
		return type;
	}

	public Long getTimeInMs() {
		return timeInMs;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public void setPaymentType(PaymentType type) {
		this.type = type;
	}

	@Override
	public void setDate(Long timeInMs) {
		this.timeInMs = timeInMs;
	}

}
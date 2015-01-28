package com.naumenko.wimm.dao.entity;

public class Entity implements WimmEntity{
	
	protected long id;
	protected String name;
	protected String description;
	protected double amount;
	protected PaymentType type;
	protected Long timeInMs;
	
	@Override
	public long getId(){
		return id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public double getAmount() {
		return amount;
	}

	@Override
	public PaymentType getType() {
		return type;
	}

	@Override
	public Long getTimeInMs() {
		return timeInMs;
	}
	
	@Override
	public void setId(long id){
		this.id = id;
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
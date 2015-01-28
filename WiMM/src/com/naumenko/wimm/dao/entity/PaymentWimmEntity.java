package com.naumenko.wimm.dao.entity;

import android.database.Cursor;

public class PaymentWimmEntity implements WimmEntity, CursorParselable{
	
	protected long id;
	protected String name;
	protected String description;
	protected double amount;
	protected PaymentType type;
	protected Long timeInMs;
	
	public PaymentWimmEntity() {
		super();
	}
	
	public PaymentWimmEntity(Cursor cursor){
		cursorToWimmEntity(cursor);
	}
	
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

	@Override
	public String toString() {
		return "Entity [id=" + id + ", name=" + name + ", description="
				+ description + ", amount=" + amount + ", type=" + type
				+ ", timeInMs=" + timeInMs + "]";
	}

	@Override
	public void cursorToWimmEntity(Cursor cursor) {
		setId(cursor.getLong(0));
		setName(cursor.getString(1));
		setDescription(cursor.getString(2));
		setAmount(cursor.getDouble(3));
		setPaymentType((PaymentType.get(cursor.getString(4))));
		setDate(cursor.getLong(5));
	}
	
}
package com.naumenko.wimm.dao.entity;

import com.naumenko.wimm.db.ENTITY_CONTRACT;

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
		setDefultState();
	}
	
	public PaymentWimmEntity(Cursor cursor){
		cursorToWimmEntity(cursor);
	}
	
	@Override
	public boolean isValid() {
		return entityParsed();
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
		setId(cursor.isNull(0) ? -1 : cursor.getLong(ENTITY_CONTRACT.ID.getIndex()));
		setName(cursor.isNull(1) ? null : cursor.getString(ENTITY_CONTRACT.NAME.getIndex()));
		setDescription(cursor.isNull(2) ? null :cursor.getString(ENTITY_CONTRACT.DESCRIPTION.getIndex()));
		setAmount(cursor.isNull(3) ? -1.0 : cursor.getDouble(ENTITY_CONTRACT.AMOUNT.getIndex()));
		setPaymentType(cursor.isNull(4) ? null : (PaymentType.get(cursor.getString(ENTITY_CONTRACT.PAYMENT_TYPE.getIndex()))));
		setDate(cursor.isNull(5) ? -1 :cursor.getLong(ENTITY_CONTRACT.DATE.getIndex()));
	}
	
	@Override
	public boolean entityParsed() {
		return id > 0;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((timeInMs == null) ? 0 : timeInMs.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentWimmEntity other = (PaymentWimmEntity) obj;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (timeInMs == null) {
			if (other.timeInMs != null)
				return false;
		} else if (!timeInMs.equals(other.timeInMs))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	private void setDefultState(){
		this.id = -1;
		this.name = null;
		this.description = null;
		this.amount = -1.0;
		this.type = null;
		this.timeInMs = -1l;
	}
	
}
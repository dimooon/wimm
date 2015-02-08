package com.naumenko.wimm.dao.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.R.xml;
import android.content.ContentValues;
import android.database.Cursor;

import com.naumenko.wimm.dao.db.EntityTable;
import com.naumenko.wimm.dao.db.EntityTable.ENTITY_CONTRACT;

public class PaymentWimmEntity implements WimmEntity{
	
	protected long id;
	protected String name;
	protected String description;
	protected double amount;
	protected PaymentType type;
	protected Long timeInMs;
	protected long list_id;

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
    public void setListId(long id) { this.list_id = id; }

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
    public long getListId() { return this.list_id; }

    @Override
	public String toString() {
		return "Entity [id=" + id + ", name=" + name + ", description="
				+ description + ", amount=" + amount + ", type=" + type
				+ ", timeInMs=" + timeInMs + "]";
	}
	
	@Override
	public void cursorToWimmEntity(Cursor cursor) {
		setId(cursor.isNull(0) ? -1 : cursor.getLong(ENTITY_CONTRACT.COLUMN_ID.getIndex()));
		setName(cursor.isNull(1) ? null : cursor.getString(ENTITY_CONTRACT.COLUMN_NAME.getIndex()));
		setDescription(cursor.isNull(2) ? null :cursor.getString(ENTITY_CONTRACT.COLUMN_DESCRIPTION.getIndex()));
		setAmount(cursor.isNull(3) ? -1.0 : cursor.getDouble(ENTITY_CONTRACT.COLUMN_AMOUNT.getIndex()));
		setPaymentType(cursor.isNull(4) ? null : (PaymentType.get(cursor.getString(ENTITY_CONTRACT.COLUMN_PAYMENT_TYPE.getIndex()))));
		setDate(cursor.isNull(5) ? -1 :cursor.getLong(ENTITY_CONTRACT.COLUMN_DATE.getIndex()));
	}
	

	@Override
	public ContentValues getConvertedContentValues() {
		
		ContentValues contentValuesExpression = new ContentValues();
		
		contentValuesExpression.put(EntityTable.ENTITY_CONTRACT.COLUMN_NAME.getContractKey(), getName());
		contentValuesExpression.put(EntityTable.ENTITY_CONTRACT.COLUMN_DESCRIPTION.getContractKey(), getDescription());
		contentValuesExpression.put(EntityTable.ENTITY_CONTRACT.COLUMN_AMOUNT.getContractKey(), getAmount());
		contentValuesExpression.put(EntityTable.ENTITY_CONTRACT.COLUMN_PAYMENT_TYPE.getContractKey(), getType().getTypeRepresentation());
		contentValuesExpression.put(EntityTable.ENTITY_CONTRACT.COLUMN_DATE.getContractKey(), getTimeInMs());
		
		return contentValuesExpression;
	}
	
	@Override
	public String getConvertedXml() {
		
		final String OPEN_TAG = "<";
		final String CLOSE_TAG = "/>"; 
		final String PADDING = " ";
		final String CLOSE_PARAMETER = "\'";
		final String NAME_TAG = " name=\'";
		final String AMOUNT_TAG = " amount=\'";
		final String TYPE_TAG = " type=\'";
		final String DATE_TAG = " date=\'";
		final String DESCRIPTION_TAG = " description=\'";
		
		StringBuilder xmlExpressionBuilder = new StringBuilder();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
    	String dateString = formatter.format(new Date(getTimeInMs()));
		
		xmlExpressionBuilder
		.append(OPEN_TAG)
		.append(PaymentWimmEntity.class.getSimpleName())
		.append(PADDING)
		
		.append(NAME_TAG)
		.append(getName())
		.append(CLOSE_PARAMETER)
		
		.append(AMOUNT_TAG)
		.append(getAmount())
		.append(CLOSE_PARAMETER)
		
		.append(TYPE_TAG)
		.append(getType().name())
		.append(CLOSE_PARAMETER)
		
		.append(DATE_TAG)
		.append(dateString)
		.append(CLOSE_PARAMETER)
		
		.append(DESCRIPTION_TAG)
		.append(CLOSE_PARAMETER)
		.append(PADDING)
		.append(CLOSE_TAG);
		
		return xmlExpressionBuilder.toString();
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
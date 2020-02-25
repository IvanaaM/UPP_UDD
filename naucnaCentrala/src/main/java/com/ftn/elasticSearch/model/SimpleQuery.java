package com.ftn.elasticSearch.model;

public class SimpleQuery {
	
	private String field;
	private String value;
	private boolean operation;
	private boolean phrase;
	
	public SimpleQuery() {
		super();
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean getOperation() {
		return operation;
	}
	public void setOperation(boolean operation) {
		this.operation = operation;
	}
	public boolean isPhrase() {
		return phrase;
	}
	public void setPhrase(boolean phrase) {
		this.phrase = phrase;
	}
	
}

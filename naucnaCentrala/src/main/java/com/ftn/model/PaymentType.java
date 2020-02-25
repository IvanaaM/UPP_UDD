package com.ftn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ftn.enums.PaymentTypes;


@Entity
public class PaymentType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column
	private PaymentTypes name;

	public PaymentType() {
		
	}

	public PaymentType(PaymentTypes name) {
		super();
		this.name = name;
	}

	public PaymentTypes getPaymentName() {
		return name;
	}

	public void setPaymentName(PaymentTypes name) {
		this.name = name;
	}
	
	
	

}

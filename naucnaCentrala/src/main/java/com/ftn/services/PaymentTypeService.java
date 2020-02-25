package com.ftn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.PaymentTypes;
import com.ftn.model.PaymentType;
import com.ftn.repository.PaymentTypeRepository;

@Service
public class PaymentTypeService {

	@Autowired
	PaymentTypeRepository ptRepository;

	public PaymentType findByName(PaymentTypes type) {
		// TODO Auto-generated method stub
		return ptRepository.findByName(type);
	}
}

package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.enums.PaymentTypes;
import com.ftn.model.PaymentType;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {

	PaymentType findByName(PaymentTypes paymentTypes);
}

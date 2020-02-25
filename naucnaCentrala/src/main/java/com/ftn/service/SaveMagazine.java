package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.ChargeType;
import com.ftn.enums.PaymentTypes;
import com.ftn.model.PaymentType;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.repository.MagazineRepository;
import com.ftn.repository.PaymentTypeRepository;
import com.ftn.repository.ScientificAreaRepository;
import com.ftn.repository.UserRepository;

@Service
public class SaveMagazine implements JavaDelegate {
	
	@Autowired
	PaymentTypeRepository ptRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MagazineRepository magazineRepository;
	
	@Autowired
	ScientificAreaRepository saRepository;


	@Override
	public void execute(DelegateExecution execution) throws Exception {

		List<FormSubmissionDto> magazineVariable = (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("casopis");
		
		List<FormSubmissionDto> saMagazine = (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("naucneOblastiCasopis");
		
		Magazine magazine = new Magazine();
		
		for (FormSubmissionDto formField : magazineVariable) {
		 
			if(formField.getFieldId().equals("naziv")) {
				magazine.setName(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("issn")) {
				magazine.setIssn(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("naplataAutorima")) {
				magazine.setChargeType(ChargeType.Autorima);
			}
			if(formField.getFieldId().equals("naplataCitaocima")) {
				magazine.setChargeType(ChargeType.Citaocima);
			}
			if(formField.getFieldId().equals("placanjeBanka")) {
				if(!formField.getFieldValue().equals("")) {
				magazine.addPaymentType(ptRepository.findByName(PaymentTypes.Banka));
				}
			}
			if(formField.getFieldId().equals("placanjePayPal")) {
				if(!formField.getFieldValue().equals("")) {
				magazine.addPaymentType(ptRepository.findByName(PaymentTypes.PayPal));
				}
			}
			if(formField.getFieldId().equals("placanjeBitCoin")) {
				if(!formField.getFieldValue().equals("")) {
				magazine.addPaymentType(ptRepository.findByName(PaymentTypes.BitCoin));
				}
			}
			
	      }
		
		for (FormSubmissionDto formField : saMagazine) {
			
			magazine.addScientificArea(saRepository.findByName(formField.getFieldValue()));
		}
		
		magazineRepository.save(magazine);
		
		
	}

}

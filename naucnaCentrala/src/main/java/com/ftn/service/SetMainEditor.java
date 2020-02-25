package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.repository.MagazineRepository;
import com.ftn.repository.UserRepository;

@Service
public class SetMainEditor implements JavaDelegate {
	
	@Autowired
	MagazineRepository magazineRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FormSubmissionDto> magazineVariable = (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("casopis");
		
		Magazine magazine = new Magazine();
		
		for (FormSubmissionDto formField: magazineVariable) {
			
			if (formField.getFieldId().equals("naziv")) {
				magazine = magazineRepository.findByName(formField.getFieldValue());
			}
		
			if(formField.getFieldId().equals("logged")) {
				magazine.setMainEditor(userRepository.findByUsername(formField.getFieldValue()));
		}
		
		}
		
		System.out.println("Setovao glavnog");

	}

}

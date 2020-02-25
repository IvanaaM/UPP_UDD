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
public class SaveEditors implements JavaDelegate {


	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MagazineRepository magazineRepository;

	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		List<FormSubmissionDto> form = (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("urednici");
		
		String naziv = (String) execution.getProcessInstance().getVariable("naziv");
		
		System.out.println(naziv);
		
		Magazine magaz = magazineRepository.findByName(naziv);
		
		for (FormSubmissionDto formField : form) {
			
			System.out.println("editor");
			magaz.addEditor(userRepository.findByUsername(formField.getFieldValue()));
		}

	}

	}



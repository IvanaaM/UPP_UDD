package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.services.MagazineService;

@Service
public class HandlerME implements JavaDelegate {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	MagazineService magazineService;


	@Override
	public void execute(DelegateExecution execution) throws Exception {


		List<FormSubmissionDto> list =  (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("chooseMagazine");

		Magazine m = magazineService.findByName(list.get(0).getFieldValue()); 
		
		execution.getProcessInstance().setVariable("glavniUrednik", m.getMainEditor().getUsername());
		
		System.out.println("postavljen");
	}

}

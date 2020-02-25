package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.UserCustom;
import com.ftn.services.UserService;

@Service
public class Pay implements JavaDelegate {
	
	@Autowired
	UserService userService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String username = (String) execution.getProcessInstance().getVariable("user");
		
		UserCustom uc = userService.findByUsername(username);
		
		uc.setFee(true);
		
	}

}

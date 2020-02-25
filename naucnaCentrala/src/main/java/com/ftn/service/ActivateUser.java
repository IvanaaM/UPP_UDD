package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.UserCustom;
import com.ftn.repository.UserRepository;

@Service
public class ActivateUser implements JavaDelegate {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		System.out.println("aktivirao");
		
		execution.getProcessInstance().setVariable("aktivan", true);
		
		String username = (String) execution.getProcessInstance().getVariable("username");
		
		UserCustom uc =	userRepository.findByUsername(username);
		
		uc.setActive(true);
		
		userRepository.save(uc);
		

	}

}

package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.services.UserService;

@Service
public class SendEmailRejectPaper implements JavaDelegate {

	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String subject = "Odbijanje rada";
		
		String text = "Rad koji ste prijavili se odbija iz tehnickih razloga \n\n Naucna Centrala";
		
		String username = (String) execution.getProcessInstance().getVariable("user");
		
		//mailService.sendEmail("ivanamarin67@gmail.com", text, subject);
		
		System.out.println("Odbija se rad za korisnika " + username);

	}

}

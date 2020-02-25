package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailAcceptPaper implements JavaDelegate {
	
	@Autowired
	MailService mailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		

		String subject = "Prihvatanje rada";
		
		String text = "Rad koji ste prijavili se prihvata \n\n Naucna Centrala";
		
		String username = (String) execution.getProcessInstance().getVariable("user");
		
		//mailService.sendEmail("ivanamarin67@gmail.com", text, subject);
		
		System.out.println("Prihvacen rad za korisnika " + username);

	}

}

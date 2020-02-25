package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailERCancel implements JavaDelegate {

	@Autowired
	MailService mailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// ukoliko recenzent ne obavi u zadataom vremenu recenziranje ovde se obavestava urednik i dodeljuje novog
		
		String username = (String) execution.getProcessInstance().getVariable("urednikNO");

		String subject = "Odabir drugog recenzenta";
		
		String text = "Odabir recenzenta mozete izvrsiti kad se ulogujete \n\n Naucna Centrala";
		
		//mailService.sendEmail("ivanamarin67@gmail.com", text, subject);
		
		System.out.println("Poslat mejl za biranje drugog recenzenta " + username);

	}

}

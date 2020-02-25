package com.ftn.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.services.MagazineService;
import com.ftn.services.UserService;

@Service
public class SendEmailMainEditor implements JavaDelegate {

	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineService magazineService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String text = "Prijavljen(izmenjen) je novi rad u sistemu \n\n Mozete pogledati na svom profilu \n\n Naucna centrala";
		String subject = "Novi rad - urednik";
	
		String nameCheck = (String) execution.getProcessInstance().getVariable("glavniUrednik");
		
		//mailService.sendEmail("ivanamarin67@gmail.com", text, subject);
		System.out.println("Poslato glavnom uredniku: " + nameCheck);

		
	}

}

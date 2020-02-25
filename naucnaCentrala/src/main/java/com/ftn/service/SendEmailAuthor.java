package com.ftn.service;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.model.UserCustom;
import com.ftn.modelDTO.Login;
import com.ftn.services.MagazineService;
import com.ftn.services.UserService;

@Service
public class SendEmailAuthor implements JavaDelegate {

	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineService magazineService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String text = "Uspesno prijavljen novi rad u sistem Naucna Centrala \n\n Naucna Centrala";
		String subject = "Novi rad - autor";
		String title = "";
		List<FormSubmissionDto> list =  (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("paper");

		for (FormSubmissionDto fsd : list) {
			if(fsd.getFieldId().equals("naslov")) {
				title = fsd.getFieldValue();
				
			}
		}
		
		String username = (String) execution.getProcessInstance().getVariable("user");
		
		//mailService.sendEmail("ivanamarin67@gmail.com", text, subject);
		
		System.out.println("Ovo je user: " + username);
		
	}

	
}

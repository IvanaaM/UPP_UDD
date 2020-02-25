package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ftn.enums.RoleName;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Role;
import com.ftn.model.UserCustom;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;

@Service
public class SendEmail implements JavaDelegate {
	
	@Autowired
	MailService mailService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;


	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		execution.setVariable("aktivan", false);
		
		String name="";
		String email="";
		
	    List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");

	    for (FormSubmissionDto formField : registration) {
	    	
	    	if(formField.getFieldId().equals("email")) {
	    		email = formField.getFieldValue();
	    	}
	    	if(formField.getFieldId().equals("username")) {
	    		name=formField.getFieldValue();
	    	}
	    	
	    }
	    
	    String text = "Dobrodosli " + name + "! \n\n " + "Da zavrsite registraciju i aktivirate Vas nalog kliknite na sledeci link:"
		+ "\n\n http://localhost:8080/user/confirmMail \n\n Naucna Centrala \n\n";
	    	    
	  //  mailService.sendEmail("ivanamarin67@gmail.com", text, "Aktivacija naloga");
	    System.out.println("posalao mejl");
	    
	}

}

package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.ftn.enums.RoleName;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Role;
import com.ftn.model.UserCustom;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.ScientificAreaRepository;
import com.ftn.repository.UserRepository;

@Service
public class SaveUser implements JavaDelegate {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ScientificAreaRepository saRepository;

	
	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
	      List<FormSubmissionDto> registration = (List<FormSubmissionDto>)execution.getVariable("registration");
	      
	      List<FormSubmissionDto> areas = (List<FormSubmissionDto>)execution.getVariable("naucneOblasti");
	      
	      System.out.println(registration);
	      User user = identityService.newUser("");
	      for (FormSubmissionDto formField : registration) {
			if(formField.getFieldId().equals("username")) {
				user.setId(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("password")) {
				user.setPassword(formField.getFieldValue());
				System.out.println(user.getPassword());
			}
	      }
	      identityService.saveUser(user);
	      
	      UserCustom u = new UserCustom();
		    for (FormSubmissionDto formField : registration) {
		    	
		    	if(formField.getFieldId().equals("email")) {
		    		u.setEmail(formField.getFieldValue());
		    	}
		    	if(formField.getFieldId().equals("username")) {
		    		u.setUsername(formField.getFieldValue());
		    	}
		    	if(formField.getFieldId().equals("drzava")) {
		    		u.setState(formField.getFieldValue());
		    	}
		    	if(formField.getFieldId().equals("titula")) {
		    		u.setTitle(formField.getFieldValue());
		    	}
		    	if(formField.getFieldId().equals("ime")) {
		    		u.setFirstName(formField.getFieldValue());
		    	}
		    	if(formField.getFieldId().equals("prezime")) {
		    		u.setLastName(formField.getFieldValue());
		    	}
		    	if(formField.getFieldId().equals("grad")) {
		    		u.setCity(formField.getFieldValue());
		    	}
		    	if(formField.getFieldId().equals("password")) {

		    	    String salt = BCrypt.gensalt();

					String passwordHashed = BCrypt.hashpw(formField.getFieldValue(), salt);

					u.setPassword(passwordHashed);
		    	}
		    	
		    
		    }
		    
			Role r = new Role();
	    	r.setName(RoleName.ROLE_USER);

	    	u.addRole(roleRepository.findByName(RoleName.ROLE_USER));
	    	
	    	for (FormSubmissionDto formField : areas) {
				
				u.addArea(saRepository.findByName(formField.getFieldValue()));
			}
	    	
			userRepository.save(u);
	
		System.out.println("sacuvao");
	}

}

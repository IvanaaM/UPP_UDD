package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.RoleName;
import com.ftn.model.UserCustom;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;

@Service
public class SaveReviewer  implements JavaDelegate {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String username = (String) execution.getProcessInstance().getVariable("username");
		
		UserCustom uc = userRepository.findByUsername(username);
		
		uc.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
		
		userRepository.save(uc);
		
	}

}

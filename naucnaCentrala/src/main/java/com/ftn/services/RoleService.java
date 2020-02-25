package com.ftn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.RoleName;
import com.ftn.model.Role;
import com.ftn.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	public Role findByName(RoleName name) {
		return roleRepository.findByName(name);
	}
}

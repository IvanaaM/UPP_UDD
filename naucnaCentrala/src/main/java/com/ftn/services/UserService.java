package com.ftn.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.enums.RoleName;
import com.ftn.model.Role;
import com.ftn.model.UserCustom;

import com.ftn.repository.UserRepository;
import com.ftn.security.UserSecurity;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	
	public List<UserCustom> getReviewers(){
		
		List<UserCustom> users = new ArrayList<UserCustom>();
		
		for (UserCustom user : userRepository.findAll()) {
			for (Role role : user.getRoles()) {
				if(role.getName().equals(RoleName.ROLE_REVIEWER)) {
					users.add(user);
				}
			}
		}
		
		return users;
		
	}
	
	public List<UserCustom> getEditors(String name){
		
		List<UserCustom> users = new ArrayList<UserCustom>();
		
		UserCustom u = userRepository.findByUsername(name);
		
		for (UserCustom user : userRepository.findAll()) {
			if(user.getUsername() != name) {
			for (Role role : user.getRoles()) {
				if(role.getName().equals(RoleName.ROLE_EDITOR)) {
					users.add(user);
				}
			}
			}
		}
		
		return users;
		
	}

	public UserCustom findByUsername(String username) {
	
		return userRepository.findByUsername(username);
	}


	public UserCustom saveUser(UserCustom uc) {
		
		return userRepository.save(uc);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserCustom user = userRepository.findByUsername(username);
	       
        return getUserSecurity(user);
		
	}
	
private UserSecurity getUserSecurity(UserCustom user) {
		
		Set<Role> roles = user.getRoles();
		
		List<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();
		for(Role s: roles) {
		
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(s.getName().toString());
			authorites.add(authority);
			
		}
		
		return new UserSecurity(user.getId(), user.getPassword(), user.getUsername(), user.isEnabled(), authorites, user.isNonLocked());
	}


}

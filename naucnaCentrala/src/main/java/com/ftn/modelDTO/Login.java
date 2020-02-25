package com.ftn.modelDTO;

import java.util.ArrayList;

import com.ftn.model.Role;

public class Login {
	
	private String username;
	private ArrayList<String> roles;
	
	public Login() {
		super();
	}

	public Login(String username, ArrayList<String> roles) {
		super();
		this.username = username;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<String> getRoles() {
		return roles;
	}

	public void setRoles(ArrayList<String> roles) {
		this.roles = roles;
	}
	
}

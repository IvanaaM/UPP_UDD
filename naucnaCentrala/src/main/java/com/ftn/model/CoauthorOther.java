package com.ftn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CoauthorOther")
public class CoauthorOther {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Email")
	private String email;
	
	@Column(name="City")
	private String city;
	
	@Column(name="State")
	private String state;
	
	@Column(name="SystemUser")
	private boolean systemUser;

	public CoauthorOther() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isSystemUser() {
		return systemUser;
	}

	public void setSystemUser(boolean systemUser) {
		this.systemUser = systemUser;
	}


}

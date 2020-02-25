package com.ftn.model;

public class UserToken {
	private String accessToken;
	private Long expiresIn;

	public UserToken() {
		this.accessToken = null;
		this.expiresIn = null;
	}

	public UserToken(String accessToken, Long expiresIn) {
		super();
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

}

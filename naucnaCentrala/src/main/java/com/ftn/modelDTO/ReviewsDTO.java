package com.ftn.modelDTO;

import java.io.Serializable;

public class ReviewsDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String comments;
	private String recomm;
	private String commForEditor;
	
	public ReviewsDTO() {
		
	}

	public ReviewsDTO(String name, String comments, String recomm, String commForEditor) {
	
		this.name = name;
		this.comments = comments;
		this.recomm = recomm;
		this.commForEditor = commForEditor;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getRecomm() {
		return recomm;
	}
	public void setRecomm(String recomm) {
		this.recomm = recomm;
	}

	public String getCommForEditor() {
		return commForEditor;
	}

	public void setCommForEditor(String commForEditor) {
		this.commForEditor = commForEditor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
}

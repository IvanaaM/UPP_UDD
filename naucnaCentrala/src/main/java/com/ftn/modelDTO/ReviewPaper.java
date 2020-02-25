package com.ftn.modelDTO;

import java.util.List;

import com.ftn.model.FormSubmissionDto;

public class ReviewPaper {
	
	private String taskId;
	private List<FormSubmissionDto> fsd;
	
	
	public ReviewPaper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ReviewPaper(String taskId, List<FormSubmissionDto> fsd) {
		super();
		this.taskId = taskId;
		this.fsd = fsd;
	}

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<FormSubmissionDto> getFsd() {
		return fsd;
	}
	public void setFsd(List<FormSubmissionDto> fsd) {
		this.fsd = fsd;
	}
	
	

}

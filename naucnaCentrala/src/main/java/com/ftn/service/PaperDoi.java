package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service
public class PaperDoi implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("Dodela DOI radu");

	}

}

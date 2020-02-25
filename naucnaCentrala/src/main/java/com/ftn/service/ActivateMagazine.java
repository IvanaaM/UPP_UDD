package com.ftn.service;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Magazine;
import com.ftn.repository.MagazineRepository;

@Service
public class ActivateMagazine implements JavaDelegate {
	
	@Autowired
	MagazineRepository magazineRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {


		String naziv = (String) execution.getProcessInstance().getVariable("naziv");

		Magazine magazine = magazineRepository.findByName(naziv);
		
		magazine.setActive(true);
		
		magazineRepository.save(magazine);
		
		
	}

}

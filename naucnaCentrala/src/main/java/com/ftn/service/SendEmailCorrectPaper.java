package com.ftn.service;


import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.UserCustom;
import com.ftn.services.MagazineService;
import com.ftn.services.UserService;

@Service
public class SendEmailCorrectPaper implements JavaDelegate {
	
	@Autowired
	MagazineService magazineService;

	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

		// treba poslati mejl korisniku koji je prijavio rad, da ga prepravi
		
		String username = (String) execution.getProcessInstance().getVariable("user");
		
		// ispraviti na setD, kako je u angularu zapisano
		List<FormSubmissionDto> list = (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("deadlines");
		
		String rok = "";
		
		for(FormSubmissionDto fsd : list) {
			if(fsd.getFieldId().equals("rok")) {
				rok = fsd.getFieldValue();
			}
			
		}
		
		UserCustom uc = userService.findByUsername(username);
		
		// rok moze stici u formi PT4M, provera vremena npr 
		// Ono sto ide nakon P je datum, a sto ide nakon T je vreme
		// Mozda rastaviti na t (split na 2 stringa)
		
		String[] rok2 = rok.split("T"); 
		
		String time = rok2[1];
		
		if(time.contains("H")) {
			time.replace("H", "sati ");
		}
		
		if(time.contains("M")) {
			time.replace("M", "minuta ");
		}
		
		System.out.println("Rok za korigovanje je " + time);

		String subject = "Korigovanje rada";
		
		String text = "Dobili ste ovaj mejl da bi korigovali PDF dokument za rad koji ste prijavili"
				+ " \n\n Mozete pogledati na svom profilu"
				+ "\n\n Rok za korigovanje rada je: " + time;
		
		//mailService.sendEmail("ivanamarin67@gmail.com", text, subject);
		
		System.out.println("Poslato za korigovanje korisniku");
		
		
	}

	

}

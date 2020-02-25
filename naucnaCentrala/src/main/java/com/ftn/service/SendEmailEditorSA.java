package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.model.UserCustom;
import com.ftn.services.MagazineService;
import com.ftn.services.UserService;

@Service
public class SendEmailEditorSA implements JavaDelegate {

	@Autowired
	MagazineService magazineService;

	@Autowired
	UserService userService;
	
	@Autowired
	MailService mailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		// treba preuzeti urednika naucne oblasti rada za dati casopis i njemu poslati mejl
		// postaviti procesnu varijabli zbog assignee
		
		List<FormSubmissionDto> list =  (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("chooseMagazine");
		List<FormSubmissionDto> list2 =  (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("paper");

		Magazine m = magazineService.findByName(list.get(0).getFieldValue());
		String no = "";
		
		for(FormSubmissionDto fd : list2) {
			if(fd.getFieldId().equals("naucnaOblast")) {
				no = fd.getFieldValue();
			}
		}
		
		UserCustom uc = magazineService.findBySA(m, no);
		
		String text = "Prijavljen je novi rad u sistem \n\n Na Vasem profilu odaberite recenzente za rad \n\n Naucna Centrala";
		String subject = "Novi rad";
		
		System.out.println("Poslato uredniku naucne oblasti da odabere recenzente: "  + uc.getUsername());
		
		execution.getProcessInstance().setVariable("urednikNO", uc.getUsername());
		
		//mailService.sendEmail("ivanamarin67@gmail.com", text, subject);
		
	}

}

package com.ftn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.enums.ChargeType;
import com.ftn.enums.PaymentTypes;
import com.ftn.model.CoauthorOther;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.model.Paper;
import com.ftn.model.UserCustom;
import com.ftn.modelDTO.PdfDto;
import com.ftn.services.MagazineService;
import com.ftn.services.PaperService;
import com.ftn.services.UserService;
import com.ftn.services.ScientificAreaService;

@Service
public class SavePaper implements JavaDelegate {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CoauthorOtherService coService;
		
	@Autowired
	MagazineService magazineService;
	
	@Autowired
	PaperService paperService;

	@Autowired
	ScientificAreaService scientificAreaService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Random rand = new Random();

		int n = rand.nextInt(500);
		
		String username = (String) execution.getProcessInstance().getVariable("user");
		
		List<FormSubmissionDto> fsd = (List<FormSubmissionDto>) execution.getVariable("paper");

		UserCustom u = userService.findByUsername(username);
		
		List<FormSubmissionDto> list =  (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("chooseMagazine");

		Magazine m = magazineService.findByName(list.get(0).getFieldValue()); 
		
		Paper p = new Paper();
		
		for(FormSubmissionDto formField : fsd) {
			
			if(formField.getFieldId().equals("naslov")) {
				p.setTitle(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("apstrakt")) {
				p.setApstract(formField.getFieldValue());
			}
		//	if(formField.getFieldId().equals("koautori")) {
		//		p.setCoauthors(formField.getFieldValue());
		//	}
			if(formField.getFieldId().equals("kljucniPojmovi")) {
				p.setKeywords(formField.getFieldValue());
			}
			if(formField.getFieldId().equals("naucnaOblast")) {
				p.setScientificArea(scientificAreaService.findByName(formField.getFieldValue()));
			}
		}
		
		
		List<String> coauth = (List<String>) execution.getProcessInstance().getVariable("coauthors");
			
		for(String s : coauth) {
			
			System.out.println("Ove je ime " + s);
			String var = s+"coa";
			
			List<FormSubmissionDto> fsd2 = (List<FormSubmissionDto>) execution.getProcessInstance().getVariable(var);

			CoauthorOther co = new CoauthorOther();
			
			for (FormSubmissionDto dd : fsd2) {

				if(dd.getFieldId().equals("imeK")) {
					co.setName(dd.getFieldValue());
					
					if(userService.findByUsername(dd.getFieldValue()) == null) {
						co.setSystemUser(false);
					} else {
						co.setSystemUser(true);
					}
				}
				if(dd.getFieldId().equals("emailK")) {
					co.setEmail(dd.getFieldValue());
				}
				if(dd.getFieldId().equals("gradK")) {
					co.setCity(dd.getFieldValue());
				}
				if(dd.getFieldId().equals("drzavaK")) {
					co.setState(dd.getFieldValue());
				}
							
			}
			
			p.getCoauthorsOthers().add(coService.saveCO(co));
		}
		
		
		
		PdfDto pd = (PdfDto) execution.getProcessInstance().getVariable("pdfUrl");
		
		p.setAuthor(u);
		p.setMagazine(m);
		p.setPathPdf(pd.getPdf());
		p.setDoi(n);
		
		paperService.savePaper(p);
	}

}

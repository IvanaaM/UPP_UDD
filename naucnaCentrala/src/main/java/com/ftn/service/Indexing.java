package com.ftn.service;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.elasticSearch.indexing.Indexer;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Paper;
import com.ftn.services.PaperService;

@Service
public class Indexing implements JavaDelegate {

	@Autowired
	Indexer indexer;
	
	@Autowired
	PaperService paperService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {

	System.out.println("Indeksiranje u toku");
		
		List<FormSubmissionDto> fsd = (List<FormSubmissionDto>) execution.getProcessInstance().getVariable("paper");
		
		String name = "";
		String pdfName = "";
		
		for(FormSubmissionDto f : fsd) {
			
			if(f.getFieldId().equals("naslov")) {
				name= f.getFieldValue();
				break;
			}
		}
		
		for(FormSubmissionDto f : fsd) {
			
			if(f.getFieldId().equals("pdf")) { 
				System.out.println("Vrednost pdf-a " + f.getFieldValue());
				String[] k = f.getFieldValue().split("\\\\");
				System.out.println(k[2]);
				pdfName=k[2];
				break;
			}
		}
		
		Paper paper = paperService.getPaper(name);
		
		indexer.index(paper, pdfName);
			
		
	}

}

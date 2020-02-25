package com.ftn.handlers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.model.ScientificArea;
import com.ftn.services.MagazineService;


@Service
public class HandlerSAM implements TaskListener {
	
	@Autowired
	MagazineService magazineService;


	@Override
	public void notify(DelegateTask delegateTask) {
		
		List<FormSubmissionDto> form = (List<FormSubmissionDto>) delegateTask.getExecution().getProcessInstance().getVariable("chooseMagazine");
		

		TaskFormData tfd = delegateTask.getExecution().getProcessEngineServices().getFormService().
				getTaskFormData(delegateTask.getId());
		
		
		String naziv = form.get(0).getFieldValue();
		System.out.println(naziv);
		
		Magazine magaz = magazineService.findByName(naziv);
		
		System.out.println(magaz.getName());
		
 		Set<ScientificArea> sareas =  magaz.getAreas();
		
		List<FormField> formFields = tfd.getFormFields();
		
		for(FormField fp : formFields) {
			System.out.println(fp.getId() + fp.getType());
			
			if(fp.getId().equals("naucnaOblast")) {
				
				EnumFormType enumFormType = (EnumFormType) fp.getType();
				
				Map<String, String> values = enumFormType.getValues();
				
				for(ScientificArea a : sareas) {
					values.put(a.getName(), a.getName());
				}
			
			}
		}
		
	}

}

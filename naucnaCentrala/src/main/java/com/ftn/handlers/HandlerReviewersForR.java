package com.ftn.handlers;

import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.model.UserCustom;
import com.ftn.services.MagazineService;

@Service
public class HandlerReviewersForR implements TaskListener {

	@Autowired
	MagazineService magazineService;
	
	@Override
	public void notify(DelegateTask delegateTask) {
		
		TaskFormData tfd = delegateTask.getExecution().getProcessEngineServices().getFormService().
				getTaskFormData(delegateTask.getId());
		
		List<FormSubmissionDto> form = (List<FormSubmissionDto>) delegateTask.getExecution().getProcessInstance().getVariable("chooseMagazine");
		
		Magazine m = magazineService.findByName(form.get(0).getFieldValue());
		
		List<FormField> formFields = tfd.getFormFields();
		
		for(FormField fp : formFields) {
			System.out.println(fp.getId() + fp.getType());
			
			if(fp.getId().equals("recenz") || fp.getId().equals("odabRec")) {
				
				EnumFormType enumFormType = (EnumFormType) fp.getType();
				
				Map<String, String> values = enumFormType.getValues();
				
				for(UserCustom uc : m.getReviewers()) {
					values.put(uc.getUsername(), uc.getFirstName() + " " + uc.getLastName());
				}
				
			}
		}

	}

}

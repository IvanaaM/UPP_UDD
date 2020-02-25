package com.ftn.handlers;

import java.util.ArrayList;
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
import com.ftn.model.UserCustom;
import com.ftn.repository.MagazineRepository;
import com.ftn.services.UserService;

@Service
public class HandlerEditors implements TaskListener {
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	MagazineRepository magazineRepository;

	@Override
	public void notify(DelegateTask delegateTask) {
		TaskFormData tfd = delegateTask.getExecution().getProcessEngineServices().getFormService().
				getTaskFormData(delegateTask.getId());
		
		List<FormField> formFields = tfd.getFormFields();
		
		String naziv = (String) delegateTask.getExecution().getProcessInstance().getVariable("naziv");
		

		List<UserCustom> users = userService.getEditors(magazineRepository.findByName(naziv).getMainEditor().getUsername());
		
		List<FormSubmissionDto> saMagazine = (List<FormSubmissionDto>) delegateTask.getExecution().getProcessInstance().getVariable("naucneOblastiCasopis");
		
		ArrayList<String> str = new ArrayList<String>();
		
		for(FormSubmissionDto ff : saMagazine) {
			str.add(ff.getFieldValue());
			
		}
		
		for(FormField fp : formFields) {
			System.out.println(fp.getId() + fp.getType());
			
			if(fp.getId().equals("urednici")) {
				
				EnumFormType enumFormType = (EnumFormType) fp.getType();
				
				Map<String, String> values = enumFormType.getValues();
				
					for (UserCustom u: users) {
						
						for(String sa : str) {
							
							if(u.chechIfAreaExists(sa)) {
								values.put(u.getUsername(), u.getFirstName()+ " " + u.getLastName());
							}
							
						}
				}
				
			}
		}
		
	}

}

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

import com.ftn.model.ScientificArea;
import com.ftn.repository.ScientificAreaRepository;

@Service
public class HandlerSA implements TaskListener {
	
	@Autowired
	ScientificAreaRepository saRepository;

	@Override
	public void notify(DelegateTask delegateTask) {

		TaskFormData tfd = delegateTask.getExecution().getProcessEngineServices().getFormService().
				getTaskFormData(delegateTask.getId());
		
		List<FormField> formFields = tfd.getFormFields();
		
		for(FormField fp : formFields) {
			System.out.println(fp.getId() + fp.getType());
			
			if(fp.getId().equals("naucnaOblast")) {
				
				EnumFormType enumFormType = (EnumFormType) fp.getType();
				
				Map<String, String> values = enumFormType.getValues();
				
				for(ScientificArea sa : saRepository.findAll()) {
					values.put(sa.getName(), sa.getName());
				}
			
			}
		}
	}

}

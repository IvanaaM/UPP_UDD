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

import com.ftn.model.Magazine;
import com.ftn.model.ScientificArea;
import com.ftn.services.MagazineService;

@Service
public class MagazinesHandler implements TaskListener {
	
	@Autowired
	MagazineService magazineService;

	@Override
	public void notify(DelegateTask delegateTask) {
		
		List<Magazine> magazines = magazineService.getAllMagazines();
		
		TaskFormData tfd = delegateTask.getExecution().getProcessEngineServices().getFormService().
				getTaskFormData(delegateTask.getId());
		
		List<FormField> formFields = tfd.getFormFields();
		
		for(FormField fp : formFields) {
			System.out.println(fp.getId() + fp.getType());
			
			if(fp.getId().equals("casopis")) {
				
				EnumFormType enumFormType = (EnumFormType) fp.getType();
				
				Map<String, String> values = enumFormType.getValues();
				
				for(Magazine sa : magazines) {
					values.put(sa.getName(), sa.getName());
				}
			
			}
		}
		
	}

}

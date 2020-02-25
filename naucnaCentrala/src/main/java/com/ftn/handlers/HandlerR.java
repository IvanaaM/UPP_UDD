package com.ftn.handlers;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import com.ftn.model.*;

@Service
public class HandlerR implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		List<FormSubmissionDto> list = (List<FormSubmissionDto>) delegateTask.getExecution().getProcessInstance().getVariable("recenzenti");
		
		ArrayList<String> r = new ArrayList<String>();
		
		for (FormSubmissionDto fd : list) {
			r.add(fd.getFieldId());
			System.out.println(fd.getFieldId());
		}
		
		System.out.println("Postavljena lista recenzenata");
		delegateTask.getExecution().getProcessInstance().setVariable("recenz", r);
	}

}

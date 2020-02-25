package com.ftn.handlers;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

@Service
public class HandlerSetRev implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {

		delegateTask.getExecution().getProcessInstance().setVariable("odabRec1", "");
		
		

	}

}

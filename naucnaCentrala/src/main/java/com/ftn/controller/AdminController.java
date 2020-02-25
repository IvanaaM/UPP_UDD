package com.ftn.controller;

import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.FormFieldsDto;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.modelDTO.UserDTO;
import com.ftn.repository.MagazineRepository;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;
import com.ftn.services.MagazineService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	
	@GetMapping(path = "/getRec/{procIn}", produces = "application/json")
	 public @ResponseBody FormFieldsDto get(@PathVariable String procIn) {
		
		
		System.out.println(procIn);
		
     List<FormSubmissionDto> registration = (List<FormSubmissionDto>)runtimeService.getVariable(procIn, "registration");
   
     Boolean rec = false;
     
     	for (FormSubmissionDto formField : registration) {
     			if(formField.getFieldId().equals("recenzent")) {
     				if(!formField.getFieldValue().equals("")) {
     				rec = true;
     				}
     			}	
     			
     	}

     	if (rec == true) {
        Task task = taskService.createTaskQuery().processInstanceId(procIn).taskDefinitionKey("Task_1gveupk").singleResult();
   
		System.out.println("Task je " + task.getId());
	
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			
			}
				
		return new FormFieldsDto(task.getId(), procIn, properties);
		
	} else {
		return null;
	}
	}
	
	@GetMapping(path = "/getUser/{procIn}", produces = "application/json")
	 public UserDTO getUser(@PathVariable String procIn) {
		
		String name = (String) runtimeService.getVariable(procIn, "username");
		 
	    return new UserDTO(name);
	}
	
	@GetMapping(path = "/getMagazine/{procIn}", produces = "application/json")
	 public Magazine getMagazine(@PathVariable String procIn) {
		
		String name = (String) runtimeService.getVariable(procIn, "naziv");
		
		Magazine magazine = magazineService.findByName(name);
		 
	    return magazine;
	}
	
	@PostMapping(path = "/saveRec/{taskId}", produces = "application/json")
	 public @ResponseBody ResponseEntity getUser(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		
	HashMap<String, Object> map = this.mapListToDto(dto);

	Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	String processInstanceId = task.getProcessInstanceId();
	runtimeService.setVariable(processInstanceId, "potvrdaZaRec", dto);
	formService.submitTaskForm(taskId, map);
	//taskService.complete(taskId);
	System.out.println("Zavrsio");
	

    return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private HashMap<String, Object> mapListToDto(List<FormSubmissionDto> list)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		for(FormSubmissionDto temp : list){
			System.out.println(temp.getFieldId());
			System.out.println(temp.getFieldValue());
			map.put(temp.getFieldId(), temp.getFieldValue());
		}
		
		return map;
	}
}

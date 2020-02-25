package com.ftn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import com.ftn.model.FormFieldsDto;
import com.ftn.model.FormSubmissionDto;
import com.ftn.modelDTO.ReviewPaper;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;

@RestController
@RequestMapping("/editor")
public class EditorController {
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	
	@GetMapping(path="/startProcessMagazine", produces="application/json") 
	public @ResponseBody FormFieldsDto getTask() {
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("ProcessKreiranjaCasopisa");
		
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
		
		System.out.println("Task je " + task.getId());
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
				
		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	
	}

	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
	@PostMapping(path = "/post/{taskId}/{type}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId, @PathVariable String type) {
		
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		System.out.println("Ovo je tip " + type);
		System.out.println("Ovo je proces " + taskId);
	
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, type, dto);
		formService.submitTaskForm(taskId, map);
		//taskService.complete(taskId);
		System.out.println("Zavrsio");
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
	@GetMapping(path = "/getTask/{procIn}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTaskNO(@PathVariable String procIn) {
			
		//String username = (String) runtimeService.getVariable(procIn, "glavniUrednik");
		
		Task task = taskService.createTaskQuery().processInstanceId(procIn).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), procIn, properties);
	
	}
	
	@GetMapping(path = "/get/{procIn}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTask(String procIn) {
	System.out.println("helo");
		Task task = taskService.createTaskQuery().processInstanceId(procIn).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), procIn, properties);
	
	}

	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR')")
	@GetMapping(path = "/getPaperData/{procIn}", produces = "application/json")
    public @ResponseBody Object getPaperData(@PathVariable String procIn) {
		
		return runtimeService.getVariable(procIn, "paper");
		
		
	}
	
	@GetMapping(path = "/getActiveInstances", produces = "application/json")
    public @ResponseBody List<FormFieldsDto> getInstances() {
	System.out.println("helo2");
	
	List<ProcessInstance> processInstances =
	        runtimeService.createProcessInstanceQuery()
	            .processDefinitionKey("Proces_obrade_podnetog_teksta").active().list();
	
	System.out.println("Aktivnih ima: " + processInstances.size());
	
	List<FormFieldsDto> lista = new ArrayList<FormFieldsDto>();
	
	for(ProcessInstance pi : processInstances) {
		// preuzimam sledeci dostupni task
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);

		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
		
		lista.add(new FormFieldsDto(task.getId(), pi.getId(), properties));
		
	}
	
	return lista;
	
	}
	
	@PostMapping(path = "/getPapersForIds", produces = "application/json")
    public @ResponseBody List<ReviewPaper> getPapers(@RequestBody List<String> list) {
	
	System.out.println("Velciinia liste: " + list.size());
	
	List<ReviewPaper> listRP = new ArrayList<ReviewPaper>();
	for(String taskId : list) {
		
		System.out.println("task je "+ taskId);

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		List<FormSubmissionDto> list2 = (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "paper");
		
		listRP.add(new ReviewPaper(taskId, list2));
		System.out.println(listRP.size());
	}
	
	return listRP;
	
	}
	
	
	@PreAuthorize("hasAuthority('ROLE_EDITOR')")
	@GetMapping(value = "/checkHasTasks/{procIn}", produces="application/json")
	public FormFieldsDto getTasks(@PathVariable String procIn) {
		
		String username1 = (String) runtimeService.getVariable(procIn, "user");
		
		System.out.println("Username1 je " + username1);
		
		
		
		String username2 = (String) runtimeService.getVariable(procIn, "glavniUrednik");
		
		System.out.println("Username2 je " + username2);
		
		
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(username2).list();
		
		System.out.println("Lista taskova ima " + tasks.size());
		
		
		TaskFormData tfd = formService.getTaskFormData(tasks.get(0).getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(tasks.get(0).getId(), procIn, properties);
	
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_USER')")
	@GetMapping(value = "/getCoauthors/{procIn}", produces="application/json")
	public Object getCoauthor(@PathVariable String procIn) {
		
		return runtimeService.getVariable(procIn, "coauthors");
		
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

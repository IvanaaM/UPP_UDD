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
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.ftn.model.ScientificArea;
import com.ftn.modelDTO.PdfDto;
import com.ftn.services.MagazineService;
import com.ftn.services.ScientificAreaService;

@RestController
@RequestMapping("/magazine")
public class MagazineController {
	
	@Autowired
	MagazineService magazineService;

	@Autowired
	ScientificAreaService saService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')  or hasAuthority('ROLE_ADMIN')" )
	@GetMapping(path="/getAll", produces="application/json")
	public @ResponseBody FormFieldsDto  getMagazines(){
		
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_obrade_podnetog_teksta");
		
		Authentication auth =  SecurityContextHolder. getContext(). getAuthentication();
		System.out.println("Ovo je username " + auth.getName());
		
		//System.out.println("Username iz autentif: " + k);
		
		runtimeService.setVariable(pi.getId(), "user", auth.getName());
		runtimeService.setVariable(pi.getId(), "coauthors", new ArrayList<String>());
		
		//Task task = taskService.createTaskQuery().taskAssignee((String) runtimeService.getVariable(pi.getId(), "user")).list().get(0);
		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).list().get(0);
				
		System.out.println("Task je " + task.getId());
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
				
		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')  or hasAuthority('ROLE_ADMIN')" )
	@GetMapping(path = "/getMagazines", produces = "application/json")
	public List<PdfDto> getM() {
		
		List<PdfDto> listM = new ArrayList<PdfDto>();
		
		for (Magazine m : magazineService.getAllMagazines()) {
			
			listM.add(new PdfDto(m.getName()));
			
		}
		
		return listM;
	}
	
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')  or hasAuthority('ROLE_ADMIN')" )
	@GetMapping(path = "/getAreas", produces = "application/json")
	public List<PdfDto> getAreas() {
		
		List<PdfDto> listM = new ArrayList<PdfDto>();
		
		for (ScientificArea sa : saService.getSA()) {
			
			listM.add(new PdfDto(sa.getName()));
			
		}
		
		return listM;
	}
	
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
	
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')  or hasAuthority('ROLE_ADMIN')" )
	@PostMapping(path = "/postForPdf/{pI}", produces = "application/json")
    public @ResponseBody ResponseEntity postForPdf(@RequestBody PdfDto pd, @PathVariable String pI) {
		
		
		System.out.println("Ovo je proces " + pI);
		
	//	TypedValue typedTransientObjectValue = Variables.objectValue(pd, true).create();	
	    runtimeService.setVariable(pI, "pdfUrl", pd);
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')")
	@GetMapping(path = "/getPdfCheck/{taskId}", produces = "application/json")
    public @ResponseBody Object getPdf(@PathVariable String taskId) {
		
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		return runtimeService.getVariable(processInstanceId, "pdfUrl");
		
    }
	
	@GetMapping(path = "/get/{procIn}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTask(@PathVariable String procIn) {
	System.out.println("helo");
	System.out.println("Ovo je id: " + procIn);
		Task task = taskService.createTaskQuery().processInstanceId(procIn).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), procIn, properties);
	
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

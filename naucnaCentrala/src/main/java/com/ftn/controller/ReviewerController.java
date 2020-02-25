package com.ftn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.model.FormFieldsDto;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.UserCustom;
import com.ftn.modelDTO.ReviewsDTO;
import com.ftn.services.MagazineService;
import com.ftn.services.UserService;

@RestController
@RequestMapping("/reviewer")
public class ReviewerController {
	
	@Autowired
	MagazineService magazineService;

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FormService formService;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	private RuntimeService runtimeService;
	
	
	@PreAuthorize("hasAuthority('ROLE_REVIEWER')")
	@GetMapping(path = "/getTask/{procIn}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTask(@PathVariable String procIn) {
			
		System.out.println("Instanca je " + procIn);
		
		Authentication auth =  SecurityContextHolder. getContext(). getAuthentication();
		System.out.println("Ovo je ulogovani " + auth.getName());
		
		Task task = taskService.createTaskQuery().taskAssignee(auth.getName()).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), procIn, properties);
		
	}
	
	@PreAuthorize("hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_USER')")
	@GetMapping(path = "/getReviews/{procIn}", produces = "application/json")
    public @ResponseBody List<ReviewsDTO> getReviews(@PathVariable String procIn) {
			
		System.out.println("Instanca je " + procIn);
		
		List<ReviewsDTO> list = new ArrayList<ReviewsDTO>();
		
		Authentication auth =  SecurityContextHolder. getContext(). getAuthentication();
		System.out.println("Ovo je ulogovani " + auth.getName());
		
		List<String> recenz = (List<String>) runtimeService.getVariable(procIn, "recenz");
		
		for (String rec : recenz) {
			
			ReviewsDTO rd = (ReviewsDTO) runtimeService.getVariable(procIn, rec+"+Rec");
			
			list.add(rd);
			System.out.println("ubacio");
			
		}
		
		return list;
		
		
	}
	
	@PostMapping(path = "/post/{taskId}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId) {
		
		ReviewsDTO rd = new ReviewsDTO();
		Authentication auth =  SecurityContextHolder. getContext(). getAuthentication();
		
		String username = auth.getName();
		System.out.println("Ovo je ulogovan " + username);
		
		UserCustom uc = userService.findByUsername(username);
		
		rd.setName(uc.getFirstName() + " " + uc.getLastName());
		
		for (FormSubmissionDto fsd : dto) {
			if (fsd.getFieldId().equals("komentarRad")) {
				rd.setComments(fsd.getFieldValue());
				System.out.println("Komentar je " + fsd.getFieldValue());
				
			}
			if (fsd.getFieldId().equals("preporukaRad")) {
				rd.setRecomm(fsd.getFieldValue());
				System.out.println("Preporuka je " + fsd.getFieldValue());
				
			}
			
			if (fsd.getFieldId().equals("komentarU")) {
				rd.setCommForEditor(fsd.getFieldValue());
				System.out.println("Komentar za urednika je " + fsd.getFieldValue());
				
			}
		}
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		
		System.out.println("Ovo je proces " + taskId);
	
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		
		runtimeService.setVariable(processInstanceId, username+"+Rec", rd);
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

package com.ftn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
//import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.enums.ChargeType;
import com.ftn.model.FormFieldsDto;
import com.ftn.model.FormSubmissionDto;
import com.ftn.model.Magazine;
import com.ftn.model.Role;
import com.ftn.modelDTO.Login;
import com.ftn.modelDTO.LoginDTO;
import com.ftn.modelDTO.PdfDto;
import com.ftn.model.UserCustom;
import com.ftn.model.UserToken;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.UserRepository;
import com.ftn.security.TokenUtils;
import com.ftn.services.MagazineService;
import com.ftn.services.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	private static String processInstanceString = "";
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	private RuntimeService runtimeService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private UserService userService;

	@Autowired
	private MagazineService magazineService;
		
	public UserController() {
		
	}
	
	@GetMapping(path = "/startProcessReg", produces = "application/json")
    public @ResponseBody FormFieldsDto get() {
		
		//provera da li korisnik sa id-jem pera postoji
		//List<User> users = identityService.createUserQuery().userId("pera").list();
		ProcessInstance pi = runtimeService.startProcessInstanceByKey("Proces_Registracije");
		
		processInstanceString = pi.getId();

		Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).taskDefinitionKey("TaskRegistracija").singleResult();
		
		System.out.println("Task je " + task.getId());
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
				
		return new FormFieldsDto(task.getId(), pi.getId(), properties);
	
	}
	

	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')  or hasAuthority('ROLE_ADMIN')" )
	@GetMapping(path = "/getTask/{procIn}", produces = "application/json")
    public @ResponseBody FormFieldsDto getTask(@PathVariable String procIn) {
			
		System.out.println("Instanca je " + procIn);
		
		
		if (runtimeService.getVariable(procIn, "user") == null) {
				return null;
		} else {
				
		String username1 = (String) runtimeService.getVariable(procIn, "user");
							
		Task task = taskService.createTaskQuery().taskAssignee(username1).list().get(0);
		
		TaskFormData tfd = formService.getTaskFormData(task.getId());
		List<FormField> properties = tfd.getFormFields();
			for(FormField fp : properties) {
				System.out.println(fp.getId() + fp.getType());
			}
		
		return new FormFieldsDto(task.getId(), procIn, properties);
		
				}
		
	}

	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')  or hasAuthority('ROLE_ADMIN')" )
	@PostMapping(path = "/post/{taskId}/{type}", produces = "application/json")
    public @ResponseBody ResponseEntity post(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId, @PathVariable String type) {
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		runtimeService.setVariable(processInstanceId, type, dto);
		
		if(type.equals("chooseMagazine")) {
			
			String username = (String) runtimeService.getVariable(processInstanceId, "user");
							
			List<FormSubmissionDto>  fd= (List<FormSubmissionDto>) runtimeService.getVariable(processInstanceId, "chooseMagazine");
			
			Magazine m = magazineService.findByName(fd.get(0).getFieldValue());	
			
			UserCustom uc = userService.findByUsername(username);
			
			runtimeService.setVariable(processInstanceId, "active", uc.isFee());
		
			if(m.getChargeType().equals(ChargeType.Autorima)) {

				runtimeService.setVariable(processInstanceId, "oa", true);
			} else {
				runtimeService.setVariable(processInstanceId, "oa", false);
				
			}

		}

		formService.submitTaskForm(taskId, map);
		System.out.println("Zavrsio");
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_EDITOR') or hasAuthority('ROLE_REVIEWER')  or hasAuthority('ROLE_ADMIN')" )
	@PostMapping(path = "/postCoauthor/{taskId}/{type}", produces = "application/json")
    public @ResponseBody ResponseEntity postCoauthor(@RequestBody List<FormSubmissionDto> dto, @PathVariable String taskId, @PathVariable String type) {
		
		HashMap<String, Object> map = this.mapListToDto(dto);
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
	
		List<String> list = (List<String>) runtimeService.getVariable(processInstanceId, "coauthors");
		
		list.add(type);
		
		runtimeService.setVariable(processInstanceId, "coauthors", list);
		String var = type + "coa";
		System.out.println("ubacujem varibalu " + var);
		
		runtimeService.setVariable(processInstanceId, var, dto);
				
		formService.submitTaskForm(taskId, map);
		System.out.println("Zavrsio");
		
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping(path="/confirmMail", produces="application/json")
	public String confirmNotification() {
		
		Execution execution = runtimeService.createExecutionQuery().
				processInstanceId(processInstanceString).activityId("Prijem").singleResult();
		
		runtimeService.signal(execution.getId());
		
		return "Uspesna registracija!";
	}
	
	@PostMapping(path="/login", produces="application/json")
	public  ResponseEntity<?>  login(@RequestBody LoginDTO loginDTO, HttpSession session) {
		
		System.out.println("usao login " + loginDTO.getUsername());
		
		User u = identityService.createUserQuery().userId(loginDTO.getUsername()).singleResult();

			System.out.println(u);
			
			UserCustom user = userService.findByUsername(loginDTO.getUsername());
		
			if (BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
				
				try{
					UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
							loginDTO.getPassword());
					
					Authentication auth = authManager.authenticate(authReq);
					
					String username = authReq.getName();
					
					System.out.println("username je sledeci" + username);
					
					String token = tokenUtils.generateToken(auth);
					
					long expiresIn = tokenUtils.getExpiredIn();

					return new ResponseEntity<>(new UserToken(token,expiresIn), HttpStatus.OK);
					}catch (Exception e) {
						e.printStackTrace();
						return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
					}
							
		} else {

			return new ResponseEntity<>(new UserToken(), HttpStatus.NOT_FOUND);
		}
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
	
	
	@GetMapping(value = "/getRoles", produces="application/json")
	public List<String> getRoles(ServletRequest request) throws Exception {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
	    
		String token = httpRequest.getHeader("token");
		
		token = token.substring(1,token.length()-1).toString();
		
		ArrayList<String> list = new ArrayList<String>();
		
		tokenUtils.getUserSecurity(token.toString()).getAuthorities().forEach(e -> {
			
			if(e.toString().equals("ROLE_USER")) {

				list.add("U");	
			} 
			if (e.toString().equals("ROLE_EDITOR")) {

				list.add("E");	
			} 
			if (e.toString().equals("ROLE_REVIEWER")) {

				list.add("R");	
			}
			if (e.toString().equals("ROLE_ADMIN")) {

				list.add("A");	
			}
 
		});

		return list;
	}

	@GetMapping(value = "/shouldPay/{procIn}", produces="application/json")
	public boolean userShouldPay(@PathVariable String procIn) throws Exception {

		Boolean oa = (Boolean) runtimeService.getVariable(procIn, "oa");
		System.out.println("OA je" + oa);
		
		Boolean active = (Boolean) runtimeService.getVariable(procIn, "active");
		System.out.println("Acctive je" + active);
				
		
		if (oa == true) {
			if (active == false) {
				return true;
			} else {
				return false;
			}
		} else { 
			return false;
		}
		}
	
	@PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_REVIEWER')")
	@GetMapping(path = "/getEditorCom/{procIn}", produces = "application/json")
    public @ResponseBody Object getPdf(@PathVariable String procIn) {
		
		List<FormSubmissionDto> fsd = (List<FormSubmissionDto>) runtimeService.getVariable(procIn, "deadlines");
		String komentar = "";
		for(FormSubmissionDto f : fsd) {
			if(f.getFieldId().equals("komentar")) {
				komentar = f.getFieldValue();
			}
			
		}
		
		PdfDto pd = new PdfDto(komentar);
		
		return pd;
		
    }
}

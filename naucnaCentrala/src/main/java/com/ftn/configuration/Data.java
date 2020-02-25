package com.ftn.configuration;

import java.util.List;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.ftn.enums.RoleName;
import com.ftn.model.Magazine;
import com.ftn.model.Role;
import com.ftn.model.UserCustom;
import com.ftn.repository.RoleRepository;
import com.ftn.repository.ScientificAreaRepository;
import com.ftn.repository.UserRepository;
import com.ftn.services.MagazineService;

@Component
public class Data implements ApplicationRunner {

	@Autowired
	IdentityService identityService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ScientificAreaRepository saRepository;
	
	@Autowired
	MagazineService magazineService;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		// da li je admin tu -> admin-marko
		List<User> users = identityService.createUserQuery().userIdIn("marko").list();
		if(users.isEmpty() ) {
			
			User admin = identityService.newUser("marko");
			admin.setEmail("marko@mail.com");
			admin.setFirstName("Marko");
			admin.setLastName("Maric");
			admin.setPassword("marko");
			identityService.saveUser(admin);
		}
			

			List<User> users2 = identityService.createUserQuery().userIdIn("anja").list();
			
			if (users2.isEmpty()) {
				
				User ured = identityService.newUser("anja");
				ured.setEmail("anja@mail.com");
				ured.setFirstName("Anja");
				ured.setLastName("Anjic");
				ured.setPassword("anja");
				identityService.saveUser(ured);
				
			}
			
			List<User> users3 = identityService.createUserQuery().userIdIn("maja").list();
			if(users3.isEmpty() ) {
				
				User ured2 = identityService.newUser("maja");
				ured2.setEmail("maja@mail.com");
				ured2.setFirstName("Maja");
				ured2.setLastName("Majic");
				ured2.setPassword("maja");
				identityService.saveUser(ured2);
			}
			
			List<User> users4 = identityService.createUserQuery().userIdIn("ivana").list();
			if(users4.isEmpty() ) {
				
				User ured3 = identityService.newUser("ivana");
				ured3.setEmail("ivana@mail.com");
				ured3.setFirstName("Ivana");
				ured3.setLastName("Ivanovic");
				ured3.setPassword("ivana");
				identityService.saveUser(ured3);
			}
			
			List<User> users5 = identityService.createUserQuery().userIdIn("dunja").list();
			if(users5.isEmpty() ) {
				
				User ured4 = identityService.newUser("dunja");
				ured4.setEmail("dunja@mail.com");
				ured4.setFirstName("Dunja");
				ured4.setLastName("Dunjic");
				ured4.setPassword("dunja");
				identityService.saveUser(ured4);
			}
			
			List<User> users6 = identityService.createUserQuery().userIdIn("sara").list();
			if(users6.isEmpty() ) {
				
				User ured6 = identityService.newUser("sara");
				ured6.setEmail("sara@mail.com");
				ured6.setFirstName("Sara");
				ured6.setLastName("Saric");
				ured6.setPassword("sara");
				identityService.saveUser(ured6);
			}
			
			
			UserCustom uc4 = new UserCustom();
			
			uc4.setFirstName("Ivana");
			uc4.setLastName("Ivanovic");
			uc4.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc4.setActive(true);
			uc4.setState("Srbija");
			uc4.setCity("Novi Sad");
			uc4.setTitle("T6");
			
			uc4.addArea(saRepository.findByName("Matematika"));
			uc4.addArea(saRepository.findByName("Medicina"));
			uc4.addArea(saRepository.findByName("Informatika"));
			
			Magazine m = magazineService.findByName("Nacionalna geografija");
			Magazine m2 = magazineService.findByName("Matematika u nauci");
			
			
			String salt4 = BCrypt.gensalt();

			String passwordHashed4 = BCrypt.hashpw("ivana", salt4);
			uc4.setPassword(passwordHashed4);
			uc4.setEmail("ivana@mail.com");
			uc4.setUsername("ivana");
			uc4.setEnabled(true);
			uc4.setNonLocked(true);
			uc4.setFee(true);
			
			m2.getReviewers().add(userRepository.save(uc4));
			m.getReviewers().add(userRepository.save(uc4));
			
			UserCustom uc5 = new UserCustom();
			
			uc5.setFirstName("Sara");
			uc5.setLastName("Saric");
			uc5.addRole(roleRepository.findByName(RoleName.ROLE_EDITOR));
			uc5.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc5.setActive(true);
			uc5.setState("Srbija");
			uc5.setCity("Novi Sad");
			uc5.setTitle("T3");
		
			uc5.addArea(saRepository.findByName("Medicina"));
			uc5.addArea(saRepository.findByName("Matematika"));
			
			String salt5 = BCrypt.gensalt();

			String passwordHashed5 = BCrypt.hashpw("sara", salt5);
			uc5.setPassword(passwordHashed5);
			uc5.setEmail("sara@mail.com");
			uc5.setUsername("sara");
			uc5.setEnabled(true);
			uc5.setNonLocked(true);
			uc5.setFee(true);
			m2.getReviewers().add(userRepository.save(uc5));
			
			
			UserCustom uc6 = new UserCustom();
			
			uc6.setFirstName("Dunja");
			uc6.setLastName("Dunjic");
			uc6.addRole(roleRepository.findByName(RoleName.ROLE_EDITOR));
			uc6.addRole(roleRepository.findByName(RoleName.ROLE_REVIEWER));
			uc6.setActive(true);
			uc6.setState("Srbija");
			uc6.setCity("Novi Sad");
			uc6.setTitle("T9");
		
			uc6.setEnabled(true);
			uc6.setNonLocked(true);
			uc6.setFee(true);
			uc6.addArea(saRepository.findByName("Geografija"));
			uc6.addArea(saRepository.findByName("Informatika"));
			
			
			String salt6 = BCrypt.gensalt();

			String passwordHashed6 = BCrypt.hashpw("dunja", salt6);
			uc6.setPassword(passwordHashed6);
			uc6.setEmail("dunja@mail.com");
			uc6.setUsername("dunja");
			
			m2.getReviewers().add(userRepository.save(uc6));
			m.getReviewers().add(userRepository.save(uc6));
			m2.getEditors().add(userRepository.save(uc6));
					
			
			magazineService.saveMagazine(m);
			magazineService.saveMagazine(m2);
			
			UserCustom uc7 = new UserCustom();
			
			uc7.setFirstName("Mara");
			uc7.setLastName("Maric");
			uc7.addRole(roleRepository.findByName(RoleName.ROLE_USER));
			uc7.setActive(true);
			uc7.setState("Srbija");
			uc7.setCity("Novi Sad");
			uc7.setTitle("T9");
		
			uc7.setEnabled(true);
			uc7.setNonLocked(true);
			uc7.setFee(true);
			uc7.addArea(saRepository.findByName("Geografija"));
			uc7.addArea(saRepository.findByName("Informatika"));
			
			String salt7 = BCrypt.gensalt();

			String passwordHashed7 = BCrypt.hashpw("mara", salt7);
			uc7.setPassword(passwordHashed7);
			uc7.setEmail("mara@mail.com");
			uc7.setUsername("mara");
			
			userRepository.save(uc7);
	}
	
	


}

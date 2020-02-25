package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.enums.RoleName;
import com.ftn.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByName(RoleName name);

}

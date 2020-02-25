package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.UserCustom;

@Repository
public interface UserRepository extends JpaRepository<UserCustom, Long> {

	 UserCustom findByUsername(String username);

	
}

package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Magazine;

@Repository
public interface MagazineRepository extends JpaRepository<Magazine, Long> {
	
	Magazine findByName(String name);

}

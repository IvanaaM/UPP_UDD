package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.ScientificArea;

@Repository
public interface ScientificAreaRepository extends JpaRepository<ScientificArea, Long> {
	
	ScientificArea findByName(String name);

}

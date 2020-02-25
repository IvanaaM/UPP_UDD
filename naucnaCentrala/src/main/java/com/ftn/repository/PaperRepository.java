package com.ftn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.model.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long>{

	Paper findByTitle(String title);
	
}

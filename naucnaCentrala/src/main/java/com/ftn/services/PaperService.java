package com.ftn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Paper;
import com.ftn.repository.PaperRepository;

@Service
public class PaperService {

	@Autowired
	PaperRepository paperRepository;
	
	public void savePaper(Paper p) {
		paperRepository.save(p);
	}
	
	public Paper getPaper(String name) {
		return paperRepository.findByTitle(name);
	}


}

package com.ftn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.CoauthorOther;
import com.ftn.repository.CoauthorOtherRepository;

@Service
public class CoauthorOtherService {
	
	@Autowired
	CoauthorOtherRepository coRepository;
	
	public CoauthorOther saveCO(CoauthorOther co) {
		return coRepository.save(co);
	}

}

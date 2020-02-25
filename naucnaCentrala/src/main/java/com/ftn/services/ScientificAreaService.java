package com.ftn.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Magazine;
import com.ftn.model.ScientificArea;
import com.ftn.repository.ScientificAreaRepository;

@Service
public class ScientificAreaService {

	@Autowired
	ScientificAreaRepository saRepository;

	public ScientificArea findByName(String fieldValue) {
	
		return saRepository.findByName(fieldValue);
	}
	
	public List<ScientificArea> getSA() {
		
		List<ScientificArea> sa = saRepository.findAll();
		
		sa.sort(Comparator.comparing(ScientificArea::getName));
		
		return sa;
	}
	
}

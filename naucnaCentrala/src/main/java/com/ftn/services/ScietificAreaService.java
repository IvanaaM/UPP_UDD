package com.ftn.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.repository.ScientificAreaRepository;

@Service
public class ScietificAreaService {

	@Autowired
	ScientificAreaRepository saRepository;
}

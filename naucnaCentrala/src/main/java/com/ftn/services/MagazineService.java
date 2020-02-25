package com.ftn.services;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.model.Magazine;
import com.ftn.model.UserCustom;
import com.ftn.repository.MagazineRepository;

@Service
public class MagazineService {
	
	@Autowired
	MagazineRepository magazineRepository;

	public Magazine findByName(String naziv) {

		return magazineRepository.findByName(naziv);
	}
	
	public Magazine saveMagazine(Magazine magazine) {
		return magazineRepository.save(magazine);
	}

	public List<Magazine> getAllMagazines() {
		
		List<Magazine> magazines = magazineRepository.findAll();
		
		magazines.sort(Comparator.comparing(Magazine::getName));
		
		return magazines;
	}

	public UserCustom findBySA(Magazine m, String no) {
		
		
		for(UserCustom uc :  m.getEditors()) {
			if(uc.chechIfAreaExists(no) == true) {
				return uc;
			}
		}

		return m.getMainEditor();

	}


}

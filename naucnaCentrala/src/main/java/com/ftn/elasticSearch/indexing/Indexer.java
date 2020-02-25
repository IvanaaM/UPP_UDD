package com.ftn.elasticSearch.indexing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.elasticSearch.model.IndexUnit;
import com.ftn.elasticSearch.repository.ESPaperRepository;
import com.ftn.model.Paper;

@Service
public class Indexer {
	
	@Autowired
	ESPaperRepository repository;
	
	public Indexer() {
	}
	
	
	public boolean delete(String filename){
		if(repository.equals(filename)){
			//repository.delete(filename);
			return true;
		} else
			return false;
		
	}
	
	public boolean update(IndexUnit unit){
		unit = repository.save(unit);
		if(unit!=null)
			return true;
		else
			return false;
	}
	
	public void add(IndexUnit unit){
		repository.index(unit);
		
	}
	
	/**
	 * 
	 * @param file Direktorijum u kojem se nalaze dokumenti koje treba indeksirati
	 */
	public void index(Paper paper, String pdfName){	
		
			try {
			
			HandlerPaper hp = new HandlerPaper();
			
			IndexUnit unit = hp.prepareForIndex(paper, pdfName);
			
			add(unit);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("indexing NOT done");
		}
	}
	
}

package com.ftn.elasticSearch.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.ftn.elasticSearch.model.IndexUnit;

@Repository
public interface ESPaperRepository extends ElasticsearchRepository<IndexUnit, String> {
	
	//List<IndexUnit> findByTitle(String title);



}

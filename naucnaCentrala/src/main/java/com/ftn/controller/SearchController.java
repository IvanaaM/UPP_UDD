package com.ftn.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.elasticSearch.model.IndexUnit;
import com.ftn.elasticSearch.model.QueryBuilder;
import com.ftn.elasticSearch.model.RequiredHighlight;
import com.ftn.elasticSearch.model.ResultData;
import com.ftn.elasticSearch.model.SearchType;
import com.ftn.elasticSearch.model.SimpleQuery;
import com.ftn.elasticSearch.repository.ESPaperRepository;
import com.ftn.elasticSearch.search.ResultRetriever;
import com.ftn.modelDTO.SearchDTO;

@RestController
@RequestMapping("/search")
public class SearchController {
	
	@Autowired
	ESPaperRepository repository;
	
	@Autowired
	private ResultRetriever resultRetriever;
		

	
	@PostMapping(value="/search", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchBoolQuery(@RequestBody List<SimpleQuery> simpleQueryList) throws Exception {
	
		boolean operation = true;
		HashMap<org.elasticsearch.index.query.QueryBuilder, Boolean> listQueries = new HashMap<org.elasticsearch.index.query.QueryBuilder, Boolean>();
		List<RequiredHighlight> rh = new ArrayList<RequiredHighlight>();

		for(SimpleQuery sq : simpleQueryList) {
			
			if(sq.getValue().equals("")) {
				
				continue;
				
			} else {
				
				if(sq.getOperation() == true) {
					operation = true;
				} else {
					operation = false;
				}
				System.out.println("usao");
				
				rh.add(new RequiredHighlight(sq.getField(), sq.getValue()));
				
				if (sq.isPhrase() == true) {
					
					listQueries.put(QueryBuilder.buildQuery(SearchType.phrase, sq.getField(), sq.getValue()), operation);
					
				} else {
					listQueries.put(QueryBuilder.buildQuery(SearchType.regular, sq.getField(), sq.getValue()), operation);
					
				}
			}
		}
		
		BoolQueryBuilder builder = QueryBuilders.boolQuery();
		
		for (Entry<org.elasticsearch.index.query.QueryBuilder, Boolean> entry : listQueries.entrySet()) {

			if (entry.getValue() == true) {
				builder.must(entry.getKey());
			} else {
				builder.should(entry.getKey());
			}
		}
		

        HighlightBuilder hb = new HighlightBuilder();
        
        for (RequiredHighlight r : rh) {
        	hb.field(r.getFieldName());
        }
		
		
		List<ResultData> results = resultRetriever.getResults(builder, hb);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
		
	}
	
	
	@GetMapping(path="/getAll", produces="application/json")
	public List<IndexUnit> getAll(){
			
	List<IndexUnit> li = new ArrayList<IndexUnit>();
		
	for (IndexUnit s : repository.findAll()) {
		li.add(s);
		repository.save(s);
	}
	
	return li;
		
	}

	
	
}

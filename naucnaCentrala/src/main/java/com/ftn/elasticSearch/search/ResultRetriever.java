package com.ftn.elasticSearch.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.ftn.elasticSearch.model.IndexUnit;
import com.ftn.elasticSearch.model.ResultData;
import com.ftn.elasticSearch.model.HighlighterResult;


@Service
public class ResultRetriever {
	
private ElasticsearchTemplate template;
	
	@Autowired
	public ResultRetriever(ElasticsearchTemplate template){
		this.template = template;
	}
	
	public List<ResultData> getResults(org.elasticsearch.index.query.QueryBuilder query, HighlightBuilder builderH) {
		if (query == null) {
			return null;
		}
		
			
		List<ResultData> results = new ArrayList<ResultData>();
		
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
				.withQuery(query)
				.build();
        
        
        List<IndexUnit> indexUnits = template.queryForList(searchQuery, IndexUnit.class);
        
        
        
        SearchRequest sr = new SearchRequest("digitallibrary7");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(query).highlighter(builderH);
        sr.source(searchSourceBuilder);
        System.out.println("usao ovde 1");
                
        ActionFuture<SearchResponse> searchResponse =  template.getClient().search(sr);
        SearchResponse sresp = searchResponse.actionGet();
        
        SearchHits sh = sresp.getHits();
     
        List<ResultData> rddata = new ArrayList<ResultData>();
        
        for(SearchHit hit: sh.getHits()) {
        	System.out.println("zatim ovde");

        	Map<String, Object> fields = hit.getSource();
        	
        	ResultData rdaa = new ResultData();
        	for (Entry<String, Object> entry : fields.entrySet()) {
        			
        		if(entry.getKey().equals("magazine")) {
        			rdaa.setMagazine(entry.getValue().toString());
        			continue;
        		}
        		if(entry.getKey().equals("title")) {
        			rdaa.setTitle(entry.getValue().toString());
        			continue;
        		}
        		if(entry.getKey().equals("area")) {
        			rdaa.setArea(entry.getValue().toString());
        			continue;
        		}
        		if(entry.getKey().equals("firstNameAuthor")) {
        			rdaa.setFirstNameAuthor(entry.getValue().toString());
        			continue;
        		}
        		if(entry.getKey().equals("lastNameAuthor")) {
        			rdaa.setLastNameAuthor(entry.getValue().toString());
        			continue;
        		}
        		if(entry.getKey().equals("keywords")) {
        			rdaa.setKeywords(entry.getValue().toString());
        			continue;
        		}
        		if(entry.getKey().equals("text")) {
        			rdaa.setText(entry.getValue().toString());
        			continue;
        		}
        		
        		
        		
        	}
        	
        	
        	Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        	
        	HighlighterResult hr = new HighlighterResult();
        	
        	String title="";
        	String text ="";
        	
        	for(String key : highlightFields.keySet()) {
        		
        		
        		if(key.equals("title")) {
        			if(highlightFields.get("title").fragments().length != 0) {
        				
        				for(int i=0; i<highlightFields.get("title").fragments().length; i++) {
    						
        					title +=  highlightFields.get("title").fragments()[i];
        				}
        				
        				hr.setTitle(title);
        				rdaa.setHighl(hr);
        				continue;
        				
        			}
        		}
        		
        		if (key.equals("text")) {

        			if(highlightFields.get("text").fragments().length != 0) {
        				
        				for(int i=0; i<highlightFields.get("text").fragments().length; i++) {
        						
        					text +=  highlightFields.get("text").fragments()[i];
        				}
        				hr.setText(text);
        				rdaa.setHighl(hr);
        				continue;
        					
        			}
        			
        		}
        		
        		if(key.equals("area")) {
        			if(highlightFields.get("area").fragments().length != 0) {
        				hr.setArea(highlightFields.get("area").fragments()[0].string());
        				rdaa.setHighl(hr);
        				continue;
        				
        			}
        		}
        		
        		if(key.equals("keywords")) {
        			if(highlightFields.get("keywords").fragments().length != 0) {
        				hr.setKeywords(highlightFields.get("keywords").fragments()[0].string());
        				rdaa.setHighl(hr);
        				continue;
        				
        			}
        		}
        		
        		if(key.equals("magazine")) {
        			if(highlightFields.get("magazine").fragments().length != 0) {
        				hr.setMagazine(highlightFields.get("magazine").fragments()[0].string());
        				rdaa.setHighl(hr);
        				continue;
        				
        			}
        		}

        		
        	}
        	
        	rddata.add(rdaa);
        }
        /*
        for (IndexUnit indexUnit : indexUnits) {
        	results.add(new ResultData(indexUnit.getMagazine(), indexUnit.getTitle(), 
        			indexUnit.getKeywords(), indexUnit.getAuthor(), indexUnit.getArea(), 
        			indexUnit.getText(), indexUnit.getFilename(), null));
		}
        */
		
		return rddata;
	}
	
	
	
	
}

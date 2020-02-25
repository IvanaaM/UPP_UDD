package com.ftn.elasticSearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;


@Document(indexName = IndexUnit.INDEX_NAME, type = IndexUnit.TYPE_NAME, shards = 1, replicas = 0)
public class IndexUnit {

	public static final String INDEX_NAME = "digitallibrary7";
	public static final String TYPE_NAME = "paper";
	
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	
	
	@Field(type = FieldType.Text, analyzer = "serbian", searchAnalyzer = "serbian")
	private String magazine;
	
	@Field(type = FieldType.Text, analyzer = "serbian", searchAnalyzer = "serbian")
	private String title;
	
	@Field(type = FieldType.Text, analyzer = "serbian", searchAnalyzer = "serbian")
	private String keywords;
	
	@Field(type = FieldType.Text, analyzer = "serbian", searchAnalyzer = "serbian")
	private String firstNameAuthor;
	
	@Field(type = FieldType.Text, analyzer = "serbian", searchAnalyzer = "serbian")
	private String lastNameAuthor;
	
	@Field(type = FieldType.Text, analyzer = "serbian", searchAnalyzer = "serbian")
	private String area;
	
	@Field(type = FieldType.Text, analyzer = "serbian", searchAnalyzer = "serbian")
	private String text;
	
	@Id
	@Field(type = FieldType.Text)
	private String filename;
	
	//@Field(type = FieldType.Text)
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
	//private String filedate;

	
	public String getMagazine() {
		return magazine;
	}

	public void setMagazine(String magazine) {
		this.magazine = magazine;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	
	public String getFirstNameAuthor() {
		return firstNameAuthor;
	}

	public void setFirstNameAuthor(String firstNameAuthor) {
		this.firstNameAuthor = firstNameAuthor;
	}

	public String getLastNameAuthor() {
		return lastNameAuthor;
	}

	public void setLastNameAuthor(String lastNameAuthor) {
		this.lastNameAuthor = lastNameAuthor;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
/*
	public String getFiledate() {
		return filedate;
	}

	public void setFiledate(String filedate) {
		this.filedate = filedate;
	}
	*/
	
}

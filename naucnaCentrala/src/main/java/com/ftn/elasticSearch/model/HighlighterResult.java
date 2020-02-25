package com.ftn.elasticSearch.model;

public class HighlighterResult {

	private String text;
	private String title;
	private String keywords;
	private String area;
	private String magazine;

	public HighlighterResult() {

	}
		
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getMagazine() {
		return magazine;
	}

	public void setMagazine(String magazine) {
		this.magazine = magazine;
	}
	
	
}

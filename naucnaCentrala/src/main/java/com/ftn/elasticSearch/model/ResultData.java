package com.ftn.elasticSearch.model;

public final class ResultData {
	
	private String magazine;
	private String title;
	private String keywords;
	private String firstNameAuthor;
	private String lastNameAuthor;
	private String area;
	private String text;
	private String filename;
	private HighlighterResult highl;
	
	public ResultData() {
		super();
	}

	public ResultData(String magazine, String title, String keywords, String firstNameAuthor, String lastNameAuthor,
			String area, String text, String filename, HighlighterResult highl) {
		super();
		this.magazine = magazine;
		this.title = title;
		this.keywords = keywords;
		this.firstNameAuthor = firstNameAuthor;
		this.lastNameAuthor = lastNameAuthor;
		this.area = area;
		this.text = text;
		this.filename = filename;
		this.highl = highl;
	}

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

	public HighlighterResult getHighl() {
		return highl;
	}

	public void setHighl(HighlighterResult highl) {
		this.highl = highl;
	}
	
	
}

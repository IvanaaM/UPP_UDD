package com.ftn.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Paper")
public class Paper {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Title")
	private String title;
	
	@Column(name="DOI")
	private int doi;
	
	@Column(name="Keywords")
	private String keywords;
	
	@Column(name="Apstract")
	private String apstract;
	
	@ManyToOne
	private ScientificArea scientificArea;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<CoauthorOther> coauthorsOthers = new HashSet<CoauthorOther>();
	
	//@Column(columnDefinition="LONGBLOB")
	@Lob
	private String pathPdf;
	
	@ManyToOne
	private UserCustom author;
	
	@ManyToOne
	private Magazine magazine;

	public Paper() {

	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getApstract() {
		return apstract;
	}

	public void setApstract(String apstract) {
		this.apstract = apstract;
	}

	public ScientificArea getScientificArea() {
		return scientificArea;
	}

	public void setScientificArea(ScientificArea scientificArea) {
		this.scientificArea = scientificArea;
	}

	public String getPathPdf() {
		return pathPdf;
	}

	public void setPathPdf(String pathPdf) {
		this.pathPdf = pathPdf;
	}

	public UserCustom getAuthor() {
		return author;
	}

	public void setAuthor(UserCustom author) {
		this.author = author;
	}

	public Magazine getMagazine() {
		return magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}

	public Set<CoauthorOther> getCoauthorsOthers() {
		return coauthorsOthers;
	}

	public void setCoauthorsOthers(Set<CoauthorOther> coauthorsOthers) {
		this.coauthorsOthers = coauthorsOthers;
	}

	public int getDoi() {
		return doi;
	}

	public void setDoi(int doi) {
		this.doi = doi;
	}
	
	
	
}

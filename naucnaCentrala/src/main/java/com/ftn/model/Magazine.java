package com.ftn.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ftn.enums.ChargeType;
import com.ftn.model.PaymentType;

@Entity
@Table(name="Magazine")
public class Magazine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Name")
    private String name;

	@Column(name="Issn")
    private String issn;
	
	@Enumerated(EnumType.STRING)
    private ChargeType chargeType;

	@OneToOne
    private UserCustom mainEditor;
	
	@ManyToMany(fetch = FetchType.EAGER)
    private Set<PaymentType> paymentTypes = new HashSet<PaymentType>();
	
	@ManyToMany(fetch = FetchType.EAGER)
    private Set<UserCustom> reviewers = new HashSet<UserCustom>();
	
	@OneToMany(fetch = FetchType.EAGER)
    private Set<UserCustom> editors = new HashSet<UserCustom>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<ScientificArea> areas = new HashSet<ScientificArea>();
	
	@Column(name="Active")
	private boolean active;

	public Magazine() {
		this.active = false;
	}

	public Magazine(String name, String issn, ChargeType chargeType, UserCustom mainEditor,
			Set<PaymentType> paymentTypes, Set<UserCustom> reviewers, Set<UserCustom> editors, boolean active) {
		super();
		this.name = name;
		this.issn = issn;
		this.chargeType = chargeType;
		this.mainEditor = mainEditor;
		this.paymentTypes = paymentTypes;
		this.reviewers = reviewers;
		this.editors = editors;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIssn() {
		return issn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
	}

	public UserCustom getMainEditor() {
		return mainEditor;
	}

	public void setMainEditor(UserCustom mainEditor) {
		this.mainEditor = mainEditor;
	}

	public Set<PaymentType> getPaymentType() {
		return paymentTypes;
	}

	public void setPaymentType(Set<PaymentType> paymentType) {
		this.paymentTypes = paymentType;
	}

	public Set<UserCustom> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Set<UserCustom> reviewers) {
		this.reviewers = reviewers;
	}

	public Set<UserCustom> getEditors() {
		return editors;
	}

	public void setEditors(Set<UserCustom> editors) {
		this.editors = editors;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void addPaymentType(PaymentType pt) {
		this.paymentTypes.add(pt);
	}

	public Set<PaymentType> getPaymentTypes() {
		return paymentTypes;
	}

	public void setPaymentTypes(Set<PaymentType> paymentTypes) {
		this.paymentTypes = paymentTypes;
	}

	public Set<ScientificArea> getAreas() {
		return areas;
	}

	public void setAreas(Set<ScientificArea> areas) {
		this.areas = areas;
	}
	
	public void addScientificArea(ScientificArea sa) {
		this.areas.add(sa);
	}
	
	public void addReviewer(UserCustom sa) {
		this.reviewers.add(sa);
	}

	public void addEditor(UserCustom sa) {
		this.editors.add(sa);
	
	}
	
}

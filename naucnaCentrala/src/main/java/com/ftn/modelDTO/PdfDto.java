package com.ftn.modelDTO;

import java.io.Serializable;

public class PdfDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pdf;
	
	public PdfDto() {

	}

	public PdfDto(String pdf) {
		super();
		this.pdf = pdf;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	
}

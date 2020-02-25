package com.ftn.elasticSearch.indexing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.text.PDFTextStripper;


import com.ftn.elasticSearch.model.IndexUnit;
import com.ftn.model.Paper;

public class HandlerPaper {
	
	
	public IndexUnit prepareForIndex(Paper paper, String pdfName) throws IOException {
		
		IndexUnit iu = new IndexUnit();
		
		iu.setMagazine(paper.getMagazine().getName());
		iu.setFirstNameAuthor(paper.getAuthor().getFirstName());
		iu.setLastNameAuthor(paper.getAuthor().getLastName());
		iu.setKeywords(paper.getKeywords());
		iu.setArea(paper.getScientificArea().getName());
		iu.setTitle(paper.getTitle());
		
		String[] s = paper.getPathPdf().split(",");
			
		FileOutputStream fos = new FileOutputStream("./files/" + pdfName);

		byte[] decoded = Base64.getDecoder().decode(s[1].getBytes()); 
		fos.write(decoded);
		fos.close();

		File file = new File("./files/" + pdfName);

		iu.setFilename(pdfName);
		
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			String text = getText(parser);
			iu.setText(text);
			System.out.println("Prosao");
			} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return iu;
	
	}
	
	public String getText(File file) {
		try {
			PDFParser parser = new PDFParser(new RandomAccessFile(file, "r"));
			parser.parse();
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greska pri konvertovanju dokumenta u pdf");
		}
		return null;
	}
	
	public String getText(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(parser.getPDDocument());
			return text;
		} catch (IOException e) {
			System.out.println("Greska pri konvertovanju dokumenta u pdf");
		}
		return null;
	}

}

package edu.illuminaty.websteganography.hider.covermedium;

import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class CovertextFromPDFFile implements CovertextCreator {
	private final PDDocument document;
	
	public CovertextFromPDFFile(PDDocument document){
		this.document = document;
	}
	
	@Override
	public String createCovertext() {
		try {
			return new PDFTextStripper().getText(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return DummyCovertextCreator.getInstance().createCovertext();
	}

}

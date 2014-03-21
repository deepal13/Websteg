package edu.illuminaty.websteganography.util.fileaccess;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.action.type.PDActionURI;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;

public class PDFFileAccessor {
	
	@SuppressWarnings("unchecked")
	public static List<URL> getLinks(PDDocument file) throws MalformedURLException{
		List<URL> links = new ArrayList<>();
		for(PDPage page: ((List<PDPage>)file.getDocumentCatalog().getAllPages())){
			try {
				for(PDAnnotation annotation:((List<PDAnnotation>)page.getAnnotations())){
					if(annotation instanceof PDAnnotationLink){
						PDAnnotationLink linkAn = (PDAnnotationLink)annotation;
						links.add(new URL(((PDActionURI)linkAn.getAction()).getURI()));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return links;
	}
}

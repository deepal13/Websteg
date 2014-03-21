package edu.illuminaty.websteganography.util.fileaccess;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODSingleXMLDocument;

public class ODTFileAccessor {
	
	public static List<URL> getLinks(ODPackage file) throws MalformedURLException{
		ODSingleXMLDocument document = file.toSingle();
		Element body = document.getBody();
		List<URL> links = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Element> children = body.getChildren("p", body.getNamespace("text"));
		for(Element child:children){
			@SuppressWarnings("unchecked")
			List<Element> childrenOfChild = child.getChildren("a", child.getNamespace("text"));
			for(Element childOfChild:childrenOfChild){
				links.add(new URL(childOfChild.getAttribute("href", childOfChild.getNamespace("xlink")).getValue()));
			}
		}
		return links;
	}
}

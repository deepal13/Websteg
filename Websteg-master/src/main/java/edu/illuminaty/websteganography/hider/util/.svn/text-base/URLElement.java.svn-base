package edu.illuminaty.websteganography.hider.util;

import java.net.URL;

public class URLElement implements PageElement {
	private final URL url;
	private final String title;
	
	public URLElement(URL url, String title){
		this.url=url;
		this.title = title;
	}
	
	@Override
	public URL getURL() {
		return url;
	}
	
	@Override
	public String getLinkingWord() {
		return title;
	}

	@Override
	public boolean isURL() {
		return true;
	}

	@Override
	public String getWord() {
		return null;
	}

	@Override
	public boolean isWord() {
		return false;
	}

	@Override
	public char getCharacter() {
		return 0;
	}

	@Override
	public boolean isCharacter() {
		return false;
	}

}

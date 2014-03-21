package edu.illuminaty.websteganography.hider.util;

import java.net.URL;

public class CharacterElement implements PageElement {
	private final char character;
	
	public CharacterElement(char character){
		this.character=character;
	}
	
	@Override
	public URL getURL() {
		return null;
	}

	@Override
	public boolean isURL() {
		return false;
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
		return character;
	}

	@Override
	public boolean isCharacter() {
		return true;
	}

	@Override
	public String getLinkingWord() {
		return null;
	}

}

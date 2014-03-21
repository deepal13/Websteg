package edu.illuminaty.websteganography.hider.crawler.textelements;

public class CharacterTextElement implements TextElement {
	
	private final char character;
	
	public CharacterTextElement(char character){
		this.character=character;
	}
	
	@Override
	public String getWord() {
		return null;
	}

	@Override
	public char getCharacter() {
		return character;
	}

	@Override
	public boolean isWord() {
		return false;
	}

	@Override
	public boolean isCharacter() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + character;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharacterTextElement other = (CharacterTextElement) obj;
		if (character != other.character)
			return false;
		return true;
	}

}

package edu.illuminaty.websteganography.hider.crawler.textelements;

public class WordTextElement implements TextElement {

	private final String word;
	
	public WordTextElement(String word){
		this.word = word;
	}
	
	@Override
	public String getWord() {
		return word;
	}

	@Override
	public char getCharacter() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isWord() {
		return true;
	}

	@Override
	public boolean isCharacter() {
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
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
		WordTextElement other = (WordTextElement) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

}

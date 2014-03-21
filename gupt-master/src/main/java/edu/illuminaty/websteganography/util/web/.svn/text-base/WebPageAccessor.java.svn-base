package edu.illuminaty.websteganography.util.web;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebPageAccessor {
	
	public static final String REGEX_WORDS = "[^a-zA-Z0-9]";
	public static URL linkAt(String url, int position) throws IOException{
		return linkAt(new URL(url), position);
	}
	
	public static URL linkAt(URL url, int position) throws IOException{
		Document doc = getWebpage(url);
		Elements links = doc.getElementsByTag("a");
		return new URL(links.get(position-1).attr("href"));
	}
	
	public static char characterAt(String url, int position) throws IOException{
		return characterAt(new URL(url), position);
	}
	
	public static char characterAt(URL url, int position) throws IOException{
		Document doc = Jsoup.parse(url, 10000);
		return doc.text().charAt(position-1);
	}
	
	public static String wordAt(String url, int position) throws IOException{
		return wordAt(new URL(url), position);
	}
	
	public static String wordAt(URL url, int position) throws IOException{
		Document doc = getWebpage(url);
		String text = doc.text();
		String[] words = text.split(REGEX_WORDS);
		return words[position-1];
	}
	
	public static int positionOfLink(String url, String link) throws IOException{
		return positionOfLink(new URL(url), link);
	}
	
	public static int positionOfLink(URL url, String link) throws IOException{
		Document doc = getWebpage(url);
		Elements links = doc.getElementsByTag("a");
		int i = 1;
		for(Element webLink:links){
			String webLinkStr = webLink.attr("href");
			if(webLinkStr.equals(link)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static int positionOfCharacter(String url, char character) throws IOException{
		return positionOfCharacter(new URL(url), character);
	}
	
	public static int positionOfCharacter(URL url, char character) throws IOException{
		Document doc = getWebpage(url);
		return doc.text().indexOf(character)+1;
	}
	
	public static int positionOfWord(String url, String word) throws IOException{
		return positionOfWord(new URL(url), word);
	}
	
	public static int positionOfWord(URL url, String word) throws IOException{
		String text = getWebpage(url).text();
		String[] words = text.split(REGEX_WORDS);
		int i = 1;
		for(String wordPage:words){
			if(wordPage.equals(word)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public static boolean containsWord(URL url, String word) throws IOException{
		return positionOfWord(url, word) > -1;
	}
	
	public static boolean containsCharacter(URL url, char character) throws IOException{
		return positionOfCharacter(url, character) > -1;
	}
	
	private static Document getWebpage(URL url) throws IOException{
		return Jsoup.connect(url.toString()).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0").referrer("www.google.com").get();
	}
}

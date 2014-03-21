package edu.illuminaty.websteganography.hider.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import edu.illuminaty.websteganography.hider.crawler.FarooResults.Result;
import edu.illuminaty.websteganography.hider.crawler.textelements.CharacterTextElement;
import edu.illuminaty.websteganography.hider.crawler.textelements.TextElement;
import edu.illuminaty.websteganography.hider.crawler.textelements.WordTextElement;
import edu.illuminaty.websteganography.hider.util.CharacterElement;
import edu.illuminaty.websteganography.hider.util.PageElement;
import edu.illuminaty.websteganography.hider.util.URLElement;
import edu.illuminaty.websteganography.hider.util.WordElement;
import edu.illuminaty.websteganography.util.web.WebPageAccessor;

public class GoogleAPICrawler implements Crawler {
	private static final Crawler instance = new GoogleAPICrawler();
	
	private static final String ADDRESS = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	private static final String CHARSET = "UTF-8";
			
	private GoogleAPICrawler(){
	}
	
	public static final Crawler getInstance(){
		return instance;
	}

	@Override
	public List<List<PageElement>> crawl(String covertext, String message) {
		Set<String> wordsCovertext = new HashSet<>(removeWhiteSpaces(covertext.split(WebPageAccessor.REGEX_WORDS)));
		List<TextElement> textElementsMessage = findTextElements(message);
		List<List<PageElement>> messageAsPes = new ArrayList<>();
		Map<TextElement, List<PageElement>> searchResults = new HashMap<>();
		for(TextElement te:textElementsMessage){
			List<PageElement> crawlRes;
			if(searchResults.containsKey(te)){
				crawlRes = searchResults.get(te);
			}else if(te.isWord()){
				String word = te.getWord();
				if(containsWord(covertext, word)){
					crawlRes = new ArrayList<>();
					crawlRes.add(new WordElement(word));
				}else{
					crawlRes = crawlForWord(word, wordsCovertext);
					if(crawlRes == null){
						for(Character character:word.toCharArray()){
							crawlRes = crawlForCharacter(character, covertext, wordsCovertext);
						}
					}
				}
			}else{
				crawlRes = crawlForCharacter(te.getCharacter(), covertext, wordsCovertext);
			}
			messageAsPes.add(crawlRes);
			searchResults.put(te, crawlRes);
		}
		return messageAsPes;
	}
	
	private List<PageElement> crawlForWord(String word, Set<String> wordsCovertext){
		try {
			URL searchURL = new URL(ADDRESS + URLEncoder.encode("\""+word+"\"", CHARSET));
			Reader reader = new InputStreamReader(getInputStream(searchURL), CHARSET);
			FarooResults results = new Gson().fromJson(reader, FarooResults.class);
			List<PageElement> wordAsPes = new ArrayList<>();
			List<Result> searchResults = results.getResponseData().getResults();
			for(Result res:searchResults){
				URL url = new URL(res.getUrl());
				try{
					if(WebPageAccessor.containsWord(url, word)){
						for(String coverWord:wordsCovertext){
							if(res.getTitle().contains(coverWord)){
								wordAsPes.add(new URLElement(url, coverWord));
								wordAsPes.add(new WordElement(word));
								wordsCovertext.remove(coverWord);
								return wordAsPes;
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(Result resToUse:searchResults){
				URL url = new URL(resToUse.getUrl());
				try{
					if(WebPageAccessor.containsWord(url, word)){
						String coverWordToUse = wordsCovertext.iterator().next();
						wordAsPes.add(new URLElement(url, coverWordToUse));
						wordAsPes.add(new WordElement(word));
						wordsCovertext.remove(coverWordToUse);
						return wordAsPes;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private InputStream getInputStream(URL url) throws IOException{
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
		connection.connect();
		return connection.getInputStream();
	}
	
	private List<PageElement> crawlForCharacter(char character, String covertext, Set<String> wordsCovertext){
		int pos = covertext.indexOf(character);
		List<PageElement> pageElements = new ArrayList<>();
		if(pos < 0){
			try {
				URL searchURL = new URL(ADDRESS + URLEncoder.encode(character+"", CHARSET));
				Reader reader = new InputStreamReader(getInputStream(searchURL), CHARSET);
				FarooResults results = new Gson().fromJson(reader, FarooResults.class);
				List<Result> searchResults = results.getResponseData().getResults();
				for(Result res:searchResults){
					URL url = new URL(res.getUrl());
					try{
						if(WebPageAccessor.containsCharacter(url, character)){
							for(String coverWord:wordsCovertext){
								if(res.getTitle().contains(coverWord)){
									pageElements.add(new URLElement(url, coverWord));
									pageElements.add(new CharacterElement(character));
									wordsCovertext.remove(coverWord);
									return pageElements;
								}
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for(Result resToUse:searchResults){
					URL url = new URL(resToUse.getUrl());
					try{
						if(WebPageAccessor.containsCharacter(url, character)){
							String coverWordToUse = wordsCovertext.iterator().next();
							pageElements.add(new URLElement(url, coverWordToUse));
							pageElements.add(new CharacterElement(character));
							wordsCovertext.remove(coverWordToUse);
							return pageElements;
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			pageElements.add(new CharacterElement(character));
		}
		return pageElements;
	}
	
	private List<TextElement> findTextElements(String message){
		List<TextElement> textElements = new ArrayList<>();
		Pattern pattern = Pattern.compile(WebPageAccessor.REGEX_WORDS);
		Matcher matcher = pattern.matcher(message);
		int start = 0;
		while(matcher.find()){
			int pos = matcher.start();
			if((pos - start)>0){
				textElements.add(new WordTextElement(message.substring(start, pos)));
			}
			textElements.add(new CharacterTextElement(message.charAt(pos)));
			start = pos+1;
		}
		if(start<message.length()){
			textElements.add(new WordTextElement(message.substring(start, message.length())));
		}
		return textElements;
	}

	private List<String> removeWhiteSpaces(String[] words){
		List<String> wordsWithoutEmptyStr = new ArrayList<>();
		for(int i=0; i<words.length;i++){
			String word = words[i];
			if(!(word==null||word.isEmpty())){
				wordsWithoutEmptyStr.add(word);
			}
		}
		return wordsWithoutEmptyStr;
	}
	
	public boolean containsWord(String text, String word){
		String[] words = text.split(WebPageAccessor.REGEX_WORDS);
		for(String wordPage:words){
			if(wordPage.equals(word)){
				return true;
			}
		}
		return false;
	}
}

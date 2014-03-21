package edu.illuminaty.websteganography.hider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import edu.illuminaty.websteganography.hider.covermedium.CovertextCreator;
import edu.illuminaty.websteganography.hider.covermedium.CovertextFromPDFFile;
import edu.illuminaty.websteganography.hider.covermedium.DummyCovertextCreator;
import edu.illuminaty.websteganography.hider.crawler.Crawler;
import edu.illuminaty.websteganography.hider.crawler.GoogleAPICrawler;
import edu.illuminaty.websteganography.hider.excpetions.HidingException;
import edu.illuminaty.websteganography.hider.util.PageElement;
import edu.illuminaty.websteganography.util.key.KeyGenerator;
import edu.illuminaty.websteganography.util.web.WebPageAccessor;

public class PDFHider implements Hider {
	private final String aesKey;
	private final String webpage;
	private Cipher cipher;
	private WebResource restService;
	private CovertextCreator coverTextCreator;
	private final Crawler crawler = GoogleAPICrawler.getInstance();
	
	public PDFHider(String key, File covertextFile) throws InvalidKeyException{
		try {
			coverTextCreator = covertextFile == null ? DummyCovertextCreator.getInstance() : new CovertextFromPDFFile(PDDocument.load(covertextFile));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			coverTextCreator = DummyCovertextCreator.getInstance();
		};
		aesKey = KeyGenerator.getAesKey(key);
		webpage = KeyGenerator.getWebpage(key);
		restService = Client.create(new DefaultClientConfig()).resource(webpage);
		SecretKeySpec keySpec;
		try {
			keySpec = new SecretKeySpec(Hex.decodeHex(aesKey.toCharArray()), KeyGenerator.ENCRYPTION);
			cipher = Cipher.getInstance(KeyGenerator.ENCRYPTION);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void hide(String message, File file) throws HidingException {
		String covertext = coverTextCreator.createCovertext();
		List<List<PageElement>> searchResult = crawler.crawl(covertext, message);
		Map<String, URL> wordToUrl = createWordToUrl(searchResult);
		try {
			List<URL> linksOnPage = makePDF(file, covertext, wordToUrl, searchResult);
			createPositionsStringAndPushToServer(searchResult, linksOnPage, new PDFTextStripper().getText(PDDocument.load(file)));
		} catch (DocumentException | IllegalBlockSizeException | BadPaddingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HidingException(e.getMessage());
		}
	}
	
	private Map<String, URL> createWordToUrl(List<List<PageElement>> searchResult){
		Map<String, URL> wordToUrl = new HashMap<>();
		for(List<PageElement> word:searchResult){
			PageElement pe = word.get(0);
			if(pe.isURL()){
				wordToUrl.put(pe.getLinkingWord(), pe.getURL());
			}
		}
		return wordToUrl;
	}
	
	private List<URL> makePDF(File file, String stegotext, Map<String, URL> wordToUrl, List<List<PageElement>> words) throws  DocumentException{
		List<Entry<Integer, String>> foundWords = new ArrayList<>();
		List<URL> linksOnPage = new ArrayList<>();
		for(String word:wordToUrl.keySet()){
			String regex = WebPageAccessor.REGEX_WORDS+word+WebPageAccessor.REGEX_WORDS;
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(stegotext);
			if(matcher.find()){
				foundWords.add(new AbstractMap.SimpleEntry<>(matcher.start()+1, word));
			}else{
				foundWords.add(new AbstractMap.SimpleEntry<>(stegotext.indexOf(word), word));
			}
		}
		Collections.sort(foundWords, new Comparator<Entry<Integer,String>>() {

			@Override
			public int compare(Entry<Integer, String> o1,
					Entry<Integer, String> o2) {
				return o1.getKey()-o2.getKey();
			}
		});
		try {
			Document doc = new Document();
			PdfWriter.getInstance(doc, new FileOutputStream(file));
			doc.open();
			Paragraph para = new Paragraph();
			int startIndex=0;
			for(Entry<Integer, String> word:foundWords){
				String wordStr = word.getValue();
				int wordIndex = word.getKey();
				para.add(stegotext.substring(startIndex, wordIndex));
				Anchor anchor = new Anchor(wordStr);
				URL url = wordToUrl.get(wordStr);
				linksOnPage.add(url);
				anchor.setReference(url.toString());
				para.add(anchor);
				startIndex = wordIndex+wordStr.length();
			}
			para.add(stegotext.substring(startIndex));
			doc.add(para);
			doc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return linksOnPage;
	}
	
	private void createPositionsStringAndPushToServer(List<List<PageElement>> words, List<URL> linksOnPage, String stegotext) throws IOException, IllegalBlockSizeException, BadPaddingException{
		String positionsTmp = "";
		for(List<PageElement> word:words){
			positionsTmp += ";";
			PageElement firstPe = word.get(0);
			URL currentURL = null;
			if(firstPe.isWord()){
				positionsTmp += getWordPos(stegotext, firstPe.getWord());
			}else if(firstPe.isCharacter()){
				positionsTmp += getCharPos(stegotext, firstPe.getCharacter())+"c";
			}else{
				currentURL = firstPe.getURL();
				positionsTmp += linksOnPage.indexOf(currentURL)+1;
			}
			for(int i=1;i<word.size();i++){
				PageElement pe = word.get(i);
				positionsTmp += ",";
				if(pe.isWord()){
					positionsTmp += WebPageAccessor.positionOfWord(currentURL, pe.getWord());
				}else if(pe.isCharacter()){
					positionsTmp += WebPageAccessor.positionOfCharacter(currentURL, pe.getCharacter())+"c";					
				}else{
					positionsTmp += WebPageAccessor.positionOfLink(currentURL, pe.getURL().toString());
					currentURL=pe.getURL();
				}
			}
		}
		String positions = positionsTmp.substring(1);
		String positionsEncr = new String(Hex.encodeHex(cipher.doFinal(positions.getBytes())));
		MultivaluedMap<String, String> message = new MultivaluedMapImpl();
		message.add("message", positionsEncr);
		restService.path("rest/change/changeMessage/").type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(message);
	}
	
	private int getWordPos(String text, String word){
		String[] words = text.split(WebPageAccessor.REGEX_WORDS);
		int i = 1;
		for(String wordInText:words){
			if(wordInText.equals(word)){
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private int getCharPos(String text, char character){
		return text.indexOf(character)+1;
	}

}

package edu.illuminaty.websteganography.extractor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jopendocument.dom.ODPackage;
import org.jopendocument.dom.ODSingleXMLDocument;
import org.jsoup.Jsoup;

import edu.illuminaty.websteganography.util.fileaccess.ODTFileAccessor;
import edu.illuminaty.websteganography.util.key.KeyGenerator;
import edu.illuminaty.websteganography.util.web.WebPageAccessor;

public class ODTExtractor implements Extractor {
	private final String aesKey;
	private final String webpage;
	private Cipher cipher;
	
	public ODTExtractor(String key) throws InvalidKeyException{
		aesKey = KeyGenerator.getAesKey(key);
		webpage = KeyGenerator.getWebpage(key);
		SecretKeySpec keySpec;
		try {
			keySpec = new SecretKeySpec(Hex.decodeHex(aesKey.toCharArray()), KeyGenerator.ENCRYPTION);
			cipher = Cipher.getInstance(KeyGenerator.ENCRYPTION);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
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
	public String extract(File stegomedium) {
		String encrPositions;
		try {
			encrPositions = Jsoup.parse(new URL(webpage), 10000).text();
			String positions = new String(cipher.doFinal(Hex.decodeHex(encrPositions.toCharArray())));
			ODPackage OdFile = new ODPackage(stegomedium);
			ODSingleXMLDocument document = OdFile.toSingle();
			String stegoText = document.getBody().getValue();
			List<URL> links = ODTFileAccessor.getLinks(OdFile);
			return lookInWeb(links, stegoText, positions);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private String lookInWeb(List<URL> links, String stegoText, String wordPositions) throws IOException{
		String[] words = wordPositions.split(";");
		String message = "";
		boolean isLastChar = false;
		for(String word:words){
			String trans = lookForWord(word.split(","), links, stegoText);
			if(word.endsWith("c")){
				message += isLastChar ? trans : " "+trans;
				isLastChar = true;
			}else{
				message += " "+trans;
				isLastChar=false;
			}
		}
		return message.substring(1);
	}
	
	private String lookForWord(String[] positions, List<URL> links, String stegoText) throws IOException{
		if(positions.length<=1){//Word is hidden in document
			String pos = positions[0];
			if(pos.endsWith("c")){
				int position = Integer.parseInt(pos.substring(0, pos.length()-1));
				return getCharAt(stegoText, position);
			}else{
				int position = Integer.parseInt(pos);
				return getWordAt(stegoText, position);
			}
		}
		//Word is hidden on a webpage
		URL url = links.get(Integer.parseInt(positions[0])-1);
		for(int i=1;i<positions.length-1;i++){
			
		}
		String pos = positions[positions.length-1];
		if(pos.endsWith("c")){
			int position = Integer.parseInt(pos.substring(0, pos.length()-1));
			return WebPageAccessor.wordAt(url, position);
		}else{
			int position = Integer.parseInt(pos);
			return WebPageAccessor.characterAt(url, position)+"";
		}
	}
	
	private String getWordAt(String text, int pos){
		String[] words = text.split(WebPageAccessor.REGEX_WORDS);
		return words[pos-1];
	}
	
	private String getCharAt(String text, int pos){
		return text.charAt(pos-1)+"";
	}

}

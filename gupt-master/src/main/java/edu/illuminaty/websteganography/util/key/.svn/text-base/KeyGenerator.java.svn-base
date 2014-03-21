package edu.illuminaty.websteganography.util.key;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class KeyGenerator {
	
	public static final String ENCRYPTION = "AES";
	private static final int KEY_BYTE_SIZE = 16;
	private static final int KEY_BIT_SIZE = 8*KEY_BYTE_SIZE;
	
	public static String generateKey(String webpage){
		try {
			javax.crypto.KeyGenerator generator = javax.crypto.KeyGenerator.getInstance(ENCRYPTION);
			generator.init(KEY_BIT_SIZE);
			SecretKey key = generator.generateKey();
			byte[] keyAsBytes = key.getEncoded();
			SecretKeySpec keySpec = new SecretKeySpec(keyAsBytes, ENCRYPTION);
			Cipher cipher = Cipher.getInstance(ENCRYPTION);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			byte[] encrWebpage = cipher.doFinal(webpage.getBytes());
			String resKey = new String(Hex.encodeHexString(keyAsBytes));
			resKey += new String(Hex.encodeHex(encrWebpage));
			return resKey;
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static String getAesKey(String key){
		try {
			byte[] keyAsBytes = Hex.decodeHex(key.toCharArray());
			return new String(Hex.encodeHexString(Arrays.copyOfRange(keyAsBytes, 0, KEY_BYTE_SIZE)));
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getWebpage(String key){
		try {
			byte[] keyAsBytes = Hex.decodeHex(key.toCharArray());
			byte[] webPageEncoded = Arrays.copyOfRange(keyAsBytes, KEY_BYTE_SIZE, keyAsBytes.length);
			byte[] aesKeyAsBytes = Hex.decodeHex(getAesKey(key).toCharArray());
			SecretKeySpec keySpec = new SecretKeySpec(aesKeyAsBytes, ENCRYPTION);
			Cipher cipher = Cipher.getInstance(ENCRYPTION);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			byte[] decrWebpage = cipher.doFinal(webPageEncoded);
			return new String(decrWebpage);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

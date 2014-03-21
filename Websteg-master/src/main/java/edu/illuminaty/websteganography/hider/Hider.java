package edu.illuminaty.websteganography.hider;

import java.io.File;

import edu.illuminaty.websteganography.hider.excpetions.HidingException;

public interface Hider {
	public void hide(String message, File file) throws HidingException;
}

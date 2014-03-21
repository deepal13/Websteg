package edu.illuminaty.websteganography.hider.excpetions;

public class HidingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1798552772757700590L;

	public HidingException() {
	}

	public HidingException(String message) {
		super(message);
	}

	public HidingException(Throwable cause) {
		super(cause);
	}

	public HidingException(String message, Throwable cause) {
		super(message, cause);
	}

	public HidingException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

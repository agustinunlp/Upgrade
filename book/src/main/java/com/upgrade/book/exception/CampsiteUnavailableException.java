package com.upgrade.book.exception;

public class CampsiteUnavailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampsiteUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

	public CampsiteUnavailableException(String message) {
		super(message);
	}

}

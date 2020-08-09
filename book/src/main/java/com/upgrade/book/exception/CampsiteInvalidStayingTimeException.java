package com.upgrade.book.exception;

public class CampsiteInvalidStayingTimeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CampsiteInvalidStayingTimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public CampsiteInvalidStayingTimeException(String message) {
		super(message);
	}

}

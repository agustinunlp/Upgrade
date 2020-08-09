package com.upgrade.book.exception;

public class CampsiteBookingException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampsiteBookingException(String message, Throwable cause) {
		super(message, cause);
	}

	public CampsiteBookingException(String message) {
		super(message);
	}

}

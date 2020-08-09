package com.upgrade.book.exception;

public class CampsiteOverlappingDatesException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CampsiteOverlappingDatesException(String message, Throwable cause) {
		super(message, cause);
	}

	public CampsiteOverlappingDatesException(String message) {
		super(message);
	}

}

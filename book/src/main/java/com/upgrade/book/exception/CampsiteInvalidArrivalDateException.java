package com.upgrade.book.exception;

public class CampsiteInvalidArrivalDateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampsiteInvalidArrivalDateException(String message, Throwable cause) {
		super(message, cause);
	}

	public CampsiteInvalidArrivalDateException(String message) {
		super(message);
	}
}

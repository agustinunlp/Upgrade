package com.upgrade.book.exception;

public class CampsiteReserveNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CampsiteReserveNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CampsiteReserveNotFoundException(String message) {
		super(message);
	}

}

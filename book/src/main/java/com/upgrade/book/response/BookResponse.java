package com.upgrade.book.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponse extends Response{
	private String reserveId;

	public BookResponse(String message) {
		super(message);
	}	

	public BookResponse(String message, String reserveId) {
		super(message);
		this.reserveId = reserveId;
	}

	public String getReserveId() {
		return reserveId;
	}

	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}

}

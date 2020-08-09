package com.upgrade.book.request;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class BookRequest implements IRequestValidator{
	@NotNull
	@JsonDeserialize(using = DateHandler.class)
	private Date departureDate;
	@NotNull
	@JsonDeserialize(using = DateHandler.class)
	private Date arrivalDate;
	@NotBlank
	private String fullName;
	@NotBlank	
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Email field is incorrect")
	private String email;	
	
	public BookRequest() {
		super();
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public boolean isValid() {
			return (this.arrivalDate.getTime() <= this.departureDate.getTime());
	}

}

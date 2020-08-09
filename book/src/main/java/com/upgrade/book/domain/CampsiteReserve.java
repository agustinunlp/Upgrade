package com.upgrade.book.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
/**
 * Represents a Campsite reservation
 * @author agustin
 *
 */
@Document(collection = "CampsiteReserve")
public class CampsiteReserve {

	@Id
	private String id;
	private String email;
	private String fullName;
	private Date arriveDate;
	private Date departureDate;

	public CampsiteReserve() {
		super();
	}	
	
	public CampsiteReserve(String id) {
		super();
		this.id = id;
	}	

	public CampsiteReserve(String email, String fullName, Date arriveDate, Date departureDate) {
		super();
		this.email = email;
		this.fullName = fullName;
		this.arriveDate = arriveDate;
		this.departureDate = departureDate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
}

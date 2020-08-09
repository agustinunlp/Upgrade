package com.upgrade.book.response;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailabilityResponse extends Response {
	private String arrival;
	private String departure;
	private Map<LocalDate, Boolean> availability;
	
	public AvailabilityResponse(String message) {
		super(message);
	}

	public AvailabilityResponse(String message, Date arrival, Date departure, Map<LocalDate, Boolean> availability) {
		this(message, arrival, departure);
		this.availability = availability;
	}
	
	public AvailabilityResponse(String message, Date arrival, Date departure) {
		super(message);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.arrival = dateFormat.format(arrival);
		this.departure = dateFormat.format(departure);
	}
	
	public String getArrival() {
		return arrival;
	}
	
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	
	public String getDeparture() {
		return departure;
	}
	
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	public Map<LocalDate, Boolean> getAvailability() {
		return availability;
	}

	public void setAvailability(Map<LocalDate, Boolean> availability) {
		this.availability = availability;
	}
}

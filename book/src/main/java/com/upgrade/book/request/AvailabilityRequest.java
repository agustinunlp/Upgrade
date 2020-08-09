package com.upgrade.book.request;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.junit.platform.commons.util.StringUtils;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class AvailabilityRequest implements IRequestValidator{
	
	private static final String VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

	@Pattern(regexp = VALIDATION_REGEX, message = "The arrival date is incorrect (yyyy-MM-dd)")
	@JsonDeserialize(using = DateHandler.class)
	private String from;
	
	@Pattern(regexp = VALIDATION_REGEX, message = "The departure date is incorrect (yyyy-MM-dd)")
	@JsonDeserialize(using = DateHandler.class)
	private String to;
	
	@Override
	public boolean isValid() {
		if (this.isEmpty()) {
			return true;
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date arrivalDate;
		Date departureDate;
		try {
			arrivalDate = dateFormat.parse(this.getFrom());
			departureDate = dateFormat.parse(this.getTo());
			return (arrivalDate.getTime() <= departureDate.getTime());
		} catch (ParseException e) {
			return false;
		}		
	}
	
	public boolean isEmpty() {
		return StringUtils.isBlank(from) && StringUtils.isBlank(to);
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

}

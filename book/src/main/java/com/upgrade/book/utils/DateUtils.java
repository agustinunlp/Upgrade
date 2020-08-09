package com.upgrade.book.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
	
	
	public Date setTime(Date date) {
		Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.HOUR_OF_DAY, 12);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    return c.getTime();
	}
	
	/**
	 * Retrive Dates between from and to Dates
	 * @param from
	 * @param to
	 * @return List
	 */
	public List<LocalDate> getDatesBetween(Date from, Date to) {
		LocalDate start = from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate end = to.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		List<LocalDate> totalDates = new ArrayList<LocalDate>();
		while (!start.isAfter(end)) {
		    totalDates.add(start);
		    start = start.plusDays(1);
		}
		return totalDates;
	}
	
	public Date getNextMonthDate() {
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}	

	public Date getTomorrowDate() {
		Calendar calendar = Calendar.getInstance(); 
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
}

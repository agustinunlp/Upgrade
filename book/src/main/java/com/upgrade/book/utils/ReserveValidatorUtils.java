package com.upgrade.book.utils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.upgrade.book.constants.Constants;

public class ReserveValidatorUtils extends DateUtils{

	/**
	 * Validate difference in days between arrival and departure between 1 and 3 
	 * @param arrivalDate
	 * @param departureDate
	 * @return boolean
	 */
	public boolean validateDiferenceInDays(Date arrivalDate, Date departureDate) {
		long diffInMillies = Math.abs(departureDate.getTime() - arrivalDate.getTime());
    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		return (diff >= Constants.MIN_DAYS) && (diff <= Constants.MAX_DAYS);
	}

	/**
	 * Validate arrival date between tomorrow and 1 month later
	 * @return boolean
	 */
	public boolean validateArrivalDate(Date arrivalDate) {
		long arrivalDateTime = arrivalDate.getTime();
		long tomorrow = getTomorrowDate().getTime();
		long nextmonth = getNextMonthDate().getTime();
		return arrivalDateTime >= tomorrow && arrivalDateTime <= nextmonth;
	}
}

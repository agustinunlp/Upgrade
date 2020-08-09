package com.upgrade.book.constants;

public class Constants {
	
	public static final int MIN_DAYS = 1;
	public static final int MAX_DAYS = 3;
	
	public static final String CAMPSITE_BOOK_FAIL = "The campsite couldn't be reserved, please check availability";
	public static final String CAMPSITE_BOOK_FAIL_WRONG_ARRIVAL_DATE = "The campsite couldn't be reserved, arrival date must be between tomorrow and 1 month later";
	public static final String CAMPSITE_BOOK_FAIL_WRONG_STAYING_TIME = "The campsite couldn't be reserved, staying time should not be more than 3 days";
	public static final String CAMPSITE_MODIFY_FAIL = "The campsite couldn't be modified, please check availability";
	public static final String CAMPSITE_MODIFY_FAIL_WRONG_ARRIVAL_DATE = "The campsite couldn't be reserved, arrival date must be between tomorrow and 1 month later";
	public static final String CAMPSITE_MODIFY_FAIL_WRONG_STAYING_TIME = "The campsite couldn't be reserved, staying time should not be more than 3 days";
	public static final String CAMPSITE_MODIFY_RESERVE_NOT_FOUND = "The campsite reserve couldn't be found, please check if the reserve id is correct";
	public static final String CAMPSITE_MODIFY_OVERLAPPING_DATES = "The campsite dates are being overlapping with other reserve, please double check availability";
	public static final String CAMPSITE_AVAILABILITY = "campsite is available";
	public static final String CAMPSITE_UNAVAILABILITY = "campsite is unavailable";
	public static final String CAMPSITE_BOOK = "campsite has been reserved successfully.";
	public static final String CAMPSITE_MODIFIED = "The campsite reserve has been modified successfully.";
	public static final String SERVER_ERROR = "A generic error occurred on the server";
	public static final String INVALID_REQUEST = "Invalid request - please verify the arrival and departure dates";
	public static final String CAMPSITE_DELETE_INVALID_REQUEST = "campsite delete operation requires the reserve Id";
	public static final String CAMPSITE_RESERVE_DELETED = "The campsite reserve was deleted successfully";
	public static final String CAMPSITE_RESERVE_DELETE_ERROR = "The campsite reserve could not be deleted, please double check if the reserve id is correct";
}

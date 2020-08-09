package com.upgrade.book.service;


import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import com.upgrade.book.domain.CampsiteReserve;
import com.upgrade.book.exception.CampsiteBookingException;
import com.upgrade.book.exception.CampsiteInvalidArrivalDateException;
import com.upgrade.book.exception.CampsiteInvalidStayingTimeException;
import com.upgrade.book.exception.CampsiteOverlappingDatesException;
import com.upgrade.book.exception.CampsiteReserveNotFoundException;
import com.upgrade.book.exception.CampsiteUnavailableException;
import com.upgrade.book.request.BookRequest;
import com.upgrade.book.request.ModifyRequest;

public interface ICampsiteReserveService {

	/**
	 * Retrieves the Campsite availability
	 * @param arrivalDate 
	 * @param leaveDate
	 * @return Map<LocalDate, Boolean>
	 * @throws CampsiteUnavailableException 
	 */
	Map<LocalDate, Boolean> availability(Date arrivalDate, Date leaveDate) throws CampsiteUnavailableException;
	
	/**
	 * reserves the Campsite
	 * @param campsiteReserve
	 * @return
	 * @throws CampsiteBookingException 
	 * @throws CampsiteInvalidArrivalDateException 
	 * @throws CampsiteInvalidStayingTimeException 
	 */
	CampsiteReserve reserve(BookRequest bookRequest) throws CampsiteBookingException, CampsiteInvalidArrivalDateException, CampsiteInvalidStayingTimeException;
	
	/**
	 * modify a campsite reserve
	 * @param modifyRequest
	 * @return CampsiteReserve
	 * @throws CampsiteInvalidArrivalDateException 
	 * @throws CampsiteInvalidStayingTimeException 
	 * @throws CampsiteOverlappingDatesException 
	 * @throws CampsiteReserveNotFoundException 
	 */
	CampsiteReserve modifyReserve(ModifyRequest modifyRequest) throws CampsiteInvalidArrivalDateException, CampsiteInvalidStayingTimeException, CampsiteReserveNotFoundException, CampsiteOverlappingDatesException;
	
	/**
	 * Cancel a campsite reserve
	 * @param reserve id
	 * @return
	 */
	Boolean cancelReserve(String id);

}

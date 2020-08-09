package com.upgrade.book.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upgrade.book.constants.Constants;
import com.upgrade.book.domain.CampsiteReserve;
import com.upgrade.book.exception.CampsiteBookingException;
import com.upgrade.book.exception.CampsiteInvalidArrivalDateException;
import com.upgrade.book.exception.CampsiteInvalidStayingTimeException;
import com.upgrade.book.exception.CampsiteOverlappingDatesException;
import com.upgrade.book.exception.CampsiteReserveNotFoundException;
import com.upgrade.book.exception.CampsiteUnavailableException;
import com.upgrade.book.request.AvailabilityRequest;
import com.upgrade.book.request.BookRequest;
import com.upgrade.book.request.ModifyRequest;
import com.upgrade.book.response.AvailabilityResponse;
import com.upgrade.book.response.BookResponse;
import com.upgrade.book.service.ICampsiteReserveService;
import com.upgrade.book.utils.DateUtils;
@RestController
@RequestMapping(path = "/reserve")
public class CampsiteReserveController 
{
	private static final String VALIDATION_REGEX = "^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$";

	@Autowired
	private ICampsiteReserveService campsiteReserveService;

	private Logger logger = LogManager.getLogger(CampsiteReserveController.class);

	/**
	 * Retrieves campsite Availability between dates
	 * (by default retrieves the availability from the following 30 days)
	 * @param not Required fromDate 
	 * @param not required toDate 
	 * @return ResponseEntity<AvailabilityResponse>
	 */
	@GetMapping(path= {"/availability/{from}/{to}", "/availability"}, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvailabilityResponse> availability(
			@Valid AvailabilityRequest availabilityRequest) {
		DateUtils dateUtils = new DateUtils();
		Date fromDate;
		Date toDate;
		if (!availabilityRequest.isEmpty()) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
			try {
				fromDate = format.parse(availabilityRequest.getFrom());
				toDate = format.parse(availabilityRequest.getTo());
			} catch (ParseException e) {
				return new ResponseEntity<AvailabilityResponse>(new AvailabilityResponse(Constants.INVALID_REQUEST), HttpStatus.BAD_REQUEST);
			}			
		}
		else {
			fromDate = dateUtils.getTomorrowDate();
			toDate = dateUtils.getNextMonthDate();
		}
		
		if (!availabilityRequest.isValid()) {
			return new ResponseEntity<AvailabilityResponse>(new AvailabilityResponse(Constants.INVALID_REQUEST, fromDate, toDate), HttpStatus.BAD_REQUEST);
		}		
		
		try {
			Map<LocalDate, Boolean> availability = campsiteReserveService.availability(fromDate, toDate);			
			return new ResponseEntity<AvailabilityResponse>(
					new AvailabilityResponse(Constants.CAMPSITE_AVAILABILITY, fromDate,toDate, availability), HttpStatus.OK);
		} catch (CampsiteUnavailableException e) {
			return new ResponseEntity<AvailabilityResponse>(new AvailabilityResponse(Constants.CAMPSITE_UNAVAILABILITY, fromDate, toDate), HttpStatus.SERVICE_UNAVAILABLE);
		} catch (Exception e) {
			return new ResponseEntity<AvailabilityResponse>(new AvailabilityResponse(Constants.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Campsite book
	 * @return ResponseEntity<BookResponse> 
	 */
	@PostMapping(path="/book", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookResponse> book(@RequestBody @Valid BookRequest bookRequest) 
	{
		if (!bookRequest.isValid()) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.INVALID_REQUEST), HttpStatus.BAD_REQUEST);
		}
		CampsiteReserve campsiteReserve;
		try {
			campsiteReserve = campsiteReserveService.reserve(bookRequest);
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_BOOK, campsiteReserve.getId()), HttpStatus.CREATED);
		} catch (CampsiteBookingException e) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_BOOK_FAIL), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (CampsiteInvalidArrivalDateException e) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_BOOK_FAIL_WRONG_ARRIVAL_DATE), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (CampsiteInvalidStayingTimeException e) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_BOOK_FAIL_WRONG_STAYING_TIME), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Campsite modify 
	 * @return ResponseEntity<BookResponse> 
	 */
	@PutMapping(path="/modify", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookResponse> modify(@RequestBody @Valid ModifyRequest modifyrequest) 
	{
		if (!modifyrequest.isValid()) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.INVALID_REQUEST), HttpStatus.BAD_REQUEST);
		}
		
		try {
			campsiteReserveService.modifyReserve(modifyrequest);
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_MODIFIED, modifyrequest.getReserveId()), HttpStatus.CREATED);
		} catch (CampsiteInvalidArrivalDateException e) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_MODIFY_FAIL_WRONG_ARRIVAL_DATE), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (CampsiteInvalidStayingTimeException e) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_MODIFY_FAIL_WRONG_STAYING_TIME), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (CampsiteReserveNotFoundException e) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_MODIFY_RESERVE_NOT_FOUND), HttpStatus.NOT_FOUND);
		} catch (CampsiteOverlappingDatesException e) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_MODIFY_OVERLAPPING_DATES), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * Campsite remove reserve 
	 * @return ResponseEntity<BookResponse> 
	 */
	@DeleteMapping(path="/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookResponse> modify(
			@PathVariable(name = "id", required = true) String id) 
	{
		if (StringUtils.isBlank(id)) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_DELETE_INVALID_REQUEST), HttpStatus.BAD_REQUEST);
		}
		
		Boolean result = campsiteReserveService.cancelReserve(id);
		if (result == Boolean.TRUE) {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_RESERVE_DELETED, id), HttpStatus.OK);			
		}
		else {
			return new ResponseEntity<BookResponse>(new BookResponse(Constants.CAMPSITE_RESERVE_DELETE_ERROR, id), HttpStatus.INTERNAL_SERVER_ERROR);			

		}
	}
}
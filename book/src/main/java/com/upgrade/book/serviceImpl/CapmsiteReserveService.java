package com.upgrade.book.serviceImpl;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrade.book.dao.ICampsiteReserveDao;
import com.upgrade.book.domain.CampsiteReserve;
import com.upgrade.book.exception.CampsiteBookingException;
import com.upgrade.book.exception.CampsiteInvalidArrivalDateException;
import com.upgrade.book.exception.CampsiteInvalidStayingTimeException;
import com.upgrade.book.exception.CampsiteOverlappingDatesException;
import com.upgrade.book.exception.CampsiteReserveNotFoundException;
import com.upgrade.book.request.BookRequest;
import com.upgrade.book.request.ModifyRequest;
import com.upgrade.book.service.ICampsiteReserveService;
import com.upgrade.book.utils.DateUtils;
import com.upgrade.book.utils.ReserveValidatorUtils;

@Service
public class CapmsiteReserveService implements ICampsiteReserveService {
	@Autowired
	private ICampsiteReserveDao campsiteReserveDao;

	@Override
	public Map<LocalDate, Boolean> availability(Date from, Date to) {		
		DateUtils dateUtils = new DateUtils();
		List<CampsiteReserve> availability = campsiteReserveDao.availability(	dateUtils.setTime(from), dateUtils.setTime(to));
		return buildAvailabilityMap(from, to, dateUtils, availability);
	}

	@Override
	public CampsiteReserve reserve(BookRequest bookRequest) throws CampsiteBookingException, CampsiteInvalidArrivalDateException, CampsiteInvalidStayingTimeException {
		return campsiteReserveDao.reserve(validateAndBuildRequest(bookRequest));
	}
	
	@Override
	public CampsiteReserve modifyReserve(ModifyRequest modifyRequest) throws CampsiteInvalidArrivalDateException, CampsiteInvalidStayingTimeException, CampsiteReserveNotFoundException, CampsiteOverlappingDatesException {
		CampsiteReserve campsiteReserve = validateAndBuildRequest(modifyRequest);
		campsiteReserve.setId(modifyRequest.getReserveId());
		return campsiteReserveDao.modifyReserve(campsiteReserve);
	}

	@Override
	public Boolean cancelReserve(String id) {
		return campsiteReserveDao.cancelReserve(id);
	}
	
	private Map<LocalDate, Boolean> buildAvailabilityMap(Date from, Date to, DateUtils dateUtils,
			List<CampsiteReserve> availability) {
		List<LocalDate> list = dateUtils.getDatesBetween(from, to);
		Map<LocalDate, Boolean> map = new HashMap<LocalDate, Boolean>();
		list.forEach(localDate -> {
			map.put(localDate, Boolean.TRUE);
		});

		availability.forEach(campsiteReserve -> {
			List<LocalDate> datesBetween = dateUtils.getDatesBetween(campsiteReserve.getArriveDate(), campsiteReserve.getDepartureDate());
			datesBetween.remove(datesBetween.size() - 1);
			datesBetween.forEach(localDate -> {
				if(map.containsKey(localDate)) {
					map.put(localDate, Boolean.FALSE);					
				}
			});		
		});
		return map;
	}
	
	private CampsiteReserve validateAndBuildRequest(BookRequest bookRequest)
			throws CampsiteInvalidArrivalDateException, CampsiteInvalidStayingTimeException {
		DateUtils dateUtils = new DateUtils();
		ReserveValidatorUtils validator = new ReserveValidatorUtils();
		if (!validator.validateArrivalDate(bookRequest.getArrivalDate())) {
			throw new CampsiteInvalidArrivalDateException("Arrival date is incorrect");
		}
		if (!validator.validateDiferenceInDays(bookRequest.getArrivalDate(), bookRequest.getDepartureDate())) {
			throw new CampsiteInvalidStayingTimeException("Staying time is incorrect");
		}
		
		CampsiteReserve campsiteReserve = new CampsiteReserve(
				bookRequest.getEmail(),
				bookRequest.getFullName(),
				dateUtils.setTime(bookRequest.getArrivalDate()),
				dateUtils.setTime(bookRequest.getDepartureDate()));
		return campsiteReserve;
	}

}

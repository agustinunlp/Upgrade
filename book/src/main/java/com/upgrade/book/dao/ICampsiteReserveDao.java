package com.upgrade.book.dao;

import java.util.Date;
import java.util.List;

import com.upgrade.book.domain.CampsiteReserve;
import com.upgrade.book.exception.CampsiteBookingException;
import com.upgrade.book.exception.CampsiteOverlappingDatesException;
import com.upgrade.book.exception.CampsiteReserveNotFoundException;

public interface ICampsiteReserveDao {

	List<CampsiteReserve> availability(Date from, Date to);
	CampsiteReserve reserve(CampsiteReserve campsiteReserve) throws CampsiteBookingException;
	CampsiteReserve modifyReserve(CampsiteReserve campsiteReserve) throws CampsiteReserveNotFoundException, CampsiteOverlappingDatesException;
	Boolean cancelReserve(String id);
	
}

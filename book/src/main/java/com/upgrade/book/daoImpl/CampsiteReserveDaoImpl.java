package com.upgrade.book.daoImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.DeleteResult;
import com.upgrade.book.dao.ICampsiteReserveDao;
import com.upgrade.book.domain.CampsiteReserve;
import com.upgrade.book.exception.CampsiteBookingException;
import com.upgrade.book.exception.CampsiteOverlappingDatesException;
import com.upgrade.book.exception.CampsiteReserveNotFoundException;

@Service
public class CampsiteReserveDaoImpl implements ICampsiteReserveDao{

	@Autowired
	@Qualifier("campsiteReserveTemplate")
	private MongoTemplate campsiteReserveTemplate;

	@Override
	public List<CampsiteReserve> availability(Date from, Date to) {
	
	Criteria criteriaArriveDate = Criteria.where("arriveDate").gte(from).andOperator(new Criteria("arriveDate").lte(to));
	Criteria criteriaDepartureDate = Criteria.where("departureDate").gte(from).andOperator(new Criteria("departureDate").lte(to));
	Query query = new Query(criteriaArriveDate.orOperator(criteriaDepartureDate)); 

		List<CampsiteReserve> reserves = campsiteReserveTemplate.find(query, CampsiteReserve.class);
		return reserves;
	}
	
	private List<CampsiteReserve> overlapping(Date from) {
		Query query = new Query();
		query.addCriteria(Criteria.where("arriveDate").lte(from).and("departureDate").gt(from));
		List<CampsiteReserve> reserves = campsiteReserveTemplate.find(query, CampsiteReserve.class);
		return reserves;
	}
	
	@Override
	public CampsiteReserve reserve(CampsiteReserve campsiteReserve) throws CampsiteBookingException {
		if (this.overlapping(campsiteReserve.getArriveDate()).isEmpty()) {
			return campsiteReserveTemplate.insert(campsiteReserve);
		}

		throw new CampsiteBookingException("Campsite booking error");				
	}

	@Override
	public CampsiteReserve modifyReserve(CampsiteReserve campsiteReserve) throws CampsiteReserveNotFoundException, CampsiteOverlappingDatesException {
		CampsiteReserve result = campsiteReserveTemplate.findById(campsiteReserve.getId(), CampsiteReserve.class);
		if (result == null) {
			throw new CampsiteReserveNotFoundException(String.format("Campsite with ID: %s - Not found", campsiteReserve.getId()));
		}
		List<CampsiteReserve> overlapping = this.overlapping(campsiteReserve.getArriveDate());
		if (overlapping.size() > 1) {
			throw new CampsiteOverlappingDatesException("Campsite booking error");				
		}
		
		if (overlapping.size() == 1) {
			if (!overlapping.get(0).getId().equals(campsiteReserve.getId())) {
				throw new CampsiteOverlappingDatesException("Campsite booking error");				
			}
		}
		
		return campsiteReserveTemplate.save(campsiteReserve);
	}

	@Override
	public Boolean cancelReserve(String id) {
		DeleteResult remove = campsiteReserveTemplate.remove(new CampsiteReserve(id));
		return remove.getDeletedCount() == 1;
	}

}

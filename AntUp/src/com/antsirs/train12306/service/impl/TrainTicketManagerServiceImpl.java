package com.antsirs.train12306.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.antsirs.core.spring.daosupport.DaoTemplate;
import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.Train;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.api.datastore.Key;

public class TrainTicketManagerServiceImpl extends DaoTemplate implements TrainTicketManagerService {
	
	public void createTicket(Ticket ticket){
		getDaoTemplate().persist(ticket);
	}
		
	public void findTicket(Key key){
		getDaoTemplate().find(Ticket.class, key);
	}	
	
	public void deleteTicket(Ticket ticket){
		getDaoTemplate().remove(ticket);
	}
	
	public void createTrain(Train train){
		getDaoTemplate().persist(train);
	}
		
	public void findTrain(Key key){
		getDaoTemplate().find(Ticket.class, key);
	}	

	/**
	 * 
	 * @param trainNo
	 * @param insertDate
	 * @return
	 */
	public List<Train> findTrain(String trainNo, String insertDate){
		String jpql = " select t from Train t where 1=1";				
		jpql += " and trainNo=:trainNo";
		jpql += " and insertDate=:insertDate";	
		Query query = entityManagerFactory.createEntityManager().createQuery(jpql);
		query.setParameter("trainNo", trainNo);
		query.setParameter("insertDate", insertDate);
		return query.getResultList();
	}	
	
	public void deleteTrain(Train train){
		getDaoTemplate().remove(train);
	}

	/**
	 * list tickets
	 */
	public List listTicket() {				
		return getDaoTemplate().getEntityManagerFactory().createEntityManager().
				createNamedQuery("listTicket").getResultList();				
	}
	
	public List<Ticket> listTicketWithPagination(Pagination pagination){		
		pagination.setQueryString(null);
		pagination.setParamValues(null);
		return findWithPagination(Ticket.class, pagination);
	}
}

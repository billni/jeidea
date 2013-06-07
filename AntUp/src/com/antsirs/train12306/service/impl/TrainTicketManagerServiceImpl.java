package com.antsirs.train12306.service.impl;

import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import com.antsirs.core.spring.daosupport.DaoTemplate;
import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.Train;
import com.antsirs.train12306.model.TrainTicketInfo;
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
	public List<Train> findTrain(String trainNo, String insertTime){
		String jpql = " SELECT train FROM Train train ";				
		jpql += " WHERE train.trainNo = :trainNo";
		jpql += " AND train.insertTime = :insertTime";	
		Query query = getDaoTemplate().getEntityManagerFactory().createEntityManager().createQuery(jpql);
		query.setParameter("trainNo", trainNo);
		query.setParameter("insertTime", new Date());
		return query.getResultList();
	}	
	
	public void deleteTrain(Train train){
		getDaoTemplate().remove(train);
	}

	/**
	 * list tickets
	 */
	public List listTicket() {				
		return getDaoTemplate().getEntityManager().createNamedQuery("listTicket").getResultList();				
	}
	
	public List<Ticket> listTicketWithPagination(Pagination pagination){		
		pagination.setQueryString(null);
		pagination.setParamValues(null);
		return findWithPagination(Ticket.class, pagination);
	}
	
	/**
	 * list tickets
	 */
	public List listTicket(String trainNo, String grade) {				
		String jpql = " SELECT ticket FROM Ticket ticket ";				
		jpql += " WHERE ticket.trainNo = :trainNo";
		jpql += " AND ticket.grade = :grade";
		jpql += " ORDER BY trainNo, grade";	
		Query query = getDaoTemplate().getEntityManagerFactory().createEntityManager().createQuery(jpql);
		query.setParameter("trainNo", trainNo);
		query.setParameter("grade", grade);
		return query.getResultList();				
	}
	
}


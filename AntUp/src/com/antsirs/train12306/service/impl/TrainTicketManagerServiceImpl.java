package com.antsirs.train12306.service.impl;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import com.antsirs.core.spring.daosupport.DaoTemplate;
import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.TicketShelf;
import com.antsirs.train12306.model.TicketStock;
import com.antsirs.train12306.model.Train;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.api.datastore.Key;

public class TrainTicketManagerServiceImpl extends DaoTemplate implements TrainTicketManagerService {
	private static final Logger logger = Logger.getLogger(TrainTicketManagerServiceImpl.class.getName());
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
	public List<Train> findTrain(String trainNo, String departureDate) {
		String jpql = " SELECT train FROM Train train ";				
		jpql += " WHERE train.trainNo = :trainNo";
		jpql += " AND train.departureDate = :departureDate";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("trainNo", trainNo);
		query.setParameter("departureDate", departureDate);
		return query.getResultList();		
	}	
	
	public void deleteTrain(Train train){
		getDaoTemplate().remove(train);
	}

	/**
	 * list tickets
	 */
	public List listTicket() {				
		return getDaoTemplate().getEntityManagerFactory().createEntityManager().createNamedQuery("listTicket").getResultList();				
	}
	
	public List<Ticket> listTicketWithPagination(Pagination pagination){		
		pagination.setQueryString(null);
		pagination.setParamValues(null);
		return findWithPagination(Ticket.class, pagination);
	}
	
	/**
	 * list tickets
	 */
	public List<Ticket> listTicket(String trainNo, String grade) {
		EntityManager entityManager= getDaoTemplate().getEntityManagerFactory().createEntityManager();
		List<Ticket> list = null;
		String jpql = " SELECT ticket FROM Ticket ticket ";				
		jpql += " WHERE ticket.trainNo = :trainNo";
		jpql += " AND ticket.grade = :grade";
		jpql += " ORDER BY trainNo, grade";	
		
		Query query = entityManager.createQuery(jpql);
		query.setParameter("trainNo", trainNo);
		query.setParameter("grade", grade);
		list = query.getResultList();
		entityManager.close();
		return list;
	}
	
	/**
	 * 
	 * @param object
	 */
	@Transactional
	public void batchInsertTicket(List<Ticket> tickets) {
		EntityManager em = getEntityManager();		
		EntityTransaction et = em.getTransaction();		
		et.begin();
		int batchSize = 30;		
		int i = 0;
		logger.info("batch insert ticket count - " + tickets.size());
		for(Ticket ticket : tickets){
			Train train = em.find(Train.class, ticket.getTrain().getTrainId());
//			Set<Ticket> ticketSet = train.getTickets();
//			ticketSet.add(ticket);
//			train.setTickets(ticketSet);
//			em.merge(train);
			ticket.setTrain(train);
			em.persist(ticket);
			i++;
			if( i % batchSize == 0 ){
				em.flush();
				em.clear();
			} 
		}
		et.commit();
		em.close();
	}
	
	/**
	 * list tickets
	 */
	public List<Train> listTrain() {
		List<Train> list = null;
		EntityManager entityManager = getDaoTemplate().getEntityManagerFactory().createEntityManager();
		list = entityManager.createNamedQuery("listTrain").getResultList();
		entityManager.close();
		return list;
	}
	
	/**
	 * persist ticketShelf
	 */
	public void createTicketShelf(TicketShelf ticketShelf){
		getDaoTemplate().merge(ticketShelf);
	}
	
	/**
	 * 
	 * @param key
	 */
	public TicketShelf findTicketShelf(String shelfLabel){
		TicketShelf ticketShelf = null;
		EntityManager entityManager= getDaoTemplate().getEntityManagerFactory().createEntityManager();
		Query query = entityManager.createQuery("SELECT m FROM TicketShelf m where m.ticketShelfLabel = :ticketShelfLabel");
		query.setParameter("ticketShelfLabel", shelfLabel);		
		query.setMaxResults(1);
		List<TicketShelf> list = query.getResultList();
		if (list != null && list.size() >0) {			
			ticketShelf = list.get(0);			
		}
		entityManager.close();
		return ticketShelf;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TicketShelf> listTicketShelf() {
		List<TicketShelf> list = null;
		EntityManager entityManager = getDaoTemplate().getEntityManagerFactory().createEntityManager();
		list = entityManager.createNamedQuery("listTicketShelf").getResultList();
		entityManager.close();
		return list;				
	}
	
	/**
	 * persist ticketStock
	 */
	public void createTicketStock(TicketStock ticketStock){
		getDaoTemplate().merge(ticketStock);
	}
	
	/**
	 * 
	 * @param key
	 */
	public TicketStock findTicketStock(String key){
		return getDaoTemplate().find(TicketStock.class, key);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<TicketStock> listTicketStock() {		
		List<TicketStock> list = null;
		EntityManager entityManager = getDaoTemplate().getEntityManagerFactory().createEntityManager();
		list =  entityManager.createNamedQuery("listTicketStock").getResultList();
		entityManager.close();
        return list;
	}
	
	/**
	 * 
	 * @param object
	 */
	@Transactional
	public void batchInsertShelf(List<TicketShelf> ticketShelfs) {
		EntityManager em = getEntityManager();		
		EntityTransaction et = em.getTransaction();		
		et.begin();
		int batchSize = 100;		
		int i = 0;
		logger.info("batch insert ticketShelf count - " + ticketShelfs.size());
		for(TicketShelf ticketShelf : ticketShelfs){
			em.merge(ticketShelf);
			i++;
			if( i % batchSize == 0 ){
				em.flush();
				em.clear();
			} 
		}
		et.commit();
		em.close();		
	}
}


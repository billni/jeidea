package com.antsirs.train12306.service;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.springframework.transaction.annotation.Transactional;
import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.TicketShelf;
import com.antsirs.train12306.model.TicketStock;
import com.antsirs.train12306.model.Train;
import com.antsirs.train12306.model.TrainTicketInfo;
import com.google.appengine.api.datastore.Key;

public interface TrainTicketManagerService {
	/**
	 * insert and update ticket to db
	 * @param object
	 */
	public void createTicket(Ticket ticket);
		
	/**
	 * find an ticket from db
	 * @param entity
	 * @param key
	 */
	public void findTicket(Key key);
	
	/**
	 * 
	 * @param train
	 */
	public void createTrain(Train train);
	
	/**
	 * 	
	 * @param key
	 */
	public void findTrain(Key key);
	
	/**
	 * 
	 * @param trainNo
	 * @param insertTime
	 * @return
	 */
	public List<Train> findTrain(String trainNo,  String departureDate);
	/**
	 * remove ticket from db
	 * @param object
	 */
	public void deleteTicket(Ticket object);
	
	/**
	 * list tickets
	 */
	public List<Ticket> listTicket();
	
	/**
	 * 
	 * @param pagination
	 * @return
	 */
	public List<Ticket> listTicketWithPagination(Pagination pagination);
	
	/**
	 * list tickets
	 */
	public List listTicket(String trainNo, String grade);
	
	/**
	 * 
	 * @param object
	 */
	public void batchInsertTicket(List<Ticket> tickets);
	
	/**
	 * list train
	 * @return
	 */
	public List<Train> listTrain() ;

	/**
	 * persist ticketShelf
	 */
	public void createTicketShelf(TicketShelf ticketShelf);
	
	/**
	 * 
	 * @param key
	 */
	public TicketShelf findTicketShelf(String key);
	
	/**
	 * 
	 * @return
	 */
	public List<TicketShelf> listTicketShelf();

	/**
	 * persist ticketStock
	 */
	public void createTicketStock(TicketStock ticketStock);
	
	/**
	 * 
	 * @param key
	 */
	public TicketStock findTicketStock(String key);
	
	/**
	 * 
	 * @return
	 */
	public List<TicketStock> listTicketStock() ;

	/**
	 * 
	 * @param object
	 */
	@Transactional
	public void batchInsertShelf(List<TicketShelf> ticketShelfs);
}

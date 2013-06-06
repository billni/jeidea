package com.antsirs.train12306.service;

import java.util.List;

import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.Train;
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
	public List<Train> findTrain(String trainNo, String insertTime);
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
}

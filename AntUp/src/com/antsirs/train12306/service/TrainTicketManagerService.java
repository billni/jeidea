package com.antsirs.train12306.service;

import java.util.List;

import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
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
	 * remove ticket from db
	 * @param object
	 */
	public void deleteTicket(Ticket object);
	
	/**
	 * list tickets
	 */
	public List<Ticket> listTicket();
	
	public List<Ticket> listTicketWithPagination(Pagination pagination);
}

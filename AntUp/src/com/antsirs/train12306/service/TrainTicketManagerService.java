package com.antsirs.train12306.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.transaction.annotation.Transactional;

import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
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
	public List<Train> findTrain(String trainNo,  Date departureDate);
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
	@Transactional
	public void batchInsert(List<Ticket> tickets);
	
	/**
	 * list train
	 * @return
	 */
	public List<Train> listTrain() ;
}

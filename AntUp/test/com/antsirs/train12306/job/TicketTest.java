package com.antsirs.train12306.job;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.antsirs.core.common.ConstantValue;
import com.antsirs.train12306.action.Crawl12306Action;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;
import com.google.apphosting.api.ApiProxy;

public class TicketTest extends AbstractTest{
	private Log logger = LogFactory.getLog(TicketTest.class);
	
	@Autowired
	public TrainTicketManagerService trainTicketManagerService ;
		
	/**
	 * 
	 * @throws Exception 
	 */
	@Test
//	@Rollback(false)
	public void testCrawlTrainTicket() throws Exception{
    	Crawl12306Action job = new Crawl12306Action();
    	
    	logger.debug("Test start");
    	job.execute();
    	logger.debug("Test finish");
	}	
	
	@Test
	public void testCrawl() {		
		Ticket ticket = new Ticket();
		ticket.setGrade("Cadfa");		
		Crawl12306Action job = new Crawl12306Action();
		Crawl12306Task task = null;
		Thread worker;
		task = new Crawl12306Task();	
		task.setEnvironment(ApiProxy.getCurrentEnvironment());		
		task.initEnvironment(job.URL, "2013-06-07", job.getHttpClient(new DefaultHttpClient()));			
		task.setTrainTicketManagerService(trainTicketManagerService);			
		worker = new Thread(task);
		worker.setName("Crawl-2013-6-7");
		logger.info("worker[" + worker.getName() + "] start");
		worker.start();
		logger.info("worker[" + worker.getName() + "] finish");
		while (true) {
			if (!worker.isAlive()) {
				logger.info("Thread.activeCount: " + Thread.activeCount());
				break;
			}
		}			
		try {
			Thread.sleep(2000);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}	
	

	@Test
	public void testListTrainTicketInfo(){
		trainTicketManagerService.listTicket(ConstantValue.T5, ConstantValue.SOFT_SLEEP_CLASS);	
	}
}

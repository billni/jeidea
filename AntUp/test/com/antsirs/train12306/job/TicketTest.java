package com.antsirs.train12306.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.antsirs.train12306.action.Crawl12306Action;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;

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
		logger.info("===== " + trainTicketManagerService);
		task.setTrainTicketManagerService(trainTicketManagerService);			
		task.setUrl(job.initUrl(job.URL, "2013-06-06"));
		task.setHttpClient(job.getHttpClient(new DefaultHttpClient()));			
		worker = new Thread(task);
		worker.setName("Crawl-2013-6-6");
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
	
	
}

package com.antsirs.train12306.job;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.antsirs.core.common.ConstantValue;
import com.antsirs.train12306.action.Crawl12306Action;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;
import com.google.apphosting.api.ApiProxy;

public class TicketTest extends AbstractTest{
	private static final Logger logger = Logger.getLogger(CrawlHongKangInsuranceTransaction.class.getName());
	
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
    	
    	logger.info("Test start");
    	job.execute();
    	logger.info("Test finish");
	}	
	
	@Test
//	@Rollback(false)
	public void testCrawl() {		
		ExecutorService  executor = Executors.newFixedThreadPool(20);//(ThreadManager.currentRequestThreadFactory());
		Crawl12306Action job = new Crawl12306Action();
		Crawl12306Task task = null;			
		for (String date : job.getFutureDays()) {
			task = new Crawl12306Task();
			logger.info("Crawling - " + date);
			task.setTrainTicketManagerService(trainTicketManagerService);			
			task.setEnvironment(ApiProxy.getCurrentEnvironment());				
//			task.initParameters(Crawl12306Action.URL, date, job.getHttpClient(new DefaultHttpClient()), job.initProxy());			
			task.initParameters(Crawl12306Action.URL,  date, null, null);		
			executor.execute(task);	
		}
		executor.shutdown();
		while (!executor.isTerminated()) {			
		}
		
		List<Ticket> list = trainTicketManagerService.listTicket();
		logger.info("list.count is " + list.size());
		for (Ticket ticket : list) {
			logger.info("ticket" + ticket.getTrainNo());
		}
//		worker = new Thread(task);
//		worker.setName("Crawl-2013-6-7");
//		logger.info("worker[" + worker.getName() + "] start");
//		worker.start();
//		logger.info("worker[" + worker.getName() + "] finish");
//		while (true) {
//			if (!worker.isAlive()) {
//				logger.info("Thread.activeCount: " + Thread.activeCount());
//				break;
//			}
//		}			
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {			
//			e.printStackTrace();
//		}
	}	
	

	@Test
	public void testListTrainTicketInfo(){
		trainTicketManagerService.listTicket(ConstantValue.T5, ConstantValue.SOFT_SLEEP_CLASS);	
	}
	
	@Test
	public void testGetCurrentEnvironment() {
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getAppId());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getAuthDomain());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getEmail());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getVersionId());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getAttributes());
	}
}

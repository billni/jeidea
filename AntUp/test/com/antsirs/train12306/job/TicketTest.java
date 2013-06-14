package com.antsirs.train12306.job;

import java.util.ArrayList;
import java.util.Date;
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
import com.antsirs.train12306.model.Train;
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
	@Rollback(false)
	public void testCrawl() {		
		ExecutorService  executor = Executors.newFixedThreadPool(20);//(ThreadManager.currentRequestThreadFactory());
		Crawl12306Action job = new Crawl12306Action();
		Crawl12306Task task = null;			
		for (String date : job.getFutureDays()) {
			task = new Crawl12306Task();
			logger.info("Crawling - " + date);
			task.setTrainTicketManagerService(trainTicketManagerService);			
			task.setEnvironment(ApiProxy.getCurrentEnvironment());				
			task.initParameters(Crawl12306Action.URL, date, job.getHttpClient(new DefaultHttpClient()), null);			
//			task.initParameters(Crawl12306Action.URL, date, null, job.initProxy());
//			task.initParameters(Crawl12306Action.URL,  date , null, null);		
			executor.execute(task);	
		}
		executor.shutdown();
		while (!executor.isTerminated()) {			
		}
		
		List<Train> trainlist = trainTicketManagerService.listTrain();
		logger.info("Train count is " + trainlist.size());

		List<Ticket> ticketlist = trainTicketManagerService.listTicket();
		logger.info("Ticket count is " + ticketlist.size());
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
	@Rollback(false)
	public void testListTrainTicketInfo(){
		
		List<Train>  trainlist = trainTicketManagerService.listTrain();
		System.out.println(trainlist.size());
		List<Train> trains = trainTicketManagerService.findTrain("T5", "2013-06-18");
		System.out.println("train size - " + trains.size());
		List<Ticket> tickets = trainTicketManagerService.listTicket(ConstantValue.T5, ConstantValue.SOFT_SLEEP_CLASS);	
		for (Ticket ticket : tickets) {

		}
	}
	
	@Test
	public void testGetCurrentEnvironment() {
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getAppId());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getAuthDomain());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getEmail());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getVersionId());
		System.out.println("ApiProxy.getCurrentEnvironment(): " + ApiProxy.getCurrentEnvironment().getAttributes());
	}
	
	@Test
	@Rollback(false)
	public void testInsertTrainAndTicket() {

		List list = trainTicketManagerService.listTrain();
		logger.info("1 list count " + list.size());
		Train train = new Train();
		train.setTrainNo("T5");
		train.setInsertTime(new Date());

		trainTicketManagerService.createTrain(train);	
		logger.info("train id - " + train.getTrainId());
		list = trainTicketManagerService.listTrain();
		
		Ticket ticket = new Ticket();
		ticket.setInsertTime(new Date());
		ticket.setDepartureDate(train.getDepartureDate());
		ticket.setGrade(ConstantValue.BUSINESS_CLASS);
		ticket.setCount("15");		
		trainTicketManagerService.createTicket(ticket);
		
		logger.info("ticket id - " + ticket.getTicketId());
		
		logger.info("2 list count " + list.size());
	}
	
	@Test
	@Rollback(false)
	public void testBatchInsertTicket() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Train train = new Train();
		train.setTrainNo("T5");
		train.setDepartureDate("2013-6-19");		
		train.setInsertTime(new Date());
		trainTicketManagerService.createTrain(train);		
		
//		Ticket ticket = new Ticket();
//		ticket.setCount("12");
//		ticket.setGrade("ConstantValue.SOFT_SLEEP_CLASS");
//		ticket.setTrainNo("T5");
//		ticket.setTrain(train);		
//		tickets.add(ticket);
//		
//		ticket = new Ticket();
//		ticket.setCount("25");
//		ticket.setTrainNo("T5");		
//		ticket.setGrade("ConstantValue.SOFT_SLEEP_CLASS");
//		ticket.setTrain(train);
//		tickets.add(ticket);
//		
//		trainTicketManagerService.batchInsert(tickets);
//		
//		List<Ticket> ts = trainTicketManagerService.listTicket();	
//		logger.info("ticket count is " + ts.size());
//		for (Ticket ti : ts) {
//			logger.info("ticket -" + ti.getTrain().getTrainNo());
//		}
	}
}

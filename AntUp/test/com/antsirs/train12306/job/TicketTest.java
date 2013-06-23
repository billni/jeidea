package com.antsirs.train12306.job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import com.antsirs.core.common.ConstantValue;
import com.antsirs.core.util.zip.ZipUtils;
import com.antsirs.train12306.action.Crawl12306Action;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.Train;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;
import com.google.apphosting.api.ApiProxy;

public class TicketTest extends AbstractTest {
	private static final Logger logger = Logger
			.getLogger(CrawlHongKangInsuranceTransaction.class.getName());

	@Autowired
	public TrainTicketManagerService trainTicketManagerService;

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	// @Rollback(false)
	public void testCrawlTrainTicket() throws Exception {
		Crawl12306Action job = new Crawl12306Action();

		logger.info("Test start");
		job.execute();
		logger.info("Test finish");
	}

	@Test
	@Rollback(false)
	public void testCrawl() {
		ThreadLocal<List<Future<List<Ticket>>>> tl = new ThreadLocal<List<Future<List<Ticket>>>>();
		List<Future<List<Ticket>>> tickets = tl.get();
		if (tickets == null) {
			tickets = new ArrayList<Future<List<Ticket>>>();
		}

		ExecutorService executor = Executors.newFixedThreadPool(20);// (ThreadManager.currentRequestThreadFactory());
		Crawl12306Action job = new Crawl12306Action();
		Crawl12306Task task = null;
		for (String date : job.getFutureDays()) {
			task = new Crawl12306Task();
			logger.info("Crawling - " + date);
			task.setTrainTicketManagerService(trainTicketManagerService);
			task.setEnvironment(ApiProxy.getCurrentEnvironment());
			task.initParameters(Crawl12306Action.URL, date,
					job.getHttpClient(new DefaultHttpClient()), null);
			// task.initParameters(Crawl12306Action.URL, date, null,
			// job.initProxy());
			// task.initParameters(Crawl12306Action.URL, date , null, null);
			// executor.execute(task);
			Future<List<Ticket>> submit = executor.submit(task);
			tickets.add(submit);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		tl.set(tickets);
		int i = 0;
		for (Future<List<Ticket>> future : tickets) {
			try {
				for (Ticket ticket : future.get()) {
					logger.info(" i: " + i++ + " ticket: "
							+ ticket.getTrainNo() + " , count: "
							+ ticket.getCount());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		logger.info("tickets size from app " + tickets.size());
		List<Train> trainlist = trainTicketManagerService.listTrain();
		logger.info("Train count is " + trainlist.size());

		List<Ticket> ticketlist = trainTicketManagerService.listTicket();
		logger.info("Ticket count is " + ticketlist.size());
		// worker = new Thread(task);
		// worker.setName("Crawl-2013-6-7");
		// logger.info("worker[" + worker.getName() + "] start");
		// worker.start();
		// logger.info("worker[" + worker.getName() + "] finish");
		// while (true) {
		// if (!worker.isAlive()) {
		// logger.info("Thread.activeCount: " + Thread.activeCount());
		// break;
		// }
		// }
		// try {
		// Thread.sleep(2000);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	@Test
	@Rollback(false)
	public void testListTrainTicketInfo() {

		// List<Train> trainlist = trainTicketManagerService.listTrain();
		// System.out.println(trainlist.size());
		// List<Train> trains = trainTicketManagerService.findTrain("T5",
		// "2013-06-18");
		// System.out.println("train size - " + trains.size());
		List<Ticket> tickets = trainTicketManagerService.listTicket();
		for (Ticket ticket : tickets) {

		}
	}

	@Test
	public void testGetCurrentEnvironment() {
		System.out.println("ApiProxy.getCurrentEnvironment(): "
				+ ApiProxy.getCurrentEnvironment().getAppId());
		System.out.println("ApiProxy.getCurrentEnvironment(): "
				+ ApiProxy.getCurrentEnvironment().getAuthDomain());
		System.out.println("ApiProxy.getCurrentEnvironment(): "
				+ ApiProxy.getCurrentEnvironment().getEmail());
		System.out.println("ApiProxy.getCurrentEnvironment(): "
				+ ApiProxy.getCurrentEnvironment().getVersionId());
		System.out.println("ApiProxy.getCurrentEnvironment(): "
				+ ApiProxy.getCurrentEnvironment().getAttributes());
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

		Ticket ticket = new Ticket();
		ticket.setCount("12");
		ticket.setGrade("ConstantValue.SOFT_SLEEP_CLASS");
		ticket.setTrainNo("T5");
		// ticket.setTrain(train);
		tickets.add(ticket);

		ticket = new Ticket();
		ticket.setCount("25");
		ticket.setTrainNo("T5");
		ticket.setGrade("ConstantValue.SOFT_SLEEP_CLASS");
		// ticket.setTrain(train);
		tickets.add(ticket);

		trainTicketManagerService.batchInsert(tickets);

		List<Ticket> ts = trainTicketManagerService.listTicket();
		logger.info("ticket count is " + ts.size());

	}

	/**
	 * test String compress util
	 */
	@Test
	public void testGZipStr() {
		StringBuffer  str = new StringBuffer();
		String ret = "";
		logger.info("before compressed, String length : " + str.length());
		try {
			ret = ZipUtils.compress(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.info("after compressed, String length : " + ret.length());
		
		try {
			ret = ZipUtils.unCompress(ret, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("after unCompressed, String length : " + ret.length() +  " content " + ret);
	}

	public void testEncodeString(){
		 StringBuffer buff = new StringBuffer();
		 buff.append("SerialNo,TrainNo,DepartureDate,Grade,Count,InsertTime,TicketId,");
		 buff.append(1);
		 buff.append("T189");
		 buff.append("2013-06-12");
		 buff.append("SoftSeatClass");
		 buff.append(123);
		 buff.append(new Date());
		 buff.append("123213AEC");
		 buff.append(",");
		 buff.append(2);
		 buff.append("T189");
		 buff.append("2013-06-12");
		 buff.append("SoftSeatClass");
		 buff.append(123);
		 buff.append(new Date());
		 buff.append("123213AEC");
		 buff.append(",");
		 buff.append(3);
		 buff.append("T189");
		 buff.append("2013-06-12");
		 buff.append("SoftSeatClass");
		 buff.append(123);
		 buff.append(new Date());
		 buff.append("123213AEC");
		 buff.append("中国");
		 buff.append(",");
		 String str = buff.toString();
		 System.out.println("1- " + str.length() + " str -" + str);
		 try {
			str = ZipUtils.compress(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 str = ZipUtils.encode64(str);
		 System.out.println("2- " + str.length() + " str -" + str);
		 str = ZipUtils.decode64(str);
		 System.out.println("2- " + str.length() + " str -" + str);
		 try {
			str = ZipUtils.unCompress(str, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("3- " + str.length() + " str -" + str);
	}
	
	/**
	 * 
	 */
	@Test
	public void testDecorder(){
		String str ="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";
		 str = ZipUtils.decode64(str);
		 try {
			str = ZipUtils.unCompress(str, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		 System.out.println("3- " + str.length() + " str -" + str);
	}
}

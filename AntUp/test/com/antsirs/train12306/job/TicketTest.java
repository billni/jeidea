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

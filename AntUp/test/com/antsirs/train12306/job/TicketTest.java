package com.antsirs.train12306.job;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
import org.junit.Ignore;
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
			
			BufferedWriter bw = new BufferedWriter(new FileWriter("./datastore/" + System.currentTimeMillis() + ".csv"));
			bw.write(str);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 System.out.println("3- " + str.length() + " str -" + str);
	}
}

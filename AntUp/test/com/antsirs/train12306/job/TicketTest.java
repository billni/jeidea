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

		trainTicketManagerService.batchInsertTicket(tickets);

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

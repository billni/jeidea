package com.antsirs.train12306.action;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.antsirs.core.util.exception.ExceptionConvert;
import com.antsirs.core.util.zip.ZipUtils;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.TicketContainer;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;
import com.antsirs.train12306.task.SendMultipartMessage;
import com.google.appengine.api.ThreadManager;
import com.google.apphosting.api.ApiProxy;

public class Crawl12306Action extends AbstrtactCrawl12306Action {

	private static final Logger logger = Logger
			.getLogger(Crawl12306Action.class.getName());
	public static final String PROXY_HOST = "10.18.8.60";
	public static final int PROXY_PORT = 8008;
	public static final String PROXY_USERNAME = "niyong";
	public static final String PROXY_PASSWORD = "Ny111111";
	public static final String PROXY_WORKSTATION = "isa06";
	public static final String PROXY_DOMAIN = "ulic";
	public static final String URL = "http://dynamic.12306.cn/otsquery/query/queryRemanentTicketAction.do";

	@Autowired
	public TrainTicketManagerService trainTicketManagerService;

	@Autowired
	public Crawl12306Task task;

	/**
	 * 
	 * @param httpClient
	 * @return
	 */
	public DefaultHttpClient getHttpClient(DefaultHttpClient httpClient) {
		NTCredentials credentials = new NTCredentials(PROXY_USERNAME,
				PROXY_PASSWORD, PROXY_WORKSTATION, PROXY_DOMAIN);
		httpClient.getCredentialsProvider().setCredentials(
				new AuthScope(PROXY_HOST, PROXY_PORT), credentials);
		HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
		return httpClient;
	}

	/**
	 * support java.net.url
	 * 
	 * @return
	 */
	public Proxy initProxy() {
		InetSocketAddress socketAddress;
		Proxy proxy = null;
		try {
			socketAddress = new InetSocketAddress(
					InetAddress.getByName(PROXY_WORKSTATION), PROXY_PORT);
			proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proxy;
	}

	/**
	 * 获得未来20天的日期
	 * 
	 * @return
	 */
	public List<String> getFutureDays() {
		List<String> list = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar(Locale.CHINESE);
		for (int i = 0; i < 20; i++) {
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, i);// 把日期往后增加一天.整数往后推,负数往前移动
			list.add(DateUtils.format(calendar.getTime(), "yyyy-MM-dd"));
		}
		return list;
	}
	
	/**
	 * 获得指定天后的日期
	 * @return
	 */
	public String getSpecialDate(int special) {
		Calendar calendar = new GregorianCalendar(Locale.CHINESE);		
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, special);
		return DateUtils.format(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
     * 
     */
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		
		List<Future<List<Ticket>>> ticketlist = (List<Future<List<Ticket>>>) ServletActionContext.getContext().getApplication().get("ticketlist");
		
		List<Future<List<Ticket>>> tickets = new ArrayList<Future<List<Ticket>>>();
		Future<List<Ticket>> futureTask = null;
		if (ticketlist == null) {
			logger.info("This tickets is null in web application, and new one at once now.");
			ticketlist = new ArrayList<Future<List<Ticket>>>();
		}

		Crawl12306Task task = null;
		ExecutorService executor = Executors.newCachedThreadPool(ThreadManager
				.currentRequestThreadFactory());
		logger.info("Before Task executed, the count of active thread is: "
				+ Thread.activeCount());
		for (String date : getFutureDays()) {
			task = new Crawl12306Task();
			logger.info("Crawling - " + date);
			//-- dev in office , use it.
			task.initParameters(URL, date,	getHttpClient(new DefaultHttpClient()), null);
			//-------------------dev in home, use it-------------------------
//			task.initParameters(URL, date,	new DefaultHttpClient(), null);
			//----------------------------------------------
//			deploy gae produce server , need use it
//			task.initParameters(URL, date, null, null);
			 //-------------------------------------------------
			task.setTrainTicketManagerService(trainTicketManagerService);
			task.setEnvironment(ApiProxy.getCurrentEnvironment());
			futureTask = executor.submit(task);
			ticketlist.add(futureTask);
			tickets.add(futureTask);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		logger.info("After crawling task executed, the count of active thread is: " + Thread.activeCount());
		ServletActionContext.getContext().getApplication().put("ticketlist", ticketlist);	
		logger.info("After crawl Ticket, we got " + tickets.size() + " days data.");
				
		List<String> t5HardSleepTicketCountContainer = null;
		List<String> t5SoftSleepTicketCountContainer = null;
		List<String> t5HardSeatTicketCountContainer = null;
		List<String> t189HardSleepTicketCountContainer = null;
		List<String> t189SoftSleepTicketCountContainer = null;
		List<String> t189HardSeatTicketCountContainer = null;
		List<String> k157HardSleepTicketCountContainer = null;
		List<String> k157SoftSleepTicketCountContainer = null;
		List<String> k157HardSeatTicketCountContainer = null;
		
		List<TicketContainer> list = trainTicketManagerService.listTicketContainer();
		if (list!=null && list.size()>0) {
			t5HardSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT5HardSleepTicketCountContainer();
			t5SoftSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT5SoftSleepTicketCountContainer();
			t5HardSeatTicketCountContainer = ((TicketContainer)list.get(0)).getT5HardSeatTicketCountContainer();
			
			t189HardSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT189HardSleepTicketCountContainer();
			t189SoftSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT189SoftSleepTicketCountContainer();
			t189HardSeatTicketCountContainer = ((TicketContainer)list.get(0)).getT189HardSeatTicketCountContainer();
			
			k157HardSleepTicketCountContainer = ((TicketContainer)list.get(0)).getK157HardSleepTicketCountContainer();
			k157SoftSleepTicketCountContainer = ((TicketContainer)list.get(0)).getK157SoftSleepTicketCountContainer();
			k157HardSeatTicketCountContainer = ((TicketContainer)list.get(0)).getK157HardSeatTicketCountContainer();
			if (t5HardSleepTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					t5HardSleepTicketCountContainer.add("0");
				}
			}			
			if (t5SoftSleepTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					t5SoftSleepTicketCountContainer.add("0");
				}
			}		
			if (t5HardSeatTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					t5HardSeatTicketCountContainer.add("0");
				}
			}
			
			if (t189HardSleepTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					t189HardSleepTicketCountContainer.add("0");
				}
			}			
			if (t189SoftSleepTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					t189SoftSleepTicketCountContainer.add("0");
				}
			}
			if (t189HardSeatTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					t189HardSeatTicketCountContainer.add("0");
				}
			}
			
			if (k157HardSleepTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					k157HardSleepTicketCountContainer.add("0");
				}
			}
			if (k157SoftSleepTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					k157SoftSleepTicketCountContainer.add("0");
				}
			}
			if (k157HardSeatTicketCountContainer.size() == 0){
				for (int i = 0; i < 20; i++) {
					k157HardSeatTicketCountContainer.add("0");
				}
			}
			
			
			logger.info("Fetch tickets count from db.");
						
		}
		int i = 0;
		if (tickets.size() > 0) {			
			try {
				for (Future<List<Ticket>> future : tickets) {
					if (i == 20) {
						i = 0;
					}					
					drawChartEndDate = getSpecialDate(i);
					if (future != null && future.get()!= null) {
						for (Ticket ticket : future.get()) {
							if (drawChartEndDate.equals(ticket.getDepartureDate())){												
								if ("T5".equals(ticket.getTrainNo())) {
									if ("HardSleepClass".equals(ticket.getGrade())) {									
										t5HardSleepTicketCountContainer.add(i, t5HardSleepTicketCountContainer.get(i) + "," + ticket.getCount());									
									} else if ("SoftSleepClass".equals(ticket.getGrade())) {																			
										t5SoftSleepTicketCountContainer.add(i, t5SoftSleepTicketCountContainer.get(i) + "," + ticket.getCount());
									} else if ("HardSeatClass".equals(ticket.getGrade())) {										
										t5HardSeatTicketCountContainer.add(i, t5HardSeatTicketCountContainer.get(i) + "," + ticket.getCount());								
									}
								}
								if ("T189".equals(ticket.getTrainNo())) {
									if ("HardSleepClass".equals(ticket.getGrade())) {										
										t189HardSleepTicketCountContainer.add(i, t189HardSleepTicketCountContainer.get(i) + "," + ticket.getCount());
									} else if ("SoftSleepClass".equals(ticket.getGrade())) {																			
										t189SoftSleepTicketCountContainer.add(i, t189SoftSleepTicketCountContainer.get(i) + "," + ticket.getCount());
									} else if ("HardSeatClass".equals(ticket.getGrade())) {										
										t189HardSeatTicketCountContainer.add(i, t189HardSeatTicketCountContainer.get(i) + "," + ticket.getCount());
									}
								}
								if ("K157".equals(ticket.getTrainNo())) {
									if ("HardSleepClass".equals(ticket.getGrade())) {										
										k157HardSleepTicketCountContainer.add(i, k157HardSleepTicketCountContainer.get(i) + "," + ticket.getCount());
									} else if ("SoftSleepClass".equals(ticket.getGrade())) {										
										k157SoftSleepTicketCountContainer.add(i, k157SoftSleepTicketCountContainer.get(i) + "," + ticket.getCount());
									} else if ("HardSeatClass".equals(ticket.getGrade())) {										
										k157HardSeatTicketCountContainer.add(i, k157HardSeatTicketCountContainer.get(i) + "," + ticket.getCount());										
									}
								}
								
							}
						}
					}
					i++;
				}				
			} catch (Exception e) {
				logger.severe("Drawing compute error, several exception: " + ExceptionConvert.getErrorInfoFromException(e));
			}			
		} else {
			logger.info("I'm Sorry , this 'tickets' is empty now! ");
		}				
		TicketContainer ticketContainer= new TicketContainer();
		ticketContainer.setTicketContainerId("0");
		
		ticketContainer.setK157HardSeatTicketCountContainer(k157HardSeatTicketCountContainer);
		ticketContainer.setK157HardSleepTicketCountContainer(k157HardSleepTicketCountContainer);
		ticketContainer.setK157SoftSleepTicketCountContainer(k157SoftSleepTicketCountContainer);
		
		ticketContainer.setT189HardSeatTicketCountContainer(t189HardSeatTicketCountContainer);
		ticketContainer.setT189HardSleepTicketCountContainer(t189HardSleepTicketCountContainer);
		ticketContainer.setT189SoftSleepTicketCountContainer(t189SoftSleepTicketCountContainer);
		
		ticketContainer.setT5HardSeatTicketCountContainer(t5HardSeatTicketCountContainer);
		ticketContainer.setT5HardSleepTicketCountContainer(t5HardSleepTicketCountContainer);
		ticketContainer.setT5SoftSleepTicketCountContainer(t5SoftSleepTicketCountContainer);
		trainTicketManagerService.createTicketContainer(ticketContainer);
		logger.info("TicketContainer was persisted successfully.");
		return NONE;
	}
	
	/**
	 * 列出ticket
	 * 
	 * @return
	 */
	public String listTicket() {
		tickets = trainTicketManagerService.listTicket();
		return SUCCESS;
	}

	/**
	 * 通过邮件提取数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String extractData() {
		int i = 0;
		StringBuffer buff = new StringBuffer();		
		List<Future<List<Ticket>>> ticketlist = (List<Future<List<Ticket>>>) ServletActionContext
				.getServletContext().getAttribute("ticketlist");
		if (ticketlist != null) {
			buff.append("SerialNo,TrainNo,DepartureDate,Grade,Count,InsertTime");
			buff.append(IOUtils.LINE_SEPARATOR);
			try {
				for (Future<List<Ticket>> future : ticketlist) {
					for (Ticket ticket : future.get()) {
						buff.append(i++);
						buff.append(",");
						buff.append(ticket.getTrainNo());
						buff.append(",");
						buff.append(ticket.getDepartureDate());
						buff.append(",");
						buff.append(ticket.getGrade());
						buff.append(",");
						buff.append(ticket.getCount());
						buff.append(",");
						buff.append(DateUtils.format(ticket.getInsertTime(), "yyyy-MM-dd HH:mm:ss"));											
						buff.append(IOUtils.LINE_SEPARATOR);
					}
					logger.info("ticket count: " + future.get().size());
				}
				String msgContent = ZipUtils.compress(buff.toString());
				msgContent = ZipUtils.encode64(msgContent);
				SendMultipartMessage.sentSimpleMail(msgContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("Extracted Data have mailed special mailbox. " + new Date(GregorianCalendar.getInstance(Locale.CHINA).getTimeInMillis()));
			synchronized (ticketlist) {
				ticketlist = new ArrayList<Future<List<Ticket>>>();
				ServletActionContext.getContext().getApplication().put("ticketlist", ticketlist);						
				i = 0;
			}
			logger.info("Clean tickets from Application Context");
		}
		return SUCCESS;
	}

	/**
	 * 通过邮件提取数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String listExtractData() {
		List<Future<List<Ticket>>> ticketlist = (List<Future<List<Ticket>>>) ServletActionContext
				.getServletContext().getAttribute("ticketlist");
		if (ticketlist != null) {
			logger.info("listExtractData, application context have ticket quantity: " + ticketlist.size());
			tickets = new ArrayList<Ticket>();
			try {
				for (Future<List<Ticket>> future : ticketlist) {
					for (Ticket ticket : future.get()) {
						tickets.add(ticket);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("ticket size "+ tickets.size());
		}
		return SUCCESS;
	}	
	
	/**
	 * 
	 * @return
	 */
	public String convertData() {		
		data = ZipUtils.decode64(data);
		 try {
			 data = ZipUtils.unCompress(data, "UTF-8");									
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String uploadData() {	
		return SUCCESS;
	}

	/**
     * output to highcharts
     */
	@SuppressWarnings("unchecked")
	public String drawTicket() throws Exception {
		//-------------for draw highcharts----------------
		drawChartStartDate = (String)ServletActionContext.getContext().getApplication().get("drawChartStartDate");
		if (drawChartStartDate == null || drawChartStartDate.equals("") ) {
			drawChartStartDate = DateUtils.format(new Date(), "yyyy/MM/dd"); //为了兼容js Date相关方法采用格式yyyy/mm/dd
			ServletActionContext.getContext().getApplication().put("drawChartStartDate", drawChartStartDate);
		}
		
		drawChartEndDate = getSpecialDate(specialDate);
			
		List<String> t5HardSleepTicketCountContainer = null;
		List<String> t5SoftSleepTicketCountContainer = null;
		List<String> t5HardSeatTicketCountContainer = null;
		List<String> t189HardSleepTicketCountContainer = null;
		List<String> t189SoftSleepTicketCountContainer = null;
		List<String> t189HardSeatTicketCountContainer = null;
		List<String> k157HardSleepTicketCountContainer = null;
		List<String> k157SoftSleepTicketCountContainer = null;
		List<String> k157HardSeatTicketCountContainer = null;
		
		List<TicketContainer> list = trainTicketManagerService.listTicketContainer();
		if (list!=null && list.size()>0) {
			t5HardSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT5HardSleepTicketCountContainer();
			t5SoftSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT5SoftSleepTicketCountContainer();
			t5HardSeatTicketCountContainer = ((TicketContainer)list.get(0)).getT5HardSeatTicketCountContainer();
			
			t189HardSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT189HardSleepTicketCountContainer();
			t189SoftSleepTicketCountContainer = ((TicketContainer)list.get(0)).getT189SoftSleepTicketCountContainer();
			t189HardSeatTicketCountContainer = ((TicketContainer)list.get(0)).getT189HardSeatTicketCountContainer();
			
			k157HardSleepTicketCountContainer = ((TicketContainer)list.get(0)).getK157HardSleepTicketCountContainer();
			k157SoftSleepTicketCountContainer = ((TicketContainer)list.get(0)).getK157SoftSleepTicketCountContainer();
			k157HardSeatTicketCountContainer = ((TicketContainer)list.get(0)).getK157HardSeatTicketCountContainer();		
				
			t5HardSleepTicketCountSpecialDate = t5HardSleepTicketCountContainer.get(Integer.valueOf(specialDate));
			t5SoftSleepTicketCountSpecialDate = t5SoftSleepTicketCountContainer.get(Integer.valueOf(specialDate));
			t5HardSeatTicketCountSpecialDate = t5HardSeatTicketCountContainer.get(Integer.valueOf(specialDate));
			t189HardSleepTicketCountSpecialDate = t189HardSleepTicketCountContainer.get(Integer.valueOf(specialDate));
			t189SoftSleepTicketCountSpecialDate = t189SoftSleepTicketCountContainer.get(Integer.valueOf(specialDate));
			t189HardSeatTicketCountSpecialDate = t189HardSeatTicketCountContainer.get(Integer.valueOf(specialDate));
			k157HardSleepTicketCountSpecialDate = k157HardSleepTicketCountContainer.get(Integer.valueOf(specialDate));
			k157SoftSleepTicketCountSpecialDate = k157SoftSleepTicketCountContainer.get(Integer.valueOf(specialDate));
			k157HardSeatTicketCountSpecialDate = k157HardSeatTicketCountContainer.get(Integer.valueOf(specialDate));
		}
		  
		logger.info("Finish computing for drawing! ");
		return SUCCESS;
	}
}

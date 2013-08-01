package com.antsirs.train12306.action;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import com.antsirs.core.util.zip.ZipUtils;
import com.antsirs.train12306.model.Ticket;
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
	public String getEndDate(int special) {
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
		List<Future<List<Ticket>>> ticketlist = (List<Future<List<Ticket>>>) ServletActionContext
				.getServletContext().getAttribute("ticketlist");
		
		List<Future<List<Ticket>>> tickets = (List<Future<List<Ticket>>>) ServletActionContext
				.getServletContext().getAttribute("tickets");
		
		if (ticketlist == null) {
			logger.info("This tickets is null in web application, and new one at once now.");
			ticketlist = new ArrayList<Future<List<Ticket>>>();
		}
		
		if (tickets == null) {
			tickets = new ArrayList<Future<List<Ticket>>>();
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
//			task.initParameters(URL, date,	getHttpClient(new DefaultHttpClient()), null);
			//-------------------dev in home, use it-------------------------
//			task.initParameters(URL, date,	new DefaultHttpClient(), null);
			//----------------------------------------------
//			deploy gae produce server , need use it
			task.initParameters(URL, date, null, null);
			 //-------------------------------------------------
			task.setTrainTicketManagerService(trainTicketManagerService);
			task.setEnvironment(ApiProxy.getCurrentEnvironment());
			Future<List<Ticket>> future = executor.submit(task);
			ticketlist.add(future);
			tickets.add(future);
			// executor.execute(task);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		logger.info("After Task executed, the count of active thread is: " + Thread.activeCount());
		ServletActionContext.getServletContext().setAttribute("ticketlist", ticketlist);		
		ServletActionContext.getServletContext().setAttribute("tickets", tickets);

		//-------------for draw highcharts----------------
		drawChartStartDate = (String)ServletActionContext.getServletContext().getAttribute("drawChartStartDate");
		if (drawChartStartDate == null || drawChartStartDate.equals("") ) {
			drawChartStartDate = DateUtils.format(new Date(), "yyyy/MM/dd"); //为了兼容js Date相关方法采用格式yyyy/mm/dd
			ServletActionContext.getServletContext().setAttribute("drawChartStartDate", drawChartStartDate);
		}
	
		t189SoftSleepTicketCount = new String[20]; 
		t189HardSleepTicketCount = new String[20]; 
		t189HardSeatTicketCount = new String[20];
		t5SoftSleepTicketCount = new String[20];
		t5HardSleepTicketCount = new String[20];
		t5HardSeatTicketCount = new String[20];
		k157SoftSleepTicketCount = new String[20];
		k157HardSleepTicketCount = new String[20];
		k157HardSeatTicketCount = new String[20];
		
		int i = 0;
		if (tickets != null) {			
			try {
				for (Future<List<Ticket>> future : tickets) {
					if (i == 20) {
						i = 0;
					}
					drawChartEndDate = getEndDate(i);
					for (Ticket ticket : future.get()) {
						if (drawChartEndDate.equals(ticket.getDepartureDate())){												
							if ("T5".equals(ticket.getTrainNo())) {
								if ("HardSleepClass".equals(ticket.getGrade())) {
									if (t5HardSleepTicketCount[i] == null || "".equals(t5HardSleepTicketCount[i])) {
										t5HardSleepTicketCount[i] = ticket.getCount();
									}  else {
										t5HardSleepTicketCount[i] = t5HardSleepTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t5HardSleepTicketCount"+drawChartEndDate, t5HardSleepTicketCount[i]);									
								} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
									if (t5SoftSleepTicketCount[i] == null || "".equals(t5SoftSleepTicketCount[i])) {
										t5SoftSleepTicketCount[i] = ticket.getCount();
									} else {
										t5SoftSleepTicketCount[i] = t5SoftSleepTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t5SoftSleepTicketCount"+drawChartEndDate, t5SoftSleepTicketCount[i]);
								} else if ("HardSeatClass".equals(ticket.getGrade())) {
									if (t5HardSeatTicketCount[i] == null || "".equals(t5HardSeatTicketCount[i])) {
										t5HardSeatTicketCount[i] = ticket.getCount();
									} else {
										t5HardSeatTicketCount[i] = t5HardSeatTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t5HardSeatTicketCount"+drawChartEndDate, t5HardSeatTicketCount[i]);								
								}
							}
							if ("T189".equals(ticket.getTrainNo())) {
								if ("HardSleepClass".equals(ticket.getGrade())) {
									if (t189HardSleepTicketCount[i] == null || "".equals(t189HardSleepTicketCount[i])) {
										t189HardSleepTicketCount[i] = ticket.getCount();
									}  else {
										t189HardSleepTicketCount[i] = t189HardSleepTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t189HardSleepTicketCount"+drawChartEndDate, t189HardSleepTicketCount[i]);
								} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
									if (t189SoftSleepTicketCount[i] == null || "".equals(t189SoftSleepTicketCount[i])) {
										t189SoftSleepTicketCount[i] = ticket.getCount();
									} else {
										t189SoftSleepTicketCount[i] = t189SoftSleepTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t189SoftSleepTicketCount"+drawChartEndDate, t189SoftSleepTicketCount[i]);
								} else if ("HardSeatClass".equals(ticket.getGrade())) {
									if (t189HardSeatTicketCount[i] == null || "".equals(t189HardSeatTicketCount[i])) {
										t189HardSeatTicketCount[i] = ticket.getCount();
									} else {
										t189HardSeatTicketCount[i] = t189HardSeatTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t189HardSeatTicketCount"+drawChartEndDate, t189HardSeatTicketCount[i]);
								}
							}
							if ("K157".equals(ticket.getTrainNo())) {
								if ("HardSleepClass".equals(ticket.getGrade())) {
									if (k157HardSleepTicketCount[i] == null || "".equals(k157HardSleepTicketCount[i])) {
										k157HardSleepTicketCount[i] = ticket.getCount();
									}  else {
										k157HardSleepTicketCount[i] = k157HardSleepTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("k157HardSleepTicketCount"+drawChartEndDate, k157HardSleepTicketCount[i]);
								} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
									if (k157SoftSleepTicketCount[i] == null || "".equals(k157SoftSleepTicketCount[i])) {
										k157SoftSleepTicketCount[i] = ticket.getCount();
									} else {
										k157SoftSleepTicketCount[i] = k157SoftSleepTicketCount[i] + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("k157SoftSleepTicketCount"+drawChartEndDate, k157SoftSleepTicketCount[i]);
								} else if ("HardSeatClass".equals(ticket.getGrade())) {
									if (k157HardSeatTicketCount[i] == null || "".equals(k157HardSeatTicketCount[i])) {
										k157HardSeatTicketCount[i] = ticket.getCount();
									} else {
										k157HardSeatTicketCount[i] = k157HardSeatTicketCount[i]+ "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("k157HardSeatTicketCount"+drawChartEndDate, k157HardSeatTicketCount[i]);
									
								}
							}
							
						}
					}
					i++;
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}		
		//-----------------------------
		
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
				ServletActionContext.getServletContext().setAttribute(
						"ticketlist", ticketlist);
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
		drawChartEndDate = getEndDate(specialDate);
		t5HardSleepTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("t5HardSleepTicketCount"+drawChartEndDate);
		t5SoftSleepTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("t5SoftSleepTicketCount"+drawChartEndDate);
		t5HardSeatTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("t5HardSeatTicketCount"+drawChartEndDate);
		t189HardSleepTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("t189HardSleepTicketCount"+drawChartEndDate);
		t189SoftSleepTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("t189SoftSleepTicketCount"+drawChartEndDate);
		t189HardSeatTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("t189HardSeatTicketCount"+drawChartEndDate);	
		k157HardSleepTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("k157HardSleepTicketCount"+drawChartEndDate);
		k157SoftSleepTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("k157SoftSleepTicketCount"+drawChartEndDate);
		k157HardSeatTicketCountSpecialDate = (String)ServletActionContext.getServletContext().getAttribute("k157HardSeatTicketCount"+drawChartEndDate);			
		drawChartStartDate = (String)ServletActionContext.getServletContext().getAttribute("drawChartStartDate");			
		return SUCCESS;
	}
}

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
		calendar.add(Calendar.DATE, special-1);
		return DateUtils.format(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
     * 
     */
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {
		List<Future<List<Ticket>>> ticketlist = (List<Future<List<Ticket>>>) ServletActionContext
				.getServletContext().getAttribute("ticketlist");
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
			// executor.execute(task);
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		logger.info("After Task executed, the count of active thread is: "
				+ Thread.activeCount());
		ServletActionContext.getServletContext().setAttribute("ticketlist",
				ticketlist);

		//-------------for draw highcharts----------------
		drawChartStartDate = (String)ServletActionContext.getServletContext().getAttribute("drawChartStartDate");
		if (drawChartStartDate == null || drawChartStartDate.equals("") ) {
			drawChartStartDate = DateUtils.format(new Date(), "yyyy/MM/dd"); //为了兼容js Date相关方法采用格式yyyy/mm/dd
			ServletActionContext.getServletContext().setAttribute("drawChartStartDate", drawChartStartDate);
		}
	
		drawChartEndDate = getEndDate(20);

		if (ticketlist != null) {			
			try {
				for (Future<List<Ticket>> future : ticketlist) {
					for (Ticket ticket : future.get()) {
						if (drawChartEndDate.equals(ticket.getDepartureDate())){												
							if ("T5".equals(ticket.getTrainNo())) {
								if ("HardSleepClass".equals(ticket.getGrade())) {
									if (t5HardSleepTicketCount == null || t5HardSleepTicketCount.equals("")) {
										t5HardSleepTicketCount = ticket.getCount();
									}  else {
										t5HardSleepTicketCount = t5HardSleepTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t5HardSleepTicketCount", t5HardSleepTicketCount);
								} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
									if (t5SoftSleepTicketCount == null || t5SoftSleepTicketCount.equals("")) {
										t5SoftSleepTicketCount = ticket.getCount();
									} else {
										t5SoftSleepTicketCount = t5SoftSleepTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t5SoftSleepTicketCount", t5SoftSleepTicketCount);
								} else if ("HardSeatClass".equals(ticket.getGrade())) {
									if (t5HardSeatTicketCount == null || t5HardSeatTicketCount.equals("")) {
										t5HardSeatTicketCount = ticket.getCount();
									} else {
										t5HardSeatTicketCount = t5HardSeatTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t5HardSeatTicketCount", t5HardSeatTicketCount);
								}
							}
							if ("T189".equals(ticket.getTrainNo())) {
								if ("HardSleepClass".equals(ticket.getGrade())) {
									if (t189HardSleepTicketCount == null || t189HardSleepTicketCount.equals("")) {
										t189HardSleepTicketCount = ticket.getCount();
									}  else {
										t189HardSleepTicketCount = t189HardSleepTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t189HardSleepTicketCount", t189HardSleepTicketCount);
								} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
									if (t189SoftSleepTicketCount == null || t189SoftSleepTicketCount.equals("")) {
										t189SoftSleepTicketCount = ticket.getCount();
									} else {
										t189SoftSleepTicketCount = t189SoftSleepTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t189SoftSleepTicketCount", t189SoftSleepTicketCount);
								} else if ("HardSeatClass".equals(ticket.getGrade())) {
									if (t189HardSeatTicketCount == null || t189HardSeatTicketCount.equals("")) {
										t189HardSeatTicketCount = ticket.getCount();
									} else {
										t189HardSeatTicketCount = t189HardSeatTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("t189HardSeatTicketCount", t189HardSeatTicketCount);
								}
							}
							if ("K157".equals(ticket.getTrainNo())) {
								if ("HardSleepClass".equals(ticket.getGrade())) {
									if (k157HardSleepTicketCount == null || k157HardSleepTicketCount.equals("")) {
										k157HardSleepTicketCount = ticket.getCount();
									}  else {
										k157HardSleepTicketCount = k157HardSleepTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("k157HardSleepTicketCount", k157HardSleepTicketCount);
								} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
									if (k157SoftSleepTicketCount == null || k157SoftSleepTicketCount.equals("")) {
										k157SoftSleepTicketCount = ticket.getCount();
									} else {
										k157SoftSleepTicketCount = k157SoftSleepTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("k157SoftSleepTicketCount", k157SoftSleepTicketCount);
								} else if ("HardSeatClass".equals(ticket.getGrade())) {
									if (k157HardSeatTicketCount == null || k157HardSeatTicketCount.equals("")) {
										k157HardSeatTicketCount = ticket.getCount();
									} else {
										k157HardSeatTicketCount = k157HardSeatTicketCount + "," + ticket.getCount();
									}
									ServletActionContext.getServletContext().setAttribute("k157HardSeatTicketCount", k157HardSeatTicketCount);
								}
							}
							
						}
					}					
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
		drawChartStartDate = (String)ServletActionContext.getServletContext().getAttribute("drawChartStartDate");
		t5HardSleepTicketCount = (String)ServletActionContext.getServletContext().getAttribute("t5HardSleepTicketCount");
		t5SoftSleepTicketCount = (String)ServletActionContext.getServletContext().getAttribute("t5SoftSleepTicketCount");
		t5HardSeatTicketCount = (String)ServletActionContext.getServletContext().getAttribute("t5HardSeatTicketCount");
		t189HardSleepTicketCount = (String)ServletActionContext.getServletContext().getAttribute("t189HardSleepTicketCount");
		t189SoftSleepTicketCount = (String)ServletActionContext.getServletContext().getAttribute("t189SoftSleepTicketCount");
		t189HardSeatTicketCount = (String)ServletActionContext.getServletContext().getAttribute("t189HardSeatTicketCount");	
		k157HardSleepTicketCount = (String)ServletActionContext.getServletContext().getAttribute("k157HardSleepTicketCount");
		k157SoftSleepTicketCount = (String)ServletActionContext.getServletContext().getAttribute("k157SoftSleepTicketCount");
		k157HardSeatTicketCount = (String)ServletActionContext.getServletContext().getAttribute("k157HardSeatTicketCount");	
		drawChartEndDate = getEndDate(20);				
		return SUCCESS;
	}
}

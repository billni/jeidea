package com.antsirs.train12306.action;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
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
import com.antsirs.train12306.model.TicketShelf;
import com.antsirs.train12306.model.TicketStock;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;
import com.antsirs.train12306.task.SendMultipartMessage;
import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.apphosting.api.ApiProxy;

public class Crawl12306Action extends AbstrtactCrawl12306Action {

	private static final Logger logger = Logger
			.getLogger(Crawl12306Action.class.getName());
	public static final String PROXY_HOST = "10.18.8.60";
	public static final int PROXY_PORT = 8008;
	public static final String PROXY_USERNAME = "niyong";
	public static final String PROXY_PASSWORD = "nY111111";
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
	 * 获得指定天前的日期
	 * @return
	 */
	public String getSpecialStartDate(int special) {
		Calendar calendar = new GregorianCalendar(Locale.CHINESE);		
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, -special);
		return DateUtils.format(calendar.getTime(), "yyyy/MM/dd");
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
//			task.initParameters(URL, date,	getHttpClient(new DefaultHttpClient()), null);
			//-------------------dev in home, use it-------------------------
//			task.initParameters(URL, date,	new DefaultHttpClient(), null);
			//----------------------------------------------
//			deploy gae produce server , need use it
			task.initParameters(URL, date, null, null);
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
		computeTicket(tickets);
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
		drawChartStartDate = getSpecialStartDate(specialDate);
		drawChartEndDate = getSpecialDate(specialDate);
		logger.info("Begin draw chart - " + drawChartEndDate);
		TicketShelf ticketShelf = null;
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-T5-HardSleepClass");
		if (ticketShelf != null) {
			t5HardSleepTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-T5-SoftSleepClass");
		if (ticketShelf != null) {
			t5SoftSleepTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-T5-HardSeatClass");
		if (ticketShelf != null) {
			t5HardSeatTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}
		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-T189-HardSleepClass");
		if (ticketShelf != null) {
			t189HardSleepTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-T189-SoftSleepClass");
		if (ticketShelf != null) {
			t189SoftSleepTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-T189-HardSeatClass");
		if (ticketShelf != null) {
			t189HardSeatTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}
		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-K157-HardSleepClass");
		if (ticketShelf != null) {
			k157HardSleepTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-K157-SoftSleepClass");
		if (ticketShelf != null) {
			k157SoftSleepTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}		
		ticketShelf = trainTicketManagerService.findTicketShelf(drawChartEndDate + "-K157-HardSeatClass");
		if (ticketShelf != null) {
			k157HardSeatTicketCountSpecialDate = ticketShelf.getTicketCount().getValue();
		}
	
		logger.info("Finish draw chart - " + drawChartEndDate);
		return SUCCESS;
	}
	
	/**
	 * 计算票数,为了图表
	 */
	public void computeTicket(List<Future<List<Ticket>>> tickets) {		
		AsyncMemcacheService asyncCache = MemcacheServiceFactory.getAsyncMemcacheService();
	    asyncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		TicketStock ticketStock = null;
		List<TicketShelf> list = null;
		int i = 0;
		logger.info("Begin compute for drawing...");
		if (tickets != null) {	
			logger.info("We have " + tickets.size() + " days data.");
			try {
				for (Future<List<Ticket>> future : tickets) {
					if (i == 20) {
						i = 0;
					}					
					drawChartEndDate = getSpecialDate(i);
					Future<Object> futureValue = (Future<Object>)asyncCache.get(drawChartEndDate+"-ticketStock");
					ticketStock = (TicketStock) futureValue.get();
					if (ticketStock == null) {
						ticketStock = trainTicketManagerService.findTicketStock(drawChartEndDate);	
						asyncCache.put(drawChartEndDate +"-ticketStock", ticketStock);
					}					
					if (ticketStock == null) {
						ticketStock = new TicketStock(); 
						ticketStock.setDepartureDate(drawChartEndDate);
						ticketStock.setTicketShelf(new HashSet<TicketShelf>());
						trainTicketManagerService.createTicketStock(ticketStock);
						asyncCache.put(drawChartEndDate +"-ticketStock", ticketStock);
					}
					list = new ArrayList<TicketShelf>();
					if (future != null && future.get()!= null) {
						for (Ticket ticket : future.get()) {
							if (drawChartEndDate.equals(ticket.getDepartureDate())){												
								if ("T5".equals(ticket.getTrainNo())) {
									if ("HardSleepClass".equals(ticket.getGrade())) {
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-T5-HardSleepClass", ticket, ticketStock));
									} else if ("SoftSleepClass".equals(ticket.getGrade())) {
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-T5-SoftSleepClass", ticket, ticketStock));
									} else if ("HardSeatClass".equals(ticket.getGrade())) {
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-T5-HardSeatClass", ticket, ticketStock));
									}
								}
								if ("T189".equals(ticket.getTrainNo())) {
									if ("HardSleepClass".equals(ticket.getGrade())) {
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-T189-HardSleepClass", ticket, ticketStock));
									} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-T189-SoftSleepClass", ticket, ticketStock));
									} else if ("HardSeatClass".equals(ticket.getGrade())) {
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-T189-HardSeatClass", ticket, ticketStock));
									}
								}
								if ("K157".equals(ticket.getTrainNo())) {
									if ("HardSleepClass".equals(ticket.getGrade())) {
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-K157-HardSleepClass", ticket, ticketStock));
									} else if ("SoftSleepClass".equals(ticket.getGrade())) {									
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-K157-SoftSleepClass", ticket, ticketStock));
									} else if ("HardSeatClass".equals(ticket.getGrade())) {
										list.addAll(saveTicketShelf(asyncCache, drawChartEndDate+"-K157-HardSeatClass", ticket, ticketStock));							
									}
								}
								
							}
						}
					}
					i++;
					trainTicketManagerService.batchInsertShelf(list);
				}				
			} catch (Exception e) {
				logger.severe("Drawing compute error, several exception: " + ExceptionConvert.getErrorInfoFromException(e));
			}			
		} else {
			logger.info("I'm Sorry , this 'tickets' is null now! ");
		}
		logger.info("Finish compute for drawing.");
	}
	
	public List<TicketShelf> saveTicketShelf(AsyncMemcacheService asyncCache, String label, Ticket ticket , TicketStock ticketStock){		
		TicketShelf ticketShelf = null;
		List<TicketShelf> list = new ArrayList<TicketShelf>();	
		Future<Object> futureValue = (Future<Object>)asyncCache.get(label);
		try {
			ticketShelf = (TicketShelf) futureValue.get();
		} catch (Exception e) {
			logger.severe("saveTicketShelf exception: " + ExceptionConvert.getErrorInfoFromException(e));
		}
		if (ticketShelf == null) {
			ticketShelf = trainTicketManagerService.findTicketShelf(label);
		}										
		if (ticketShelf == null) {
			ticketShelf =  new TicketShelf();
			ticketShelf.setTicketShelfLabel(label);
			ticketShelf.setTicketCount(ticket.getCount());
			ticketShelf.setTicketStock(ticketStock);
		} else {
			ticketShelf.setTicketCount(ticketShelf.getTicketCount().getValue() + "," + ticket.getCount());
		}
		asyncCache.put(label, ticketShelf);
		list.add(ticketShelf);
		return list;
		
		
	}
}

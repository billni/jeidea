package com.antsirs.train12306.action;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;
import com.google.appengine.api.ThreadManager;
import com.google.apphosting.api.ApiProxy;

public class Crawl12306Action extends AbstrtactCrawl12306Action {
	
	private static final Logger logger = Logger.getLogger(Crawl12306Action.class.getName());
	public static final String PROXY_HOST = "10.18.8.60";
	public static final int PROXY_PORT = 8008;
	public static final String PROXY_USERNAME = "niyong";
	public static final String PROXY_PASSWORD = "Ny111111";
	public static final String PROXY_WORKSTATION = "isa06";
	public static final String PROXY_DOMAIN = "ulic";
	public static final String URL = "http://dynamic.12306.cn/otsquery/query/queryRemanentTicketAction.do";

	@Autowired
	public TrainTicketManagerService trainTicketManagerService ;
	
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
	 * @return
	 */
	public Proxy initProxy() {  
        InetSocketAddress socketAddress;
        Proxy proxy = null;
		try {
			socketAddress = new InetSocketAddress(InetAddress.getByName(PROXY_WORKSTATION), PROXY_PORT);
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
     * 
     */
	public String execute() throws Exception {		
		Crawl12306Task task = null;	
		ExecutorService  executor = Executors.newCachedThreadPool(ThreadManager.currentRequestThreadFactory());
		logger.info("Before Task executed, the count of active thread is: " + Thread.activeCount());		
		for (String date : getFutureDays()) {
			task = new Crawl12306Task();
			logger.info("Crawling - " + date);
//			task.initParameters(URL, date, getHttpClient(new DefaultHttpClient()), null);
//			task.initParameters(URL, date, null, initProxy());
			task.initParameters(URL, date, null, null);			
			task.setTrainTicketManagerService(trainTicketManagerService);
			task.setEnvironment(ApiProxy.getCurrentEnvironment());
			executor.execute(task);	
		}
		executor.shutdown();
		while (!executor.isTerminated()) {			
		}
		logger.info("After Task executed, the count of active thread is: " + Thread.activeCount());				
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
	

}

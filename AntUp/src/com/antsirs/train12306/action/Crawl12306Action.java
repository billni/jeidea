package com.antsirs.train12306.action;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.backportconcurrent.ThreadPoolTaskExecutor;

import com.antsirs.train12306.service.TrainTicketManagerService;
import com.antsirs.train12306.task.Crawl12306Task;
import com.google.apphosting.api.ApiProxy;

public class Crawl12306Action extends AbstrtactCrawl12306Action {
	
	public Log logger = LogFactory.getLog(Crawl12306Action.class);
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
	 * Generate Url
	 * 
	 * @return
	 */
	public String initUrl(String url, String date) {
		URIBuilder builder;
		URI ret = null;
		try {
			builder = new URIBuilder(URL);
			ret = builder
					.addParameter("method", "queryLeftTicket")
					.addParameter("orderRequest.train_date", date)
					.addParameter("orderRequest.from_station_telecode", "BJP")
					.addParameter("orderRequest.to_station_telecode", "LZZ")
					.addParameter("orderRequest.train_no", "")
					.addParameter("trainPassType", "QB")
					.addParameter("trainClass", "QB#D#Z#T#K#QT#")
					.addParameter("includeStudent", "00")
					.addParameter("seatTypeAndNum", "")
					.addParameter("orderRequest.start_time_str", "00:00--24:00")
					.build();
			logger.info("url: " + ret.toASCIIString());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return ret.toASCIIString();
	}

	/**
	 * 获得未来20天的日期
	 * 
	 * @return
	 */
	public List<String> getFuture20Days() {
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
		Thread worker;
		for (String date : getFuture20Days()) {
			task = new Crawl12306Task();
			logger.info("Crawling - " + date);							
			task.setUrl(initUrl(URL, date));
			task.setHttpClient(getHttpClient(new DefaultHttpClient()));			
			task.setTrainTicketManagerService(trainTicketManagerService);
			task.setEnvironment(ApiProxy.getCurrentEnvironment());
			worker = new Thread(task);
			worker.setName("Crawl-" + date);
			logger.info("worker[" + worker.getName() + "] - start");
			worker.start();
			logger.info("worker[" + worker.getName() + "] - finish");
			while (true) {
				if (!worker.isAlive()) {
					logger.info("Thread.activeCount: " + Thread.activeCount());
					break;
				}
			}			
			Thread.sleep(2000);
		}
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

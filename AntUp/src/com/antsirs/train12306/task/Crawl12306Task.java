package com.antsirs.train12306.task;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.Train;
import com.antsirs.train12306.model.TrainTicketInfo;
import com.antsirs.train12306.service.TrainTicketManagerService;
import java.util.logging.Logger;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;

public class Crawl12306Task implements Runnable {
	private static final Logger logger = Logger.getLogger(Crawl12306Task.class.getName());

	private URL url;
	private DefaultHttpClient httpClient;		

	public TrainTicketManagerService trainTicketManagerService;
	
	public TrainTicketManagerService getTrainTicketManagerService() {
		return trainTicketManagerService;
	}

	public void setTrainTicketManagerService(TrainTicketManagerService trainTicketManagerService) {
		this.trainTicketManagerService = trainTicketManagerService;
	}

	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public Proxy proxy;
	
	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public URL getUrl() {
		return url;
	}
	
	public void setUrl(URL url) {
		this.url = url;
	}

	public String departureDate;
	
	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public Crawl12306Task() {
	}

	/**
	 * Generate Url
	 * 
	 * @return
	 */
	public URL initUrl(String url, String date) {		
		URI ret = null;
		try {
			URIBuilder builder = new URIBuilder(url);
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
			logger.info("url: " + ret.toURL().toString());
			return ret.toURL();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}		
	
	
	/**
	 * 根据URL获得html信息
	 * 
	 * @param url
	 * @return
	 */
	public String getTrainTicketInfoByUrl(String url) {
		StringWriter sw = new StringWriter();
		try {
			logger.info("HttpClient连接开启.");
			HttpResponse response = getHttpClient().execute(new HttpGet(url));// 得到responce对象
			int resStatu = response.getStatusLine().getStatusCode();// 返回
			if (resStatu == HttpStatus.SC_OK) {// 200正常
				// 获得相应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStreamReader insr = new InputStreamReader(
							entity.getContent(), "UTF-8" /*
														 * ContentType.getOrDefault
														 * (entity).getCharset()
														 */);
					IOUtils.copy(insr, sw);
					insr.close();
				}
			} else {
				logger.info("Http Status Code:" + resStatu);
			}
		} catch (Exception e) {
			logger.severe(e.getMessage());			
			e.printStackTrace();
		} finally {
			logger.info("HttpClient连接关闭.");
			httpClient.getConnectionManager().shutdown();
		}
		return sw.toString();
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public String crawlTrainTicketInfo(URL url, Proxy proxy){
		StringWriter sw = new StringWriter();	
		InputStreamReader insr = null;
		long startTime = 0L;
        try {
        	startTime = System.currentTimeMillis();
        	logger.info("crawlTrainTicketInfo start. " + startTime);
        	logger.info("crawl url: " + url.toString());        	
//       	insr = new InputStreamReader(getUrl().openConnection(proxy).getInputStream(), "UTF-8" /*ContentType.getOrDefault*/);        	
       		insr = new InputStreamReader(url.openConnection().getInputStream(), "UTF-8" /*ContentType.getOrDefault*/);
			IOUtils.copy(insr, sw);
			sw.close();
			insr.close();
        } catch (Exception e) {        	 
			logger.severe(e.getMessage());			
			e.printStackTrace();
		}        
        logger.info("crawlTrainTicketInfo complete. Spend time(s): " + (System.currentTimeMillis() - startTime)/1000);
		return sw.toString();
	}


	/**
	 * Generate Record
	 * 
	 * @param data
	 */
	public List<TrainTicketInfo> anaylseTrainTicketInfo(String data) {
		List<TrainTicketInfo> list = null;
		TrainTicketInfo trainTicketInfo = null;
		if (data != null && !"".equals(data)) {
			list = new ArrayList<TrainTicketInfo>();
			String str[] = data.split(",<span");
			String temp = "";
			for (String sf : str) {
				if (sf.length() > 2) {
					sf = sf.replace("&nbsp;", "").trim();
					temp = matchPattern(sf, "id(.*)Out");
					sf = sf.replace(temp, "");
					sf = sf.replace("()'>", "");
					sf = sf.replace("</span>", "");
					sf = sf.replace("<br>", ",");
					sf = sf.replace(
							"<img src='/otsquery/images/tips/first.gif'>", "");
					sf = sf.replace("<font color='darkgray'>", "");
					sf = sf.replace("</font>", "");

					String s[] = sf.split(",");
					if (s != null && s.length > 0) {
						trainTicketInfo = new TrainTicketInfo();
						trainTicketInfo.setTrainNo(s[0]);
						trainTicketInfo.setFromStation(s[1]);
						trainTicketInfo.setDepartureTime(s[2]);
						trainTicketInfo.setToStation(s[3]);
						trainTicketInfo.setArrvialTime(s[4]);
						trainTicketInfo.setDuring(s[5]);
						trainTicketInfo.setBusinessClass(s[6]);
						trainTicketInfo.setSpecialClass(s[7]);
						trainTicketInfo.setFirstClass(s[8]);
						trainTicketInfo.setSecondClass(s[9]);
						trainTicketInfo.setSeniorSoftSleepClass(s[10]);
						trainTicketInfo.setSoftSleepClass(s[11]);
						trainTicketInfo.setHardSleepClass(s[12]);
						trainTicketInfo.setSoftSeatClass(s[13]);
						trainTicketInfo.setHardSeatClass(s[14]);
						trainTicketInfo.setStanding(s[15]);
						trainTicketInfo.setOthers(s[16]);
						trainTicketInfo.setInsertTime(new Date());
						list.add(trainTicketInfo);
					}
				}
			}
		}
		return list;
	}

	/**
	 * Match special string
	 * 
	 * @param text
	 * @param pattern
	 * @return
	 */
	public String matchPattern(String text, String pattern) {
		String ret = "";
		if (text != null && !"".equals(text)) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(text);
			if (m.find()) {
				ret = m.group().trim();
			}
		}
		return ret;
	}

	/**
	     */
	public boolean checkTicketResource(TrainTicketInfo trainTicketInfo) {
		boolean flag = false;
		if (NumberUtils.isNumber(trainTicketInfo.getBusinessClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSpecialClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getFirstClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSecondClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSeniorSoftSleepClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSoftSleepClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getHardSleepClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSoftSeatClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getHardSeatClass())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getStanding())) {
			flag = true;
		}
		if (NumberUtils.isNumber(trainTicketInfo.getOthers())) {
			flag = true;
		}
		return flag;
	}

	/**
	 * @param trainTicketInfo
	 */
	public Train createTrainInfo(TrainTicketInfo trainTicketInfo) {				
		ApiProxy.setEnvironmentForCurrentThread(environment);
		Train train = null;		
		List list = trainTicketManagerService.findTrain(trainTicketInfo.getTrainNo(), trainTicketInfo.getDepartureDate() );
		if (list == null || list.size() == 0) {
			logger.info("Create a train   - " + trainTicketInfo.getTrainNo() + " DepartureDate - " + trainTicketInfo.getDepartureDate() );
			train = new Train();
			train.setTrainNo(trainTicketInfo.getTrainNo());
			train.setFromStation(trainTicketInfo.getFromStation());
			train.setToStation(trainTicketInfo.getToStation());
			train.setDepartureTime(trainTicketInfo.getDepartureTime());
			train.setArrvialTime(trainTicketInfo.getArrvialTime());
			train.setDuring(trainTicketInfo.getDuring());
			train.setDepartureDate(trainTicketInfo.getDepartureDate());
			train.setInsertTime(new Date());			
			trainTicketManagerService.createTrain(train);			
			logger.info("Create a train completed  - " + trainTicketInfo.getTrainNo() + " DepartureDate - " + trainTicketInfo.getDepartureDate() );
		} else {
			train = (Train) list.get(0);
			logger.info("find a train record - " + train.getTrainNo() + " DepartureDate - " + train.getDepartureDate() );
		}
		return train;
	}
	
	public Environment environment;
		
	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	/**
	 * 
	 * @param train
	 * @param trainTicketInfo
	 */
	public List<Ticket> createTicketInfo(Train train, TrainTicketInfo trainTicketInfo) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket = null;
		if (NumberUtils.isNumber(trainTicketInfo.getBusinessClass())) {
			ticket = new Ticket();	
			ticket.setGrade("BusinessClass");
			ticket.setCount(trainTicketInfo.getBusinessClass());			
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSpecialClass())) {
			ticket = new Ticket();			
			ticket.setGrade("SpecialClass");
			ticket.setCount(trainTicketInfo.getSpecialClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getFirstClass())) {
			ticket = new Ticket();
			ticket.setTrain(train);					
			ticket.setGrade("FirstClass");
			ticket.setCount(trainTicketInfo.getFirstClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSecondClass())) {
			ticket = new Ticket();		
			ticket.setGrade("SecondClass");
			ticket.setCount(trainTicketInfo.getSecondClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSeniorSoftSleepClass())) {
			ticket = new Ticket();			
			ticket.setGrade("SeniorSoftSleepClass");
			ticket.setCount(trainTicketInfo.getSeniorSoftSleepClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSoftSleepClass())) {
			ticket = new Ticket();			
			ticket.setGrade("SoftSleepClass");
			ticket.setCount(trainTicketInfo.getSoftSleepClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getHardSleepClass())) {
			ticket = new Ticket();		
			ticket.setGrade("HardSleepClass");
			ticket.setCount(trainTicketInfo.getHardSleepClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSoftSeatClass())) {
			ticket = new Ticket();	
			ticket.setGrade("SoftSeatClass");
			ticket.setCount(trainTicketInfo.getSoftSeatClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getHardSeatClass())) {
			ticket = new Ticket();	
			ticket.setGrade("HardSeatClass");
			ticket.setCount(trainTicketInfo.getHardSeatClass());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getStanding())) {
			ticket = new Ticket();	
			ticket.setGrade("Standing");
			ticket.setCount(trainTicketInfo.getStanding());
			tickets.add(saveTicket(ticket, train));
		}
		if (NumberUtils.isNumber(trainTicketInfo.getOthers())) {
			ticket = new Ticket();
			ticket.setGrade("Others");
			ticket.setCount(trainTicketInfo.getOthers());
			tickets.add(saveTicket(ticket, train));
		}
		return tickets;
	}
	
	/**
	 * 
	 * @param ticket
	 * @param train
	 * @param trainTicketInfo
	 */
	public Ticket saveTicket(Ticket ticket, Train train){		
		ticket.setTrain(train);	
		ticket.setInsertTime(new Date());				
		ticket.setTrainNo(train.getTrainNo());
		ticket.setDepartureDate(train.getDepartureDate());
		return ticket;
	}

	/**
	 * @throws JSONException
	 * 
	 */
	public void doCrawl() throws JSONException {
		String info = crawlTrainTicketInfo(getUrl(), getProxy());
		logger.info("crawlTrainTicketInfo: " + info);
		if (!info.equals("")) {
			JSONObject jsonObject = new JSONObject(info);
			String data = jsonObject.get("datas").toString();
			List<TrainTicketInfo> trainTicketInfos = anaylseTrainTicketInfo(data);
			List<Ticket> ticketList = new ArrayList<Ticket>();
			Train train = null;
			if (trainTicketInfos != null) {
				logger.info("save TrainTicketInfo, TrainTicketInfo size: " + trainTicketInfos.size());
				for (TrainTicketInfo trainTicketInfo : trainTicketInfos) {
					if (checkTicketResource(trainTicketInfo)) {
						trainTicketInfo.setDepartureDate(getDepartureDate());
						train = createTrainInfo(trainTicketInfo);
						ticketList.addAll(createTicketInfo(train, trainTicketInfo));						
					}
				}
				trainTicketManagerService.batchInsert(ticketList);
			}
		}
	}

	/**
	     * 
	     */
	public void run() {		
		ApiProxy.setEnvironmentForCurrentThread(environment);
		trainTicketManagerService = getTrainTicketManagerService();
		try {			
			doCrawl();			
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("出现异常 - " + e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param date
	 * @param client
	 */
	public void initParameters(String url , String date, DefaultHttpClient client, Proxy proxy){		
		setUrl(initUrl(url, date));
		setDepartureDate(date);
		setHttpClient(client);
		setProxy(proxy);
	}
	
}

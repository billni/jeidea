package com.antsirs.train12306.action;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.model.Train;
import com.antsirs.train12306.model.TrainTicketInfo;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Crawl12306Task implements Runnable {
	private Log logger = LogFactory.getLog(Crawl12306Task.class);

	private String url;
	private DefaultHttpClient httpClient;
	private TrainTicketManagerService trainTicketManagerService;

	public DefaultHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public TrainTicketManagerService getTrainTicketManagerService() {
		return trainTicketManagerService;
	}

	public void setTrainTicketManagerService(
			TrainTicketManagerService trainTicketManagerService) {
		this.trainTicketManagerService = trainTicketManagerService;
	}

	public Crawl12306Task() {
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
			logger.info("Show exception when access this url.");
			e.printStackTrace();
		} finally {
			logger.info("HttpClient连接关闭.");
			httpClient.getConnectionManager().shutdown();
		}
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
					if (s != null && s.length > 0)
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
		Train train = null;
		List list = getTrainTicketManagerService().findTrain(trainTicketInfo.getTrainNo(), "2013-6-2");
		if (list == null) {
			train = new Train();
			train.setTrainNo(trainTicketInfo.getTrainNo());
			train.setFromStation(trainTicketInfo.getFromStation());
			train.setToStation(trainTicketInfo.getToStation());
			train.setDepartureTime(trainTicketInfo.getDepartureTime());
			train.setArrvialTime(trainTicketInfo.getArrvialTime());
			train.setDuring(trainTicketInfo.getDuring());
			train.setInsertTime(new Date());
			getTrainTicketManagerService().createTrain(train);
		} else {
			train = (Train) list.get(0);
		}
		return train;
	}

	/**
	 * 
	 * @param train
	 * @param trainTicketInfo
	 */
	public void createTicketInfo(Train train, TrainTicketInfo trainTicketInfo) {
		Ticket ticket = null;
		if (NumberUtils.isNumber(trainTicketInfo.getBusinessClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("BusinessClass");
			ticket.setCount(trainTicketInfo.getBusinessClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSpecialClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("SpecialClass");
			ticket.setCount(trainTicketInfo.getSpecialClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getFirstClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("FirstClass");
			ticket.setCount(trainTicketInfo.getFirstClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSecondClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("SecondClass");
			ticket.setCount(trainTicketInfo.getSecondClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSeniorSoftSleepClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("SeniorSoftSleepClass");
			ticket.setCount(trainTicketInfo.getSeniorSoftSleepClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSoftSleepClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("SoftSleepClass");
			ticket.setCount(trainTicketInfo.getSoftSleepClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getHardSleepClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("HardSleepClass");
			ticket.setCount(trainTicketInfo.getHardSleepClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getSoftSeatClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("SoftSeatClass");
			ticket.setCount(trainTicketInfo.getSoftSeatClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getHardSeatClass())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("HardSeatClass");
			ticket.setCount(trainTicketInfo.getHardSeatClass());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getStanding())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("Standing");
			ticket.setCount(trainTicketInfo.getStanding());
			getTrainTicketManagerService().createTicket(ticket);
		}
		if (NumberUtils.isNumber(trainTicketInfo.getOthers())) {
			ticket = new Ticket();
			ticket.setTrainId(train.getTrainId().toString());
			ticket.setInsertTime(new Date());
			ticket.setGrade("Others");
			ticket.setCount(trainTicketInfo.getOthers());
			getTrainTicketManagerService().createTicket(ticket);
		}
	}

	/**
	 * @throws JSONException
	 * 
	 */
	public void doCrawl() throws JSONException {
		String info = getTrainTicketInfoByUrl(getUrl());
		logger.info("getTrainTicketInfoByUrl: " + info);
		JSONObject jsonObject = new JSONObject(info);
		String data = jsonObject.get("datas").toString();
		List<TrainTicketInfo> list = anaylseTrainTicketInfo(data);
		Train train = null;
		if (list != null) {
			for (TrainTicketInfo trainTicketInfo : list) {
				if (checkTicketResource(trainTicketInfo)) {
					train = createTrainInfo(trainTicketInfo);
					createTicketInfo(train, trainTicketInfo);
				}
			}			
		}		
	}

	/**
	     * 
	     */
	public void run() {
		try {
			doCrawl();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("出现异常" + e.getMessage());
		}
	}
}

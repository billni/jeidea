package com.antsirs.train12306.action;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.tools.ant.util.DateUtils;
import com.antsirs.train12306.job.GetThread;
import com.antsirs.train12306.model.Ticket;
import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class Crawl12306Action extends AbstrtactCrawl12306Action {
	private Log logger = LogFactory.getLog(Crawl12306Action.class);
	private static final String PROXY_HOST= "10.18.8.60";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "Ny111111";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";	
	private static String Url = "http://dynamic.12306.cn/otsquery/query/queryRemanentTicketAction.do";
	
    public DefaultHttpClient getHttpClient(DefaultHttpClient httpClient){
        NTCredentials credentials = new NTCredentials(PROXY_USERNAME ,PROXY_PASSWORD , PROXY_WORKSTATION, PROXY_DOMAIN);	        
        httpClient.getCredentialsProvider().setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT), credentials);	      
        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return httpClient;
    }	
	
    /**
     * Generate Url 
     * @return
     */
    public URI initUrl(){
    	URIBuilder builder;
    	URI ret = null;
		try {
			builder = new URIBuilder(Url);
			ret =  builder.addParameter("method", "queryLeftTicket")									
								.addParameter("orderRequest.train_date", DateUtils.format(new Date(), "yyyy-MM-dd"))
								.addParameter("orderRequest.from_station_telecode","BJP")
								.addParameter("orderRequest.to_station_telecode","LZZ")
								.addParameter("orderRequest.train_no","")
								.addParameter("trainPassType","QB")
	                                 .addParameter("trainClass","QB#D#Z#T#K#QT#")
	                                 .addParameter("includeStudent","00")
	                                 .addParameter("seatTypeAndNum","")
	                                 .addParameter("orderRequest.start_time_str","00:00--24:00").build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return ret;
    }
    
    /**
     * Generate Url 
     * @return
     */
    public List<String> initUrl(List<String> dates){
    	URIBuilder builder;
    	List<String> urls = null;
    	URI ret;
    	for (String date : dates) {
    		try {
    			builder = new URIBuilder(Url);
    			ret =  builder.addParameter("method", "queryLeftTicket")									
    								.addParameter("orderRequest.train_date", date)
    								.addParameter("orderRequest.from_station_telecode","BJP")
    								.addParameter("orderRequest.to_station_telecode","LZZ")
    								.addParameter("orderRequest.train_no","")
    								.addParameter("trainPassType","QB")
    	                                 .addParameter("trainClass","QB#D#Z#T#K#QT#")
    	                                 .addParameter("includeStudent","00")
    	                                 .addParameter("seatTypeAndNum","")
    	                                 .addParameter("orderRequest.start_time_str","00:00--24:00").build();
    			urls.add(ret.toString());
    		} catch (URISyntaxException e) {
    			e.printStackTrace();
    		}
		}		
		return urls;
    }
    
    /**
     * 获得未来20天的日期
     * @return
     */
    public List<String> getFuture10Days() {
    	List<String> list = new ArrayList<String>();
    	Date date = new Date();
    	Calendar calendar = new GregorianCalendar(Locale.CHINESE);
    	calendar.setTime(date);    	
    	for (int i = 0; i < 20; i++) {	
    		calendar.add(calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
        	date = calendar.getTime(); //这个时间
        	list.add(DateUtils.format(date, "yyyy-MM-dd"));
		}    	
    	return list;
    }
    
	/** 
	     * 根据URL获得html信息 
	     * @param url 
	     * @return 
	     */  
	    public String getHtmlByUrl(String url){
	        StringWriter sw = new StringWriter();	   
	        DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient());      
	        HttpGet httpGet = new HttpGet(url);	        
	        try {	        	
	        	logger.info("HttpClient连接开启.");
	            HttpResponse response = httpClient.execute(httpGet);//得到responce对象  
	            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
	            if (resStatu == HttpStatus.SC_OK) {//200正常
	                //获得相应实体  	            	
	                HttpEntity entity = response.getEntity();
	                if (entity!=null) {	             
	                	InputStreamReader insr = new InputStreamReader(entity.getContent(), "UTF-8" /*ContentType.getOrDefault(entity).getCharset()*/);
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
	     * @param data
	     */
	    public List generateRecords(String data){
	    	List<Ticket> tickets = null;
            Ticket ticket = null;            
	        if (data!= null && !"".equals(data)) {	 
	        	tickets = new ArrayList<Ticket>();
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
	        			sf = sf.replace("<img src='/otsquery/images/tips/first.gif'>", "");
	        			sf = sf.replace("<font color='darkgray'>", "");
	        			sf = sf.replace("</font>", "");	        				        				        			
	        				        			
	        			String s[] = sf.split(",") ;	        			
	        			if (s != null && s.length >0)
	        				ticket =  new Ticket();
							ticket.setTrainNo(s[0]);
							ticket.setFromStation(s[1]);
							ticket.setDepartureDate(s[2]);
							ticket.setToStation(s[3]);
							ticket.setArrvialDate(s[4]);
							ticket.setDuring(s[5]);
							ticket.setBusinessClass(s[6]);
							ticket.setSpecialClass(s[7]);
							ticket.setFirstClass(s[8]);
							ticket.setSecondClass(s[9]);
							ticket.setSeniorSoftSleepClass(s[10]);
							ticket.setSoftSleepClass(s[11]);
							ticket.setHardSleepClass(s[12]);
							ticket.setSoftSeatClass(s[13]);
							ticket.setHardSeatClass(s[14]);
							ticket.setStanding(s[15]);
							ticket.setOthers(s[16]);
							ticket.setInsertTime(new Date());
							tickets.add(ticket);
						}	
	        	}
	        }
	        return tickets;
	    }
	    	    
	    /**
	     * Match special string
	     * @param text
	     * @param pattern
	     * @return
	     */
	    public String matchPattern(String text, String pattern){
	    	String ret = "";
	        if (text!= null && !"".equals(text)) {	        		             	            
	            Pattern p = Pattern.compile(pattern);
	            Matcher m = p.matcher(text);
	            if (m.find()){
		           ret =  m.group().trim();
	            }
	        } 
	        return ret;
	    }
	    
	    
	    public void pooledGetHtmlByUrl() throws Exception{
	    	SchemeRegistry schemeRegistry = new SchemeRegistry();
	    	schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
	    	ClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
//	    	DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient(cm));
	    	DefaultHttpClient httpClient = new DefaultHttpClient(cm);
	    	
	    	// URIs to perform GETs on
	    	String[] urisToGet = {
	    	   
	    	};

	    	// create a thread for each URI
	    	GetThread[] threads = new GetThread[urisToGet.length];
	    	for (int i = 0; i < threads.length; i++) {
	    	    HttpGet httpGet = new HttpGet(urisToGet[i]);
	    	    threads[i] = new GetThread(httpClient, httpGet);
	    	}

	    	// start the threads
	    	for (int j = 0; j < threads.length; j++) {
	    	    threads[j].start();
	    	}

	    	// join the threads
	    	for (int j = 0; j < threads.length; j++) {
	    	    threads[j].join();
	    	}
	    }
	    

	    
	    /**
	     * 调用crawl
	     */
	    public String execute() throws Exception {
	    	logger.info("initUrl: " + initUrl().toString());
	    	String html = getHtmlByUrl(initUrl().toString());
	    	logger.info("getHtmlByUrl: " + html);
	    	JSONObject jsonObject = new JSONObject(html);	    	
	    	String result = jsonObject.get("datas").toString();	    	
	    	List<Ticket> list = generateRecords(result);
	    	if (list != null) {	    		
	    		for (Ticket ticket : list) {
	    			if (checkTicketResource(ticket)) {
	    				trainTicketManagerService.createTicket(ticket);
	    			}
				}
	    		logger.info("Crawl12306 finish." + list.size());
	    	}	   
	    	logger.info("Crawl12306 execute one time.");
	    	return NONE;
	    }
	    
	    public boolean checkTicketResource(Ticket ticket) {
	    	boolean flag = false;
				if (NumberUtils.isNumber(ticket.getBusinessClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getSpecialClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getFirstClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getSecondClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getSeniorSoftSleepClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getSoftSleepClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getHardSleepClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getSoftSeatClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getHardSeatClass())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getStanding())) {
					flag = true;
				}
				if (NumberUtils.isNumber(ticket.getOthers())) {
					flag = true;
				}
				return flag;
			}
	    	    	
	    
	    
	    /**
	     * 列出ticket
	     * @return
	     */
	    public String listTicket() {	    	
	    	tickets = trainTicketManagerService.listTicket();	    			    			    	
	    	return SUCCESS;
	    }
}


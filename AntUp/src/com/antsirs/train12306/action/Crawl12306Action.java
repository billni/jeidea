package com.antsirs.train12306.action;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
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
import org.datanucleus.util.Log4JLogger;

import com.antsirs.train12306.job.GetThread;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;
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
	     * 根据URL获得html信息 
	     * @param url 
	     * @return 
	     */  
	    public String getHtmlByUrl(String url){
	        StringWriter sw = new StringWriter();	   
	        DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient());      
	        HttpGet httpGet = new HttpGet(url);	        
	        try {	        	
	            HttpResponse response = httpClient.execute(httpGet);//得到responce对象  
	            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
	            if (resStatu == HttpStatus.SC_OK) {//200正常
	                //获得相应实体  	            	
	                HttpEntity entity = response.getEntity();
	                if (entity!=null) {	             
	                	InputStreamReader insr = new InputStreamReader(entity.getContent(), "utf-8" /*ContentType.getOrDefault(entity).getCharset()*/);
                        IOUtils.copy(insr, sw);
	                	insr.close();
	                }
	            } else {
		            System.out.println("Http Status Code:" + resStatu);
	            }
	        } catch (Exception e) {  
	        	 System.out.println("Show exception when access this ulr.");
	             e.printStackTrace();  
	        } finally {	        	        	
	        	logger.info("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
	        }  
	        return sw.toString();  
	    }
	    
	    /**
	     * Analyse Record
	     * @param data
	     */
	    public List analyseRecords(String data){
	    	List<Ticket> tickets = null;
            Ticket ticket = null;            
	        if (data!= null && !"".equals(data)) {	 
	        	tickets = new ArrayList<Ticket>();
	        	String str[] = data.split(",<span");
	        	String temp = "";
	        	for (String sf : str) {	        			
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
	    
	    public URI initUrl(){
	    	URIBuilder builder;
	    	URI ret = null;
			try {
				builder = new URIBuilder(Url);
				ret =  builder.addParameter("method", "queryLeftTicket")
//									.addParameter("orderRequest.train_date", DateUtils.format(new Date(), "yyyy-MM-dd"))
									.addParameter("orderRequest.train_date", "2013-05-27")
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
	     * Main entry
	     * @param args
	     * @throws Exception
	     */
	    public static void main(String[] args) throws Exception {
	    	Crawl12306Action job = new Crawl12306Action();
//	    	System.out.println(job.initUrl().toString());
	    	String html = job.getHtmlByUrl(job.initUrl().toString());
//	    	System.out.println(html);//	    	
	    	JSONObject jsonObject = new JSONObject(html);
	    	String result = jsonObject.get("datas").toString();
//	    	System.out.println(result);	    	
	    	job.analyseRecords(result);
	    	//0,<span id='id_24000000T50E' class='base_txtdiv' onmouseover=javascript:onStopHover('24000000T50E#BXP#LZZ') onmouseout='onStopOut()'>T5</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;15:45,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;16:41,24:56,--,--,--,--,--,8,5,--,<font color='darkgray'>无</font>,150,--,\n1,<span id='id_240000T18909' class='base_txtdiv' onmouseover=javascript:onStopHover('240000T18909#BXP#LZZ') onmouseout='onStopOut()'>T189</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:17,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;20:00,25:43,--,--,--,--,12,11,<font color='darkgray'>无</font>,--,9,10,--,\n2,<span id='id_240000K1570Q' class='base_txtdiv' onmouseover=javascript:onStopHover('240000K1570Q#BXP#LZZ') onmouseout='onStopOut()'>K157</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:26,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;01:18,30:52,--,--,--,--,--,11,<font color='darkgray'>无</font>,--,<font color='darkgray'>无</font>,193,--,
	    	String time = jsonObject.get("time").toString();
//	    	System.out.println(time);	    	
//	    	String html = job.getHtmlByUrl("http://baoxian.taobao.com/json/PurchaseList.do?page=1&itemId=17305541936&sellerId=1128953583&callback=mycallback&sold_total_num=0");
//	    	job.analyseTransactionRecordsHtml(html);
//	    	job.poolRequest();
//	    	job.transformToJsonObject();
		}
	    
	    /**
	     * 调用crawl
	     */
	    public String execute() throws Exception {
	    	String html = getHtmlByUrl(initUrl().toString());
	    	JSONObject jsonObject = new JSONObject(html);
	    	String result = jsonObject.get("datas").toString();	    	
	    	List<Ticket> list = analyseRecords(result);
	    	if (list != null) {
	    		for (Ticket ticket : list) {
	    			trainTicketManagerService.createTicket(ticket);
				}
	    		logger.info("Crawl12306 finish." + list.size());
	    	}	    	
	    	return NONE;
	    }
}


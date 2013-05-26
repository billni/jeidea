package com.antup.train12306.job;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
import org.apache.tools.ant.util.DateUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class Crawl12306  {
	private Log logger = LogFactory.getLog(Crawl12306.class);
	private static final String PROXY_HOST= "10.18.8.108";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "nY111111";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";
	private static Long PageCount=0l;
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
	        DefaultHttpClient httpClient = new DefaultHttpClient();      
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
	    
	    public void analyseRecordsHtml(String data){
	        if (data!= null && !"".equals(data)) {
	        	String str[] = data.split("<span");
	        	for (String string : str) {
	        		String str2[] = data.split(",");
	        		for (String s2 : str2) {
						System.out.println(s2);
					}
	        	} 
	        }
	    }
	    
	    public void analyseTransactionCountHtml(String html){
	        if (html!= null && !"".equals(html)) {	        	
	            Document doc = Jsoup.parse(html);  
	            Elements trs = doc.select("ul.tab-bar  li:not(.sel)");
	            Pattern p = Pattern.compile("m>(.*)");
	            Matcher m = p.matcher(trs.html());
	            if (m.find()){
		            PageCount = Long.parseLong(m.group().replace("m>", "").replace(" ", ""))/10;
//		            System.out.println(m.group().replace("m>", "").replace("�?, "") + "�?�? + PageCount + "�?);
	            }
	        } 
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
	    
	    public static void main(String[] args) throws Exception {
	    	Crawl12306 job = new Crawl12306();
//	    	System.out.println(job.initUrl().toString());
	    	String html = job.getHtmlByUrl(job.initUrl().toString());
//	    	System.out.println(html);
	    	JSONObject jsonObject = new JSONObject(html);
	    	String result = jsonObject.get("datas").toString();
//	    	System.out.println(result);
	    	job.analyseRecordsHtml(result);
	    	//0,<span id='id_24000000T50E' class='base_txtdiv' onmouseover=javascript:onStopHover('24000000T50E#BXP#LZZ') onmouseout='onStopOut()'>T5</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;15:45,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;16:41,24:56,--,--,--,--,--,8,5,--,<font color='darkgray'>无</font>,150,--,\n1,<span id='id_240000T18909' class='base_txtdiv' onmouseover=javascript:onStopHover('240000T18909#BXP#LZZ') onmouseout='onStopOut()'>T189</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:17,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;20:00,25:43,--,--,--,--,12,11,<font color='darkgray'>无</font>,--,9,10,--,\n2,<span id='id_240000K1570Q' class='base_txtdiv' onmouseover=javascript:onStopHover('240000K1570Q#BXP#LZZ') onmouseout='onStopOut()'>K157</span>,<img src='/otsquery/images/tips/first.gif'>&nbsp;&nbsp;&nbsp;&nbsp;北京西&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;18:26,&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;柳州&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>&nbsp;&nbsp;&nbsp;&nbsp;01:18,30:52,--,--,--,--,--,11,<font color='darkgray'>无</font>,--,<font color='darkgray'>无</font>,193,--,
	    	String time = jsonObject.get("time").toString();
//	    	System.out.println(time);
	    	job.analyseTransactionCountHtml(html);
//	    	String html = job.getHtmlByUrl("http://baoxian.taobao.com/json/PurchaseList.do?page=1&itemId=17305541936&sellerId=1128953583&callback=mycallback&sold_total_num=0");
//	    	job.analyseTransactionRecordsHtml(html);
//	    	job.poolRequest();
//	    	job.transformToJsonObject();
		}
}


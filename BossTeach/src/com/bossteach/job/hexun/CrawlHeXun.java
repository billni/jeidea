package com.bossteach.job.hexun;

import java.io.InputStreamReader;
import java.io.StringWriter;
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
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class CrawlHeXun {
	private Log logger = LogFactory.getLog(CrawlHeXun.class);
	private static final String PROXY_HOST= "10.18.8.108";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "nY111111";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";

	
	/** 
	     * 根据URL获得html信息 
	     * @param url 
	     * @return 
	     */  
	    public String getHtmlByUrl(String url){
	        StringWriter sw = new StringWriter();
	        
	        DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient());//创建httpClient对象                	       
	        //如果代理需要密码验证，这里设置用户名密码  	        
	        HttpGet httpGet = new HttpGet(url);	        
	        try {	        	
	            HttpResponse response = httpClient.execute(httpGet);//得到responce对象  
	            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
	            if (resStatu == HttpStatus.SC_OK) {//200正常
	                //获得相应实体  	            	
	                HttpEntity entity = response.getEntity();
	                if (entity!=null) {	             
	                	InputStreamReader insr = new InputStreamReader(entity.getContent(), ContentType.getOrDefault(entity).getCharset());
                        IOUtils.copy(insr, sw);
	                	insr.close();
	                }
	            } else {
		            System.out.println("Http Status Code:" + resStatu);
	            }
	        } catch (Exception e) {  
	        	 System.out.println("访问【"+url+"】出现异常!");
	             e.printStackTrace();  
	        } finally {	        	        	
	        	logger.info("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
	        }  
	        return sw.toString();  
	    }
	    
	    public void analyseHtml(){
	        String html = getHtmlByUrl("http://www.bjepb.gov.cn/air2008/AirForeCastAndReport.aspx");  
	        if (html!= null && !"".equals(html)) {	        	
	            Document doc = Jsoup.parse(html);  
	            Elements dlElement = doc.select("#DataGridDataDic");	            
	            for (Element ele: dlElement) { 
	            	System.out.println(ele.html());  
	            }  
	        } 
	    }
	    
	    public void poolRequest() throws Exception{
	    	SchemeRegistry schemeRegistry = new SchemeRegistry();
	    	schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
	    	ClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
	    	DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient(cm));

	    	// URIs to perform GETs on
	    	String[] urisToGet = {
	    	    "http://www.oschina.net/",
	    	    "http://www.csdn.com/",
	    	    "http://www.iteye.com/",
	    	    "http://www.bjjtgl.gov.cn/"
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
	    	    
	    
	    public void transformToJsonObject() throws Exception{
	        String html = getHtmlByUrl("http://www.bjepb.gov.cn/air2008/AirForeCastAndReport.aspx");  
	        if (html!= null && !"".equals(html)) {	
	        	System.out.println(html);	       
	        	//必须是json格式的string才能 转换为jsonobject
	        	//hexun的网页格式不是json，所以转换异常
		    	JSONObject resp = new JSONObject(html); 
	            if (resp.has("data")) {
	                JSONObject data = resp.getJSONObject("data");
	                if (data.has("ip")) {
	                }
	            }
	        }
	    }
	    
	    public DefaultHttpClient getHttpClient(DefaultHttpClient httpClient){
	        NTCredentials credentials = new NTCredentials(PROXY_USERNAME ,PROXY_PASSWORD , PROXY_WORKSTATION, PROXY_DOMAIN);	        
	        httpClient.getCredentialsProvider().setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT), credentials);	      
	        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
	        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	        return httpClient;
	    }
	    
	    public static void main(String[] args) throws Exception {
	    	CrawlHeXun job = new CrawlHeXun();
	    	job.analyseHtml();
//	    	job.poolRequest();
//	    	job.transformToJsonObject();
		}
}


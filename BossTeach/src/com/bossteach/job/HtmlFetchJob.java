package com.bossteach.job;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlFetchJob {
	private Log logger = LogFactory.getLog(HtmlFetchJob.class);
	private static final String PROXY_HOST= "10.18.8.108";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "111111nY";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";

	
	/** 
	     * 根据URL获得html信息 
	     * @param url 
	     * @return 
	     */  
	    public String getHtmlByUrl(String url){  
	        StringBuffer text = null;  
	        
	        DefaultHttpClient httpClient = new DefaultHttpClient();//创建httpClient对象        
	        NTCredentials credentials = new NTCredentials(PROXY_USERNAME ,PROXY_PASSWORD , PROXY_WORKSTATION, PROXY_DOMAIN);	        
	        httpClient.getCredentialsProvider().setCredentials(new AuthScope(PROXY_HOST, PROXY_PORT), credentials);	      
	        HttpHost proxy = new HttpHost(PROXY_HOST, PROXY_PORT);
	        httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);	        	       
	        //如果代理需要密码验证，这里设置用户名密码  	        
	        HttpGet httpGet = new HttpGet(url);
	        
	        try {	        	
	            HttpResponse response = httpClient.execute(httpGet);//得到responce对象  
	            int resStatu = response.getStatusLine().getStatusCode();//返回 
	            System.out.println("Http状态码: " + resStatu);
	            if (resStatu == HttpStatus.SC_OK) {//200正常
	                //获得相应实体  	            	
	                HttpEntity entity = response.getEntity();
	                if (entity!=null) {
		                InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent());
		                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		                text = new StringBuffer();
		                while (bufferedReader.readLine() != null) {
		                	text.append(bufferedReader.readLine());
		                }
		                bufferedReader.close();
		                inputStreamReader.close();	               
	                }  
	            }  
	        } catch (Exception e) {  
	        	 System.out.println("访问【"+url+"】出现异常!");
	             e.printStackTrace();  
	        } finally {	        	        	
	        	logger.info("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
	            
	        }  
	        return text.toString();  
	    }
	    
	    public void analyseHtml(){
	        String html = getHtmlByUrl("http://www.hexun.com");  
	        if (html!= null &&!"".equals(html)) { 
	        	System.out.println(html);
	            Document doc = Jsoup.parse(html);  
	            Elements linksElements = doc.select("div#caiJingGuanming>div.con_A_l>div.con_A_a>div.con_A_lm");  
	            //以上代码的意思是 找id为�?page”的div里面   id为�?content”的div里面   id为�?main”的div里面   class为�?left”的div里面   id为�?recommend”的div里面ul里面li里面a标签  
	            for (Element ele:linksElements) {  
	                String href = ele.attr("div");  
	                String title = ele.text();  
	                logger.debug(href+","+title);  
	            }  
	        } 
	    }
	    public static void main(String[] args) {
	    	HtmlFetchJob job = new HtmlFetchJob();
	    	job.analyseHtml();
		}
}


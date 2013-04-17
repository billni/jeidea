package com.bossteach.job.baidu;

import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.appengine.labs.repackaged.org.json.JSONObject;


public class CrawlBaiDu {
	private static final String PROXY_HOST= "10.18.8.108";
	private static final int PROXY_PORT = 8008;
	private static final String PROXY_USERNAME= "niyong";
	private static final String PROXY_PASSWORD= "nY111111";
	private static final String PROXY_WORKSTATION= "isa06";
	private static final String PROXY_DOMAIN= "ulic";
		
	public static final String SUBMIT_URL = "http://wenku.baidu.com/submit";
	public static String VERIFY_URL = "http://wenku.baidu.com/tongji/general.html";
	public static String DOC_SEQUENCE = "1362964968296";
	
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
	    public String getByUrl(String url){
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
	                	InputStreamReader insr = new InputStreamReader(entity.getContent(), "gb2312" /*ContentType.getOrDefault(entity).getCharset()*/);
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
	        	System.out.println("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
	        }  
	        return sw.toString();  
	    }
	    
	    public void analyseHtml(String str){	    	
	        String html = getByUrl(str);
	        if (html!= null && !"".equals(html)) { 	
	            Document doc = Jsoup.parse(html);  
	            Elements dlElement = doc.select("a#recom-btn");	            
	            for (Element ele: dlElement) { 
	            	System.out.println(ele.html());  
	            }		       
	        }
	    }
	    
	    public String getDocId(String url){	    	
	    	Matcher m = Pattern.compile("view/(.*?).html").matcher(url);
	    	if (m.find()) {
	    		return m.group().replaceAll("view/", "").replaceAll(".html", "");
	    	}
	    	return "";
	    }
	    
	    public String postByUrl(String docViewUrl, String submitUrl){
	    	DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient());//创建httpClient对象     
	    	StringWriter sw = new StringWriter();
	    	HttpPost httpPost = new HttpPost(submitUrl);
		    try {		    			    	
		        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		        formparams.add(new BasicNameValuePair("ct", "20009"));
		        formparams.add(new BasicNameValuePair("doc_id", getDocId(docViewUrl)));		        
		        formparams.add(new BasicNameValuePair("value_recommend", "1"));
		        formparams.add(new BasicNameValuePair("value_score", "5"));		        
		        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, "UTF-8");		        		        
		        httpPost.setEntity(formEntity);					    
		        
				HttpResponse response = httpClient.execute(httpPost);//得到responce对象
	            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
	            if (resStatu == HttpStatus.SC_OK) {//200正常
	                //获得相应实体  	            	
	                HttpEntity entity = response.getEntity();
	                if (entity!=null) {	             
	                	InputStreamReader insr = new InputStreamReader(entity.getContent(), "gb2312" /*ContentType.getOrDefault(entity).getCharset()*/);
                        IOUtils.copy(insr, sw);
	                	insr.close();
	                }
	            } else {
		            System.out.println("Http Status Code:" + resStatu);
	            }				
			} catch (Exception e1) { 
	        	 System.out.println("访问【"+submitUrl+"】出现异常!");
				e1.printStackTrace();
			} finally {	        	        	
				System.out.println("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
	        }  
	        return sw.toString();  
	    }
	    


	
	    	    
	    
	    public void transformToJsonObject() throws Exception{
	        String html = getByUrl("http://wenku.baidu.com/view/ca3a72ded15abe23482f4dc5.html");  
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
	    
	    public String verifyDoc(String url){
	    	DefaultHttpClient httpClient = getHttpClient(new DefaultHttpClient());//创建httpClient对象     
	    	StringWriter sw = new StringWriter();
	    	HttpPost httpPost = new HttpPost(url);
		    try {		    			    	
		        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		        formparams.add(new BasicNameValuePair("type", "docValuation"));
		        formparams.add(new BasicNameValuePair("t", "1362906937804"));		        
		        formparams.add(new BasicNameValuePair("isRecommond", "1"));		       	        
		        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formparams, "UTF-8");		        		        
		        httpPost.setEntity(formEntity);					    
		        
				HttpResponse response = httpClient.execute(httpPost);//得到responce对象
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
		            System.out.println("Http Status Code:" + resStatu);
	            }				
			} catch (Exception e1) { 
	        	 System.out.println("访问【"+url+"】出现异常!");
				e1.printStackTrace();
			} finally {	        	        	
				System.out.println("HttpClient连接关闭.");
	            httpClient.getConnectionManager().shutdown();	            
			}
		    return sw.toString();
	    }
	    
	    public String generateSequence(){
	    	StringBuffer se = new StringBuffer(DOC_SEQUENCE);	    	
	    	int tail = Integer.parseInt(se.subSequence(DOC_SEQUENCE.length()-3, DOC_SEQUENCE.length()).toString());	    	
	    	return "";
	    }
	    
	    public static void main(String[] args) throws Exception {
	    	CrawlBaiDu job = new CrawlBaiDu();	    		    		    	
	    	//扫描doc list
	    	//提交 验证url
//	    	job.analyseHtml(DOC_VIEW_URL);
	    	System.out.println(job.verifyDoc(VERIFY_URL));
	    	System.out.println(job.postByUrl("http://wenku.baidu.com/view/be8fb62d7375a417866f8fbc.html", SUBMIT_URL));
//	    	job.poolRequest();
//	    	job.transformToJsonObject();
		}
}


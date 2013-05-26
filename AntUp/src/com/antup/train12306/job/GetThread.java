package com.antup.train12306.job;

import java.io.InputStreamReader;
import java.io.StringWriter;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class GetThread extends Thread {

	private final HttpClient httpClient;
	private final HttpContext context;
	private final HttpGet httpGet;

	public GetThread(HttpClient httpClient, HttpGet httpGet) {
		this.httpClient = httpClient;
		this.context = new BasicHttpContext();
		this.httpGet = httpGet;
	}

	@Override
	public void run() {
        StringWriter sw = new StringWriter();
		try {
			HttpResponse response = this.httpClient.execute(this.httpGet, this.context);
            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
            if (resStatu == HttpStatus.SC_OK) {//200正常{
				HttpEntity entity = response.getEntity();
                if (entity!=null) {	                	
                	IOUtils.copy(new InputStreamReader(entity.getContent(), ContentType.getOrDefault(entity).getCharset()), sw); 				
                } else {
                	System.out.println("Http Status Code:" + response.getStatusLine().getStatusCode());            
                	// ensure the connection gets released to the manager
                }
            }
		} catch (Exception ex) {
			this.httpGet.abort();
			ex.printStackTrace();
		} finally {
            httpClient.getConnectionManager().shutdown();	            
        } 
	}

}

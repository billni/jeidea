package com.bossteach.job.hexun;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

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
		StringBuffer text = new StringBuffer();
		try {
			HttpResponse response = this.httpClient.execute(this.httpGet, this.context);
            int resStatu = response.getStatusLine().getStatusCode();//返回 	 
            if (resStatu == HttpStatus.SC_OK) {//200正常{
				HttpEntity entity = response.getEntity();
				if (entity != null) {
	                InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent(), ContentType.getOrDefault(entity).getCharset());
	                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);		                
	                while (bufferedReader.readLine() != null) {
	                	
	                	text.append(bufferedReader.readLine());
	                }
	                bufferedReader.close();
	                inputStreamReader.close();
	                EntityUtils.consume(entity);
				}				
            } else {
	            System.out.println("Http Status Code:" + response.getStatusLine().getStatusCode());            
			// ensure the connection gets released to the manager
            }
		} catch (Exception ex) {
			this.httpGet.abort();
			ex.printStackTrace();
		}
	}

}

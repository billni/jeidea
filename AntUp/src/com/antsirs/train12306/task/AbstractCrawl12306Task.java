package com.antsirs.train12306.task;

import java.net.Proxy;
import java.net.URL;
import org.apache.http.impl.client.DefaultHttpClient;
import com.antsirs.train12306.service.TrainTicketManagerService;
import java.util.logging.Logger;

public class AbstractCrawl12306Task {
	private static final Logger logger = Logger.getLogger(AbstractCrawl12306Task.class.getName());

	public URL url;
	public DefaultHttpClient httpClient;		

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

	public AbstractCrawl12306Task() {
	}

}

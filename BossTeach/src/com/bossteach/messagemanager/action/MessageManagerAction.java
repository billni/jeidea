package com.bossteach.messagemanager.action;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class MessageManagerAction extends AbstrtactMesssageManagerAction {

	private static final long serialVersionUID = 2229507497640458151L;
	private Log logger = LogFactory.getLog(MessageManagerAction.class);

	/**
	 * add messge by visitor
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addMessage() throws Exception {
		logger.info("8888888888888888888888888888");
		Entity message = new Entity("Message");
		message.setProperty("visitor", visitor.getName());
		message.setProperty("content", visitor.getMessage().getContent());
		message.setProperty("createDate", new Date());
		datastore.put(message);
		logger.info("8888888888888888888888888888");
		return SUCCESS;
	}

	public String listMessage() throws Exception {
		Filter createDateMinFilter = new FilterPredicate("createDate", FilterOperator.GREATER_THAN_OR_EQUAL, DateFormat.getInstance().parseObject("2013-1-1"));
		Filter createDateMaxFilter = new FilterPredicate("createDate",	FilterOperator.LESS_THAN_OR_EQUAL, DateFormat.getInstance().parseObject("2013-12-1")); // Use
		Filter createDateRangeFilter =   CompositeFilterOperator.and(createDateMinFilter, createDateMaxFilter);
		Query q = new Query("Message").setFilter(createDateRangeFilter); 
		messages = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());		
		return SUCCESS;
	}
}

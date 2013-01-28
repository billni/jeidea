package com.bossteach.messagemanager.action;

import com.bossteach.core.struts.actionsuppport.BaseActionSupport;
import com.google.appengine.api.datastore.Entity;

public class MessageManagerAction extends BaseActionSupport{

	private static final long serialVersionUID = 2229507497640458151L;

	/**
	 * add messge by visitor
	 * @return
	 * @throws Exception
	 */
	public String addMessage() throws Exception{
		Entity message = new Entity("Message");
		message.setProperty("visitorName", "niyong");
		message.setProperty("content", "hello world");
		datastore.put(message);
		return SUCCESS;
	}
}

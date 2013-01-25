package com.bossteach.messagemanager.action;

import com.bossteach.core.struts.actionsuppport.AbstractActionSupport;
import com.google.appengine.api.datastore.Entity;

public class MessageManagerAction extends AbstractActionSupport{

	private static final long serialVersionUID = 2229507497640458151L;

	/**
	 * 增加一个留言
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

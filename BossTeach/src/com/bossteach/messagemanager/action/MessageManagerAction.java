package com.bossteach.messagemanager.action;

import com.google.appengine.api.datastore.Entity;

public class MessageManagerAction extends AbstrtactMesssageManagerAction{

	private static final long serialVersionUID = 2229507497640458151L;

	/**
	 * add messge by visitor
	 * @return
	 * @throws Exception
	 */
	public String addMessage() throws Exception{		
		Entity message = new Entity("Message");
		message.setProperty("visitor", visitor.getName());
		message.setProperty("content", visitor.getMessage().getContent());
		datastore.put(message);
		return SUCCESS;
	}
}

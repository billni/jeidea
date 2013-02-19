package com.bossteach.messagemanager.action;

import java.util.List;
import com.bossteach.core.struts.actionsuppport.BaseActionSupport;
import com.bossteach.messagemanager.service.MessageManagerService;
import com.bossteach.model.Message;
import com.bossteach.model.Visitor;

public abstract class AbstrtactMesssageManagerAction extends BaseActionSupport{

	private static final long serialVersionUID = -1065187233304314936L;
	
	public MessageManagerService messageManagerService = null;
	
	public MessageManagerService getMessageManagerService() {
		return messageManagerService;
	}

	public void setMessageManagerService(MessageManagerService messageManagerService) {
		this.messageManagerService = messageManagerService;
	}

	public Visitor visitor;
	protected List<Message> messages;

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}

package com.bossteach.messagemanager.action;

import java.util.List;

import com.bossteach.core.struts.actionsuppport.BaseActionSupport;
import com.bossteach.model.Visitor;
import com.google.appengine.api.datastore.Entity;

public abstract class AbstrtactMesssageManagerAction extends BaseActionSupport{

	private static final long serialVersionUID = -1065187233304314936L;
	public Visitor visitor;
	protected List<Entity> messages;

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public List<Entity> getMessages() {
		return messages;
	}

	public void setMessages(List<Entity> messages) {
		this.messages = messages;
	}
	
}

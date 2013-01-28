package com.bossteach.messagemanager.action;

import com.bossteach.core.struts.actionsuppport.BaseActionSupport;
import com.bossteach.model.Visitor;

public abstract class AbstrtactMesssageManagerAction extends BaseActionSupport{

	private static final long serialVersionUID = -1065187233304314936L;
	protected Visitor visitor;

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	
}

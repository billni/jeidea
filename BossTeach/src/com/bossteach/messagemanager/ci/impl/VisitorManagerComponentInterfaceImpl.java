package com.bossteach.messagemanager.ci.impl;

import com.bossteach.messagemanager.ci.VisitorManagerComponentInterface;
import com.bossteach.model.Visitor;
import com.bossteach.visitormanager.service.VisitorManagerService;

public class VisitorManagerComponentInterfaceImpl implements  VisitorManagerComponentInterface{
	
	private VisitorManagerService visitorManagerService;

	public void setVisitorManagerService(VisitorManagerService visitorManagerService) {
		this.visitorManagerService = visitorManagerService;
	}

	public VisitorManagerService getVisitorManagerService() {
		return visitorManagerService;
	}
	
	/**
	 * add visitor
	 * @param visitor
	 */
	public void createVisitor(Visitor visitor){
		visitorManagerService.createVisitor(visitor);
	}
	

}

package com.bossteach.messagemanager.ci.impl;

import java.util.List;
import com.bossteach.messagemanager.ci.VisitorManagerCI;
import com.bossteach.model.Visitor;
import com.bossteach.visitormanager.service.VisitorManagerService;

public class VisitorManagerCIImpl implements  VisitorManagerCI{
	
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
	
	public List<Visitor> findVisitor(String mail) {		
		return visitorManagerService.findVisitor(mail);		
	}
	

}

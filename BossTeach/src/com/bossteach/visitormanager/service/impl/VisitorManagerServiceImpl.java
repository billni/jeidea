package com.bossteach.visitormanager.service.impl;

import com.bossteach.core.spring.daosupport.DaoTemplate;
import com.bossteach.model.Visitor;
import com.bossteach.visitormanager.service.VisitorManagerService;
import com.google.appengine.api.datastore.Key;

public class VisitorManagerServiceImpl extends DaoTemplate implements VisitorManagerService {
	
	public void createVisitor(Visitor visitor){
		getDaoTemplate().persist(visitor);
	}
		
	public void findVisitor(Key key){
		getDaoTemplate().find(Visitor.class, key);
	}	
	
	@Override
	public void disableVisitor(Visitor visitor) {
		getDaoTemplate().remove(visitor);	
	}	
}

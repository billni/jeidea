package com.bossteach.visitormanager.service.impl;

import java.util.List;

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
		
	public void disableVisitor(Visitor visitor) {
		getDaoTemplate().remove(visitor);	
	}

	public List<Visitor> findVisitor(String mail) {		
		return getDaoTemplate().getEntityManagerFactory().createEntityManager().
					createNamedQuery("findVisitorWithMail").setParameter("mail", mail).getResultList();				
	}	
}

package com.bossteach.core.struts.actionsuppport;

import javax.persistence.EntityManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class AbstractActionSupport extends BaseActionSupport{
		
	protected DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	protected EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}		
	
	
}

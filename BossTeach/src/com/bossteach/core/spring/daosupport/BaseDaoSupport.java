package com.bossteach.core.spring.daosupport;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class BaseDaoSupport {
			
	private static EntityManagerFactory entityManagerFactory;
	 
	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		BaseDaoSupport.entityManagerFactory = entityManagerFactory;
	}
		
	public EntityManager getManager(){
		return entityManagerFactory.createEntityManager();
	}		
}

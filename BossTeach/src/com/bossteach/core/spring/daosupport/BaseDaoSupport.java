package com.bossteach.core.spring.daosupport;

import javax.persistence.EntityManagerFactory;
import org.springframework.orm.jpa.JpaTemplate;

public class BaseDaoSupport extends JpaTemplate{
		
	protected static EntityManagerFactory entityManagerFactory;
				
	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		BaseDaoSupport.entityManagerFactory = entityManagerFactory;
	}

	public BaseDaoSupport getBaseDaoSupport() {
		return (BaseDaoSupport)new JpaTemplate(BaseDaoSupport.entityManagerFactory);
	}			
}

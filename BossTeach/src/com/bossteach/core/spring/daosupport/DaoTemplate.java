package com.bossteach.core.spring.daosupport;

import javax.persistence.EntityManagerFactory;
import org.springframework.orm.jpa.JpaTemplate;

public class DaoTemplate extends JpaTemplate{
		
	protected static EntityManagerFactory entityManagerFactory;
				
	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		DaoTemplate.entityManagerFactory = entityManagerFactory;
	}

	public JpaTemplate getDaoTemplate() {
		return new JpaTemplate(DaoTemplate.entityManagerFactory);
	}			
}

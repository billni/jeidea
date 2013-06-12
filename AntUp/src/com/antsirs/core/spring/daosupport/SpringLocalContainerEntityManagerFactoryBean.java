package com.antsirs.core.spring.daosupport;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

public class SpringLocalContainerEntityManagerFactoryBean extends
		LocalContainerEntityManagerFactoryBean {
	public void setPersistenceXmlLocation(String persistenceXmlLocation) {
		String file = SpringLocalContainerEntityManagerFactoryBean.class.getClassLoader().getResource(persistenceXmlLocation).getFile();
		super.setPersistenceXmlLocation(file);
	}
}

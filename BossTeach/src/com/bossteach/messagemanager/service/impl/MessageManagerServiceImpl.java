package com.bossteach.messagemanager.service.impl;

import com.bossteach.core.spring.daosupport.BaseDaoSupport;
import com.bossteach.messagemanager.service.MessageManagerService;

public class MessageManagerServiceImpl extends BaseDaoSupport implements MessageManagerService {
	
	public void save(Object object){
		getManager().persist(object);
	}
	
	public void update(Object object){
		getManager().refresh(object);
	}
	
	public void find(Class<?> entity, Object key){
		getManager().find(entity, key);
	}
	
	public void flush(){
		getManager().flush();
	}
	
	public void delete(Object object){
		getManager().remove(object);
	}
	
	public void merge(Object object){
		getManager().merge(object);
	}
}

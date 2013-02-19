package com.bossteach.messagemanager.service;

public interface MessageManagerService {
	public void save(Object object);
	
	public void update(Object object);
	
	public void find(Class<?> entity, Object key);
	
	public void flush();
	
	public void delete(Object object);
	
	public void merge(Object object);
}

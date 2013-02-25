package com.bossteach.visitormanager.service;

import com.bossteach.model.Visitor;
import com.google.appengine.api.datastore.Key;

public interface VisitorManagerService {

	/**
	 * insert and update visitor to db
	 * @param object
	 */
	public void createVisitor(Visitor visitor);
		
	/**
	 * find an visitor from db
	 * @param entity
	 * @param key
	 */
	public void findVisitor(Key key);
		
	/**
	 * remove visitor from db
	 * @param object
	 */
	public void disableVisitor(Visitor visitor);
}

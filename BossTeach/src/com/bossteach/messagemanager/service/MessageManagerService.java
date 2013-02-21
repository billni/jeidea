package com.bossteach.messagemanager.service;

import com.bossteach.model.Message;
import com.google.appengine.api.datastore.Key;

public interface MessageManagerService {
	/**
	 * insert and update message to db
	 * @param object
	 */
	public void createMessage(Message message);
		
	/**
	 * find an message from db
	 * @param entity
	 * @param key
	 */
	public void findMessage(Key key);
		
	/**
	 * remove message from db
	 * @param object
	 */
	public void deleteMessage(Message object);
}

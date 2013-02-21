package com.bossteach.messagemanager.service.impl;

import com.bossteach.core.spring.daosupport.BaseDaoSupport;
import com.bossteach.messagemanager.service.MessageManagerService;
import com.bossteach.model.Message;
import com.google.appengine.api.datastore.Key;

public class MessageManagerServiceImpl extends BaseDaoSupport implements MessageManagerService {
	
	public void createMessage(Message message){
		getBaseDaoSupport().persist(message);
	}
		
	public void findMessage(Key key){
		getBaseDaoSupport().find(Message.class, key);
	}	
	
	public void deleteMessage(Message message){
		deleteMessage(message);
	}
}

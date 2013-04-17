package com.bossteach.messagemanager.service.impl;

import java.util.List;
import com.bossteach.core.spring.daosupport.DaoTemplate;
import com.bossteach.core.spring.daosupport.Pagination;
import com.bossteach.messagemanager.service.MessageManagerService;
import com.bossteach.model.Message;
import com.google.appengine.api.datastore.Key;

public class MessageManagerServiceImpl extends DaoTemplate implements MessageManagerService {
	
	public void createMessage(Message message){
		getDaoTemplate().persist(message);
	}
		
	public void findMessage(Key key){
		getDaoTemplate().find(Message.class, key);
	}	
	
	public void deleteMessage(Message message){
		getDaoTemplate().remove(message);
	}

	/**
	 * list messages
	 */
	public List listMessage() {				
		return getDaoTemplate().getEntityManagerFactory().createEntityManager().
				createNamedQuery("listMessage").getResultList();				
	}
	
	public List<Message> listMessageWithPagination(Pagination pagination){		
		pagination.setQueryString(null);
		pagination.setParamValues(null);
		return findWithPagination(Message.class, pagination);
	}
}

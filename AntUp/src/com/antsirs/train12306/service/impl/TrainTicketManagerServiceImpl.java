package com.antsirs.train12306.service.impl;

import java.util.List;
import com.antsirs.core.spring.daosupport.DaoTemplate;
import com.antsirs.core.spring.daosupport.Pagination;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.api.datastore.Key;

public class TrainTicketManagerServiceImpl extends DaoTemplate implements TrainTicketManagerService {
	
	public void createTicket(Ticket ticket){
		getDaoTemplate().persist(ticket);
	}
		
	public void findTicket(Key key){
		getDaoTemplate().find(Ticket.class, key);
	}	
	
	public void deleteTicket(Ticket ticket){
		getDaoTemplate().remove(ticket);
	}

	/**
	 * list tickets
	 */
	public List listTicket() {				
		return getDaoTemplate().getEntityManagerFactory().createEntityManager().
				createNamedQuery("listTicket").getResultList();				
	}
	
	public List<Ticket> listTicketWithPagination(Pagination pagination){		
		pagination.setQueryString(null);
		pagination.setParamValues(null);
		return findWithPagination(Ticket.class, pagination);
	}
}

package com.antsirs.train12306.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.antsirs.core.struts.actionsuppport.BaseActionSupport;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;

public abstract class AbstrtactCrawl12306Action extends BaseActionSupport{
	
	public List<Ticket> tickets;

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	
}

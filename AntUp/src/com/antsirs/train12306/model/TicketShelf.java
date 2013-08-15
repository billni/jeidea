package com.antsirs.train12306.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.datanucleus.jpa.annotations.Extension;

@Entity
@NamedQueries(@NamedQuery(name = "listTicketShelf", query = "SELECT m FROM TicketShelf m"))
public class TicketShelf {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String ticketShelfId;
	
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String ticketCount;

	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@ManyToOne(cascade = {CascadeType.REFRESH }, fetch=FetchType.LAZY)   
	private TicketStock container;
	
	public TicketShelf() {
	}

	public String getTicketShelfId() {
		return ticketShelfId;
	}

	public void setTicketShelfId(String ticketShelfId) {
		this.ticketShelfId = ticketShelfId;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}
	
	



}

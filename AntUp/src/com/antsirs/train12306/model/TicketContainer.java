package com.antsirs.train12306.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.datanucleus.jpa.annotations.Extension;

@Entity
@NamedQueries(@NamedQuery(name = "listTicketContainer", query = "SELECT m FROM TicketContainer m"))
public class TicketContainer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String ticketContainerId;
	
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String ticketCount;

	public TicketContainer() {
	}

	public String getTicketContainerId() {
		return ticketContainerId;
	}

	public void setTicketContainerId(String ticketContainerId) {
		this.ticketContainerId = ticketContainerId;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}
	
	



}

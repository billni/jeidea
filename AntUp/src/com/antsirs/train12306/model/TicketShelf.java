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

import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries(@NamedQuery(name = "listTicketShelf", query = "SELECT m FROM TicketShelf m"))
public class TicketShelf {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key ticketShelfId;
	
	private String ticketShelfLabel;
	
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String ticketCount;

	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	@ManyToOne(cascade = {CascadeType.MERGE}, fetch=FetchType.LAZY)   
	private TicketStock ticketStock;
	
	public TicketShelf() {
	}

	public Key getTicketShelfId() {
		return ticketShelfId;
	}

	public void setTicketShelfId(Key ticketShelfId) {
		this.ticketShelfId = ticketShelfId;
	}

	public String getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}

	public TicketStock getTicketStock() {
		return ticketStock;
	}

	public void setTicketStock(TicketStock ticketStock) {
		this.ticketStock = ticketStock;
	}

	public String getTicketShelfLabel() {
		return ticketShelfLabel;
	}

	public void setTicketShelfLabel(String ticketShelfLabel) {
		this.ticketShelfLabel = ticketShelfLabel;
	}
	
	



}

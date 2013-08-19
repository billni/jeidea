package com.antsirs.train12306.model;

import java.io.Serializable;

import javax.jdo.annotations.Persistent;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.datanucleus.jpa.annotations.Extension;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

@Entity
@NamedQueries(@NamedQuery(name = "listTicketShelf", query = "SELECT m FROM TicketShelf m"))
public class TicketShelf implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key ticketShelfId;
	
	private String ticketShelfLabel;
	
	@Persistent(serialized="true", defaultFetchGroup="true") 
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private Text ticketCount;

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

	public Text getTicketCount() {
		return ticketCount;
	}

	public void setTicketCount(String ticketCount) {
		this.ticketCount = new Text(ticketCount); ;
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

package com.antsirs.train12306.model;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.datanucleus.jpa.annotations.Extension;

@Entity
@NamedQueries(@NamedQuery(name = "listTicketContainer", query = "SELECT m FROM TicketContainer m"))
public class TicketContainer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String ticketContainerId;
	
	@Basic
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private List<String> countList;

	public TicketContainer() {
	}

	public String getTicketContainerId() {
		return ticketContainerId;
	}

	public void setTicketContainerId(String ticketContainerId) {
		this.ticketContainerId = ticketContainerId;
	}

	public List<String> getCountList() {
		return countList;
	}

	public void setCountList(List<String> countList) {
		this.countList = countList;
	}

}

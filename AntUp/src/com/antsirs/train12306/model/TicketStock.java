package com.antsirs.train12306.model;

import java.util.HashSet;
import java.util.Set;
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
@NamedQueries({
		@NamedQuery(name = "listTicketStock", query = "SELECT m FROM TicketStock m"),
		@NamedQuery(name = "findTicketStockById", query = "SELECT m FROM TicketStock m WHERE m.departureDate = :departureDate") })
public class TicketStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String departureDate;

	@OneToMany(targetEntity=TicketShelf.class, mappedBy="ticketStock", fetch=FetchType.LAZY)
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private Set<TicketShelf> ticketShelfSet = new HashSet<TicketShelf>();

	public TicketStock() {
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public Set<TicketShelf> getTicketShelfSet() {
		return ticketShelfSet;
	}

	public void setTicketShelf(Set<TicketShelf> ticketShelfSet) {
		this.ticketShelfSet = ticketShelfSet;
	}

	
	

}

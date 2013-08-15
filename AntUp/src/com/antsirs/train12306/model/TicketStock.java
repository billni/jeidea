package com.antsirs.train12306.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.google.appengine.api.datastore.Key;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.datanucleus.jpa.annotations.Extension;

@Entity
@NamedQueries({
		@NamedQuery(name = "listTrain", query = "SELECT m FROM Train m"),
		@NamedQuery(name = "findTrainById", query = "SELECT m FROM Train m WHERE m.trainId = :trainId") })
public class TicketStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String departureDate;

	@OneToMany(targetEntity=TicketShelf.class, mappedBy="train", fetch=FetchType.LAZY)
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private Set<Ticket> tickets = new HashSet<Ticket>();


	public TicketStock() {
	}

	
	

}

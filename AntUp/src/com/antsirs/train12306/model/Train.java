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
public class Train {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key trainId;
	private String trainNo;
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String fromStation;
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String toStation;
	private String departureDate;
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String departureTime;
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String arrvialTime;
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private String during;
	private Date insertTime;
	@OneToMany(targetEntity=Ticket.class, mappedBy="train", fetch=FetchType.LAZY)
	@Extension(vendorName="datanucleus", key="gae.unindexed", value="true")
	private Set<Ticket> tickets = new HashSet<Ticket>();


	public Train() {
	}

	public Key getTrainId() {
		return trainId;
	}

	public void setTrainId(Key trainId) {
		this.trainId = trainId;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrvialTime() {
		return arrvialTime;
	}

	public void setArrvialTime(String arrvialTime) {
		this.arrvialTime = arrvialTime;
	}

	public String getDuring() {
		return during;
	}

	public void setDuring(String during) {
		this.during = during;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	@Temporal(TemporalType.DATE)
	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	

}

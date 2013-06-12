package com.antsirs.train12306.model;

import java.util.Date;

import com.google.appengine.api.datastore.Key;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
		@NamedQuery(name = "listTicket", query = "SELECT m FROM Ticket m"),
		@NamedQuery(name = "findTicketById", query = "SELECT m FROM Ticket m WHERE m.ticketId = :ticketId"),
		@NamedQuery(name = "findTicketByTrainNo", query = "SELECT m FROM Ticket m WHERE m.trainNo = :trainNo order by m.insertTime")})
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key ticketId;
	  @ManyToOne(fetch=FetchType.LAZY, targetEntity=Train.class)
	private Train train;
	private String trainNo;
	private String departureDate;
	private String grade;
	private String count;
	private Date insertTime;

	public Ticket() {
	}

	public Key getTicketId() {
		return ticketId;
	}

	public void setTicketId(Key ticketId) {
		this.ticketId = ticketId;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	
}

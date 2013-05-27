package com.antsirs.train12306.model;

import java.util.Date;

import com.google.appengine.api.datastore.Key;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "listTicket", query = "SELECT m FROM Ticket m"),
		@NamedQuery(name = "findTicketById", query = "SELECT m FROM Ticket m WHERE m.ticketId = :ticketId") })
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key ticketId;
	private String trainNo;
	private String fromStation;
	private String toStation;
	private String departureDate;
	private String arrvialDate;
	private String during;
	private String businessClass;
	private String specialClass;
	private String firstClass;
	private String secondClass;
	private String seniorSoftSleepClass;
	private String softSleepClass;
	private String hardSleepClass;
	private String softSeatClass;
	private String hardSeatClass;
	private String standing;
	private String others;
	private Date insertTime;

	public Ticket() {
	}

	public Key getTicketId() {
		return ticketId;
	}

	public void setTicketId(Key ticketId) {
		this.ticketId = ticketId;
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

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrvialDate() {
		return arrvialDate;
	}

	public void setArrvialDate(String arrvialDate) {
		this.arrvialDate = arrvialDate;
	}

	public String getDuring() {
		return during;
	}

	public void setDuring(String during) {
		this.during = during;
	}

	public String getBusinessClass() {
		return businessClass;
	}

	public void setBusinessClass(String businessClass) {
		this.businessClass = businessClass;
	}

	public String getSpecialClass() {
		return specialClass;
	}

	public void setSpecialClass(String specialClass) {
		this.specialClass = specialClass;
	}

	public String getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(String firstClass) {
		this.firstClass = firstClass;
	}

	public String getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(String secondClass) {
		this.secondClass = secondClass;
	}

	public String getSeniorSoftSleepClass() {
		return seniorSoftSleepClass;
	}

	public void setSeniorSoftSleepClass(String seniorSoftSleepClass) {
		this.seniorSoftSleepClass = seniorSoftSleepClass;
	}

	public String getSoftSleepClass() {
		return softSleepClass;
	}

	public void setSoftSleepClass(String softSleepClass) {
		this.softSleepClass = softSleepClass;
	}

	public String getHardSleepClass() {
		return hardSleepClass;
	}

	public void setHardSleepClass(String hardSleepClass) {
		this.hardSleepClass = hardSleepClass;
	}

	public String getSoftSeatClass() {
		return softSeatClass;
	}

	public void setSoftSeatClass(String softSeatClass) {
		this.softSeatClass = softSeatClass;
	}

	public String getHardSeatClass() {
		return hardSeatClass;
	}

	public void setHardSeatClass(String hardSeatClass) {
		this.hardSeatClass = hardSeatClass;
	}

	public String getStanding() {
		return standing;
	}

	public void setStanding(String standing) {
		this.standing = standing;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}

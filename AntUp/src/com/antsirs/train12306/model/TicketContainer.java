package com.antsirs.train12306.model;

import java.util.List;
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
	private List<String> t5HardSleepTicketCountContainer;
	private List<String> t5SoftSleepTicketCountContainer;
	private List<String> t5HardSeatTicketCountContainer;
	private List<String> t189HardSleepTicketCountContainer;
	private List<String> t189SoftSleepTicketCountContainer;
	private List<String> t189HardSeatTicketCountContainer;
	private List<String> k157HardSleepTicketCountContainer;
	private List<String> k157SoftSleepTicketCountContainer;
	private List<String> k157HardSeatTicketCountContainer;
	
	public TicketContainer() {
	}
	public String getTicketContainerId() {
		return ticketContainerId;
	}
	public void setTicketContainerId(String ticketContainerId) {
		this.ticketContainerId = ticketContainerId;
	}
	public List<String> getT5HardSleepTicketCountContainer() {
		return t5HardSleepTicketCountContainer;
	}
	public void setT5HardSleepTicketCountContainer(
			List<String> t5HardSleepTicketCountContainer) {
		this.t5HardSleepTicketCountContainer = t5HardSleepTicketCountContainer;
	}
	public List<String> getT5SoftSleepTicketCountContainer() {
		return t5SoftSleepTicketCountContainer;
	}
	public void setT5SoftSleepTicketCountContainer(
			List<String> t5SoftSleepTicketCountContainer) {
		this.t5SoftSleepTicketCountContainer = t5SoftSleepTicketCountContainer;
	}
	public List<String> getT5HardSeatTicketCountContainer() {
		return t5HardSeatTicketCountContainer;
	}
	public void setT5HardSeatTicketCountContainer(
			List<String> t5HardSeatTicketCountContainer) {
		this.t5HardSeatTicketCountContainer = t5HardSeatTicketCountContainer;
	}
	public List<String> getT189HardSleepTicketCountContainer() {
		return t189HardSleepTicketCountContainer;
	}
	public void setT189HardSleepTicketCountContainer(
			List<String> t189HardSleepTicketCountContainer) {
		this.t189HardSleepTicketCountContainer = t189HardSleepTicketCountContainer;
	}
	public List<String> getT189SoftSleepTicketCountContainer() {
		return t189SoftSleepTicketCountContainer;
	}
	public void setT189SoftSleepTicketCountContainer(
			List<String> t189SoftSleepTicketCountContainer) {
		this.t189SoftSleepTicketCountContainer = t189SoftSleepTicketCountContainer;
	}
	public List<String> getT189HardSeatTicketCountContainer() {
		return t189HardSeatTicketCountContainer;
	}
	public void setT189HardSeatTicketCountContainer(
			List<String> t189HardSeatTicketCountContainer) {
		this.t189HardSeatTicketCountContainer = t189HardSeatTicketCountContainer;
	}
	public List<String> getK157HardSleepTicketCountContainer() {
		return k157HardSleepTicketCountContainer;
	}
	public void setK157HardSleepTicketCountContainer(
			List<String> k157HardSleepTicketCountContainer) {
		this.k157HardSleepTicketCountContainer = k157HardSleepTicketCountContainer;
	}
	public List<String> getK157SoftSleepTicketCountContainer() {
		return k157SoftSleepTicketCountContainer;
	}
	public void setK157SoftSleepTicketCountContainer(
			List<String> k157SoftSleepTicketCountContainer) {
		this.k157SoftSleepTicketCountContainer = k157SoftSleepTicketCountContainer;
	}
	public List<String> getK157HardSeatTicketCountContainer() {
		return k157HardSeatTicketCountContainer;
	}
	public void setK157HardSeatTicketCountContainer(
			List<String> k157HardSeatTicketCountContainer) {
		this.k157HardSeatTicketCountContainer = k157HardSeatTicketCountContainer;
	}

}

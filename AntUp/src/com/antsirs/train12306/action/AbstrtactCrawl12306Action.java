package com.antsirs.train12306.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.antsirs.core.struts.actionsuppport.BaseActionSupport;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;
import com.google.appengine.api.memcache.AsyncMemcacheService;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public abstract class AbstrtactCrawl12306Action extends BaseActionSupport{
	
	public List<Ticket> tickets;

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String data;

	public MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	public String drawChartStartDate;
	public String drawChartEndDate;
	
	public String getDrawChartStartDate() {
		return drawChartStartDate;
	}

	public void setDrawChartStartDate(String drawChartStartDate) {
		this.drawChartStartDate = drawChartStartDate;
	}

	public String getDrawChartEndDate() {
		return drawChartEndDate;
	}

	public void setDrawChartEndDate(String drawChartEndDate) {
		this.drawChartEndDate = drawChartEndDate;
	}

	//hightcharts show
	public String t189SoftSleepTicketCountSpecialDate;
	public String t189HardSleepTicketCountSpecialDate;
	public String t189HardSeatTicketCountSpecialDate;
	public String t5SoftSleepTicketCountSpecialDate;
	public String t5HardSleepTicketCountSpecialDate;
	public String t5HardSeatTicketCountSpecialDate;
	public String k157SoftSleepTicketCountSpecialDate;
	public String k157HardSleepTicketCountSpecialDate;
	public String k157HardSeatTicketCountSpecialDate;

	public String getT189SoftSleepTicketCountSpecialDate() {
		return t189SoftSleepTicketCountSpecialDate;
	}

	public void setT189SoftSleepTicketCountSpecialDate(
			String t189SoftSleepTicketCountSpecialDate) {
		this.t189SoftSleepTicketCountSpecialDate = t189SoftSleepTicketCountSpecialDate;
	}

	public String getT189HardSleepTicketCountSpecialDate() {
		return t189HardSleepTicketCountSpecialDate;
	}

	public void setT189HardSleepTicketCountSpecialDate(
			String t189HardSleepTicketCountSpecialDate) {
		this.t189HardSleepTicketCountSpecialDate = t189HardSleepTicketCountSpecialDate;
	}

	public String getT189HardSeatTicketCountSpecialDate() {
		return t189HardSeatTicketCountSpecialDate;
	}

	public void setT189HardSeatTicketCountSpecialDate(
			String t189HardSeatTicketCountSpecialDate) {
		this.t189HardSeatTicketCountSpecialDate = t189HardSeatTicketCountSpecialDate;
	}

	public String getT5SoftSleepTicketCountSpecialDate() {
		return t5SoftSleepTicketCountSpecialDate;
	}

	public void setT5SoftSleepTicketCountSpecialDate(
			String t5SoftSleepTicketCountSpecialDate) {
		this.t5SoftSleepTicketCountSpecialDate = t5SoftSleepTicketCountSpecialDate;
	}

	public String getT5HardSleepTicketCountSpecialDate() {
		return t5HardSleepTicketCountSpecialDate;
	}

	public void setT5HardSleepTicketCountSpecialDate(
			String t5HardSleepTicketCountSpecialDate) {
		this.t5HardSleepTicketCountSpecialDate = t5HardSleepTicketCountSpecialDate;
	}

	public String getT5HardSeatTicketCountSpecialDate() {
		return t5HardSeatTicketCountSpecialDate;
	}

	public void setT5HardSeatTicketCountSpecialDate(
			String t5HardSeatTicketCountSpecialDate) {
		this.t5HardSeatTicketCountSpecialDate = t5HardSeatTicketCountSpecialDate;
	}

	public String getK157SoftSleepTicketCountSpecialDate() {
		return k157SoftSleepTicketCountSpecialDate;
	}

	public void setK157SoftSleepTicketCountSpecialDate(
			String k157SoftSleepTicketCountSpecialDate) {
		this.k157SoftSleepTicketCountSpecialDate = k157SoftSleepTicketCountSpecialDate;
	}

	public String getK157HardSleepTicketCountSpecialDate() {
		return k157HardSleepTicketCountSpecialDate;
	}

	public void setK157HardSleepTicketCountSpecialDate(
			String k157HardSleepTicketCountSpecialDate) {
		this.k157HardSleepTicketCountSpecialDate = k157HardSleepTicketCountSpecialDate;
	}

	public String getK157HardSeatTicketCountSpecialDate() {
		return k157HardSeatTicketCountSpecialDate;
	}

	public void setK157HardSeatTicketCountSpecialDate(
			String k157HardSeatTicketCountSpecialDate) {
		this.k157HardSeatTicketCountSpecialDate = k157HardSeatTicketCountSpecialDate;
	}
	
	public int specialDate;

	public int getSpecialDate() {
		return specialDate;
	}

	public void setSpecialDate(int specialDate) {
		this.specialDate = specialDate;
	}
	
	
	
}

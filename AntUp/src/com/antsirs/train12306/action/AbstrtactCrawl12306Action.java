package com.antsirs.train12306.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.antsirs.core.struts.actionsuppport.BaseActionSupport;
import com.antsirs.train12306.model.Ticket;
import com.antsirs.train12306.service.TrainTicketManagerService;

public abstract class AbstrtactCrawl12306Action extends BaseActionSupport{
	
	public List<Ticket> tickets;

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String data;

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
	public String[] t189SoftSleepTicketCount;
	public String[] t189HardSleepTicketCount;
	public String[] t189HardSeatTicketCount;
	public String[] t5SoftSleepTicketCount;
	public String[] t5HardSleepTicketCount;
	public String[] t5HardSeatTicketCount;
	public String[] k157SoftSleepTicketCount;
	public String[] k157HardSleepTicketCount;
	public String[] k157HardSeatTicketCount;

	public String[] getT189SoftSleepTicketCount() {
		return t189SoftSleepTicketCount;
	}

	public void setT189SoftSleepTicketCount(String[] t189SoftSleepTicketCount) {
		this.t189SoftSleepTicketCount = t189SoftSleepTicketCount;
	}

	public String[] getT189HardSleepTicketCount() {
		return t189HardSleepTicketCount;
	}

	public void setT189HardSleepTicketCount(String[] t189HardSleepTicketCount) {
		this.t189HardSleepTicketCount = t189HardSleepTicketCount;
	}

	public String[] getT189HardSeatTicketCount() {
		return t189HardSeatTicketCount;
	}

	public void setT189HardSeatTicketCount(String[] t189HardSeatTicketCount) {
		this.t189HardSeatTicketCount = t189HardSeatTicketCount;
	}

	public String[] getT5SoftSleepTicketCount() {
		return t5SoftSleepTicketCount;
	}

	public void setT5SoftSleepTicketCount(String[] t5SoftSleepTicketCount) {
		this.t5SoftSleepTicketCount = t5SoftSleepTicketCount;
	}

	public String[] getT5HardSleepTicketCount() {
		return t5HardSleepTicketCount;
	}

	public void setT5HardSleepTicketCount(String[] t5HardSleepTicketCount) {
		this.t5HardSleepTicketCount = t5HardSleepTicketCount;
	}

	public String[] getT5HardSeatTicketCount() {
		return t5HardSeatTicketCount;
	}

	public void setT5HardSeatTicketCount(String[] t5HardSeatTicketCount) {
		this.t5HardSeatTicketCount = t5HardSeatTicketCount;
	}

	public String[] getK157SoftSleepTicketCount() {
		return k157SoftSleepTicketCount;
	}

	public void setK157SoftSleepTicketCount(String[] k157SoftSleepTicketCount) {
		this.k157SoftSleepTicketCount = k157SoftSleepTicketCount;
	}

	public String[] getK157HardSleepTicketCount() {
		return k157HardSleepTicketCount;
	}

	public void setK157HardSleepTicketCount(String[] k157HardSleepTicketCount) {
		this.k157HardSleepTicketCount = k157HardSleepTicketCount;
	}

	public String[] getK157HardSeatTicketCount() {
		return k157HardSeatTicketCount;
	}

	public void setK157HardSeatTicketCount(String[] k157HardSeatTicketCount) {
		this.k157HardSeatTicketCount = k157HardSeatTicketCount;
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

package com.bossteach.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries({ 
    @NamedQuery(name="listHongKangInsuranceTransaction",query="SELECT hkt FROM HongKangInsuranceTransaction hkt"), 
    @NamedQuery(name="findHongKangInsuranceTransactionWithId",query="SELECT hkt FROM HongKangInsuranceTransaction hkt WHERE hkt.transactionId = :transactionId")      
})
public class HongKangInsuranceTransaction  {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key transactionId;
	private String buyer;
	private String itemName;
	private String itemId;
	private Double premium;
	private Long count;
	private String transactionDate;
	private String status;
	private String sellerId;
	
	
	public HongKangInsuranceTransaction() {
	}
	
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public Key getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Key transactionId) {
		this.transactionId = transactionId;
	}

	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}

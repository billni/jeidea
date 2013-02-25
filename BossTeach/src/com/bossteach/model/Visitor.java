package com.bossteach.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import com.google.appengine.api.datastore.Key;

@Entity
@NamedQueries({
    @NamedQuery(name="findVisitorWithId",query="SELECT v FROM Visitor v WHERE v.visitorId = :visitorId"),
    @NamedQuery(name="findVisitorWithMail",query="SELECT v FROM Visitor v WHERE v.mail = :mail")    
})
public class Visitor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key visitorId;
	private String name;
	private String mail;
	private String fax;	
	private Boolean active;
	private Date createdDate;
	private Date bannedDate;
	
	public Visitor() {	
	}
	
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getBannedDate() {
		return bannedDate;
	}

	public void setBannedDate(Date bannedDate) {
		this.bannedDate = bannedDate;
	}

	public Boolean getActive() {
		return active;
	}

	/**
	 *  set visitor account status. 1 means active , 0 means inactive.
	 * @param active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}	
	public Key getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(Key visitorId) {
		this.visitorId = visitorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}	
}

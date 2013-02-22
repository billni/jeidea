package com.bossteach.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Visitor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key visitorId;
	private String name;
	private String mail;
	private String fax;
	private Message message;
	
	public Visitor() {	
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
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	
	
}

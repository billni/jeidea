package com.bossteach.model;

import com.google.appengine.api.datastore.Key; 
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Message{
	@Id	
	@GeneratedValue(generator = "uuidGenerator")
	private Key messageId;
	private String content;
	private Visitor visitor;
	private Date createDate;
	private Date echoDate;
	
	public Message() {	
	}
	
	public Key getMessageId() {
		return messageId;
	}
	public void setMessageId(Key messageId) {
		this.messageId = messageId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Visitor getVisitor() {
		return visitor;
	}
	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEchoDate() {
		return echoDate;
	}
	public void setEchoDate(Date echoDate) {
		this.echoDate = echoDate;
	}
	
	
}

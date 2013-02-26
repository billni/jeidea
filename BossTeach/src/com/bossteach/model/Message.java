package com.bossteach.model;

import com.google.appengine.api.datastore.Key; 
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ 
    @NamedQuery(name="listMessage",query="SELECT m FROM Message m"), 
    @NamedQuery(name="findMessageWithId",query="SELECT m FROM Message m WHERE m.messageId = :messageId")      
})
public class Message{
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Key messageId;
	private String content;
	
	@ManyToOne(cascade=	{CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.LAZY)
	private Visitor visitor;
	private Date createdDate;
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getEchoDate() {
		return echoDate;
	}
	public void setEchoDate(Date echoDate) {
		this.echoDate = echoDate;
	}
}

package com.bossteach.model;

import java.util.Date;

public class Message{
	private Long messageId;
	private String content;
	private Visitor visitor;
	private Date createDate;
	private Date echoDate;
	
	public Message() {	
	}
	
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
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

package com.domain;

import java.sql.Timestamp;

public class Request {
	private int id;
	private Register requester;
	private Register responder;
	private Timestamp date;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Register getRequester() {
		return requester;
	}
	public void setRequester(Register requester) {
		this.requester = requester;
	}
	public Register getResponder() {
		return responder;
	}
	public void setResponder(Register responder) {
		this.responder = responder;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
}

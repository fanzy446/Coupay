package com.domain;

import java.sql.Date;
import java.sql.Timestamp;

public class TransferRecord {
	private String id;
	private Register sender;
	private Register receiver;
	private double amount;
	private Timestamp tradeDate;
	private String description;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Timestamp getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Timestamp tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Register getSender() {
		return sender;
	}
	public void setSender(Register sender) {
		this.sender = sender;
	}
	public Register getReceiver() {
		return receiver;
	}
	public void setReceiver(Register receiver) {
		this.receiver = receiver;
	}
	
}

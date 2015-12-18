package com.service.dataObject;

public class TransferData {
	public static final String BYACCOUNT = "byAccount";
	public static final String BYBANKCARD = "byBankCard";
	private String sender;
	private String receiver;
	private String type;
	private double amount;
	private String payPassword;
	
	
	
	public TransferData(String sender, String receiver,
			double amount, String payPassword) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.payPassword = payPassword;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getPayPassword() {
		return payPassword;
	}
	public void setPayPassword(String payPassword) {
		this.payPassword = payPassword;
	}
}

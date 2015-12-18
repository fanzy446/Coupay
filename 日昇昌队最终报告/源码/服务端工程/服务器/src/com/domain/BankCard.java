package com.domain;

public class BankCard {
	private String cardNumber;
	private Register owner;
	private String bankName;
	
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public Register getOwner() {
		return owner;
	}
	public void setOwner(Register owner) {
		this.owner = owner;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}

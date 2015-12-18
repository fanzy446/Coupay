package com.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;



public class Register implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String head;
	private String loginPassword;
	private String status;
	private Account account;
	private Set<Contact> contacts = new HashSet<Contact>(0);
	private Set<BankCard> bankCards = new HashSet<BankCard>(0);
	private Set<TransferRecord> sendRecords = new HashSet<TransferRecord>(0);
	private Set<TransferRecord> receiveRecords = new HashSet<TransferRecord>(0);
	private Set<Message> messages = new HashSet<Message>(0);
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Set<Contact> getContacts() {
		return contacts;
	}
	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}
	public Set<BankCard> getBankCards() {
		return bankCards;
	}
	public void setBankCards(Set<BankCard> bankCards) {
		this.bankCards = bankCards;
	}
	public Set<TransferRecord> getSendRecords() {
		return sendRecords;
	}
	public void setSendRecords(Set<TransferRecord> sendRecords) {
		this.sendRecords = sendRecords;
	}
	public Set<TransferRecord> getReceiveRecords() {
		return receiveRecords;
	}
	public void setReceiveRecords(Set<TransferRecord> receiveRecords) {
		this.receiveRecords = receiveRecords;
	}
	public Set<Message> getMessages() {
		return messages;
	}
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
}

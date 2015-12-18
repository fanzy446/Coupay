package com.futurePayment.model;

public class BasicInformation {
	private String name;
	private String head;
	private int grade;
	private double balance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{name:" + name + ",head:" + head + ",grade:" + grade + ",balance:" + balance
				+ "}";
	}
}

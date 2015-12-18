package com.domain;

public class VipCardCollection {
	private int id;
	private Customer owner;
	private VipCard vipCard;
	private String vipNumber;
	private int grade;
	
	public Customer getOwner() {
		return owner;
	}
	public void setOwner(Customer owner) {
		this.owner = owner;
	}
	public VipCard getVipCard() {
		return vipCard;
	}
	public void setVipCard(VipCard vipCard) {
		this.vipCard = vipCard;
	}
	
	public String getVipNumber() {
		return vipNumber;
	}
	public void setVipNumber(String vipNumber) {
		this.vipNumber = vipNumber;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}

package com.domain;


public class Friendship {
	
	private FriendshipId id;
	private Register person1;
	private Register person2;      
	private String level;          //¹Ø×¢³Ì¶È
	
	public FriendshipId getId() {
		return id;
	}
	public void setId(FriendshipId id) {
		this.id = id;
	}
	
	public Register getPerson1() {
		return person1;
	}
	public void setPerson1(Register person1) {
		this.person1 = person1;
	}
	public Register getPerson2() {
		return person2;
	}
	public void setPerson2(Register person2) {
		this.person2 = person2;
	}
	public void setPerson2(Customer person2) {
		this.person2 = person2;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
}

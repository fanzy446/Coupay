package com.domain;

import java.util.HashSet;
import java.util.Set;

public class VipCard {
	private int id;
	private String vipCardNumber;
	private Seller seller;
	private Set<VipCardCollection> collections = new HashSet<VipCardCollection>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getVipCardNumber() {
		return vipCardNumber;
	}
	public void setVipCardNumber(String vipCardNumber) {
		this.vipCardNumber = vipCardNumber;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public Set<VipCardCollection> getCollections() {
		return collections;
	}
	public void setCollections(Set<VipCardCollection> collections) {
		this.collections = collections;
	}
	
}

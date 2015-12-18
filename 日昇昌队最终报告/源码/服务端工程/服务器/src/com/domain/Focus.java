package com.domain;

public class Focus {
	private FocusId id;
	private Customer customer;
	private Seller seller;
	
	
	public FocusId getId() {
		return id;
	}
	public void setId(FocusId id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
}

package com.domain;

import java.io.Serializable;

public class FocusId implements Serializable{
	
	private static final long serialVersionUID = -1449979135123027628L;
	
	private int customerId;
	private int sellerId;
	
	
	public int getCustomerId() {
		return customerId;
	}	
	
	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@Override
	public int hashCode() {
		return (customerId + sellerId) * (customerId - sellerId);
	}

	@Override
	public boolean equals(Object obj) {
		FocusId id = (FocusId) obj;
		return (customerId == id.getCustomerId() && sellerId == id.getSellerId());
	}
	
}

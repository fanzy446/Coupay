package com.externalSystemInteface;

import java.io.Serializable;

public class ProductInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3870168217916094394L;
	
	private String productId;
	private String productName;
	private double price;
	private double discountRate;
	private double discount;
	private String decription;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getDecription() {
		return decription;
	}
	public void setDescription(String decription) {
		this.decription = decription;
	}
}

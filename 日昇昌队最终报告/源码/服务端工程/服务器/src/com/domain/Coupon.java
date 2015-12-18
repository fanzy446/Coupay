package com.domain;

import java.sql.Date;

public class Coupon {
	private int id;
	private long CouponNumber;
	private Seller seller;
	private Date startDate;
	private Date endDate;
	private String type;
	private double value;
	private double least;
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public double getLeast() {
		return least;
	}
	public void setLeast(double least) {
		this.least = least;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	private String picture;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public long getCouponNumber() {
		return CouponNumber;
	}
	public void setCouponNumber(long couponNumber) {
		CouponNumber = couponNumber;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}	
}

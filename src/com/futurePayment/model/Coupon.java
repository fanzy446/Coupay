package com.futurePayment.model;

public class Coupon {
	private int couponId;
	private String picture;
	private String enterpriseName;
	private String startTime;
	private String endTime;
	private String type;
	private double value;
	private double least;
	private int amount;

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{couponId:" + couponId + ",picture:" + picture
				+ ",enterpriseName:" + enterpriseName + ",startTime:"
				+ startTime + ",endTime:" + endTime + ",type:" + type
				+ ",value:" + value + ",amount:" + amount + ",least:" + least
				+ "}";
	}
}

package com.domain;

import java.util.HashSet;
import java.util.Set;

public class Seller extends Register{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int grade;
	private double longitude;
	private double latitude;
	private String introduction;
	private String address;
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private Set<Coupon> coupons = new HashSet<Coupon>(0);
	private Set<Share> comments = new HashSet<Share>(0);
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Set<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	public Set<Share> getComments() {
		return comments;
	}

	public void setComments(Set<Share> comments) {
		this.comments = comments;
	}
	
}

package com.domain;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class Customer extends Register {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String sex;
	private Date birthday;
	private String realName;
	private Set<CouponCollection> collections = new HashSet<CouponCollection>(0);
	private Set<Share> shares = new HashSet<Share>(0);
	private Set<VipCardCollection> vipCollections = new HashSet<VipCardCollection>(0);
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public Set<CouponCollection> getCollections() {
		return collections;
	}
	public void setCollections(Set<CouponCollection> collections) {
		this.collections = collections;
	}
	public Set<Share> getShares() {
		return shares;
	}
	public void setShares(Set<Share> shares) {
		this.shares = shares;
	}
	public Set<VipCardCollection> getVipCollections() {
		return vipCollections;
	}
	public void setVipCollections(Set<VipCardCollection> vipCollections) {
		this.vipCollections = vipCollections;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
}

package com.futurePayment.model;

public class EnterpriseBasicInfo {
	private String name;
	private String head;
	private String introduction;
	private int distance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getDistance() {
		return distance;
	}

	public void setDiatance(int diatance) {
		this.distance = diatance;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{name:" + name + ",head:" + head + ",introduction:"
				+ introduction + ",distance:" + distance + "}";
	}

}

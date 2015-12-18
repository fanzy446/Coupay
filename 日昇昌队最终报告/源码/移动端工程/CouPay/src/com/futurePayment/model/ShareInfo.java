package com.futurePayment.model;

public class ShareInfo {
	private String head = null;
	private String enterpriseName = null;
	private int grade = 0;
	private String content = null;
	private double money;
	// �ϴ�ʱ����Ƭ
	private byte[] photo = null;
	// ����ʱ����Ƭ��ַ
	private String picture = null;

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{head:" + head + ",enterpriseName:" + enterpriseName
				+ ",grade:" + grade + ",content:" + content + ",money:" + money
				+ "}";
	}

}

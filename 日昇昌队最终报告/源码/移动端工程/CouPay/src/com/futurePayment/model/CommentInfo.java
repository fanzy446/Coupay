package com.futurePayment.model;

import com.billme.logic.MainService;
import com.billme.util.FileUtil;

public class CommentInfo {
	private String head = null;
	private String name = null;
	private String enterpriseName = null;
	private int grade = 0;
	private String content = null;
	private String photo = null;
	private double money = 0;
	private String time = null;

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

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

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void modify() {
		head = name;
		photo = name + enterpriseName + time;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{head:" + head + ",name:" + name + ",enterpriseName:"
				+ enterpriseName + ",grade:" + grade + ",content:" + content
				+ ",photo:" + photo + ",money:" + money + ",time:" + time + "}";
	}
}

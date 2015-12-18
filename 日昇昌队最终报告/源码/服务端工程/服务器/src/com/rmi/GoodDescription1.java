package com.rmi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class GoodDescription1 implements Serializable{
	
	private static final long serialVersionUID = -3715508884186789547L;
	private String serialNumber;
	private String name;
	private String type;
	private String color;
	private double size;
	double price;
	private Set<Good1> goods = new HashSet<Good1>(0);
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public double getSize() {
		return size;
	}
	
	public void setSize(double size) {
		this.size = size;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Set<Good1> getGoods() {
		return goods;
	}
	
	public void setGoods(Set<Good1> goods) {
		this.goods = goods;
	}
	
}

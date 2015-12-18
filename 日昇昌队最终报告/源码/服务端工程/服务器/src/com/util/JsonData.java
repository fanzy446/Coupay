package com.util;

import net.sf.json.JSONObject;

public class JsonData {

	private String name;
	private int ServiceType;
	private JSONObject data;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getServiceType() {
		return ServiceType;
	}
	public void setServiceType(int serviceType) {
		ServiceType = serviceType;
	}
	public JSONObject getData() {
		return data;
	}
	public void setData(JSONObject data) {
		this.data = data;
	}
	
}

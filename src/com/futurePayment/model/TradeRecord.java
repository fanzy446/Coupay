package com.futurePayment.model;

/**
 * 
 * @author luo
 * 
 */
public class TradeRecord {
	private String Id;
	private String sender;
	private String receiver;
	private String receiverPic;
	private double amount;
	private String date;
	private String method;
	private String mounth;
	private String Year;
	private String day;
	private String state;
	private int type;
	private String title;
	
	public String getMounth() {
		return mounth;
	}

	public void setMounth(String mounth) {
		this.mounth = mounth;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
		String []temp = date.split("/");
		setYear(temp[0]);
		setMounth(temp[1]);
		setDay(temp[2]);
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReceiverPic() {
		return receiverPic;
	}

	public void setReceiverPic(String receiverPic) {
		this.receiverPic = receiverPic;
	}
	
	
}

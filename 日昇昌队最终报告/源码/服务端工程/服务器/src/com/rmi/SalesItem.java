package com.rmi;

public class SalesItem {
	public String goodId;
	public String goodName;
	public double price;
	public double quantity;
	public double subTotal;
	
	
	public double calcateSubTotal(GoodCatalog catalog) {
		GoodDescription1 description = catalog.getGoodDescription(goodId);
		this.price = description.getPrice();
		subTotal = quantity * price;
		return subTotal;
	}
	
	public String getGoodId() {
		return goodId;
	}

	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}

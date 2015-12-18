package com.rmi;

public interface IBusinessServer {
	public String generateBill(String saleItems);
	public String getGoodDecription(String goodId);
	public void updateGoodDescription(String salesItems);
	public void addGoodDescription(GoodDescription1 description);
}

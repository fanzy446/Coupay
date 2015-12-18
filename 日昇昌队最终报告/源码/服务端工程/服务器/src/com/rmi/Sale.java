package com.rmi;


import java.util.List;

import net.sf.json.JSONObject;

public class Sale {
	
	public List<SalesItem> items;
	
	/**
	 * �����ܼ�
	 * @param catalog            ��ƷĿ¼
	 * @param items              ��Ʒ��Ŀ
	 * @return                   �ܼ�
	 */
	public double calculateTotal(GoodCatalog catalog) {
		double total = 0;
		for(SalesItem item : items) {
			total += item.calcateSubTotal(catalog);
		}
		return total;
	}
	
	public String generateBill(GoodCatalog catalog){
		double total = calculateTotal(catalog);
		JSONObject jobj = new JSONObject();
		jobj.put("items", items);
		jobj.put("total", total);
		return jobj.toString();
	}
	
	public List<SalesItem> getItems() {
		return items;
	}

	public void setItems(List<SalesItem> items) {
		this.items = items;
	}
	
}

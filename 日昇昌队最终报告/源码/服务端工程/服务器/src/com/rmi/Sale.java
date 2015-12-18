package com.rmi;


import java.util.List;

import net.sf.json.JSONObject;

public class Sale {
	
	public List<SalesItem> items;
	
	/**
	 * 计算总价
	 * @param catalog            商品目录
	 * @param items              商品条目
	 * @return                   总价
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

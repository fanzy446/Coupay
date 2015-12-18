package com.rmi;

import java.util.LinkedList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BusinessServer1 implements IBusinessServer {
	private GoodCatalog catalog;
	
	@Override
	public String getGoodDecription(String goodId) {
		System.out.println("±»µ÷ÓÃ");
		GoodDescription1 description = catalog.getGoodDescription(goodId);
		JSONObject jobj = new JSONObject();
		jobj.put("name",description.getName());
		jobj.put("price", description.getPrice());
		jobj.put("color", description.getColor());
		jobj.put("type", description.getType());
		jobj.put("size", description.getSize());
		return jobj.toString();
	}
	
	@Override
	public void updateGoodDescription(String salesItems) {
		
	}

	public GoodCatalog getCatalog() {
		return catalog;
	}

	public void setCatalog(GoodCatalog catalog) {
		this.catalog = catalog;
	}

	@Override
	public void addGoodDescription(GoodDescription1 description) {
		catalog.addGoodDescription(description);
	}

	@Override
	public String generateBill(String saleMessage) {
		JSONObject jobj = JSONObject.fromObject(saleMessage);
		double numberOfItems = jobj.getDouble("numberOfItems");
		JSONArray id = jobj.getJSONArray("goodId");
		JSONArray quantity = jobj.getJSONArray("quantity");
		
		List<SalesItem> items = new LinkedList<SalesItem>();
		for(int i = 0; i < numberOfItems; i++) {
			SalesItem item = new SalesItem();
			item.setGoodId(id.getString(i));
			item.setQuantity(Double.valueOf(quantity.get(i).toString()));
			items.add(item);
		}
		Sale sale = new Sale();
		sale.setItems(items);
		return sale.generateBill(catalog);
	}
	
}

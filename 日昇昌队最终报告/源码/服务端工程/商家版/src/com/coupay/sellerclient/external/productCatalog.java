package com.coupay.sellerclient.external;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import com.coupay.sellerclient.data.Product;
import com.coupay.sellerclient.util.DbHelper;

public class productCatalog {
	
	
	public HashMap<String, Product> getProduct(Object[] productIds) {
		if(productIds.length < 1)
			return null;
		HashMap<String, Product> map = new HashMap<String, Product>();
		String sql = "select * from product where productId in (";
		for(int i = 0; i < productIds.length - 1; i++)
			sql = sql + productIds[i] + ",";
		sql = sql + productIds[productIds.length-1] + ")";
		
		ResultSet rs = DbHelper.execQuery(sql);
		
		try {
			while(rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getString("productId"));
				product.setProductName(rs.getString("productName"));
				product.setProductType(rs.getString("productType"));
				product.setProductUnit(rs.getString("productUnit"));
				product.setPrice(rs.getDouble("price"));
				map.put(product.getProductId(), product);
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

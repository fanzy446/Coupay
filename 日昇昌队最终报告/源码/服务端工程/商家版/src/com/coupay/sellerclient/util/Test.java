package com.coupay.sellerclient.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*String sql = "select * from product";
		ResultSet rs = DbHelper.execQuery(sql);
		try {
			while(rs.next()) {
				System.out.println(rs.getString("productName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("wht", 3);
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		if(it.hasNext()) {
			System.out.println(it.next());
		}
	}

}

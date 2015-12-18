package com.rmi;

import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Client {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("com/rmi/client_context.xml");
		IBusinessServer server = (IBusinessServer) context.getBean("businessClient");
		/*GoodDescription1 description = new GoodDescription1();
		description.setSerialNumber("123456");
		description.setName("大红西瓜");
		description.setColor("绿色");
		description.setType("水果");
		description.setPrice(5.0);
		server.addGoodDescription(description);*/
		String[] id = {"123456","12346"};
		double q1 = 1, q2 = 2;
		double[] quantity = {q1,q2};
		JSONObject jobj = new JSONObject();
		jobj.put("goodId", id);
		jobj.put("quantity", quantity);
		jobj.put("numberOfItems", 2);
		String bill = server.generateBill(jobj.toString());
		System.out.println(bill);
	}

}

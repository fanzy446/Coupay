package com.rmi;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Server {

	public static void main(String[] args) {
		ApplicationContext app = new ClassPathXmlApplicationContext("com/rmi/ApplicationContext.xml");
		System.out.println(app.getBean("businessServer1"));
		GoodCatalog catalog = (GoodCatalog) app.getBean("goodCatalog");
		GoodDescription1 d = catalog.getGoodDescription("12346");
		System.out.println(d);
	}

}

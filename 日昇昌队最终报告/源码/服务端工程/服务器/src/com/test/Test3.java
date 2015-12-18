package com.test;

import java.util.List;

import com.domain.Customer;
import com.domain.Seller;
import com.domain.VipCard;
import com.domain.VipCardCollection;
import com.exploer.*;
public class Test3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*ServerPushSystem system = new ServerPushSystem();
		system.start();
		system.recordMessage("luoxi", "≤‚ ‘≤‚ ‘","text");
		String fileName = "C:\\Users\\luo\\Pictures\\6a1f0209jw1dxz5uba66yj.jpg";
		system.recordMessage("luoxi", fileName, "picture");
		system.addReceiver("luoxi");
		Client client = new Client("luoxi",5000,10 * 1000);
		Thread thread = new Thread(client);
		thread.start();*/
		
		/*SellerService service = new SellerService();
		VipCard card = new VipCard();
		//RegisterService service1 = new RegisterService();
		Seller seller = service.getSeller("huang");
		card.setSeller(seller);
		card.setVipCardNumber("1234567");
		service.saveVipCard(card, "huang");*/
			
//		CustomerService service = new CustomerService();
		/*SellerService service1 = new SellerService();
		VipCard card = service1.getVipCard("huang", "1234567");
		VipCardCollection collection = new VipCardCollection();
		collection.setVipCard(card);
		collection.setVipNumber("6461616");
		service.saveVipCardCollection(collection, "deng");*/
		
		/*VipCardCollection collection = service.getVipCardCollection("deng", "huang", "1234567");
		System.out.println(collection.getVipNumber());
		
		service.removeVipCardCollection(collection);*/
		
//		SellerService service2 = new SellerService();
		
		/*Customer customer = service.getCustomer("deng");
		Seller seller = service2.getSeller("huang");
		
		service.addFocus(customer, seller);
		service.removeFocus(customer, seller);*/
//		
//		List<String> names = service2.getFocusingCustomer("huang");
//		System.out.println(names.get(0));
//		
//		List<String> names2 = service.getFocusedSeller("deng");
//		System.out.println(names2.get(0));
	}

}

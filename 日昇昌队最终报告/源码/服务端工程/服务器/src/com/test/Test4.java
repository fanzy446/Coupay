package com.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.domain.Contact;
import com.domain.Coupon;
import com.domain.CouponCollection;
import com.domain.Customer;
import com.domain.Register;
import com.domain.Request;
import com.domain.Seller;
import com.domain.VipCard;

public class Test4 {
	public static void main(String[] args) {
//		RegisterService service1 = new RegisterService();
//		CustomerService service2 = new CustomerService();
//		SellerService service3 = new SellerService();
		/*Contact contact = new Contact();
		contact.setNumber("15464664");
		contact.setType("phone");
		Register register = service1.getRegister("luo");
		service1.addContact(register, contact);*/
		
		/*service1.doTransfer("luo", "ye", "luo", 10);
	
		service1.deposit("luo", 10);
		
		service1.withDraw("ye", 10, "ye");*/
		/*
		Share share = new Share();
		share.setContent("‘Ÿ∑¢");
		share.setDate(new Timestamp(System.currentTimeMillis()));
		service2.saveShare(share, "deng");*/
		
		/*service1.saveRequest("ye","deng");
		List<String> list = service1.getRequester("luo");
		System.out.println(list.size());
		System.out.println(list.get(0));
		
		service1.respond("deng","ye");*/
		
		/*List<Share> shares = service2.getShareByPage("ye", 1, 2);
		System.out.println(shares.get(0).getContent());
		System.out.println(shares.get(1).getContent());*/
		/*
		List<Share> shares = service2.getTopicByPage("deng", 1, 2);
		System.out.println(shares.get(0).getContent());
		System.out.println(shares.get(1).getContent());*/
		
		/*Coupon coupon = new Coupon();
		coupon.setContent("”≈ª›»Ø2");
		coupon.setCouponNumber(123464);
		coupon.setStartDate(new Timestamp(System.currentTimeMillis()));
		coupon.setEndDate(new Timestamp(System.currentTimeMillis()));
		coupon.setType("discount");
		coupon.setRate(0.6);
		Seller seller = service3.getSeller("luo");
		coupon.setSeller(seller);
		service3.saveCoupon(coupon);*/
		/*
		Coupon coupon = service3.getCoupon("luo", 0);
		System.out.println(coupon.getContent());
		CouponCollection collection = new CouponCollection();
		collection.setCoupon(coupon);
		collection.setNumber(1);
		service2.addCouponCollection(collection, "ye");
		CouponCollection collection = service2.getCouponcollection("ye", "luo", 0);
		service2.removeCouponCollection(collection);
		List list = service2.getCouponCollection("ye", "luo");
		System.out.println(list.size());
		Coupon coupon = service2.getCoupon("ye", "luo", 123464);
		System.out.println(coupon.getContent());
		VipCard card = new VipCard();
		card.setVipCardNumber("123");
		service3.saveVipCard(card, "luo");*/
	
//		List<Seller> sellers = service3.getSeller(112, 120, 2, 1);
//		System.out.println(sellers.size());
		
	}
}

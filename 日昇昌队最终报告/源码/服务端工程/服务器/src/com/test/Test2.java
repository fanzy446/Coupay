package com.test;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.SessionTrackingMode;
import javax.swing.ImageIcon;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.domain.Account;
import com.domain.Coupon;
import  com.domain.Customer;
import com.domain.Register;
import com.domain.Request;
import com.domain.Seller;

import com.subsystem.serverpush.Client;
import com.subsystem.serverpush.ServerPushSystem;
import com.util.HqlUtil;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*ApplicationContext context = new ClassPathXmlApplicationContext("ssh.xml");
		SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.commit();*/
		/*CustomerService service = new CustomerService();
		Customer customer = new Customer();
		customer.setSex("ƒ–");
		customer.setName("ye");
		customer.setLoginPassword("ye");
		customer.setStatus("active");
		customer.setBirthday(new Date(System.currentTimeMillis()));
		service.addCustomer(customer);*/
		/*Seller seller = new Seller();
		seller.setName("luo");
		seller.setStatus("active");
		seller.setLoginPassword("huang");
		seller.setAddress("guangzhou");
		SellerService service = new SellerService();
		service.addSeller(seller);*/
		
//		RegisterService service1 = new RegisterService();
//		Account account = new Account();
//		account.setBalance(1000);
//		account.setPayPassword("luo");
//		Register register = service1.getRegister("luo");
//		service1.createAccount(register, account);
//		
		
		/*CustomerService service = new CustomerService();
		Share share = new Share();
		share.setDate(new Date(System.currentTimeMillis()));
		share.setContent("∑÷œÌ≤‚ ‘");
		service.saveShare(share, "deng");*/
		/*CustomerService service = new CustomerService();
		Dialogue dialogue = new Dialogue();
		dialogue.setDate(new Date(System.currentTimeMillis()));
		dialogue.setContent("∆¿¬€1");
		
		service.saveDialogue(dialogue, 1,"deng","wang");*/
		
		/*CustomerService service = new CustomerService();
		Share share = service.getShare(7);
		System.out.println(share);
		System.out.println(share.getContent());*/
		
		/*Request request = new Request();
		RegisterService service = new RegisterService();
		Register requester = service.getRegister("ye");
		Register responder = service.getRegister("luoxi");
		request.setStatus("unanswered");
		request.setRequester(requester);
		request.setResponder(responder);
		
		service.saveRequest(request);*/
		
		/*RegisterService service1 = new RegisterService();
		Request request = (Request) HqlUtil.get(Request.class, 11);
		service1.response(request);*/
		
		/*CustomerService service = new CustomerService();
		Comment comment = new Comment();
		comment.setContent("∫√∆¿");*/
		
		/*SellerService service2 = new SellerService();
		Seller receiver = service2.getSeller("huang");
		comment.setReceiver(receiver);
		service.saveComment(comment, "deng");*/
		/*List<Share> shares = service.getTopicByPage("luoxi", 1, 2);
		System.out.println(shares.size());*/
	
		/*RegisterService service = new RegisterService();
		Account account = service.getAccount("luo");
		account.setGrade(1);
		service.updateAccount(account);
		
		Register register = service.getRegister("ye");
		register.setAddress("shenzhen");
		register.setLoginPassword("yezi");
		service.updateRegister(register);*/
		
	/*	Customer customer = (Customer) HqlUtil.get(Customer.class, 5);
		customer.setBirthday(new Date(System.currentTimeMillis()));
		CustomerService service = new CustomerService();
		service.updateCustomer(customer);*/
		
		/*RegisterService service = new RegisterService();
		Request request = new Request();
		Register requester = service.getRegister("luoxi");
		Register responder = service.getRegister("ye");
		request.setRequester(requester);
		request.setResponder(responder);
		service.saveRequest(request);
		service.deleteFriendship("luoxi", "ye");
		service.response(request);*/
		
		/*ServerPushSystem system = new ServerPushSystem();
		system.start();
		system.recordMessage("luoxi", "≤‚ ‘≤‚ ‘");
		system.addReceiver("luoxi");
		Client client = new Client("luoxi",5000,10 * 1000);
		Thread thread = new Thread(client);
		thread.start();*/
	}
}

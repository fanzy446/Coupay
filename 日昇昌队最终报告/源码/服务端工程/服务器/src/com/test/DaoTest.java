package com.test;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.imageio.stream.FileImageOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.action.customer.TransferAction;
import com.dao.LoginMessageDao;
import com.dao.RegisterDao;
import com.domain.Account;
import com.domain.Contact;
import com.domain.Coupon;
import com.domain.Customer;
import com.domain.LoginMessage;
import com.domain.Message;
import com.domain.Register;
import com.domain.Request;
import com.domain.Seller;
import com.domain.Share;
import com.domain.TransferRecord;
import com.externalSystemInteface.IServerPushSystem;
import com.service.dataObject.TransferData;
import com.service.dataObject.TransferRecordData;
import com.service.interfaces.AccountService;
import com.service.interfaces.CircleService;
import com.service.interfaces.CouponService;
import com.service.interfaces.CustomerService;
import com.service.interfaces.LoginMessageService;
import com.service.interfaces.RegisterService;
import com.service.interfaces.SellerService;
import com.service.interfaces.TransferService;
import com.subsystem.serverpush.MessageDbClass;
import com.subsystem.serverpush.ServerPushSystem;
import com.util.DistanceUtil;

public class DaoTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-beans.xml","applicationContext-common.xml"});
/*//		CustomerService customerService = (CustomerService) context.getBean("customerService");
//		Customer customer = new Customer();
//		customer.setBirthday(new Date(2013,9,1));
//		customer.setName("hello");
//		customerService.addCustomer(customer);
//		Register register = new Register();
//		register.setName("helloworld");
//		register.setLoginPassword("12345");
//		register.setStatus("normal");
//		register.setAddress("hello");
//		Account account = new Account();
//		account.setPayPassword("123");
//		account.setBalance(0);
//		register.setAccount(account );
//		RegisterService registerService = (RegisterService) context.getBean("registerService");
//		registerService.addRegister(register);	
//		RegisterDao registerDao = (RegisterDao) context.getBean("registerDao");
//		registerDao.update(register);
		
//		Comment comment = new Comment();
//		comment.setContent("hello");
//		Customer sender = new Customer();
//		sender.setId(2);
//		comment.setSender(sender);
//		Seller receiver = new Seller();
//		receiver.setId(3);
//		comment.setReceiver(receiver);
		CircleService circleService = (CircleService) context.getBean("circleService");
//		service.addComment(comment);
		
//		CircleService service = (CircleService) context.getBean("circleService");
//		String name = 
//		service.getTopicByPage(name, page, pageSize);
//		Share share = new Share();
//		share.setContent("hello");
//		share.setGrade(0);
//		JSONObject temp = JSONObject.fromObject(share);
//		System.out.println(temp.toString());
//		List<Share> s = service.getTopicByPage("lgb", 1, 10);
//		System.out.println(s.size());
//		JSONArray shares = new JSONArray();
//		for(Share share: s){
//			System.out.println("hello");
//			System.out.println(share.getContent());
//			JSONObject temp = JSONObject.fromObject(share);
//			shares.add(temp);
//			System.out.println(temp.toString());
//		}
//		LoginMessage message = new LoginMessage();
//		message.setAddress("gz");
//		Timestamp date = new Timestamp(System.currentTimeMillis());
//		message.setDate(date);
//		System.out.println(message.getDate());
//		Register register = new Register();
//		register.setId(3);
//		message.setRegister(register);
//		LoginMessageService loginMessageService = (LoginMessageService) context.getBean("loginMessageService");
//		loginMessageService.add(message);
//		System.out.println("end");
//		Customer customer = new Customer();
//		customer.setSex("ÄÐ");
//		customer.setName("come2");
//		customer.setRealName("hello");
//		customer.setStatus("normal");
//		Date birthday = java.sql.Date.valueOf("2013-10-01");
//		System.out.println(birthday);
//		customer.setBirthday(birthday);
//		customer.setLoginPassword("123");
//		
//		Account account = new Account();
//		account.setPayPassword("123");
		RegisterService registerService = (RegisterService) context.getBean("registerService");
//		account.setOwner(customer);
//		account.setBalance(0);
//		account.setGrade(0);
//		
//		
//		customer.setAccount(account);
		//´´½¨Account

//
//		CustomerService customerService = (CustomerService) context.getBean("customerService");
//		customerService.addCustomer(customer);
//		System.out.println("creating customer");
//		
//				
//		AccountService accountService = (AccountService) context.getBean("accountService");
//		accountService.addAccount(account);
//		System.out.println("creating account");
//		SellerService sellerService = (SellerService) context.getBean("sellerService");
		
//		List<Seller> sellers = sellerService.getSeller(113.396089, 23.0537576, 45, 1, 10);
//		double distance = DistanceUtil.GetDistance(113.396089, 23.0537576, 113.402257, 23.050752);
//		System.out.println(distance);
		
		TransferService transferService = (TransferService) context.getBean("transferService");
//		List<TransferRecordData> records = service.getSentTransferRecord("lgb","123333",1, 3);
//		for(TransferRecordData record: records) {
//			System.out.println(record.getRecordNumber() + "  " + record.getDate() + " " + record.getSender());
//		}
//		pushService.recordMessage("lgb", "hello"); */
		TransferService transferService = (TransferService) context.getBean("transferService");
		List<TransferRecord> records = transferService.getSentTransferRecord("lgb", "TR20130910155650700xr", 1, 5);
		for(TransferRecord r: records) {
			System.out.println(r.getTradeDate());
		}
	}

}

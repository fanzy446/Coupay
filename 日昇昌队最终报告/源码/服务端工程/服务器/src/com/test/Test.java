package com.test;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.domain.Account;
import com.domain.BankCard;
import com.domain.Contact;
import com.domain.Coupon;
import com.domain.CouponCollection;
import com.domain.Customer;
import com.domain.Register;
import com.domain.Seller;
import com.subsystem.bankSystem.MyBankSystem;
import com.subsystem.serverpush.MessageDbClass;
import com.subsystem.serverpush.ServerPushSystem;
import com.util.HqlUtil;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*Configuration configuration = new Configuration().configure();
		SessionFactory factory = configuration.buildSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		tx.commit();*/
		//Session session = HqlUtil.openSession();
		
		/*RegisterService service = new RegisterService();
		
		Register register1 = new Register();
		register1.setName("luoxi");
		register1.setLoginPassword("luo");
		register1.setAddress("guangdong");
		register1.setStatus("active");
		service.addRegister(register1);
		
		Register register2 = new Register();
		register2.setName("ye");
		register2.setLoginPassword("ye");
		register2.setStatus("active");
		register2.setAddress("guangdong");
		service.addRegister(register2);
	*/
		
		/*ApplicationContext context = new ClassPathXmlApplicationContext("ssh.xml");
		SessionFactory factory = (SessionFactory) context.getBean("sessionFactory");
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();
		transaction.commit();*/
		
		/*RegisterService service = new RegisterService();
		service.doTransfer("luo", "li", "luo", 15);*/
		
		/*String hql = "from Account where owner.name =?";
		String[] parameters = {"luo"};
		List<Object> list = HqlUtil.executeQuery(hql, parameters);
		if(list.size() == 1){
			System.out.println(((Account)list.get(0)).getBalance());
		}*/
		
		/*RegisterService registerService = new RegisterService();
		Register register = new Register();
		register.setName("ye");
		register.setLoginPassword("ye");
		register.setStatus("active");
		register.setAddress("guangzhou");
		
		registerService.addRegister(register);
		
		Account account = new Account();
		account.setBalance(500);
		account.setPayPassword("ye");
		
		registerService.createAccount(register, account);*/
		/*register.setName("luo");
		Contact contact = new Contact();
		contact.setNumber("15464664");
		contact.setType("phone");
		
		BankCard card = new BankCard();
		card.setBankName("gongshang");
		card.setCardNumber("164466");
		
		registerService.bindCard(card, register);*/
		/*registerService.doTransfer("luo", "ye", "luo", 20);*/
		
		/*MyBankSystem sys = new MyBankSystem();
		sys.withDraw("12435", "luo", 10);*/
		
		/*MessageDbClass db = new MessageDbClass();
		List<com.subsystem.serverpush.Message> list = db.getMessage("luo");
		System.out.println(list.size());*/
	
		/*SellerService service = new SellerService();
		Seller seller = service.getSeller("huang");
		Coupon coupon = new Coupon();
		coupon.setContent("”≈ª›»Ø1");
		coupon.setCouponNumber(123456);
		coupon.setStartDate(new Timestamp(System.currentTimeMillis()));
		coupon.setEndDate(new Timestamp(System.currentTimeMillis()));
		coupon.setSeller(seller);
		coupon.setType("discount");
		coupon.setRate(0.6);
		service.saveCoupon(coupon);*/
		
		/*SellerService service = new SellerService();
		Coupon coupon = service.getCoupon("huang", 123456);
		CouponCollection collection = new CouponCollection();
		collection.setCoupon(coupon);
		collection.setNumber(1);
		CustomerService service2 = new CustomerService();
		Customer owner = service2.getCustomer("luoxi");
		collection.setOwner(owner);
		service2.addCouponCollection(collection, "deng");*/
		
		/*CustomerService service = new CustomerService();
		CouponCollection collection = service.getCouponcollection("deng", "huang", 123456);
		service.removeCouponCollection(collection);*/
		
		
	}

}

package com.action.customer;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;


import com.domain.BankAccount;
import com.domain.BankCard;
import com.domain.Register;
import com.luo.pushUtil.PushMessageUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.BankAccountService;
import com.service.interfaces.BankCardService;
import com.service.interfaces.RegisterService;
import com.util.ResultCode;

@SuppressWarnings("serial")
public class ManageAction extends ActionSupport implements SessionAware {

	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	private BankAccountService bankAccountService;
	private BankCardService bankCardService;
	private RegisterService registerService;
	
	
	public JSONObject getResponse() {
		return response;
	}


	public void setResponse(JSONObject response) {
		this.response = response;
	}


	public Map<String, Object> getSession() {
		return session;
	}


	public void setSession(Map<String, Object> session) {
		this.session = session;
	}


	public BankAccountService getBankAccountService() {
		return bankAccountService;
	}


	public void setBankAccountService(BankAccountService bankAccountService) {
		this.bankAccountService = bankAccountService;
	} 


	public BankCardService getBankCardService() {
		return bankCardService;
	}


	public void setBankCardService(BankCardService bankCardService) {
		this.bankCardService = bankCardService;
	}


	public RegisterService getRegisterService() {
		return registerService;
	}


	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}


	//	/**
//	 * 获取用户信息
//	 * @return
//	 */
//	public String getUserInfo(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		String name = data.getString("name");
//		String phone = null;
//		String email = null;
//		Register register = registerService.getRegister(name);
//		String address = register.getAddress();
//		//获取用户信息
//		Customer customer = customerService.getCustomer(name);
//		String sex = customer.getSex();
//		Date birthday = customer.getBirthday();
//		//获取联系
//		List<Contact> contacts = registerService.getContact(name);
//		Iterator<Contact> it = contacts.iterator();
//		while(it.hasNext()){
//			Contact temp = it.next();
//			if(temp.getType().equals("phone"))
//				phone = temp.getNumber();
//			if(temp.getType().equals("email"))
//				email = temp.getNumber();
//		}
//		data.clear();
//		data.put("name", name);
//		data.put("sex", sex);
//		data.put("address", address);
//		data.put("birthday", birthday);
//		data.put("phone", phone);
//		data.put("email", email);
//		response.put("data", data);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";		
//	}
//	
//	/**
//	 * 修改个人信息
//	 * @return
//	 */
//	public String modifyUserInfo(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		String name = data.getString("name");
//		String sex = data.getString("sex");
//		Date birthday = (Date) data.get("birthday");
//		String phone = data.getString("phone");
//		String email = data.getString("email");
//		Register register = new Register();
//		register.setName(name);
//		registerService.updateRegister(register);
//		Customer customer = new Customer();
//		customer.setSex(sex);
//		customer.setBirthday(birthday);
//		//更新用户信息
//		customerService.updateCustomer(customer);
//		//删除联系
//		//TODO
//		//添加联系
//		Contact contact = new Contact();
//		contact.setType("phone");
//		contact.setNumber(phone);
//		Contact contact1 = new Contact();
//		contact1.setType("email");
//		contact1.setNumber(email);
//		registerService.addContact(register, contact);
//		registerService.addContact(register, contact1);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";
//	}
//	/**
//	 * 获取支付记录
//	 * 需要的参数：1.name 2.page 3.pageSize
//	 * @return
//	 */
//	public String getPayRecord(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		//获取记录
//		String name = data.getString("name");
//		int page = data.getInt("page");
//		int pageSize = data.getInt("pageSize");
//		List<TransferRecord> records = transferService.getSentTransferRecord(name);
//		JSONArray tradeRecords = new JSONArray();
//		for(TransferRecord r: records){
//			JSONObject record = new JSONObject();
//			record.put("sender", r.getSender());
//			record.put("receiver", r.getReceiver());
//			record.put("amount", r.getAmount());
//			record.put("date", r.getTradeDate());
//			//支付方式？
//			//TODO
//			tradeRecords.add(record);
//		}
//		data.clear();
//		data.put("tradeRecords", tradeRecords);
//		response.put("data", data);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";
//	}
//	
//	/**
//	 * 获取收款信息
//	 * 需要的参数：1.name 2.page 3.pageSize
//	 * @return
//	 */
//	public String getReceiveRecord(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		//获取记录
//		String name = data.getString("name");
//		int page = data.getInt("page");
//		int pageSize = data.getInt("pageSize");	
//		List<TransferRecord> records = transferService.getReceivedTransferRecord(name);
//		JSONArray tradeRecords = new JSONArray();
//		for(TransferRecord r: records){
//			JSONObject record = new JSONObject();
//			record.put("sender", r.getSender());
//			record.put("receiver", r.getReceiver());
//			record.put("amount", r.getAmount());
//			record.put("date", r.getTradeDate());
//			//支付方式？
//			//TODO
//			tradeRecords.add(record);
//		}
//		data.clear();
//		data.put("tradeRecords", tradeRecords);
//		response.put("data", data);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";
//	}
//	/**
//	 * 获取VIP卡
//	 * @return
//	 */
//	public String getVIP(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		//获取记录
//		String name = data.getString("name");
//		//获取VIP卡
//		List<VipCardCollection> vips = vipCardService.getVipCard(name);
//		JSONArray cardList = new JSONArray();
//		for(VipCardCollection v: vips){
//			JSONObject card = new JSONObject();
//			card.put("grade", v.getGrade());
//			card.put("enterpriseName", v.getVipCard().getSeller());
//			cardList.add(card);
//		}
//		data.clear();
//		data.put("cardList", cardList);
//		response.put("data", data);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";
//	}
//	
//	/**
//	 * 申请VIPCARD
//	 * @return
//	 */
//	public String applyVIP(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		//获取记录
//		String name = data.getString("name");
//		String enterpriseName = data.getString("enterpriseName");
//		//TODO
//		//要存储申请信息到数据库
//		return null; 
//	}
//	
//	
//	/**
//	 * 获取系统积分
//	 * @return
//	 */
//	public String getSysGrade(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		//获取记录
//		String name = data.getString("name");
//		//获取系统积分
//		Account account = RegisterService.getAccount(name);
//		int grade = account.getGrade();
//		data.clear();
//		data.put("grade", grade);
//		response.put("data", data);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";
//	}
	/**
	 * 检查用户名是否存在
	 * @return
	 */
	public String checkExist(){
		/*int result = 0;
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//获取记录
		String name = (String) session.get("name");
		System.out.println(name);
		Register register = registerService.getRegister(name);
		if(register==null){
			result = ResultCode.REGISTER_NOT_EXISTS;
		}else{
			result = ResultCode.FAILURE;
		}
		//TODO
		System.out.println(result);
		response.put("result", result);
		return "success";*/
		return "wh";
	}

	
	public String bindCard() {
		try{
			System.out.println("begin");
			int result;
			BankCard card  = new BankCard();
			JSONObject data = (JSONObject) session.get("data");
			String cardNumber = data.getString("cardNumber");
			String password = data.getString("password");
			BankAccount account = bankAccountService.getBankAccount(cardNumber);
			if(account == null) 
				result = ResultCode.ACCOUNT_NOT_EXISTS;
			if(account!= null && !account.getPassword().equals(password)) 
				result= ResultCode.WRONG_PASSWORD;
			else {
				card.setBankName(account.getBankName());
				card.setCardNumber(cardNumber);
				String name = (String) session.get("name");
				Register owner = registerService.getRegister(name);
				card.setOwner(owner);
				bankCardService.addBankCard(card);
				result = ResultCode.SUCCESS;
			}
			System.out.println("success");
			data.clear();
			data.put("cardNumber", card.getCardNumber());
			data.put("bank", card.getBankName());
			response.put("result", result);
			response.put("data", data);
			System.out.println(response);
			return "success";
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	public String getBindedCards() {
		try {
			System.out.println("begin");
			String name = (String) session.get("name");
			System.out.println(name);
			List<BankCard> cards = bankCardService.getBankCards(name);
			JSONArray array = new JSONArray();
			for(BankCard card: cards) {
				JSONObject jobj = new JSONObject();
				jobj.put("cardNumber", card.getCardNumber());
				jobj.put("bankName", card.getBankName());
				array.add(jobj);
			}
			JSONObject data = new JSONObject();
			data.put("cardList",array);
			System.out.println(data);
			response.put("data", data);
			response.put("result", ResultCode.SUCCESS);
			System.out.println("success");
			System.out.println(response);
			return "success";
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
//	
//	/**
//	 * 解除银行卡
//	 */
//	public String deleteBankCard(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		String cardNumber = data.getString("cardNumber");
//		//解除绑定银行卡
//		RegisterService.removeCard(cardNumber);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";
//	}

//	
//	/**
//	 * 赠送优惠券
//	 * @return
//	 */
//	public String sendCoupon(){
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		//获取记录
//		String name = data.getString("name");
//		Customer sender = CustomerService.getCustomer(name);
//		String receiverName = data.getString("receiver");
//		Customer receiver = CustomerService.getCustomer(receiverName);
//		int couponId = data.getInt("couponId");
//		int amount = data.getInt("amount");
//		/**
//		 * 赠送优惠券
//		 * 
//		 */
//		CustomerService.sendCoupon(sender, receiver, couponId, amount);
//		return "success";
//	}
//	
//	/**
//	 * 添加评论
//	 * 需要1.用户名 2.内容 3.时间 4.商家名字
//	 * @return
//	 */
//	public String addComment(){
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		//获取记录
//		String name = data.getString("name");
//		String content = data.getString("content");
//		Timestamp time = (Timestamp) data.get("time");
//		String enterpriseName = data.getString("enterpriseName");
//		//新建一个评论
//		Comment comment = new Comment();
//		comment.setContent(content);
//		comment.setDate(time);
//		comment.setSender(CustomerService.getCustomer(name));
//		comment.setReceiver(SellerService.getSeller(enterpriseName));
//		CustomerService.saveComment(comment, name);
//		response.put("result", ResultCode.SUCCESS);
//		return "success";
//	}
//	
//	/*
//	 * 获得对商家评论
//	 */
//	public String getComment(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		String name = data.getString("name");
//		String enterpriseName = data.getString("enterpriseName");
//		//TODO
//		//??业务函数
//		return null;
//	}
}

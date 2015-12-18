package com.action.register;

import java.sql.Date;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.domain.Account;
import com.domain.Contact;
import com.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.AccountService;
import com.service.interfaces.CustomerService;
import com.service.interfaces.RegisterService;
import com.util.ResultCode;

@SuppressWarnings({ "serial" })
public class RegistAction extends ActionSupport implements SessionAware{
	
	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	private RegisterService registerService;
	private AccountService accountService;
	private CustomerService customerService;
	public String execute(){
		/**
		 * 接受到的数据里面有：
		 * 对于客户 (registType==customer)
		 * 1.注册信息：用户名(name)，地址(address)，登录密码(loginPassword)
		 * 2.客户信息：性别(sex)，生日(birthday)，是否加一个真实姓名？
		 * 3.联系方式：类型(type:String)，号码(num:String)
		 * 4.创建一个account
		 * 
		 */
		/**
		 * 对于商家 (registType==seller)
		 * 1.注册信息：商家名(name)，地址(address)，登录密码(loginPassword)
		 * 2.创建一个seller
		 * 3.联系方式：类型(type:String)，号码(num:String)
		 * 4.创建一个account
		 */
		JSONObject data = (JSONObject) session.get("data");
		Customer customer = new Customer();
		customer.setSex(data.getString("sex"));
		customer.setName(data.getString("name"));
		customer.setRealName(data.getString("realName"));
		customer.setStatus("normal");
		Date birthday = java.sql.Date.valueOf((String) data.get("birthday"));
		System.out.println(birthday);
		customer.setBirthday(birthday);
		customer.setLoginPassword(data.getString("loginPassword"));
		

		//创建Account
		Account account = new Account();
		account.setPayPassword(data.getString("payPassword"));
		account.setOwner(customer);
		account.setBalance(0);
		account.setGrade(0);
		
		customer.setAccount(account);
		customerService.addCustomer(customer);
		System.out.println("creating customer");
		accountService.addAccount(account);
		System.out.println("creating account");
		//创建Contact
		//TODO
		JSONArray contacts = (JSONArray) data.get("contact");
		System.out.println(contacts);
		int size = contacts.size();
		for(int i=0; i<size; i++){
			JSONObject contact = contacts.getJSONObject(i);
			String type = contact.getString("type");
			String number = contact.getString("number");
			Contact temp = new Contact();
			temp.setType(type);
			temp.setNumber(number);
			temp.setRegister(registerService.getRegister(data.getString("name")));
			registerService.addContact(temp);
		}
		System.out.println("creating contact");
	
		response.put("result", ResultCode.SUCCESS);
		System.out.println("注册成功");
		return "success";
		
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public CustomerService getCustomerService() {
		return customerService;
	}
	public RegisterService getRegisterService() {
		return registerService;
	}
	public JSONObject getResponse() {
		return response;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
	public void setResponse(JSONObject response) {
		this.response = response;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}

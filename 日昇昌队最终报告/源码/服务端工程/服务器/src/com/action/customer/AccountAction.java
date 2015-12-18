package com.action.customer;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.domain.Account;
import com.domain.Register;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.AccountService;
import com.service.interfaces.RegisterService;
import com.util.ResultCode;

public class AccountAction extends ActionSupport implements SessionAware, ApplicationAware {

	
	private JSONObject response = new JSONObject();
	private Map<String, Object> application;
	private Map<String, Object> session;
	private AccountService accountService;
	
	public String addAccount(){
		int result = 0;
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//取出银行账号
		//取出拥有者
		//取出余额
		//取出等级
		//取出支付密码
		int id = Integer.parseInt(data.getString("id"));
		double balance = Double.parseDouble(data.getString("balance"));
		Register owner = (Register) data.get("owner");
		int grade = Integer.parseInt(data.getString("grade"));
		String payPassword = data.getString("payPassword");
		//创建Account对象
		Account account = new Account();
		//Account赋值
		account.setId(id);
		account.setOwner(owner);
		account.setBalance(balance);
		account.setGrade(grade);
		account.setPayPassword(payPassword);
		//增加账户
		accountService.addAccount(account);
		
		result = ResultCode.SUCCESS;
		System.out.println("增加账户成功");
		
		data.clear();
		data.put("account", account);
		
		response.accumulate("data", data);
		response.accumulate("result", result);
		
		return "success";
	}
	
	public String getAccount(){
		int result = 0;
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//取出拥有者姓名
		String name = ((Register)data.get("owner")).getName();
		//获得该用户账户信息
		Account account =  accountService.getAccount(name);
		
		result = ResultCode.SUCCESS;
		System.out.println("增加账户成功");
		
		data.clear();
		data.put("account", account);
		
		return "success";
	}
	
	public String updateAccount(){
		int result = 0;
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//取出银行账号
		//取出拥有者
		//取出余额
		//取出等级
		//取出支付密码
		int id = Integer.parseInt(data.getString("id"));
		double balance = Double.parseDouble(data.getString("balance"));
		Register owner = (Register) data.get("owner");
		int grade = Integer.parseInt(data.getString("grade"));
		String payPassword = data.getString("payPassword");
		//创建Account对象
		Account account = new Account();
		//Account赋值
		account.setId(id);
		account.setOwner(owner);
		account.setBalance(balance);
		account.setGrade(grade);
		account.setPayPassword(payPassword);
		//更新账户信息
		accountService.updateAccount(account);
		
		result = ResultCode.SUCCESS;
		System.out.println("更新账户成功");
		
		data.clear();
		data.put("account", account);
		
		response.accumulate("data", data);
		response.accumulate("result", result);
		
		return "success";
	}
	public String deposit(){
		int result = 0;
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//取出拥有者姓名
		String name = ((Register)data.get("owner")).getName();
		//取出金额
		double amount = Double.parseDouble(data.getString("amount"));
		//存钱
		accountService.deposit(name, amount);
		
		result = ResultCode.SUCCESS;
		System.out.println("存钱成功");
		
		data.clear();
		data.put("amount", amount);
		
		response.accumulate("data", data);
		response.accumulate("result", result);
		
		return "success";
	}
	
	public String withDraw(){
		int result = 0;
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//取出拥有者姓名
		String name = ((Register)data.get("owner")).getName();
		//取出金额
		double amount = Double.parseDouble(data.getString("amount"));
		//取出密码
		String payPassword = data.getString("payPassword");
		
		//减少金额
		accountService.withDraw(name, payPassword, amount);
		
		result = ResultCode.SUCCESS;
		System.out.println("消费成功");
		
		data.clear();
		data.put("amount", amount);
		
		response.accumulate("data", data);
		response.accumulate("result", result);
		
		return "success";
	}
	public Map<String, Object> getApplication() {
		return application;
	}

	public JSONObject getResponse() {
		return response;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	public void setResponse(JSONObject response) {
		this.response = response;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	

}

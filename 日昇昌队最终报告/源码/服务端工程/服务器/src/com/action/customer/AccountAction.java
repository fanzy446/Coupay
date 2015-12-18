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
		//ȡ������
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//ȡ�������˺�
		//ȡ��ӵ����
		//ȡ�����
		//ȡ���ȼ�
		//ȡ��֧������
		int id = Integer.parseInt(data.getString("id"));
		double balance = Double.parseDouble(data.getString("balance"));
		Register owner = (Register) data.get("owner");
		int grade = Integer.parseInt(data.getString("grade"));
		String payPassword = data.getString("payPassword");
		//����Account����
		Account account = new Account();
		//Account��ֵ
		account.setId(id);
		account.setOwner(owner);
		account.setBalance(balance);
		account.setGrade(grade);
		account.setPayPassword(payPassword);
		//�����˻�
		accountService.addAccount(account);
		
		result = ResultCode.SUCCESS;
		System.out.println("�����˻��ɹ�");
		
		data.clear();
		data.put("account", account);
		
		response.accumulate("data", data);
		response.accumulate("result", result);
		
		return "success";
	}
	
	public String getAccount(){
		int result = 0;
		//ȡ������
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//ȡ��ӵ��������
		String name = ((Register)data.get("owner")).getName();
		//��ø��û��˻���Ϣ
		Account account =  accountService.getAccount(name);
		
		result = ResultCode.SUCCESS;
		System.out.println("�����˻��ɹ�");
		
		data.clear();
		data.put("account", account);
		
		return "success";
	}
	
	public String updateAccount(){
		int result = 0;
		//ȡ������
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//ȡ�������˺�
		//ȡ��ӵ����
		//ȡ�����
		//ȡ���ȼ�
		//ȡ��֧������
		int id = Integer.parseInt(data.getString("id"));
		double balance = Double.parseDouble(data.getString("balance"));
		Register owner = (Register) data.get("owner");
		int grade = Integer.parseInt(data.getString("grade"));
		String payPassword = data.getString("payPassword");
		//����Account����
		Account account = new Account();
		//Account��ֵ
		account.setId(id);
		account.setOwner(owner);
		account.setBalance(balance);
		account.setGrade(grade);
		account.setPayPassword(payPassword);
		//�����˻���Ϣ
		accountService.updateAccount(account);
		
		result = ResultCode.SUCCESS;
		System.out.println("�����˻��ɹ�");
		
		data.clear();
		data.put("account", account);
		
		response.accumulate("data", data);
		response.accumulate("result", result);
		
		return "success";
	}
	public String deposit(){
		int result = 0;
		//ȡ������
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//ȡ��ӵ��������
		String name = ((Register)data.get("owner")).getName();
		//ȡ�����
		double amount = Double.parseDouble(data.getString("amount"));
		//��Ǯ
		accountService.deposit(name, amount);
		
		result = ResultCode.SUCCESS;
		System.out.println("��Ǯ�ɹ�");
		
		data.clear();
		data.put("amount", amount);
		
		response.accumulate("data", data);
		response.accumulate("result", result);
		
		return "success";
	}
	
	public String withDraw(){
		int result = 0;
		//ȡ������
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//ȡ��ӵ��������
		String name = ((Register)data.get("owner")).getName();
		//ȡ�����
		double amount = Double.parseDouble(data.getString("amount"));
		//ȡ������
		String payPassword = data.getString("payPassword");
		
		//���ٽ��
		accountService.withDraw(name, payPassword, amount);
		
		result = ResultCode.SUCCESS;
		System.out.println("���ѳɹ�");
		
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

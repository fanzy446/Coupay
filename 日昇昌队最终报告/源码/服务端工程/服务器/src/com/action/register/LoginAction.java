package com.action.register;

import java.sql.Timestamp;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.domain.Account;
import com.domain.LoginMessage;
import com.domain.Register;
import com.externalSystemInteface.IServerPushSystem;
import com.luo.pushUtil.PushMessageUtil;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.AccountService;
import com.service.interfaces.LoginMessageService;
import com.service.interfaces.RegisterService;
import com.util.OnlineListService;
import com.util.PushType;
import com.util.ResultCode;

public class LoginAction extends ActionSupport implements SessionAware,ApplicationAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3884570609133285075L;
	private JSONObject response = new JSONObject();
	private Map<String, Object> application;
	private Map<String, Object> session;
	private RegisterService registerService;
	private AccountService accountService;
	private LoginMessageService loginMessageService;
	private IServerPushSystem pushService;
		
	public String execute(){
		int result = 0;
		//ȡ������ 
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		//ȡ���û���������
		String name = data.getString("name");
		String password = data.getString("password");
		//У���˺�����
		int flag = registerService.login(name, password);
		if(flag==1){
			result = ResultCode.SUCCESS;
			System.out.println(name + " ��¼�ɹ�"); 
			//���˺��ѵ�¼�����ظ���¼
			//���ɹ���½,���˺Ų�����������
			
			//TODO
//			if(!OnlineListService.search(application, name)){
//				//���˺Ų������������
//				OnlineListService.add(application, name);
//			}else{
//				//���˺��ѵ�¼�����ظ���¼
//				System.out.println("�ظ���¼");
//				result = ResultCode.REPEAT_LOGIN;
//			}
		}
		if(flag==-1){
			result = ResultCode.REGISTER_NOT_EXISTS;
			System.out.println("�û�������");
		}
		if(flag==0){
			result = ResultCode.WRONG_PASSWORD;
			System.out.println("�������");
		}
		
		/**
		 * ��ӵ�¼��Ϣ
		 * ��Ҫ�Ĳ���
		 * 1.GPSλ��
		 * 2.��¼ʱ��
		 */
		//TODO
		LoginMessage message = new LoginMessage();
		message.setAddress("gz");
		Timestamp date = new Timestamp(System.currentTimeMillis());
		message.setDate(date);
		Register register = new Register();
		register.setId(registerService.getRegister(name).getId());
		message.setRegister(register);	
		loginMessageService.add(message);
		/**
		 * ������Ϣ
		 * 1.name
		 * 2.grade ����
		 * 3.balance ���
		 */	
		Account account = accountService.getAccount(name);
		int grade = account.getGrade();
		double balance = account.getBalance();
		
		data.clear();
		data.put("name", name);
		data.put("grade", grade);
		data.put("balance", balance);
		
		response.put("data", data);
		response.put("result", result);
		System.out.println("responsing");
		JSONObject jobj = new JSONObject();
//		jobj.put("pushType", PushType.MUTIPLE_PAY);
//		PushMessageUtil.pushBroadcastMessage("�û���¼");
		return "success";
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
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

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public LoginMessageService getLoginMessageService() {
		return loginMessageService;
	}

	public void setLoginMessageService(LoginMessageService loginMessageService) {
		this.loginMessageService = loginMessageService;
	}

	
}

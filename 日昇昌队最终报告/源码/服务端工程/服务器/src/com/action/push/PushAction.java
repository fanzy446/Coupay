package com.action.push;

import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.domain.Message;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.RegisterService;
import com.service.interfaces.TransferService;
import com.subsystem.serverpush.ServerPushSystem;
import com.util.PushType;
import com.util.ResultCode;

/**
 * 服务器推送的Action
 * @author Lee
 *
 */
public class PushAction extends ActionSupport implements SessionAware, ApplicationAware{

	private TransferService transferService;	
	private ServerPushSystem pushService;
	private RegisterService registerService;
	private JSONObject response = new JSONObject();
	private Map<String, Object> application;
	private Map<String, Object> session;
	
	public TransferService getTransferService() {
		return transferService;
	}

	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}

	public ServerPushSystem getPushService() {
		return pushService;
	}

	public void setPushService(ServerPushSystem pushService) {
		this.pushService = pushService;
	}

	public RegisterService getRegisterService() {
		return registerService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public JSONObject getResponse() {
		return response;
	}

	public void setResponse(JSONObject response) {
		this.response = response;
	}

	public Map<String, Object> getApplication() {
		return application;
	}

	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String testPay() {
		System.out.println("多人支付");
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		JSONArray payerlist = (JSONArray) data.get("payerlist"); 
		System.out.println("enter");
		for(int i=0; i<payerlist.size(); i++){
			System.out.println("for");
			JSONObject payer = payerlist.getJSONObject(i);
			System.out.println(payer);
			Message message = new Message();
			JSONObject content = new JSONObject();
			content.put("pushType", PushType.MUTIPLE_PAY);				
			content.put("data", data);
			content.put("date", System.currentTimeMillis());
			message.setContent(content.toString());
			System.out.println(payer.getString("payer"));
			message.setReceiver(registerService.getRegister(payer.getString("payer")));
			message.setStatus("unread");
			System.out.println("record");
			//TODO
			pushService.recordMessage("lgb", message);
		}
		response.put("result", ResultCode.SUCCESS);
		System.out.println("多人支付完毕！");
		return "success";
	}
}

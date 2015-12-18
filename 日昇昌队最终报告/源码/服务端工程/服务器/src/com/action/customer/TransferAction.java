package com.action.customer;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.domain.Message;
import com.externalSystemInteface.IServerPushSystem;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.dataObject.TransferData;
import com.service.interfaces.RegisterService;
import com.service.interfaces.TransferService;
import com.util.PushType;
import com.util.ResultCode;

public class TransferAction extends ActionSupport implements SessionAware, ApplicationAware,ServletContextAware{

	private static final long serialVersionUID = 1L;
	private TransferService transferService;	
	private RegisterService registerService;
	private JSONObject response = new JSONObject();
	private Map<String, Object> application;
	private Map<String, Object> session;
	private ServletContext servletContext;
	
    public void setServletContext(ServletContext context) {  
    	//context对象不是应用程序传进去的，而是由struts框架自动注入的  
        this.servletContext = context ;  
    }
	
	public void setTransferService(TransferService service) {
		this.transferService = service;
	}
	public IServerPushSystem getPushService() {
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		IServerPushSystem pushService = (IServerPushSystem) applicationContext.getBean("serverpushSystem");
		return pushService;
	}
	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}
	public Map<String, Object> getApplication() {
		return application;
	}
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
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
	
	/**
	 * 单人支付
	 * @return
	 */
	public String personalTransfer() {
		System.out.println("个人支付");
		JSONObject data = (JSONObject) session.get("data");
		String sender = data.getString("sender");
		String receiver = data.getString("receiver");
		String password = data.getString("password");
		double amount = data.getDouble("amount");
		String method = data.getString("method");
		TransferData transferData = new TransferData(sender,receiver,amount,password);
		if(method.equals("QRCode")){
			//若是正常的支付方式，要验证密码					
			transferData.setType("QRCode");			
		}
		if(method.equals("NFC")){
			//若是NFC支付方式，则不用验证密码
			transferData.setType("NFC");
		}
		
		int result = transferService.doTransfer(transferData);
		System.out.println(result);
		switch(result){
		case 0: result = ResultCode.WRONG_PASSWORD;break;
		case 1: result = ResultCode.NOT_ENOUGH_MONEY;break;
		case 2: result = ResultCode.SUCCESS;break;
		default: result = ResultCode.FAILURE;break;
		}
		response.put("result", result);
		System.out.println("支付完毕！");
		return "success";
	}
	
	/**
	 * 多人支付
	 * @return
	 */
	public String multiplePay() {
		try {
			System.out.println("多人支付");
			JSONObject data = (JSONObject) session.get("data");
			System.out.println(data);
			String sponsor = (String) session.get("name");
			JSONArray payerlist = (JSONArray) data.get("payerlist"); 
			for(int i=0; i<payerlist.size(); i++){
				JSONObject payer = payerlist.getJSONObject(i);
				System.out.println(payer);
				Message message = new Message();
				JSONObject content = new JSONObject();
				content.put("pushType", PushType.MUTIPLE_PAY);
				JSONObject data1 = new JSONObject();
				data1.put("money", payer.get("amount"));
				data1.put("sponsor", sponsor);
				data1.put("number", payerlist.size());
				data1.put("receiver", data.get("enterpriseName"));
				content.put("data", data1);
				content.put("date", System.currentTimeMillis());
				message.setContent(content.toString());
				message.setReceiver(registerService.getRegister(payer.getString("payer")));
				message.setStatus("unread");
				System.out.println("record");
				getPushService().recordMessage(data.getString("receiver"), message);
			}
			response.put("result", ResultCode.SUCCESS);
			System.out.println("多人支付完毕！");
			return "success";
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		} 
	}
}
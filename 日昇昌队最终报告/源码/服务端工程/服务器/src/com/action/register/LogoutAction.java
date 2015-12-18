package com.action.register;
import java.util.Map;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.util.OnlineListService;
import com.util.ResultCode;

@SuppressWarnings({ "serial" })
public class LogoutAction extends ActionSupport implements SessionAware,ApplicationAware{
	
	private JSONObject response = new JSONObject();
	private Map<String, Object> application;
	private Map<String, Object> session;
	public String execute(){
		int result = 0;
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		String name = data.getString("data");
		//从在线链表删除用户名
		OnlineListService.delete(application, name);
		result = ResultCode.SUCCESS;
		response.put("result", result);
		return SUCCESS;
	}
	public Map<String, Object> getApplication() {
		return application;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public JSONObject getResponse() {
		return response;
	}
	public void setResponse(JSONObject response) {
		this.response = response;
	}
	

}

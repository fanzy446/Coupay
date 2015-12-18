package com.action.register;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.domain.Register;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.CustomerService;
import com.service.interfaces.RegisterService;
import com.util.ResultCode;

public class SearchAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8625768321917904254L;
	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	private CustomerService customerService;
	
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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


//	public String searchCustomer() {
//		int result = 0;
//		JSONObject data = (JSONObject) session.get("data");
//		String partition = data.getString("name");
//		List<Customer> customers = customerService.searchRegister(partition);
//		if(registers.isEmpty()){
//			result = ResultCode.EMPTY;
//		}else{
//			result = ResultCode.SUCCESS;
//		}
//		List<String> names = new LinkedList<String>();
//		for(Register r: registers) {
//			names.add(r.getName());
//		}
//		data.clear();
//		data.put("nameList", names);
//		response.put("result", result);
//		response.put("data", data);
//		System.out.println("获取成功！");
//		return "success";
//	}
}

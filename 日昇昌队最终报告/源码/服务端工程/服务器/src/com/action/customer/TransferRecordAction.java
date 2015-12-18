package com.action.customer;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.SessionAware;

import com.domain.TransferRecord;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.RegisterService;
import com.service.interfaces.TransferService;
import com.util.ResultCode;

public class TransferRecordAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4203541073921986097L;
	private TransferService transferService;
	private RegisterService registerService;
	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	public JSONObject getResponse() {
		return response;
	}
	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
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
	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}
	
	public String execute() {
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		int flag = data.getInt("flag");
		if(flag == 0)
			return getSentTransferRecordByPage();
		else return getSentTransferRecordByOffset();
	}
	public String getSentTransferRecordByPage() {
		System.out.println("byPage");
		JSONObject data = (JSONObject) session.get("data");
		String name = (String) session.get("name");
		int curpage = 1;
		int pageSize = data.getInt("perPage");
		List<TransferRecord> records = transferService.getSentTransferRecord(name, curpage, pageSize);
		JSONArray recordList = new JSONArray();
		for(TransferRecord r: records) {
			JSONObject record = JSONObject.fromObject(r);
			record.put("date", r.getTradeDate().toString().substring(0, 10));
			record.put("receiverPic", r.getReceiver().getHead());
			System.out.println(record);
			recordList.add(record);
		}
		System.out.println(recordList);
		data.clear();
		System.out.println("record");
		data.put("tradeRecords", recordList);
		response.put("data", data);
		response.put("result", ResultCode.SUCCESS);
		System.out.println("查询成功!");
		return "success";
	}

	
	public String getSentTransferRecordByOffset() {
		System.out.println("offset");
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		String name = (String) session.get("name");
		String currecord = data.getString("recordId");
		int direction = data.getInt("flag");
		int number = 10;
		List<TransferRecord> records = transferService.getSentTransferRecord(name, currecord, direction, number);
		JSONArray recordList = new JSONArray();
		for(TransferRecord r: records) {
			JSONObject record = JSONObject.fromObject(r);
			record.put("date", r.getTradeDate().toString().substring(0, 10));
			record.put("receiverPic", r.getReceiver().getHead());
			System.out.println(record);
			recordList.add(record);
		}
		System.out.println(recordList.toString());
		response.put("result", ResultCode.SUCCESS);
		data.clear();
		data.put("tradeRecords",recordList);
		response.put("data", data);
		System.out.println("查询成功！");
		return "success";
	}
	
	public String getReceiveTransferRecordBypage() {
		return null;
	}
	
	public String getReceiveTransferRecordByOffset() {
		return null;
	}
}

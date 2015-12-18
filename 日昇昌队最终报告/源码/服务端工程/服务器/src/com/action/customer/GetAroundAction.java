package com.action.customer;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.domain.Seller;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.SellerService;
import com.util.DistanceUtil;
import com.util.ResultCode;

public class GetAroundAction extends ActionSupport implements SessionAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4620411510753101528L;
	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	private SellerService sellerService;
	public String execute(){
		int result = 0;
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data.toString());
		double lon = data.getDouble("longitude");
		double lat = data.getDouble("latitude");
		float accuracy = Float.parseFloat(data.getString("accuracy"));
		System.out.println("ready");
		List<Seller> s = sellerService.getSeller(lon, lat, accuracy, 1, 10);
		System.out.println("hello");
		if(s!=null){
			result = ResultCode.SUCCESS;
		}else{
			result = ResultCode.EMPTY;
		}
		JSONArray sellers = new JSONArray();
		for(Seller temp: s){
			JSONObject seller = new JSONObject();
			seller.put("head", temp.getHead());
			seller.put("name", temp.getName());
			seller.put("introduction", temp.getIntroduction());
			int distance = (int) DistanceUtil.GetDistance(lon, lat, temp.getLongitude(), temp.getLatitude());
			seller.put("distance", distance);
			sellers.add(seller);
			System.out.println(seller.toString());
		}
		data.clear();
		data.put("sellers", sellers);
		response.put("result", result);
		response.put("data", data);
		System.out.println("获取商家成功！");
		return "success";
	}
	public JSONObject getResponse() {
		return response;
	}
	public SellerService getSellerService() {
		return sellerService;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	
	public void setResponse(JSONObject response) {
		this.response = response;
	}
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}

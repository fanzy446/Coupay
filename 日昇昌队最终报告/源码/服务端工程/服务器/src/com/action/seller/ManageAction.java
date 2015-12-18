package com.action.seller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.domain.Seller;
import com.domain.VipCard;
import com.opensymphony.xwork2.ActionSupport;
import com.util.ResultCode;

public class ManageAction extends ActionSupport implements SessionAware{

	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
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
	 * 商家获取评论
	 * @return
	 */
	public String getComment(){
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		String name = data.getString("name");
		int page = data.getInt("page");
		int pageSize = data.getInt("pageSize");
//		List<Comment> commentList = SellerService.getComment(name, page, pageSize); 
		//TODO
		return null;
	}
	
	/**
	 * 商家设置VIP卡
	 * @return
	 */
	public String setVIP(){
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		String name = data.getString("name");
		Seller seller = new Seller();
		seller.setName(name);
		VipCard card = new VipCard();
		card.setSeller(seller);
//		SellerService.saveVipCard(card , name);
		response.put("result", ResultCode.SUCCESS);
		return "success";
	}
}

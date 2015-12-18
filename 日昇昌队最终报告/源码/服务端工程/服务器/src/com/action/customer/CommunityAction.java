package com.action.customer;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.domain.Customer;
import com.domain.Focus;
import com.domain.Register;
import com.domain.Request;
import com.domain.Seller;
import com.domain.Share;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.CircleService;
import com.service.interfaces.RegisterService;
import com.service.interfaces.SellerService;
import com.util.ResultCode;

public class CommunityAction extends ActionSupport implements SessionAware{
	
	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	private RegisterService registerService;
	private CircleService circleService;
//	private CustomerService customerService;
//	private SellerService sellerService;
	
//	public void setSellerService(SellerService sellerService) {
//		this.sellerService = sellerService;
//	}
//	public void setCustomerService(CustomerService customerService) {
//		this.customerService = customerService;
//	}
	public void setCircleService(CircleService circleService) {
		this.circleService = circleService;
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
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	/**
	 * 添加好友请求
	 * @return
	 */
	public String addFriendRequest(){
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		String name = (String) session.get("name");
		String friendName = data.getString("friend");
		Request request = new Request();
		request.setRequester(registerService.getRegister(name));
		request.setResponder(registerService.getRegister(friendName));
		request.setStatus("unanswered");
		Timestamp date = new Timestamp(System.currentTimeMillis()); 
		request.setDate(date);
		circleService.addRequest(request);
		response.put("result", ResultCode.SUCCESS);
		System.out.println("添加好友请求成功！");
		return "success";
	}
	
	/**
	 * 删除好友
	 * @return
	 */
	public String deleteFriend(){
		//取出数据
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
		String name = data.getString("name");
		String friendName = data.getString("friendName");
		//TODO
		return null;		
	}
	
	/**
	 * 关注商家
	 * @return
	 */
//	public String addFocus(){
//		//取出数据
//		JSONObject data = (JSONObject) session.get("data");
//		System.out.println(data);
//		String name = data.getString("name");
//		String enterpriseName = data.getString("enterpriseName");
//		Focus focus = new Focus();
//		focus.setCustomer(customerService.getCustomer(name));
//		focus.setSeller(sellerService.getSeller(enterpriseName));
//		
//		return null;
//		
//	}
	
	/**
	 * 取消关注商家
	 * @return
	 */
	public String deleteFocus(){
		//TODO
		return null;
		
	}
	
	
	/**
	 * 添加对商家的评论
	 */
	public String addShare() throws IOException{
		int result = 0;
		//取出数据
		System.out.println("enter");
		JSONObject data = (JSONObject) session.get("data");
		System.out.println(data);
//		String header = data.getString("header");
		String enterpriseName = data.getString("enterpriseName");
		String name = (String) session.get("name");
		int grade = data.getInt("grade");
		String content = data.getString("content");
		double money = data.getDouble("money");
		Timestamp date = new Timestamp(System.currentTimeMillis());
		System.out.println(date);
		byte[] photo = (byte[]) data.get("photo");
        //TODO
		//如何存文件？
		for(int index = 0; ;index++){
			System.out.println("enter");
			String path = "http://110.64.89.205:8080/ssh/"+name+index+".jpg";
			if(!circleService.checkPicturePath(path)){
				//不存在,则添加图片
//        			String newFilename = "D:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\ssh\\"+name+index+".jpg";
//            		FileImageOutputStream imgout = new FileImageOutputStream(new File(newFilename));
//        			imgout.write(photo,0,photo.length);
//        			imgout.close();
		        Share share = new Share();
		        share.setContent(content); 
				share.setDate(date);
				share.setGrade(grade);
//				share.setHeader(header);
				share.setMoney(money);
				share.setPicturePath(path);
				Seller seller = new Seller();
				seller.setName(enterpriseName);
				share.setSeller(seller);
				Customer sharer = new Customer();
				sharer.setName(name);
				share.setSharer(sharer);
				circleService.addShare(share);
				break;
			}        		
		}
		
		result = ResultCode.SUCCESS;
		response.put("result", result);
        return "success";
	}
	
	/**
	 * 获取好友分享
	 */
	public String getShare() {
		int result = 0;
		JSONObject data = new JSONObject();
		//取出名字
		System.out.println("hello");
		String name = (String) session.get("name");
		System.out.println(name);
		//分页获取好友分享
		//TODO
		List<Share> s = circleService.getTopicByPage(name, 1, 10);
		if(s!=null){
			result = ResultCode.SUCCESS;
		}else{
			System.out.println("empty");
			result = ResultCode.EMPTY;
		}
		JSONArray comment = new JSONArray();
		for(Share share: s){
			JSONObject temp = new JSONObject();
			temp.put("content", share.getContent());
			temp.put("time", share.getDate().toString());			
			temp.put("grade", share.getGrade());
			temp.put("head", share.getHeader());
			temp.put("money", share.getMoney());
			temp.put("photo", share.getPicturePath());
			temp.put("enterpriseName", registerService.getRegister(share.getSeller().getId()).getName());
			temp.put("name", registerService.getRegister(share.getSharer().getId()).getName());
			System.out.println(temp.toString());
			comment.add(temp);
		}
		data.clear();
		data.put("comment", comment);
		response.put("data", data);
		response.put("result", result);
		return "success";
	}
	
	/**
	 * 获取好友列表
	 * @return
	 */
	public String getFriend(){
		int result = 0;
		String name = (String) session.get("name");
		List<Register> fri = circleService.getFriends(name);
		JSONArray friends = new JSONArray();
		if(fri.isEmpty()){
			result = ResultCode.EMPTY;
		}else{
			result = ResultCode.SUCCESS;
			for(Register f: fri){
				JSONObject friend = new JSONObject();
				friend.put("path", f.getHead());
				friend.put("name", f.getName());
				System.out.println(friend.toString());
				friends.add(friend);
			}
		}
		JSONObject data = new JSONObject();
		System.out.println(friends.toString());
		data.put("friendList", friends);
		response.put("data", data);
		response.put("result", result);
		System.out.println("成功获取好友列表！");
		return "success";		
	}
}

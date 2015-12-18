package com.action.top;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.util.JsonData;
import com.util.OnlineListService;
import com.util.ServiceType;

@SuppressWarnings("serial")
public class TopAction extends ActionSupport implements SessionAware,ApplicationAware{
	
	private Map<String, Object> application;
	private Map<String, Object> session;
	private String json;	
	
	
	public String execute(){
		System.out.println(json);
		JsonData jsonData = (JsonData) JSONObject.toBean(JSONObject.fromObject(json), JsonData.class);
		if(jsonData==null)
			System.out.println("empty");
		//从接受的数据中拿出name,data,serviceType
		String name = jsonData.getName();
		System.out.println(name+" 用户登录！");
		JSONObject data = jsonData.getData();
		System.out.println(data);
		int serviceType = jsonData.getServiceType();
		System.out.println("服务类型 = "+serviceType);
		//data放入session里
		session.put("data", data);
		//name放入session里
		session.put("name", name);
		//从application里搜索在线链表，若没有则创建一个放进去
		if(!application.containsKey("onlineList")){
			OnlineListService.create(application);
			System.out.println("创建在线链表");
		}
		if(serviceType==ServiceType.LOGIN){
			//若为登录请求，马上跳到登录页面
			System.out.println("转到登录页面");
			return "login";
		}
		if(serviceType==ServiceType.REGIST){
			//若为注册请求，马上跳到注册页面
			System.out.println("转到注册页面");
			return "regist";
		}
		if(serviceType==ServiceType.CHECK_USER_EXISTENCE){
			//若为查询用户是否存在请求，则马上跳出
			System.out.println("查询用户是否存在");
			return "check_customer_exists";
		}
//		if(!OnlineListService.search(application,name)){
//			//若不存在与在线链表中，则跳到登录页面
//			System.out.println("转到登录页面");
//			return "login";
//		}
		//其他服务？？？
		//TODO
		switch(serviceType){
//		case -201: return "logout";//注销
//		case -216: return "get_user_info4customer";//获取用户信息
		case -238: return "query_pay_traderecord";//查找支付的交易记录
//		case -232: return "query_receive_traderecord";//查找收款的交易记录
//		case -217: return "modify_user_info4customer";//更改用户信息
//		case -208: return "query_vipCard";//查询VIPCARD
//		case -209: return "apply_for_vipCard";//申请VIPCARD
//		case -210: return "use_vipCard";//用会员卡支付
//		case -211: return "query_user_grade";//查询用户系统积分
		case -230: return "query_bankCard";//查询银行卡
		case -206: return "add_bankCard";//添加银行卡
//		case -207: return "delete_bankCard";//删除银行卡
		case -204: return "personal_pay";//单人支付
		case -205: return "multiple_pay";//多人支付
		case -219: return "add_friendRequest";//添加好友请求
//		case -220: return "delete_friend";//删除好友
//		case -221: return "focus_seller";//关注商家
//		case -222: return "cancel_focus_seller";//取消关注商家
		case -223: return "share_experience";//分享消费体验
		case -213: return "query_coupon";//查询优惠券
//		case -214: return "send_coupon";//赠送优惠券
//		case -233: return "add_comment";//添加评论
		case -224: return "get_share";//获取好友分享  
		case -229: return "get_surrounding_enterprise";//获取周边商家
//		case -234: return "get_comment4seller";//商家获取评论
//		case -235: return "get_share";//获取个人分享
//		case -236: return "set_VIP";//商家创建VIP卡
		case -231: return "get_friend";//获取好友列表
		case -233: return "search_customer";//查询用户
		case -218: return "query_bill";
//		TODO	
		
		}
		System.out.println("error");
		return ERROR;
	}


	public Map<String, Object> getApplication() {
		return application;
	}


	public String getJson() {
		return json;
	}


	public Map<String, Object> getSession() {
		return session;
	}


	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}


	public void setJson(String json) {
		this.json = json;
	}


	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}

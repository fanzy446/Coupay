package com.util;

/**
 * 
 * @author luo
 *
 */
public class ServiceType {
	public static int LOGIN = -200;		//登录
	public static int LOGOUT = -201;	//注销
	public static int REGIST = -202;	//注册
	public static int CHECK_USER_EXISTENCE = -203;		//查看用户是否存在
	public static int PERSONAL_PAY = -204;		//个人支付
	public static int MULTIPLE_PAY = -205;		
	public static int BIND_ACCOUNT = -206;
	public static int UNBIND_ACCOUNT = -207;
	public static int QUERY_VIP = -208;
	public static int APPLY_FOR_VIP = -209;
	public static int USE_VIP = -210;
	public static int QUERY_USER_GRADE = -211;
	public static int USER_GRADE_SWAP = -212;
	public static int QUERY_COUPON = -213;
	public static int SEND_COUPON = -214;
	public static int USE_COUPON = -215;
	public static int GET_USER_INFO = -216;
	public static int MODIFY_PERSONAL_DETAIL = -217;
	public static int QUERY_BILL = -218;
	public static int ADD_FRIEND = -219;
	public static int DELETE_FRIEND = -220;
	public static int ATTENT_ENTERPRISE = -221;
	public static int INATTENT_ENTERPRISE = -222;
	public static int SHARE_EXPERIENCE = -223;
	public static int GET_COMMENT4CUSTOMER = -224;		//获取好友分享 
	public static int REPLY_COMMENT = -225;
	public static int GET_LOCAL_PICTURE = -226;
	public static int GET_ADVERTISING = -227;
	public static int GET_ENTERPRISE_DETAIL = -228;
	public static int GET_SURROUNDIND_ENTERPRISE = -229;	//获取周边商家
	public static int QUERY_ACCOUNT = -230;
	public static int QUERY_FRIEND = -231;		//获取好友列表
	public static int QUERY_RECEIVE_TRADERECORD = -232;	//查询收款记录
	public static int QUERY_PAY_TRADERECORD = -238;		//查询支付记录
	public static int ADD_COMMENT = -241;
	public static int GET_COMMENT4SELLER = -234;
	public static int GET_SHARE = -235;		//获取分享
	public static int SAVE_VIP = -236;
	public static int GET_FRIEND = -237;	//获取好友列表
	public static int SEARCH_FRIEND = -233;
	public static int SEARCH_ENTERPRISE = -239;
	public static int QUERY_ENTERPRISE = -240;
	public static int QUERY_AVAILABLE_COUPON = -241;
}

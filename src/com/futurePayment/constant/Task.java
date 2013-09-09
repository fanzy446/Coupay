package com.futurePayment.constant;

import java.util.Map;

public class Task {
	private int taskId; // 任务编号
	private Map taskParam; // 任务参数
	// now there are total 45 tasks;
	public static final int TASK_USER_LOGIN = 1000;
	public static final int TASK_USER_LOGOUT = 1001;
	public static final int TASK_GET_USER_INFO = 1002;
	public static final int TASK_USER_REGIST = 1003;
	public static final int TASK_CHECK_NAME_AVAILABLE = 1004;

	public static final int TASK_SINGLE_USER_PAY = 1005;
	public static final int TASK_MULTI_USER_PAY = 1006;
	public static final int TASK_NFC_PAY=1007;
	public static final int TASK_TRANSFER_TO_USER=1008;
	
	public static final int TASK_GET_TRADING_REACORD = 1009;
	public static final int TASK_REFRESH_TRADING_REACORD = 1010;
	public static final int TASK_LOAD_TRADING_REACORD = 1011;
	public static final int TASK_GET_BALANCE = 1012;

	public static final int TASK_GET_BANK_CARD = 1013;
	public static final int TASK_ADD_BANK_CARD = 1014;
	public static final int TASK_DEL_BANK_CARD = 1015;

	public static final int TASK_GET_COUPON = 1016;
	public static final int TASK_PRESENT_COUPON = 1017;
	public static final int TASK_GET_COUPON_INFO = 1018;
	public static final int TASK_DEL_COUPON = 1019;
	public static final int TASK_EXCHANGE_COUPON = 1020;
	public static final int TASK_USE_COUPON = 1021;

	public static final int TASK_GET_VIP_CARDS = 1022;
	public static final int TASK_GET_VIP_CARD_INFO = 1023;
	public static final int TASK_APPLY_VIP_CARD = 1024;
	public static final int TASK_LOAN_VIP_CARD = 1025;
	public static final int TASK_DEL_VIP_CARD = 1026;
	public static final int TASK_USE_VIP_CARD = 1027;

	public static final int TASK_EXCHANGE_CREDIT = 1028;
	public static final int TASK_GET_EXCHANGE_LIST = 1029;

	public static final int TASK_MODIFY_PASSWORD = 1030;
	// security tasks have not been difine yet
	public static final int TASK_SHARE_MOMENT = 1031;
	public static final int TASK_GET_MOMENTS = 1032;
	public static final int TASK_GET_FRIENDS = 1033;
	public static final int TASK_ADD_FRIEND = 1034;
	public static final int TASK_DEL_FRIEND = 1035;
	public static final int TASK_SEARCH_FRIEND = 1035;
	public static final int TASK_FOLLOW_ENTERPRISE = 1037;
	public static final int TASK_UNFOLLOW_ENTERPRISE = 1038;
	public static final int TASK_GET_ENTERPRISES = 1039;
	public static final int TASK_SEARCH_ENTERPRISE = 1040;

	public static final int TASK_GET_PIC = 1041;
	public static final int TASK_GET_ADVERTISING = 1042;
	public static final int TASK_GET_ENTERPRISE_INFO = 1043;
	public static final int TASK_GET_AROUND_ENTERPRISES = 1044;

	// initial operation
	// 2 init mission
	public static final int INIT_SOCIETY = 2000;
	public static final int INIT_SURROUND = 2001;

	public Task(int id, Map param) {
		this.taskId = id;
		this.taskParam = param;
	}

	public Task(int id) {
		this.taskId = id;
		this.taskParam = null;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map getTaskParam() {
		return taskParam;
	}

	public void setTaskParam(Map taskParam) {
		this.taskParam = taskParam;
	}

}

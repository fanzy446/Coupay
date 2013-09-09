package com.billme.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidupush.Utils;
import com.billme.ui.*;
import com.billme.util.FileUtil;
import com.billme.util.LocationUtil;

import com.futurePayment.constant.Task;
import com.futurePayment.model.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@SuppressLint({ "UseValueOf", "HandlerLeak" })
public class MainService extends Service implements Runnable {

	/**
	 * @param args
	 */
	public static ArrayList<Activity> allActivities = new ArrayList<Activity>();
	public static int lastActivityId;
	public static ArrayList<Task> allTask = new ArrayList<Task>();
	public boolean isRun = true;
	private static FuturePayment futurePayment = FuturePayment.getInstance();
	private static ImageHelper imageHelper = ImageHelper.getInstance();
	private static int statusBarHeight = 0;
	private static int titleBarHeight = 0;

	public static void newTask(Task task) {
		Log.i("error", "add task");
		allTask.add(task);
	}

	private Handler handler = new Handler() {
		// 回调函数
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Task.TASK_USER_LOGIN: {
				// 登陆
				Log.i("error", "登陆回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("LoginActivity");
				// if (msg.obj instanceof PaymentException) {
				// PaymentException e = (PaymentException) msg.obj;
				// // TODO 返回失败信息
				// ba.refresh(new Integer(LoginActivity.LOGIN_FAILURE), e);
				// } else if (msg.obj instanceof BasicInformation) {
				// BasicInformation bi = (BasicInformation) msg.obj;
				// futurePayment.getUser().setName(bi.getName());
				// futurePayment.getUser().setBalance(bi.getBalance());
				// futurePayment.getUser().setGrade(bi.getGrade());
				ba.refresh(new Integer(LoginActivity.LOGIN_SECCUSS));
				// }
			}
				break;
			case Task.TASK_GET_USER_INFO: {
				Log.i("error", "获取用户信息回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("MainActivity");
				ba.refresh(new Integer(MainActivity.REFRESH_USERINFO), msg.obj);
				break;
			}
			case Task.TASK_CHECK_NAME_AVAILABLE: {
				Log.i("error", "测试用户名是否有效回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("RegistActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(RegistActivity.NAME_NOT_AVAILABLE),
							e);
				} else {
					ba.refresh(new Integer(RegistActivity.NAME_AVAILABLE), null);
				}
				break;
			}
			case Task.TASK_USER_REGIST: {
				Log.i("error", "用户注册回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("RegistActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(RegistActivity.REGIST_FAILURE), e);
				} else {
					ba.refresh(new Integer(RegistActivity.REGIST_SUCCESS), null);
				}
				break;
			}
			case Task.TASK_GET_BANK_CARD: {
				Log.i("error", "获取银行卡列表回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("BankCardActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(
							BankCardActivity.GET_BANK_CARD_FAILURE), e);
				} else if (msg.obj instanceof LinkedList<?>) {
					futurePayment.getUser().setBankCardList(
							(LinkedList<BankCard>) msg.obj);
					ba.refresh(new Integer(
							BankCardActivity.GET_BANK_CARD_SECCUSS));
				}
				break;
			}
			case Task.TASK_ADD_BANK_CARD: {
				Log.i("error", "添加银行卡回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("BankCardActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(
							BankCardActivity.ADD_BANK_CARD_FAILURE), e);
				} else if (msg.obj instanceof BankCard) {
					ba.refresh(new Integer(
							BankCardActivity.ADD_BANK_CARD_SUCCESS), msg.obj);
				}
				break;
			}
			case Task.TASK_GET_FRIENDS: {
				Log.i("error", "查询好友列表回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("RelationActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(
							new Integer(RelationActivity.GET_FRIEND_FAILURE), e);
				} else if (msg.obj instanceof LinkedList<?>) {

					futurePayment.getUser().setFriendList(
							(LinkedList<Friend>) msg.obj);
					ba.refresh(new Integer(RelationActivity.GET_FRIEND_SUCCESS));
				}
				break;
			}
			case Task.TASK_SINGLE_USER_PAY: {
				Log.i("error", "单人支付回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("PaymentActivity");
				if (msg.obj instanceof PaymentException || (Boolean) msg.obj) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(PaymentActivity.PAY_FAILURE), e);
				} else {
					ba.refresh(new Integer(PaymentActivity.PAY_SUCCESS), null);
				}
				break;
			}
			case Task.TASK_MULTI_USER_PAY: {
				Log.i("error", "多人支付回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("PaymentConfirmActivity");
				if (msg.obj instanceof PaymentException || (Boolean) msg.obj) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(
							new Integer(PaymentConfirmActivity.APPLY_FAILURE),
							e);
				} else {
					ba.refresh(
							new Integer(PaymentConfirmActivity.APPLY_SUCCESS),
							null);
				}
				break;
			}
			case Task.TASK_SHARE_MOMENT: {
				Log.i("error", "分享消费体验回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("ShareActivity");
				if (msg.obj instanceof PaymentException || (Boolean) msg.obj) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(ShareActivity.SHARE_FAILURE), e);
				} else {
					ba.refresh(new Integer(ShareActivity.SHARE_SECCUSS), null);
				}
				break;
			}
			case Task.TASK_GET_MOMENTS: {
				Log.i("error", "获得消费体验回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SocietyActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(SocietyActivity.GET_FAILURE), e);
				} else {
					ba.refresh(new Integer(SocietyActivity.GET_SECCUSS),
							msg.obj);

				}
				break;
			}
			case Task.TASK_GET_AROUND_ENTERPRISES: {
				Log.i("error", "获取周边商家信息回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SurroundActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(SurroundActivity.GET_FAILURE), e);
				} else {
					ba.refresh(new Integer(SurroundActivity.GET_SECCUSS),
							msg.obj);
				}
				break;
			}
			case Task.INIT_SOCIETY: {
				Log.i("error", "初始化社区界面回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SocietyActivity");
				if (msg.obj != null)
					ba.refresh(new Integer(SocietyActivity.INITIAL_SECCUSS),
							(LinkedList<CommentInfo>) msg.obj);
				else
					ba.refresh(new Integer(SocietyActivity.INITIAL_FAILURE),
							(LinkedList<CommentInfo>) msg.obj);
				break;
			}
			case Task.INIT_SURROUND: {
				Log.i("error", "初始化周边界面回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SurroundActivity");
				if (msg.obj != null)
					ba.refresh(new Integer(SurroundActivity.INITIAL_SECCUSS),
							(ArrayList<EnterpriseBasicInfo>) msg.obj);
				else
					ba.refresh(new Integer(SurroundActivity.INITIAL_FAILURE),
							(ArrayList<EnterpriseBasicInfo>) msg.obj);
				break;
			}
			case Task.TASK_GET_COUPON: {
				Log.i("error", "获得优惠券列表回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("CouponActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(CouponActivity.GET_COUPON_FAILURE),
							e);
				} else if (msg.obj instanceof LinkedList<?>) {

					ba.refresh(new Integer(CouponActivity.GET_COUPON_SECCUSS),
							msg.obj);
				}
				break;
			}
			case Task.TASK_GET_TRADING_REACORD: {
				Log.i("error", "获取账单信息回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("TradeRecordActivity");
				if (msg.obj instanceof PaymentException || msg.obj == null)
					ba.refresh(TradeRecordActivity.GETRECORD_FAILED);
				else
					ba.refresh(TradeRecordActivity.INITRECORD_SECCUSS, msg.obj);
			}
				break;
			case Task.TASK_REFRESH_TRADING_REACORD: {
				Log.i("error", "下拉刷新账单信息");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("TradeRecordActivity");
				if (msg.obj instanceof PaymentException || msg.obj == null)
					ba.refresh(TradeRecordActivity.GETRECORD_FAILED);
				else
					ba.refresh(TradeRecordActivity.REFRESHRECORD_SECCUSS,
							msg.obj);
			}
				break;
			case Task.TASK_LOAD_TRADING_REACORD: {
				Log.i("error", "上拉加载账单信息");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("TradeRecordActivity");
				if (msg.obj instanceof PaymentException || msg.obj == null)
					ba.refresh(TradeRecordActivity.GETRECORD_FAILED);
				else
					ba.refresh(TradeRecordActivity.LOADRECORD_SECCUSS, msg.obj);
			}
				break;
			case Task.TASK_GET_ENTERPRISES: {
				Log.i("error", "得到关注商家列表回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("RelationActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(
							RelationActivity.GET_ENTERPRISE_FAILURE), e);
				} else if (msg.obj instanceof LinkedList<?>) {
					futurePayment.getUser().setConcernList(
							(LinkedList<EnterpriseBasicInfo>) msg.obj);
					ba.refresh(new Integer(
							RelationActivity.GET_ENTERPRISE_SUCCESS));
				}
				break;
			}
			case Task.TASK_SEARCH_ENTERPRISE: {
				Log.i("error", "搜索商家回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SearchEnterpriseActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(
							new Integer(
									SearchEnterpriseActivity.SEARCH_ENTERPRISE_FAILURE),
							e);
				} else if (msg.obj instanceof LinkedList<?>) {
					ba.refresh(
							new Integer(
									SearchEnterpriseActivity.SEARCH_ENTERPRISE_SUCCESS),
							msg.obj);
				}
				break;
			}
			case Task.TASK_SEARCH_FRIEND: {
				Log.i("error", "搜索好友回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SearchFriendActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(
							SearchFriendActivity.SEARCH_FRIEND_FAILURE), e);
				} else if (msg.obj instanceof LinkedList<?>) {
					ba.refresh(new Integer(
							SearchFriendActivity.SEARCH_FRIEND_SUCCESS),
							msg.obj);
				}
				break;
			}
			case Task.TASK_FOLLOW_ENTERPRISE: {
				Log.i("error", "关注商家回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SearchEnterpriseActivity");
				if (msg.obj instanceof PaymentException) {
					// TODO 返回失败信息
					ba.refresh(
							new Integer(
									SearchEnterpriseActivity.ATTENT_ENTERPRISE_FAILURE),
							msg.obj);
				} else {
					ba.refresh(new Integer(
							SearchEnterpriseActivity.ATTENT_ENTERPRISE_SUCCESS));
				}
				break;
			}
			case Task.TASK_ADD_FRIEND: {
				Log.i("error", "添加好友回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("SearchFriendActivity");
				if (msg.obj instanceof PaymentException) {
					PaymentException e = (PaymentException) msg.obj;
					// TODO 返回失败信息
					ba.refresh(new Integer(
							SearchFriendActivity.ADD_FRIEND_FAILURE), e);
				} else {
					ba.refresh(new Integer(
							SearchFriendActivity.ADD_FRIEND_SUCCESS));
				}
			}
				break;
			case Task.TASK_NFC_PAY: {
				Log.i("error", "NFC 支付回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("PaymentActivity");
				// if (msg.obj instanceof PaymentException ||(Boolean)msg.obj) {
				// PaymentException e = (PaymentException) msg.obj;
				// // TODO 返回失败信息
				// ba.refresh(new Integer(NFCPaymentActivity.NFCPAY_FAILURE),
				// e);
				// } else {
				ba.refresh(new Integer(NFCPaymentActivity.NFCPAY_SUCCESS));
				// }
			}
				break;
			case Task.TASK_TRANSFER_TO_USER: {
				Log.i("error", "转账回调中");
				BillMeActivity ba = (BillMeActivity) MainService
						.getActivityByName("PaymentActivity");
				// if (msg.obj instanceof PaymentException ||(Boolean)msg.obj) {
				// PaymentException e = (PaymentException) msg.obj;
				// // TODO 返回失败信息
				// ba.refresh(new
				// Integer(NFCTransferActivity.NFCTRANSFER_FAILURE), e);
				// } else {
				ba.refresh(new Integer(NFCTransferActivity.NFCTRANSFER_SUCCESS));
				// }
			}
			default:
				break;
			}

		}
	};

	public void doTask(Task task) {
		Message msg = handler.obtainMessage();
		msg.what = task.getTaskId();
		try {
			switch (task.getTaskId()) {
			case Task.TASK_USER_LOGIN: {
				Log.i("error", "登陆中");
				String userName = (String) task.getTaskParam().get("userName");
				String userPassword = (String) task.getTaskParam().get(
						"userPassword");
				futurePayment = FuturePayment.getInstance(userName);
				try {
					msg.obj = futurePayment.loginUser(userPassword);
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_GET_USER_INFO: {
				Log.i("error", "获取用户信息");

				msg.obj = task.getTaskParam();
				break;
			}
			case Task.TASK_CHECK_NAME_AVAILABLE: {
				Log.i("error", "测试用户名是否有效");
				try {
					futurePayment.checkUserExistence((String) task
							.getTaskParam().get("name"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_USER_REGIST: {
				Log.i("error", "用户注册");
				RegistInformation ri = new RegistInformation();
				try {
					ri.setName((String) task.getTaskParam().get("name"));
					ri.setLoginPassword((String) task.getTaskParam().get(
							"loginPassword"));
					ri.setPayPassword((String) task.getTaskParam().get(
							"payPassword"));
					ri.setRealName((String) task.getTaskParam().get("realName"));
					ri.setBirthday((Date) task.getTaskParam().get("birthday"));
					ri.setSex((String) task.getTaskParam().get("sex"));
					ri.appendContact("phone",
							(String) task.getTaskParam().get("phone"));
					ri.appendContact("email",
							(String) task.getTaskParam().get("email"));
					futurePayment.regist(ri);
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_GET_BANK_CARD: {
				Log.i("error", "获取银行卡列表");
				try {
					msg.obj = futurePayment.queryAccount();
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_ADD_BANK_CARD: {
				Log.i("error", "添加银行卡");
				try {
					BankCard bc = new BankCard();
					String cardNumber = (String) task.getTaskParam().get(
							"cardNumber");
					String bank = futurePayment.bindAccount(cardNumber,
							(String) task.getTaskParam().get("password"));
					bc.setBankName(bank);
					bc.setCardNumber(cardNumber);
					msg.obj = bc;
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_GET_FRIENDS: {
				Log.i("error", "获取好友列表");
				try {
					msg.obj = futurePayment.queryFriend();
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_SINGLE_USER_PAY: {
				Log.i("error", "个人支付");
				try {
					Transfer t = new Transfer();
					t.setSender(getUser().getName());
					t.setReceiver((String) task.getTaskParam().get("receiver"));
					t.setAmount((Double) task.getTaskParam().get("money"));
					t.setMethod((String) task.getTaskParam().get("method"));
					msg.obj = futurePayment.personalPay(t, (String) task
							.getTaskParam().get("password"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_MULTI_USER_PAY: {
				Log.i("error", "多人支付");
				try {
					ArrayList<HashMap<String, Object>> paramList = (ArrayList<HashMap<String, Object>>) task
							.getTaskParam().get("sender");
					ArrayList<HashMap<String, Object>> tempList = new ArrayList<HashMap<String, Object>>();
					for (int i = 0; i < paramList.size(); i++) {
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("payer", paramList.get(i).get("name"));
						map.put("amount", paramList.get(i).get("money"));
						tempList.add(map);
					}
					msg.obj = futurePayment.multiplePay(tempList);
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_SHARE_MOMENT: {
				Log.i("error", "分享消费体验");
				try {
					ShareInfo si = new ShareInfo();
					si.setEnterpriseName((String) task.getTaskParam().get(
							"receiver"));
					si.setGrade((Integer) task.getTaskParam().get("grade"));
					si.setContent((String) task.getTaskParam().get("content"));
					si.setMoney((Double) task.getTaskParam().get("money"));
					si.setPhoto((byte[]) task.getTaskParam().get("photo"));
					msg.obj = futurePayment.shareExperience(si);
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_GET_MOMENTS: {
				Log.i("error", "获得消费体验");
				try {
					msg.obj = futurePayment.getExperience();
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_GET_AROUND_ENTERPRISES: {
				Log.i("error", "获取周边商家信息");
				try {
					msg.obj = futurePayment
							.getSurroundingEnterprise((Location) task
									.getTaskParam().get("location"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.INIT_SOCIETY: {
				Log.i("error", "初始化社区界面");
				FileUtil fileUtil = new FileUtil(MainService.getUser()
						.getName());
				JSONArray ja = new JSONArray(fileUtil.readFromSD("society"));
				Gson gson = new Gson();
				LinkedList<CommentInfo> al = gson.fromJson(ja.toString(),
						new TypeToken<LinkedList<CommentInfo>>() {
						}.getType());
				msg.obj = al;
				break;
			}
			case Task.INIT_SURROUND: {
				Log.i("error", "初始化周边界面");
				FileUtil fileUtil = new FileUtil(MainService.getUser()
						.getName());
				JSONArray ja = new JSONArray(fileUtil.readFromSD("surround"));
				Gson gson = new Gson();
				LinkedList<EnterpriseBasicInfo> al = gson.fromJson(
						ja.toString(),
						new TypeToken<LinkedList<EnterpriseBasicInfo>>() {
						}.getType());
				msg.obj = al;
				break;
			}
			case Task.TASK_GET_COUPON: {
				Log.i("error", "获取优惠券列表");
				try {
					msg.obj = futurePayment.queryCoupon();
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_GET_TRADING_REACORD: {
				Log.i("error", "获取账单信息");
				try {
					ArrayList<TradeRecord> tempTr;
					tempTr = MainService.getUser().getTradeRecordList();
					if (tempTr == null) {
						tempTr = futurePayment.getBill(1, 10, null);
						MainService.getUser().setTradeRecordList(tempTr);
					}
					msg.obj = tempTr;
				} catch (Exception e) {
					msg.obj = e;
				}
			}
				break;
			case Task.TASK_REFRESH_TRADING_REACORD: {
				Log.i("error", "下拉刷新账单信息");
				try {
					ArrayList<TradeRecord> tempTr;
					String id = MainService.getUser().getTradeRecordList()
							.get(0).getId();
					tempTr = futurePayment.refreshBill(id);
					MainService.getUser().addTradeRecordListAtBegin(tempTr);
					msg.obj = tempTr;
				} catch (PaymentException e) {
					msg.obj = e;
				}
			}
				break;
			case Task.TASK_LOAD_TRADING_REACORD: {
				Log.i("error", "上拉加载账单信息");
				try {
					ArrayList<TradeRecord> tempTr;
					String id = MainService
							.getUser()
							.getTradeRecordList()
							.get(MainService.getUser().getTradeRecordList()
									.size()).getId();
					tempTr = futurePayment.loadBill(id);
					MainService.getUser().addTradeRecordListAtEnd(tempTr);
					msg.obj = tempTr;
				} catch (PaymentException e) {
					msg.obj = e;
				}
			}
				break;
			case Task.TASK_GET_ENTERPRISES: {
				Log.i("error", "获取关注的商家列表");
				try {
					msg.obj = futurePayment.queryEnterprise();
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_SEARCH_ENTERPRISE: {
				Log.i("error", "搜索商家");
				try {
					msg.obj = futurePayment.searchEnterprise((String) task
							.getTaskParam().get("name"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_SEARCH_FRIEND: {
				Log.i("error", "搜索好友");
				try {
					msg.obj = futurePayment.searchFriend((String) task
							.getTaskParam().get("name"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_FOLLOW_ENTERPRISE: {
				Log.i("error", "关注商家");
				try {
					futurePayment.attentEnterprise((String) task.getTaskParam()
							.get("name"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_ADD_FRIEND: {
				Log.i("error", "添加好友");
				try {
					futurePayment.addFriend((String) task.getTaskParam().get(
							"name"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
				break;
			}
			case Task.TASK_NFC_PAY: {
				Log.i("error", "NFC 支付");
				try {
					Transfer t = new Transfer();
					t.setSender(getUser().getName());
					t.setReceiver((String) task.getTaskParam().get("receiver"));
					t.setAmount((Double) task.getTaskParam().get("money"));
					t.setMethod((String) task.getTaskParam().get("method"));
					msg.obj = futurePayment.personalPay(t);
				} catch (PaymentException e) {
					msg.obj = e;
				}
			}
				break;
			case Task.TASK_TRANSFER_TO_USER: {
				Log.i("error", "用户转账");
				try {
					Transfer t = new Transfer();
					t.setSender((String) task.getTaskParam().get("sender"));
					t.setReceiver(getUser().getName());
					t.setAmount((Double) task.getTaskParam().get("money"));
					t.setMethod((String) task.getTaskParam().get("method"));
					if ((Double) task.getTaskParam().get("money") <= getUser()
							.getMaxtransfer())
						msg.obj = futurePayment.personalPay(t);
					else
						msg.obj = futurePayment.personalPay(t, (String) task
								.getTaskParam().get("password"));
				} catch (PaymentException e) {
					msg.obj = e;
				}
			}
				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		handler.sendMessage(msg);
		allTask.remove(task);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Log.i("error", " 服务初始化中");
		super.onCreate();
		this.isRun = true;
		Thread t = new Thread(this);
		t.start();
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY, Utils.API_KEY);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.i("error", "服务启动中");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		this.isRun = false;
	}

	public static void exitApp(Context context) {
		for (Activity ac : allActivities) {
			ac.finish();
		}
		Intent it = new Intent("com.billme.logic.MainService");
		context.stopService(it);
	}

	public static FuturePayment getFuturePayment() {
		return futurePayment;
	}

	public static User getUser() {
		return futurePayment.getUser();
	}

	public static Activity getActivityByName(String name) {
		for (Activity ac : allActivities) {
			if (ac.getClass().getName().indexOf(name) >= 0) {
				return ac;
			}
		}
		return null;
	}

	public static void AlertNetError(final Context con) {
		AlertDialog.Builder ab = new AlertDialog.Builder(con);
		ab.setTitle(R.string.Error);
		ab.setMessage(R.string.NoSignalException);
		ab.setNegativeButton(R.string.btn_exit, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				MainService.exitApp(con);
			}
		});

		ab.setPositiveButton(R.string.btn_setnet, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				con.startActivity(new Intent(
						android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			}
		});
		ab.create().show();
	}

	@Override
	public void run() {
		while (isRun) {
			Task lastTask = null;
			synchronized (allTask) {
				if (allTask.size() > 0) {
					lastTask = allTask.get(0); // 取任务
					doTask(lastTask); // 执行任务
				}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ImageHelper getImageHelper() {
		return imageHelper;
	}

	public static int getStatusBarHeight() {
		return statusBarHeight;
	}

	public static void setStatusBarHeight(int statusBarHeight) {
		MainService.statusBarHeight = statusBarHeight;
	}

	public static int getTitleBarHeight() {
		return titleBarHeight;
	}

	public static void setTitleBarHeight(int titleBarHeight) {
		MainService.titleBarHeight = titleBarHeight;
	}
}

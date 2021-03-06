package com.futurePayment.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import android.location.Location;

import com.billme.util.FileUtil;

/**
 * 
 * @author luo
 * 
 */
public class FuturePayment {
	private static FuturePayment instance = null;
	private User user = new User();
	private FuturePaymentSupport supporter;

	/**
	 * 
	 * @param userId
	 *            用户名
	 * @param password
	 *            登陆密码 构造函数
	 */
	private FuturePayment(String name) {
		user.setName(name);
		new FileUtil(name);
		supporter = new FuturePaymentSupport(name);
	}

	public static FuturePayment getInstance() {
		if (instance != null)
			return instance;
		return new FuturePayment(null);
	}

	public static FuturePayment getInstance(String name) {
		instance = new FuturePayment(name);
		return instance;
	}

	/**
	 * 用户登陆
	 */
	public BasicInformation loginUser(String password) throws PaymentException {
		try {
			return supporter.loginUser(password);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 用户登出
	 * 
	 */
	public void logoutUser() {
		supporter.logoutUser();
	}

	/**
	 * 得到用户信息
	 * 
	 * @return JSONObject
	 * 
	 */
	public JSONObject getUserInfo() throws PaymentException {
		try {
			return supporter.getUserInfo();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 个人支付
	 * 
	 * @param transfer
	 *            transfer to be handled.
	 * @param password
	 *            the payment password of the user
	 * 
	 */
	public boolean personalPay(Transfer transfer, String password)
			throws PaymentException {
		try {
			return supporter.personalPay(transfer, password);
		} catch (PaymentException e) {
			throw e;
		}
	}

	public boolean personalPay(Transfer transfer) throws PaymentException {
		try {
			return supporter.personalPay(transfer);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 分页获取交易记录
	 * 
	 * @param page
	 *            page number
	 * @param perPage
	 *            number of records per page
	 * @param condition
	 *            the search condition
	 * @return list of records
	 * 
	 */
	public ArrayList<TradeRecord> getBill(int page, int perpage,
			HashMap<String, Object> condition) throws PaymentException {
		try {
			return supporter.getBill(page, perpage, condition);
		} catch (PaymentException e) {
			throw e;
		}
	}

	public ArrayList<TradeRecord> refreshBill(String id)
			throws PaymentException {
		try {
			return supporter.getBill(id, 1);
		} catch (PaymentException e) {
			throw e;
		}
	}

	public ArrayList<TradeRecord> loadBill(String id) throws PaymentException {
		try {
			return supporter.getBill(id, -1);
		} catch (PaymentException e) {
			throw e;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 注册
	 * 
	 * @param name
	 *            姓名
	 * @param password
	 *            密码
	 * @return result of regist 注册结果
	 * 
	 */
	public boolean regist(RegistInformation ri) throws PaymentException {
		try {
			return supporter.regist(ri);
		} catch (PaymentException e) {

			throw e;
		}
	}

	/**
	 * 检查用户是否存在
	 * 
	 * @return 检查结果
	 * @throws PaymentException
	 */
	public boolean checkUserExistence(String name) throws PaymentException {
		try {
			return supporter.checkUserExistence(name);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 多人支付
	 * 
	 * @param payerlist
	 *            付款者名单
	 * @return 支付结果
	 * @throws PaymentException
	 */
	public boolean multiplePay(ArrayList<HashMap<String, Object>> payerlist)
			throws PaymentException {
		try {
			return supporter.multiplePay(payerlist);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 查询所有银行卡
	 * 
	 * @return
	 * @return 银行帐号信息
	 * @throws PaymentException
	 */
	public LinkedList<BankCard> queryAccount() throws PaymentException {
		try {
			return supporter.queryAccount();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 银行卡绑定
	 * 
	 * @param cardNumber
	 *            银行卡
	 * @param password
	 *            密码
	 * @return 绑定结果
	 * @throws PaymentException
	 */
	public String bindAccount(String cardNumber, String password)
			throws PaymentException {
		try {
			return supporter.bindAccount(cardNumber, password);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 解除银行卡绑定
	 * 
	 * @param bank
	 *            银行
	 * @param cardNumber
	 *            银行卡号码
	 * @return 解除绑定结果
	 * @throws PaymentException
	 */
	public boolean unbindAccount(String bank, String cardNumber)
			throws PaymentException {
		try {
			return supporter.unbindAccount(bank, cardNumber);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 查询会员 return 会员列表
	 **/
	public ArrayList<VipCard> queryVip() throws PaymentException {
		try {
			return supporter.queryVip();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 申请会员
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @return 申请结果
	 * @throws PaymentException
	 */
	public boolean applyForVip(String enterpriseId) throws PaymentException {
		try {
			return supporter.applyForVip(enterpriseId);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 使用会员卡
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @param amount
	 *            消费金额
	 * @return
	 * @throws PaymentException
	 */
	public boolean useVip(String enterpriseId, double amount)
			throws PaymentException {
		try {
			return supporter.useVip(enterpriseId, amount);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 查询账号积分
	 * 
	 * @return 查询结果
	 * @throws PaymentException
	 */
	public int queryUserGrade() throws PaymentException {
		try {
			return supporter.queryUserGrade();
		} catch (PaymentException e) {
			throw e;
		}
	}

	public LinkedList<Coupon> getSwapList() throws PaymentException {
		try {
			return supporter.getSwapList();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 账号积分兑换
	 * 
	 * @param grade
	 *            使用积分
	 * @param couponId
	 *            优惠券id
	 * @return 兑换结果
	 * @throws PaymentException
	 */
	public boolean userGradeSwap(int grade, int couponId)
			throws PaymentException {
		try {
			return supporter.userGradeSwap(grade, couponId);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 查询优惠券
	 * 
	 * @return 查询结果
	 * @throws PaymentException
	 */
	public LinkedList<Coupon> queryCoupon() throws PaymentException {
		try {
			return supporter.queryCoupon();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 查询本次消费能够使用的优惠券
	 * 
	 * @param name
	 *            商家名
	 * @param money
	 *            此次消费金额
	 * @return 优惠券列表
	 * @throws PaymentException
	 */
	public LinkedList<Coupon> queryAvailableCoupon(String name, Double money)
			throws PaymentException {
		try {
			return supporter.queryAvailableCoupon(name, money);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 赠送优惠券
	 * 
	 * @param receiver
	 *            接受者
	 * @param couponId
	 *            优惠券id
	 * @param amount
	 *            数量
	 * @return 赠送结果
	 * @throws PaymentException
	 */
	public boolean sendCoupon(String receiver, String couponId, int amount)
			throws PaymentException {
		try {
			return supporter.sendCoupon(receiver, couponId, amount);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 使用优惠券
	 * 
	 * @param coupun
	 *            优惠券
	 * @return 使用结果
	 * @throws PaymentException
	 */
	public boolean useCoupon(ArrayList<Coupon> coupon) throws PaymentException {
		try {
			return supporter.useCoupon(coupon);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 修改个人信息
	 * 
	 * @param name
	 *            姓名
	 * @param sex
	 *            性别
	 * @param birthday
	 *            生日
	 * @param phone
	 *            电话
	 * @param email
	 *            邮箱
	 * @return 修改结果
	 * @throws PaymentException
	 */
	public boolean modifyPersonalDetail(String name, String sex, Date birthday,
			String phone, String email) throws PaymentException {
		try {
			return supporter.modifyPersonalDetail(name, sex, birthday, phone,
					email);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 查询账单
	 * 
	 * @param page
	 *            第几页
	 * @param perpage
	 *            每页几项
	 * @param condition
	 *            条件
	 * @return 查询结果
	 * @throws PaymentException
	 */
	public boolean queryBill(int page, int perpage, JSONObject condition)
			throws PaymentException {
		try {
			return supporter.queryBill(page, perpage, condition);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 查询好友列表
	 * 
	 * @return 好友列表
	 * @throws PaymentException
	 */
	public LinkedList<Friend> queryFriend() throws PaymentException {
		try {
			LinkedList<Friend> friend = supporter.queryFriend();
			// for (int i = 0; i < friend.size(); i++) {
			// fileUtil.modelToAddress(friend.get(i));
			// }
			return friend;
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 以前匹配方式检索商家
	 * 
	 * @param name
	 *            检索字段
	 * @return 搜索结果
	 * @throws PaymentException
	 */
	public LinkedList<Friend> searchFriend(String name) throws PaymentException {
		try {
			return supporter.searchFriend(name);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 添加好友
	 * 
	 * @param friendId
	 *            好友id
	 * @return 添加结果
	 * @throws PaymentException
	 */
	public boolean addFriend(String name) throws PaymentException {
		try {
			return supporter.addFriend(name);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 删除好友
	 * 
	 * @param friendId
	 *            好友ID
	 * @return 删除结果
	 * @throws PaymentException
	 */
	public boolean deleteFriend(String friendId) throws PaymentException {
		try {
			return supporter.deleteFriend(friendId);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 得到关注的商家列表
	 * 
	 * @return 商家基本列表
	 * @throws PaymentException
	 */
	public LinkedList<EnterpriseBasicInfo> queryEnterprise()
			throws PaymentException {
		try {
			return supporter.queryEnterprise();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 以前匹配方式检索商家
	 * 
	 * @param name
	 *            检索字段
	 * @return 搜索结果
	 * @throws PaymentException
	 */
	public LinkedList<EnterpriseBasicInfo> searchEnterprise(String name)
			throws PaymentException {
		try {
			return supporter.searchEnterprise(name);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 关注商家
	 * 
	 * @param name
	 *            商家id
	 * @return 关注结果
	 * @throws PaymentException
	 */
	public boolean attentEnterprise(String name) throws PaymentException {
		try {
			return supporter.attentEnterprise(name);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 取消关注商家
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @return 结果
	 * @throws PaymentException
	 */
	public boolean inattentEnterprise(String enterpriseId)
			throws PaymentException {
		try {
			return supporter.inattentEnterprise(enterpriseId);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 分享消费体验
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @param grade
	 *            评分
	 * @param content
	 *            内容
	 * @param time
	 *            时间
	 * @return 分享结果
	 * @throws PaymentException
	 */
	public boolean shareExperience(ShareInfo si) throws PaymentException {
		try {
			return supporter.shareExperience(si);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 获得消费体验
	 * 
	 * @return 消费体验列表
	 * @throws PaymentException
	 */
	public List<CommentInfo> getExperience() throws PaymentException {
		try {
			return supporter.getExperience();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 获得位置图片
	 * 
	 * @return 位置图片
	 * @throws PaymentException
	 */
	public JSONObject getLocalPicture() throws PaymentException {
		try {
			return supporter.getLocalPicture();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 获得广告
	 * 
	 * @return 广告
	 * @throws PaymentException
	 */
	public JSONObject getAdvertising() throws PaymentException {
		try {
			return supporter.getAdvertising();
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 获得商家详细信息
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @return 商家详细信息
	 * @throws PaymentException
	 */
	public JSONObject getEnterpriseDetail(String enterpriseId)
			throws PaymentException {
		try {
			return supporter.getEnterpriseDetail(enterpriseId);
		} catch (PaymentException e) {
			throw e;
		}
	}

	/**
	 * 获得周边商家
	 * 
	 * @return 周边商家基本信息
	 * @throws PaymentException
	 */
	public LinkedList<EnterpriseBasicInfo> getSurroundingEnterprise(
			Location location) throws PaymentException {
		try {
			return supporter.getSurroundingEnterprise(location);
		} catch (PaymentException e) {
			throw e;
		}
	}

	// /**
	// * 检测文件是否存在
	// * @param path 目录路径
	// * @param name 文件名
	// * @return 文件是否存在
	// */
	// public boolean isFileExist(String path, String name)
	// {
	// FileUtil fu = new FileUtil();
	// //在应用根目录基础加入用户名
	// return fu.isFileExists(getUser().getName() + File.separator + path,
	// name);
	// }
	// /**
	// * 检测文件是否一致，通过大小
	// * @param path 目录路径
	// * @param name 文件名
	// * @return 文件是否一致
	// */
	// public boolean isSameFile(String path, String name, int size)
	// {
	// FileUtil fu = new FileUtil();
	// //在应用根目录基础加入用户名
	// return fu.isSameFile(getUser().getName() + File.separator + path, name,
	// size);
	// }
	// /**
	// * 把实体转化成存储地址
	// *
	// * @param urlStr
	// * @return
	// */
	// public String modelToAddress(Object model) {
	// String result = null;
	// if (model instanceof Friend) {
	// Friend f = (Friend) model;
	// String dir = "Friend";
	// String name = f.getName();
	// result = dir + File.separator + name;
	// if (fileUtil.isSameFile(dir, name, f.getSize())) {
	//
	// } else {
	// if (fileUtil.downloadFile(f.getPath(), dir, name, true)) {
	// f.setPath(result);
	// f.setSize(fileUtil.getFileSize(dir, name));
	// } else {
	// // 下载失败
	// }
	// }
	// }
	// return result;
	// }
}

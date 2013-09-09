package com.futurePayment.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.futurePayment.constant.ResultCode;
import com.futurePayment.constant.ServiceType;
import com.futurePayment.http.MyHttpClient;
import com.futurePayment.http.MyResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.location.Location;
import android.util.Log;

/**
 * 
 * @author luo
 * 
 */
public class FuturePaymentSupport {
	private String name;
	private MyHttpClient http;

	public FuturePaymentSupport(String name) {
		this.name = name;
		http = new MyHttpClient(name);
	}

	/**
	 * 
	 * @return
	 * @throws PaymentException
	 *             try to begin session with the server
	 */
	public BasicInformation loginUser(String password) throws PaymentException {
		BasicInformation bi = new BasicInformation();
		try {
			JSONObject jobj = new JSONObject();
			jobj.put("name", name);
			jobj.put("password", password);
			MyResponse response = http.post(ServiceType.LOGIN, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				Gson gson = new Gson();
				JSONObject ob = response.getData();
				bi = gson.fromJson(ob.toString(), BasicInformation.class);
			} else
				throw new PaymentException(response.getResultCode());
		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bi;
	}

	/**
	 * end session with the server
	 */
	public void logoutUser() {
		http.post(ServiceType.LOGOUT, null);
	}

	public JSONObject getUserInfo() throws PaymentException {
		JSONObject info = null;
		JSONObject jobj = new JSONObject();
		MyResponse response = http.post(ServiceType.GET_USER_INFO, jobj);
		if (response.getResultCode() == ResultCode.SUCCESS) {
			info = response.getData();
		} else
			throw new PaymentException(response.getResultCode());
		return info;
	}

	/**
	 * 
	 * @param transfer
	 * @param password
	 */
	public boolean personalPay(Transfer transfer, String password)
			throws PaymentException {
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("password", password);
			jobj.put("sender", transfer.getSender());
			jobj.put("receiver", transfer.getReceiver());
			jobj.put("amount", transfer.getAmount());
			jobj.put("method", transfer.getMethod());
			String bank = transfer.getBank();
			String cardNumber = transfer.getCardNumber();
			if (bank != null && cardNumber != null) {
				jobj.put("bank", bank);
				jobj.put("cardNumber", cardNumber);
			}

			MyResponse response = http.post(ServiceType.PERSONAL_PAY, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());
		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// throw new PaymentException(ResultCode.TRANSFER_FAILURE);
		return false;
	}

	/**
	 * 
	 * @param transfer
	 * @param password
	 */
	public boolean personalPay(Transfer transfer) throws PaymentException {
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("sender", transfer.getSender());
			jobj.put("receiver", transfer.getReceiver());
			jobj.put("amount", transfer.getAmount());
			jobj.put("method", transfer.getMethod());
			String bank = transfer.getBank();
			String cardNumber = transfer.getCardNumber();
			if (bank != null && cardNumber != null) {
				jobj.put("bank", bank);
				jobj.put("cardNumber", cardNumber);
			}

			MyResponse response = http.post(ServiceType.PERSONAL_PAY, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// throw new PaymentException(ResultCode.TRANSFER_FAILURE);
		return false;
	}

	// public boolean get(Transfer transfer)throws PaymentException{
	// JSONObject jobj = new JSONObject();
	// try{
	// jobj.put("serviceType", ServiceType.TRANSFER_GET);
	// jobj.put("sender", transfer.getSender());
	// jobj.put("receiver", transfer.getReceiver());
	// jobj.put("amount",transfer.getAmount());
	// MyResponse response = http.post(Uris.TRANSFER, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS)
	// return true;
	// }catch(Exception e){
	// e.printStackTrace();
	// throw new PaymentException(ResultCode.FAILURE);
	// }
	// throw new PaymentException();
	// }

	/**
	 * @param bank
	 * @param cardNumber
	 */
	public String bindAccount(String cardNumber, String password)
			throws PaymentException {
		try {
			JSONObject jobj = new JSONObject();
			jobj.put("cardNumber", cardNumber);
			jobj.put("password", password);
			MyResponse response = http.post(ServiceType.BIND_ACCOUNT, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return response.getData().getString("bank");
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param page
	 *            page number
	 * @param perPage
	 *            number of records per page
	 * @param condition
	 *            the search condition
	 * @return list of records 分页获取交易记录
	 */
	public ArrayList<TradeRecord> getBill(int page, int perPage,
			HashMap<String, Object> condition) throws PaymentException {
		ArrayList<TradeRecord> records = null;
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("name", name);
			jobj.put("page", page);
			jobj.put("perPage", perPage);
			jobj.put("condition", new JSONObject(condition));

			MyResponse response = http.post(ServiceType.QUERY_BILL, jobj);
			int resultCode = response.getResultCode();
			if (resultCode != ResultCode.EMPTY) {
				records = new ArrayList<TradeRecord>();
				JSONArray array = response.getResultArray("tradeRecords");
				for (int i = 0; i < array.length(); i++) {
					JSONObject ob = array.getJSONObject(i);
					TradeRecord record = new TradeRecord();
					record.setSender(ob.getString("sender"));
					record.setReceiver(ob.getString("receiver"));
					record.setReceiverPic(ob.getString("reciverPic"));
					record.setAmount(ob.getDouble("amount"));
					record.setDate(ob.getString("date"));
					record.setState(ob.getString("state"));
					record.setType(ob.getInt("type"));
					record.setTitle(ob.getString("title"));
					records.add(record);
				}
			} else
				throw new PaymentException(response.getResultCode());
		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
	}

	public ArrayList<TradeRecord> getBill(String id, int flag)
			throws PaymentException {
		ArrayList<TradeRecord> records = null;
		JSONObject jobj = new JSONObject();
		try {
			jobj.put("name", name);
			jobj.put("recordId", id);
			jobj.put("flag", flag);

			MyResponse response = http.post(ServiceType.QUERY_BILL, jobj);
			int resultCode = response.getResultCode();
			if (resultCode != ResultCode.EMPTY) {
				records = new ArrayList<TradeRecord>();
				JSONArray array = response.getResultArray("tradeRecords");
				for (int i = 0; i < array.length(); i++) {
					JSONObject ob = array.getJSONObject(i);
					TradeRecord record = new TradeRecord();
					record.setId(ob.getString("id"));
					record.setSender(ob.getString("sender"));
					record.setReceiver(ob.getString("receiver"));
					record.setReceiverPic(ob.getString("reciverPic"));
					record.setAmount(ob.getDouble("amount"));
					record.setDate(ob.getString("date"));
					record.setState(ob.getString("state"));
					record.setType(ob.getInt("type"));
					record.setTitle(ob.getString("title"));
					records.add(record);
				}
			} else
				throw new PaymentException(response.getResultCode());
		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records;
	}

	// public String lookUpUserNameByCardNumber(String cardNumber){
	// JSONObject jobj = new JSONObject();
	// try {
	// jobj.put("serviceType", ServiceType.LOOK_UP_NAME_BY_CARDNUMBER);
	// MyResponse response = http.post(Uris.ACCOUNT_SERVICE, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS){
	// return response.getResponseBody().getString("userName");
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

	// public String lookUpUserNameByCardNumber(String cardNumber){
	// JSONObject jobj = new JSONObject();
	// try {
	// jobj.put("serviceType", ServiceType.LOOK_UP_NAME_BY_CARDNUMBER);
	// MyResponse response = http.post(Uris.ACCOUNT_SERVICE, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS){
	// return response.getResponseBody().getString("userName");
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }

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
		Gson gson = new Gson();
		boolean result = false;
		try {
			JSONObject jobj = new JSONObject(gson.toJson(ri));
			jobj.put("birthday", ri.getBirthday());
			MyResponse response = http.post(ServiceType.REGIST, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 检查用户是否存在
	 * 
	 * @return 检查结果
	 * @throws PaymentException
	 */
	public boolean checkUserExistence(String name) throws PaymentException {
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("name", name);
			MyResponse response = http.post(ServiceType.CHECK_USER_EXISTENCE,
					jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 提交商品清单
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @param goodlist
	 *            商品清单
	 * @return 提交结果
	 * @throws PaymentException
	 */
	// public boolean sumit_goods_list(String enterpriseId,JSONObject[]
	// goodlist)throws PaymentException{
	// JSONObject jobj = new JSONObject();
	// boolean result = false;
	// try {
	// jobj.put("enterpriseId", enterpriseId);
	// jobj.put("goodlist", goodlist);
	// MyResponse response = http.post(ServiceType.SUBMIT_GOODS_LIST, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS)
	// return true;
	// else
	// throw new PaymentException(response.getResultCode());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	/**
	 * 获得商品信息
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @param goodsId
	 *            商品id
	 * @return 商品信息
	 * @throws PaymentException
	 */
	// public JSONObject get_goods_detail(String enterpriseId,String
	// goodsId)throws PaymentException{
	// JSONObject jobj = new JSONObject();
	// JSONObject goodsDetail = null;
	// boolean result = false;
	// try {
	// jobj.put("enterpriseId",enterpriseId);
	// jobj.put("goodsId",goodsId);
	// MyResponse response = http.post(ServiceType.GET_GOODS_DETAIL, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS)
	// {
	// goodsDetail=response.getResultObject("goodsDetail");
	// }
	// else
	// throw new PaymentException(response.getResultCode());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return goodsDetail;
	// }

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
		boolean result = false;
		try {
			Gson gson = new Gson();
			JSONObject temp = new JSONObject();
			temp.put("payerlist", new JSONArray(gson.toJson(payerlist)));
			MyResponse response = http.post(ServiceType.MUTIPLE_PAY, temp);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询银行帐号
	 * 
	 * @return 银行帐号信息
	 * @throws PaymentException
	 */
	public LinkedList<BankCard> queryAccount() throws PaymentException {
		Gson gson = new Gson();
		LinkedList<BankCard> accountInfo = new LinkedList<BankCard>();
		try {
			MyResponse response = http.post(ServiceType.QUERY_ACCOUNT, null);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				accountInfo = gson.fromJson(response.getResultArray("cardList")
						.toString(), new TypeToken<LinkedList<BankCard>>() {
				}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accountInfo;
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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("bank", bank);
			jobj.put("cardNumber", cardNumber);
			MyResponse response = http.post(ServiceType.UNBIND_ACCOUNT, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获得商品清单
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @return
	 * @throws PaymentException
	 */
	// public JSONObject get_goods_list(String enterpriseId)throws
	// PaymentException{
	// JSONObject jobj = new JSONObject();
	// JSONObject goodsList = null;
	// try {
	// jobj.put("enterpriseId",enterpriseId);
	// MyResponse response = http.post(ServiceType.GET_GOODS_LIST, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS)
	// goodsList = response.getResultObject("goodsList");
	// else
	// throw new PaymentException(response.getResultCode());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return goodsList;
	// }

	/**
	 * 查询会员卡
	 * 
	 * @return
	 * @throws PaymentException
	 */
	public ArrayList<VipCard> queryVip() throws PaymentException {
		Gson gson = new Gson();
		ArrayList<VipCard> al = null;
		try {
			MyResponse response = http.post(ServiceType.QUERY_VIP, null);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				al = gson.fromJson(response.getResultArray("vipList")
						.toString(), new TypeToken<ArrayList<VipCard>>() {
				}.getType());
			}

			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("enterpriseId", enterpriseId);
			MyResponse response = http.post(ServiceType.APPLY_FOR_VIP, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("enterpriseId", enterpriseId);
			jobj.put("amount", amount);
			MyResponse response = http.post(ServiceType.USE_VIP, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 会员卡积分兑换
	 * 
	 * @param enterpriseId
	 *            商家id
	 * @param grade
	 *            使用积分
	 * @param goodsId
	 *            商品ID
	 * @return
	 * @throws PaymentException
	 */
	// public boolean vip_grade_swap(String enterpriseId,int grade,String
	// goodsId)throws PaymentException{
	// JSONObject jobj = new JSONObject();
	// boolean result = false;
	// try {
	// jobj.put("enterpriseId",enterpriseId);
	// jobj.put("grade",grade);
	// jobj.put("goodsId",goodsId);
	// MyResponse response = http.post(ServiceType.VIP_GRADE_SWAP, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS)
	// return true;
	// else
	// throw new PaymentException(response.getResultCode());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

	/**
	 * 查询账号积分
	 * 
	 * @return 查询结果
	 * @throws PaymentException
	 */
	public int queryUserGrade() throws PaymentException {
		int grade = 0;
		try {
			MyResponse response = http.post(ServiceType.QUERY_USER_GRADE, null);
			if (response.getResultCode() == ResultCode.SUCCESS)
				grade = response.getData().getInt("userGrade");
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return grade;
	}

	public LinkedList<Coupon> getSwapList() throws PaymentException {
		LinkedList<Coupon> al = null;
		Gson gson = new Gson();
		try {
			MyResponse response = http.post(ServiceType.QUERY_SWAP, null);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				al = gson.fromJson(response.getResultArray("swapList")
						.toString(), new TypeToken<LinkedList<Coupon>>() {
				}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	/**
	 * 账号积分兑换
	 * 
	 * @param grade
	 *            使用积分
	 * @return 兑换结果
	 * @throws PaymentException
	 */
	public boolean userGradeSwap(int grade, int couponId)
			throws PaymentException {
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("grade", grade);
			jobj.put("couponId", couponId);
			MyResponse response = http.post(ServiceType.USER_GRADE_SWAP, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询优惠券
	 * 
	 * @return 查询结果
	 * @throws PaymentException
	 */
	public LinkedList<Coupon> queryCoupon() throws PaymentException {
		LinkedList<Coupon> al = null;
		Gson gson = new Gson();
		try {
			MyResponse response = http.post(ServiceType.QUERY_COUPON, null);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				al = gson.fromJson(response.getResultArray("couponList")
						.toString(), new TypeToken<LinkedList<Coupon>>() {
				}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	public LinkedList<Coupon> queryAvailableCoupon(String name, Double money)
			throws PaymentException {
		LinkedList<Coupon> al = null;
		Gson gson = new Gson();
		try {
			JSONObject temp = new JSONObject();
			temp.put("name", name);
			temp.put("money", money);
			MyResponse response = http.post(ServiceType.QUERY_AVAILABLE_COUPON,
					temp);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				al = gson.fromJson(response.getResultArray("couponList")
						.toString(), new TypeToken<LinkedList<Coupon>>() {
				}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	/**
	 * 
	 * 查询优惠券详细信息
	 * 
	 * @param couponId
	 *            优惠券id
	 * @return 查询结果
	 * @throws PaymentException
	 */
	// public boolean query_coupun_detail(String couponId)throws
	// PaymentException{
	// JSONObject jobj = new JSONObject();
	// boolean result = false;
	// try {
	// jobj.put("couponId",couponId);
	// MyResponse response = http.post(ServiceType.QUERY_COUPON_DETAIL, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS)
	// return true;
	// else
	// throw new PaymentException(response.getResultCode());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return result;
	// }

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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("receiver", receiver);
			jobj.put("couponId", couponId);
			jobj.put("amount", amount);
			MyResponse response = http.post(ServiceType.SEND_COUPON, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("coupon", coupon);
			MyResponse response = http.post(ServiceType.USE_COUPON, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询个人信息
	 * 
	 * @return 查询结果
	 * @throws PaymentException
	 */
	// public JSONObject query_personal_detail()throws PaymentException{
	// JSONObject jobj = new JSONObject();
	// JSONObject personal_detail = null;
	// try {
	// MyResponse response = http.post(ServiceType.QUERY_PERSONAL_DETAIL, jobj);
	// if(response.getResultCode() == ResultCode.SUCCESS)
	// personal_detail = response.getResultObject("personal_detail");
	// else
	// throw new PaymentException(response.getResultCode());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return personal_detail;
	// }

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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("name", name);
			jobj.put("sex", sex);
			jobj.put("birthday", birthday);
			jobj.put("phone", phone);
			jobj.put("email", email);
			MyResponse response = http.post(ServiceType.MODIFY_PERSONAL_DETAIL,
					jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("page", page);
			jobj.put("condition", condition);
			MyResponse response = http.post(ServiceType.QUERY_BILL, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public LinkedList<Friend> queryFriend() throws PaymentException {
		LinkedList<Friend> al = null;
		try {
			Gson gson = new Gson();
			MyResponse response = http.post(ServiceType.QUERY_FRIEND, null);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				JSONArray ja = response.getResultArray("friendList");
				al = gson.fromJson(ja.toString(),
						new TypeToken<LinkedList<Friend>>() {
						}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	public LinkedList<Friend> searchFriend(String name) throws PaymentException {
		LinkedList<Friend> al = null;
		try {
			Gson gson = new Gson();
			JSONObject jo = new JSONObject();
			jo.put("name", name);
			MyResponse response = http.post(ServiceType.SEARCH_FRIEND, jo);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				JSONArray ja = response.getResultArray("friendList");
				al = gson.fromJson(ja.toString(),
						new TypeToken<LinkedList<Friend>>() {
						}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	/**
	 * 添加好友
	 * 
	 * @param name
	 *            好友id
	 * @return 添加结果
	 * @throws PaymentException
	 */
	public boolean addFriend(String name) throws PaymentException {
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("friend", name);
			MyResponse response = http.post(ServiceType.ADD_FRIEND, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("friendId", friendId);
			MyResponse response = http.post(ServiceType.DELETE_FRIEND, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public LinkedList<EnterpriseBasicInfo> queryEnterprise()
			throws PaymentException {
		LinkedList<EnterpriseBasicInfo> al = null;
		try {
			Gson gson = new Gson();
			MyResponse response = http.post(ServiceType.QUERY_ENTERPRISE, null);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				JSONArray ja = response.getResultArray("enterpriseList");
				al = gson.fromJson(ja.toString(),
						new TypeToken<LinkedList<EnterpriseBasicInfo>>() {
						}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	public LinkedList<EnterpriseBasicInfo> searchEnterprise(String name)
			throws PaymentException {
		LinkedList<EnterpriseBasicInfo> al = null;
		try {
			Gson gson = new Gson();
			JSONObject jo = new JSONObject();
			jo.put("enterprise", name);
			MyResponse response = http.post(ServiceType.SEARCH_ENTERPRISE, jo);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				JSONArray ja = response.getResultArray("enterpriseList");
				al = gson.fromJson(ja.toString(),
						new TypeToken<LinkedList<EnterpriseBasicInfo>>() {
						}.getType());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	/**
	 * 关注商家
	 * 
	 * @param name
	 *            商家名
	 * @return 关注结果
	 * @throws PaymentException
	 */
	public boolean attentEnterprise(String name) throws PaymentException {
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("enterprise", name);
			MyResponse response = http
					.post(ServiceType.ATTENT_ENTERPRISE, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		JSONObject jobj = new JSONObject();
		boolean result = false;
		try {
			jobj.put("enterpriseId", enterpriseId);
			MyResponse response = http.post(ServiceType.INATTENT_ENTERPRISE,
					jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
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
		Gson gson = new Gson();
		boolean result = false;
		try {
			MyResponse response = http.post(ServiceType.SHARE_EXPERIENCE,
					new JSONObject(gson.toJson(si)));
			if (response.getResultCode() == ResultCode.SUCCESS)
				return true;
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public LinkedList<CommentInfo> getExperience() throws PaymentException {
		LinkedList<CommentInfo> al = null;
		Gson gson = new Gson();
		try {
			MyResponse response = http.post(ServiceType.GET_COMMENT, null);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				al = gson.fromJson(response.getResultArray("comment")
						.toString(), new TypeToken<LinkedList<CommentInfo>>() {
				}.getType());

			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	/**
	 * 获得位置图片
	 * 
	 * @return 位置图片
	 * @throws PaymentException
	 */
	public JSONObject getLocalPicture() throws PaymentException {
		JSONObject jobj = new JSONObject();
		JSONObject local_picture = null;
		try {
			MyResponse response = http
					.post(ServiceType.GET_LOCAL_PICTURE, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				local_picture = response.getResultObject("local_picture");
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return local_picture;
	}

	/**
	 * 获得广告
	 * 
	 * @return 广告
	 * @throws PaymentException
	 */
	public JSONObject getAdvertising() throws PaymentException {
		JSONObject jobj = new JSONObject();
		JSONObject advertising = null;
		try {
			MyResponse response = http.post(ServiceType.GET_ADVERTISING, jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				advertising = response.getResultObject("advertising");
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return advertising;
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
		JSONObject jobj = new JSONObject();
		JSONObject enterprise_detail = null;
		try {
			MyResponse response = http.post(ServiceType.GET_ENTERPRISE_DETAIL,
					jobj);
			if (response.getResultCode() == ResultCode.SUCCESS)
				enterprise_detail = response
						.getResultObject("enterprise_detail");
			else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return enterprise_detail;
	}

	/**
	 * 获得周边商家
	 * 
	 * @return 周边商家
	 * @throws PaymentException
	 */
	public LinkedList<EnterpriseBasicInfo> getSurroundingEnterprise(
			Location location) throws PaymentException {
		LinkedList<EnterpriseBasicInfo> al = null;
		JSONObject jo = new JSONObject();
		Gson gson = new Gson();
		try {
			jo.put("longitude", location.getLongitude());
			jo.put("latitude", location.getLatitude());
			jo.put("accuracy", location.getAccuracy());
			MyResponse response = http.post(
					ServiceType.GET_SURROUNDIND_ENTERPRISE, jo);
			if (response.getResultCode() == ResultCode.SUCCESS) {
				JSONArray ja = response.getResultArray("sellers");
				al = gson.fromJson(ja.toString(),
						new TypeToken<LinkedList<EnterpriseBasicInfo>>() {
						}.getType());
				Log.i("test", al.toString());
			} else
				throw new PaymentException(response.getResultCode());

		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return al;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

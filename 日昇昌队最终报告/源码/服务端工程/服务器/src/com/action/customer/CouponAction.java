package com.action.customer;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.SessionAware;

import com.domain.Coupon;
import com.opensymphony.xwork2.ActionSupport;
import com.service.interfaces.CouponService;
import com.util.ResultCode;

public class CouponAction extends ActionSupport implements SessionAware{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1172379819822334212L;
	private JSONObject response = new JSONObject();
	private Map<String, Object> session;
	private CouponService couponService;
	/**
	 * ªÒ»°”≈ª›»Ø
	 * @return
	 */
	public String getCoupon(){
		try {
			int result = 0;
			String name = (String) session.get("name");
			System.out.println(name);
			List<Coupon> coupons = couponService.getCoupon(name, 1, 10);
			if(coupons.isEmpty()){
				result = ResultCode.EMPTY;
				System.out.println("empty");
			}else{
				result = ResultCode.SUCCESS;
				JSONArray array = new JSONArray();
				for(Coupon c: coupons){
					JSONObject coupon = new JSONObject();
					coupon.put("enterpriseName", c.getSeller().getName());
					System.out.println(c.getSeller().getName());
					coupon.put("startTime", c.getStartDate().toString());
					coupon.put("endTime", c.getEndDate().toString());
					coupon.put("picture", c.getPicture());
					coupon.put("type", c.getType());
					coupon.put("value", c.getValue());
					coupon.put("least", c.getLeast());
					coupon.put("couponId", c.getCouponNumber());
					int amount = couponService.getNumberOfCoupon(name, coupon.getString("enterpriseName"), coupon.getLong("couponId"));
					coupon.put("amount", amount);
					array.add(coupon);
				}

				JSONObject data = new JSONObject();
				data.put("couponList", array);
				System.out.println(data.toString());
				response.put("data", data);
			}
			response.put("result", result);
			System.out.println("getting coupon");
			return "success";	
		} catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
}
	public CouponService getCouponService() {
		return couponService;
	}
	public JSONObject getResponse() {
		return response;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	
	public void setCouponService(CouponService couponService) {
		this.couponService = couponService;
	}
	public void setResponse(JSONObject response) {
		this.response = response;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}

package com.service.interfaces;

import java.util.List;

import com.domain.Coupon;
import com.domain.CouponCollection;


public interface CouponService {
	
	public void addCoupon(Coupon coupon);
	
	public Coupon getCoupon(String name,long couponNumber);
	
	public Coupon getCoupon(String customerName,String sellerName,long couponNumber);
	
	public List<Coupon> getCoupon(String name, int page, int pageSize);
	
	public List<Coupon> getCoupon(String customerName,String sellerName);
	
	public void updateCoupon(Coupon coupon);
	
	public void deleteCoupon(Coupon coupon);
	
	public void updateCouponCollection(CouponCollection collection);
	
	public void deleteCouponCollection(CouponCollection collection);
	
	public int getNumberOfCoupon(String customerName, String sellerName, long couponNumber);
	
}

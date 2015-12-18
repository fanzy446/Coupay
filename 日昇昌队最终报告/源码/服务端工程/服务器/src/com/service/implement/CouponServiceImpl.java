package com.service.implement;

import java.util.List;

import com.dao.CouponDao;
import com.domain.Coupon;
import com.domain.CouponCollection;
import com.service.interfaces.CouponService;

public class CouponServiceImpl implements CouponService {

	private CouponDao dao;
	
	
	public void setDao(CouponDao dao) {
		this.dao = dao;
	}

	@Override
	public void addCoupon(Coupon coupon) {
		dao.saveCoupon(coupon);
	}

	@Override
	public Coupon getCoupon(String sellerName, long couponNumber) {
		return dao.getCoupon(sellerName, couponNumber);
	}

	@Override
	public Coupon getCoupon(String customerName, String sellerName,
			long couponNumber) {
		return dao.getCoupon(customerName, sellerName, couponNumber);
	}

	@Override
	public List<Coupon> getCoupon(String customerName, int page, int pageSize) {
		return dao.getCouponOfCustomer(customerName, page, pageSize);
	}

	@Override
	public List<Coupon> getCoupon(String customerName, String sellerName) {
		return dao.getCoupon(customerName, sellerName);
	}

	@Override
	public void updateCoupon(Coupon coupon) {
		dao.updateCoupon(coupon);
	}

	@Override
	public void deleteCoupon(Coupon coupon) {
		dao.deleteCoupon(coupon);
	}

	@Override
	public void updateCouponCollection(CouponCollection collection) {
		dao.updateCouponCollection(collection);
	}

	@Override
	public void deleteCouponCollection(CouponCollection collection) {
		dao.deleteCouponCollection(collection);
	}

	@Override
	public int getNumberOfCoupon(String customerName, String sellerName,
			long couponNumber) {
		return dao.getNumberOfCoupon(customerName, sellerName, couponNumber);		
	}

}

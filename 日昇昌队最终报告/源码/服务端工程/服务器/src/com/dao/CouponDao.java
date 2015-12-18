package com.dao;

import java.util.LinkedList;
import java.util.List;
import com.domain.Coupon;
import com.domain.CouponCollection;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 * 封装Coupon 和 CouponCollection CURD 操作的dao类
 */
public class CouponDao extends MyDaoSupport {
	
	/**
	 * 根据id查Coupon实例
	 * @param id       指定id
	 * @return         指定id对应实例
	 */
	public Coupon getCoupon(int id) {
		return getHibernateTemplate().get(Coupon.class, id);
	}
	
	/**
	 * 保存Coupon实例
	 * @param coupon     要保存的实例
	 * @return           保存对象的标识属性值
	 */
	public Integer saveCoupon(Coupon coupon) {
		return (Integer) getHibernateTemplate().save(coupon);
	}
	
	/**
	 * 修改Coupon实例
	 * @param coupon    要修改的实例
	 */
	public void updateCoupon(Coupon coupon) {
		getHibernateTemplate().update(coupon);
	}
	
	/**
	 * 删除Coupon实例
	 * @param coupon      要删除的实例
	 */
	public void deleteCoupon(Coupon coupon) {
		getHibernateTemplate().delete(coupon);
	}
	
	/**
	 * 根据id查CouponCollection实例
	 * @param id       指定id
	 * @return         指定id对应实例
	 */
	public CouponCollection getCouponCollection(int id) {
		return getHibernateTemplate().get(CouponCollection.class, id);
	}
	
	/**
	 * 保存CouponCollection对象
	 * @param collection         要保存的对象
	 * @return                   保存对象的标识属性值
	 */
	public Integer saveCouponCollection(CouponCollection collection) {
		return (Integer) getHibernateTemplate().save(collection);
	}
	
	/**
	 * 删除CouponCollection实例
	 * @param collection        要删除的实例
	 */
	public void deleteCouponCollection(CouponCollection collection) {
		getHibernateTemplate().delete(collection);
	}
	/**
	 * 修改CouponCollection实例
	 * @param collection         要修改的实例
	 */
	public void updateCouponCollection(CouponCollection collection) {
		getHibernateTemplate().update(collection);
	}
	/**
	 * 查询某个商家的所有优惠券
	 * @param sellerName        商家用户名
	 * @return                  指定商家的所有优惠券
	 */
	public List<Coupon> getCouponOfSeller(String sellerName) {
		String hql = "from Coupon where seller.name = '" + sellerName + "'";
		@SuppressWarnings("unchecked")
		List<Coupon> list = getHibernateTemplate().find(hql);
		return list;
	}
	
	/**
	 * 分页查询某商家的优惠券
	 * @param sellerName            商家用户名
	 * @param page                  当前页
	 * @param pageSize              每页记录数
	 * @return                      当前页所有记录
	 */
	public List<Coupon> getCouponOfSeller(String sellerName, int page, int pageSize) {
		String hql = "from Coupon where seller.name = '" + sellerName + "'";
		@SuppressWarnings("unchecked")
		List<Coupon> list = findByPage(hql, page, pageSize);
		return list;
	}
	/**
	 * 查询某个客户收藏的所有优惠券
	 * @param customerName
	 * @return
	 */
	public List<Coupon> getCouponOfCustomer(String customerName) {
		String hql = "from CouponCollection where owner.name = '" + customerName + "'";
		@SuppressWarnings("unchecked")
		List<CouponCollection> list = getHibernateTemplate().find(hql);
		List<Coupon> coupons = new LinkedList<Coupon>();
		for(CouponCollection collection : list) {
			coupons.add(collection.getCoupon());
		}
		return coupons;
	}
	
	/**
	 * 分页查询某个用户收藏的优惠券
	 * @param customerName             客户用户名
	 * @param page                     当前页
	 * @param pageSize                 每页记录数
	 * @return                         当前页所有记录
	 */
	public List<Coupon> getCouponOfCustomer(String customerName, int page, int pageSize) {
		String hql = "from CouponCollection where owner.name = '" + customerName + "'";
		@SuppressWarnings("unchecked")
		List<CouponCollection> list = findByPage(hql, page, pageSize);
		List<Coupon> coupons = new LinkedList<Coupon>();
		for(CouponCollection collection : list) {
			coupons.add(collection.getCoupon());
		}
		return coupons;
	}
	
	/**
	 * 查询某商家指定优惠券号的Coupon实例
	 * @param sellerName        商家用户名
	 * @param couponNumber      优惠券号
	 * @return                  指定Coupon实例
	 */
	public Coupon getCoupon(String sellerName, long couponNumber) {
		String hql = "from Coupon where seller.name='" + sellerName +
				 " and couponNumber='" + couponNumber;
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find(hql);
		if(list != null && list.size() == 1) {
			return (Coupon) list.get(0);
		}
		return null;
	}
	
	/**
	 * 查询客户收藏的某家商家的所有优惠券
	 * @param customerName
	 * @param sellerName
	 * @return
	 */
	public List<Coupon> getCoupon(String customerName, String sellerName) {
		String hql = "from CouponCollection where owner.name=? and coupon.id in" +
				"(select id from Coupon where seller.name= ?)";
		Object[] values = { customerName, sellerName };
		@SuppressWarnings("unchecked")
		List<CouponCollection> list = find(hql, values);
		List<Coupon> coupons = new LinkedList<Coupon>();
		for(CouponCollection collection : list) {
			coupons.add(collection.getCoupon());
		}
		return coupons;
	}
	
	/**
	 * 查询客户收藏的某家商店指定优惠券号的Coupon实例
	 * @param customerName     客户用户名
	 * @param sellerName       商家用户名
	 * @param couponNumber     优惠券号
	 * @return                 指定Coupon实例
	 */
	public Coupon getCoupon(String customerName, String sellerName, long couponNumber) {
		String hql = "from CouponCollection where owner.name=? and coupon.id in" +
				"(select id from Coupon where seller.name=?) and coupon.couponNumber=?";
		Object[] values = { customerName, sellerName, couponNumber };
		@SuppressWarnings("unchecked")
		List<CouponCollection> list = find(hql, values);
		if(list != null && list.size() == 1) {
			return list.get(0).getCoupon();
		}
		return null;
	}
	
	/**
	 * 根据客户名字，商家名字，优惠券号码查询该客户有多少张该优惠券
	 * @param customerName	客户用户名
	 * @param sellerName	商家名字
	 * @param couponNumber	优惠券号码
	 * @return				优惠券的张数
	 */
	public int getNumberOfCoupon(String customerName, String sellerName, long couponNumber) {
		String hql = "from CouponCollection where owner.name=? and coupon.id in " +
				"(select id from Coupon where seller.name=?) and coupon.couponNumber=?";
		Object[] values = { customerName, sellerName, couponNumber };
		List<CouponCollection> list = find(hql, values);
		if(list != null && list.size() == 1) {
			return list.get(0).getNumber();
		}
		return 0;
	}
}
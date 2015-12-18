package com.dao;

import java.util.LinkedList;
import java.util.List;
import com.domain.Coupon;
import com.domain.CouponCollection;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 * ��װCoupon �� CouponCollection CURD ������dao��
 */
public class CouponDao extends MyDaoSupport {
	
	/**
	 * ����id��Couponʵ��
	 * @param id       ָ��id
	 * @return         ָ��id��Ӧʵ��
	 */
	public Coupon getCoupon(int id) {
		return getHibernateTemplate().get(Coupon.class, id);
	}
	
	/**
	 * ����Couponʵ��
	 * @param coupon     Ҫ�����ʵ��
	 * @return           �������ı�ʶ����ֵ
	 */
	public Integer saveCoupon(Coupon coupon) {
		return (Integer) getHibernateTemplate().save(coupon);
	}
	
	/**
	 * �޸�Couponʵ��
	 * @param coupon    Ҫ�޸ĵ�ʵ��
	 */
	public void updateCoupon(Coupon coupon) {
		getHibernateTemplate().update(coupon);
	}
	
	/**
	 * ɾ��Couponʵ��
	 * @param coupon      Ҫɾ����ʵ��
	 */
	public void deleteCoupon(Coupon coupon) {
		getHibernateTemplate().delete(coupon);
	}
	
	/**
	 * ����id��CouponCollectionʵ��
	 * @param id       ָ��id
	 * @return         ָ��id��Ӧʵ��
	 */
	public CouponCollection getCouponCollection(int id) {
		return getHibernateTemplate().get(CouponCollection.class, id);
	}
	
	/**
	 * ����CouponCollection����
	 * @param collection         Ҫ����Ķ���
	 * @return                   �������ı�ʶ����ֵ
	 */
	public Integer saveCouponCollection(CouponCollection collection) {
		return (Integer) getHibernateTemplate().save(collection);
	}
	
	/**
	 * ɾ��CouponCollectionʵ��
	 * @param collection        Ҫɾ����ʵ��
	 */
	public void deleteCouponCollection(CouponCollection collection) {
		getHibernateTemplate().delete(collection);
	}
	/**
	 * �޸�CouponCollectionʵ��
	 * @param collection         Ҫ�޸ĵ�ʵ��
	 */
	public void updateCouponCollection(CouponCollection collection) {
		getHibernateTemplate().update(collection);
	}
	/**
	 * ��ѯĳ���̼ҵ������Ż�ȯ
	 * @param sellerName        �̼��û���
	 * @return                  ָ���̼ҵ������Ż�ȯ
	 */
	public List<Coupon> getCouponOfSeller(String sellerName) {
		String hql = "from Coupon where seller.name = '" + sellerName + "'";
		@SuppressWarnings("unchecked")
		List<Coupon> list = getHibernateTemplate().find(hql);
		return list;
	}
	
	/**
	 * ��ҳ��ѯĳ�̼ҵ��Ż�ȯ
	 * @param sellerName            �̼��û���
	 * @param page                  ��ǰҳ
	 * @param pageSize              ÿҳ��¼��
	 * @return                      ��ǰҳ���м�¼
	 */
	public List<Coupon> getCouponOfSeller(String sellerName, int page, int pageSize) {
		String hql = "from Coupon where seller.name = '" + sellerName + "'";
		@SuppressWarnings("unchecked")
		List<Coupon> list = findByPage(hql, page, pageSize);
		return list;
	}
	/**
	 * ��ѯĳ���ͻ��ղص������Ż�ȯ
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
	 * ��ҳ��ѯĳ���û��ղص��Ż�ȯ
	 * @param customerName             �ͻ��û���
	 * @param page                     ��ǰҳ
	 * @param pageSize                 ÿҳ��¼��
	 * @return                         ��ǰҳ���м�¼
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
	 * ��ѯĳ�̼�ָ���Ż�ȯ�ŵ�Couponʵ��
	 * @param sellerName        �̼��û���
	 * @param couponNumber      �Ż�ȯ��
	 * @return                  ָ��Couponʵ��
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
	 * ��ѯ�ͻ��ղص�ĳ���̼ҵ������Ż�ȯ
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
	 * ��ѯ�ͻ��ղص�ĳ���̵�ָ���Ż�ȯ�ŵ�Couponʵ��
	 * @param customerName     �ͻ��û���
	 * @param sellerName       �̼��û���
	 * @param couponNumber     �Ż�ȯ��
	 * @return                 ָ��Couponʵ��
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
	 * ���ݿͻ����֣��̼����֣��Ż�ȯ�����ѯ�ÿͻ��ж����Ÿ��Ż�ȯ
	 * @param customerName	�ͻ��û���
	 * @param sellerName	�̼�����
	 * @param couponNumber	�Ż�ȯ����
	 * @return				�Ż�ȯ������
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
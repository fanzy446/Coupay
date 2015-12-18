package com.dao;

import java.util.List;

import com.domain.Seller;
import com.util.MyDaoSupport;

/**
 *
 * @author luo
 * ��װ��Seller����CUID������dao�ࡣ
 */
public class SellerDao extends MyDaoSupport {
	private double lonoffset;         //����ƫ����
	private double latoffset;         //γ��ƫ����
	/**
	 * ����Seller ʵ��
	 * @param id  ��ʶ����ֵ
	 * @return    ָ��id��Ӧ��Sellerʵ��
	 */
	public Seller getSeller(Integer id) {
		return getHibernateTemplate().get(Seller.class, id);
	}
	
	/**
	 * ����Seller ʵ��
	 * @param seller    Ҫ�����ʵ��
	 * @return             ���ر������ı�ʶ����ֵ
	 */
	public Integer save(Seller seller) {
		return (Integer) getHibernateTemplate().save(seller);
	}
	
	/**
	 * �޸�Sellerʵ��
	 * @param seller        Ҫ�޸ĵ�Sellerʵ��
	 */
	public void update(Seller seller) {
		getHibernateTemplate().update(seller);
	}
	
	/**
	 * ɾ��Sellerʵ��
	 * @param seller        Ҫɾ����Sellerʵ��
	 */
	public void delete(Seller seller) {
		getHibernateTemplate().delete(seller);
	}
	
	/**
	 * �����û�����ѯSellerʵ��
	 * @param name        �û���
	 * @return            �û�����ӦSellerʵ��
	 */
	public Seller getSeller(String name) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from Seller where name='"+name+"'");
		if(list != null && list.size() == 1) {
			return (Seller) list.get(0);
		}
		return null;
	}
	
	/**
	 * ���ݾ�γ�ȷ�ҳ��ѯ��ָ��ƫ������Χ�ڵ�Sellerʵ��
	 * @param lon       ����
	 * @param lat       γ��
	 * @param accuracy  ƫ����
	 * @return                ���в�ѯ���
	 */
	public List<Seller> getSeller(double lon, double lat, float accuracy, int page, int pageSize) {
		String hql = "from Seller where longitude <= " + (accuracy + lon) + " and longitude >=" + (lon - accuracy)
				+ " and latitude <= " + (lat + accuracy) + " and latitude >= " + (lat - accuracy);
		@SuppressWarnings("unchecked")
		List<Seller> list = findByPage(hql, page, pageSize);
		return list;
	}
}

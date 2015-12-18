package com.dao;

import java.util.List;

import com.domain.Seller;
import com.util.MyDaoSupport;

/**
 *
 * @author luo
 * 封装对Seller对象CUID操作的dao类。
 */
public class SellerDao extends MyDaoSupport {
	private double lonoffset;         //经度偏移量
	private double latoffset;         //纬度偏移量
	/**
	 * 加载Seller 实例
	 * @param id  标识属性值
	 * @return    指定id对应的Seller实例
	 */
	public Seller getSeller(Integer id) {
		return getHibernateTemplate().get(Seller.class, id);
	}
	
	/**
	 * 保存Seller 实例
	 * @param seller    要保存的实例
	 * @return             返回保存对象的标识属性值
	 */
	public Integer save(Seller seller) {
		return (Integer) getHibernateTemplate().save(seller);
	}
	
	/**
	 * 修改Seller实例
	 * @param seller        要修改的Seller实例
	 */
	public void update(Seller seller) {
		getHibernateTemplate().update(seller);
	}
	
	/**
	 * 删除Seller实例
	 * @param seller        要删除的Seller实例
	 */
	public void delete(Seller seller) {
		getHibernateTemplate().delete(seller);
	}
	
	/**
	 * 根据用户名查询Seller实例
	 * @param name        用户名
	 * @return            用户名对应Seller实例
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
	 * 根据经纬度分页查询在指定偏移量范围内的Seller实例
	 * @param lon       经度
	 * @param lat       纬度
	 * @param accuracy  偏移量
	 * @return                所有查询结果
	 */
	public List<Seller> getSeller(double lon, double lat, float accuracy, int page, int pageSize) {
		String hql = "from Seller where longitude <= " + (accuracy + lon) + " and longitude >=" + (lon - accuracy)
				+ " and latitude <= " + (lat + accuracy) + " and latitude >= " + (lat - accuracy);
		@SuppressWarnings("unchecked")
		List<Seller> list = findByPage(hql, page, pageSize);
		return list;
	}
}

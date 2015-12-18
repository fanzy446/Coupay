package com.dao;

import java.util.List;

import com.domain.Customer;
import com.domain.Register;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 * 封装对Customer对象的CURD操作的dao类
 */
public class CustomerDao extends MyDaoSupport {
	
	/**
	 * 加载 Customer 实例
	 * @param id  标识属性值
	 * @return    指定id对应的Customer实例
	 */
	public Customer get(Integer id) {
		return getHibernateTemplate().get(Customer.class, id);
	}
	
	/**
	 * 保存Customer 实例
	 * @param custoemr     要保存的实例
	 * @return             返回保存对象的标识属性值
	 */
	public Integer save(Customer custoemr) {
		return (Integer) getHibernateTemplate().save(custoemr);
	}
	
	/**
	 * 修改Register实例
	 * @param register        要修改的Register实例
	 */
	public void update(Customer customer) {
		getHibernateTemplate().update(customer);
	}
	
	/**
	 * 删除Customer实例
	 * @param customer        要删除的Customer实例
	 */
	public void delete(Customer customer) {
		getHibernateTemplate().delete(customer);
	}
	
	/**
	 * 根据用户名查询Customer实例
	 * @param name        用户名
	 * @return            用户名对应Custoemr实例
	 */
	public Customer getCustomer(String name) {
		@SuppressWarnings("rawtypes")
		List list = getHibernateTemplate().find("from Customer where name='"+name+"'");
		if(list != null && list.size() == 1) {
			return (Customer) list.get(0);
		}
		return null;
	}
	
	/**
	 * 模糊查找个人用户
	 * @param name		名字的部分
	 * @return			用户的列表
	 */
	public List<Customer> searchCustomer(String name) {
		@SuppressWarnings("unchecked")
		List<Customer> list = getHibernateTemplate().find("from Customer where name LIKE'"+name+"%'");
		return list;
	}
}

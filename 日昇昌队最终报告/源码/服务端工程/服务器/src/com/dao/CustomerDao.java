package com.dao;

import java.util.List;

import com.domain.Customer;
import com.domain.Register;
import com.util.MyDaoSupport;

/**
 * 
 * @author luo
 * ��װ��Customer�����CURD������dao��
 */
public class CustomerDao extends MyDaoSupport {
	
	/**
	 * ���� Customer ʵ��
	 * @param id  ��ʶ����ֵ
	 * @return    ָ��id��Ӧ��Customerʵ��
	 */
	public Customer get(Integer id) {
		return getHibernateTemplate().get(Customer.class, id);
	}
	
	/**
	 * ����Customer ʵ��
	 * @param custoemr     Ҫ�����ʵ��
	 * @return             ���ر������ı�ʶ����ֵ
	 */
	public Integer save(Customer custoemr) {
		return (Integer) getHibernateTemplate().save(custoemr);
	}
	
	/**
	 * �޸�Registerʵ��
	 * @param register        Ҫ�޸ĵ�Registerʵ��
	 */
	public void update(Customer customer) {
		getHibernateTemplate().update(customer);
	}
	
	/**
	 * ɾ��Customerʵ��
	 * @param customer        Ҫɾ����Customerʵ��
	 */
	public void delete(Customer customer) {
		getHibernateTemplate().delete(customer);
	}
	
	/**
	 * �����û�����ѯCustomerʵ��
	 * @param name        �û���
	 * @return            �û�����ӦCustoemrʵ��
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
	 * ģ�����Ҹ����û�
	 * @param name		���ֵĲ���
	 * @return			�û����б�
	 */
	public List<Customer> searchCustomer(String name) {
		@SuppressWarnings("unchecked")
		List<Customer> list = getHibernateTemplate().find("from Customer where name LIKE'"+name+"%'");
		return list;
	}
}

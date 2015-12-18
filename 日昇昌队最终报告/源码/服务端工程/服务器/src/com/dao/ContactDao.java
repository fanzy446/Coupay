package com.dao;

import java.util.List;

import com.domain.Contact;
import com.util.MyDaoSupport;

/**
 *
 * @author luo
 *
 * 封装对Contact实例CURD操作的dao类
 * 
 */
public class ContactDao extends MyDaoSupport {
	
	/**
	 * 保存Contact实例
	 * @param contact         
	 * @return
	 */
	public Integer save(Contact contact) {
		return (Integer) getHibernateTemplate().save(contact);
	}
	
	/**
	 * 根据id查Contact实例
	 * @param id
	 * @return
	 */
	public Contact getContact(int id) {
		return getHibernateTemplate().get(Contact.class, id);
	}
	
	/**
	 * 根据用户名查Contact实例
	 * @param name
	 * @return
	 */
	public Contact getContact(String name) {
		String hql = "from Contact where register.name='" + name + "'";
		@SuppressWarnings("unchecked")
		List<Contact> list = getHibernateTemplate().find(hql);
		if(list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 修改Contact实例
	 * @param contact
	 */
	public void updateContact(Contact contact) {
		getHibernateTemplate().update(contact);
	}
	
	/**
	 * 删除Contact实例
	 * @param contact
	 */
	public void deleteContact(Contact contact) {
		getHibernateTemplate().delete(contact);
	}
}

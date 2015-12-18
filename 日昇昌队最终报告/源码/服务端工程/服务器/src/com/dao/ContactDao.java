package com.dao;

import java.util.List;

import com.domain.Contact;
import com.util.MyDaoSupport;

/**
 *
 * @author luo
 *
 * ��װ��Contactʵ��CURD������dao��
 * 
 */
public class ContactDao extends MyDaoSupport {
	
	/**
	 * ����Contactʵ��
	 * @param contact         
	 * @return
	 */
	public Integer save(Contact contact) {
		return (Integer) getHibernateTemplate().save(contact);
	}
	
	/**
	 * ����id��Contactʵ��
	 * @param id
	 * @return
	 */
	public Contact getContact(int id) {
		return getHibernateTemplate().get(Contact.class, id);
	}
	
	/**
	 * �����û�����Contactʵ��
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
	 * �޸�Contactʵ��
	 * @param contact
	 */
	public void updateContact(Contact contact) {
		getHibernateTemplate().update(contact);
	}
	
	/**
	 * ɾ��Contactʵ��
	 * @param contact
	 */
	public void deleteContact(Contact contact) {
		getHibernateTemplate().delete(contact);
	}
}

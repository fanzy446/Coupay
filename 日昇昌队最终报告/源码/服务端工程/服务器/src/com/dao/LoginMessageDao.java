package com.dao;

import com.domain.LoginMessage;
import com.util.MyDaoSupport;

public class LoginMessageDao extends MyDaoSupport {

	/**
	 * ��ӵ�¼��Ϣ
	 * @param message
	 */
	public void add(LoginMessage message) {
		getHibernateTemplate().save(message);
	}
}

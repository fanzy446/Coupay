package com.dao;

import java.util.List;

import com.domain.Message;
import com.util.MyDaoSupport;

/**
 * ��װ��Messageʵ��CURD������dao�ࡣ
 * @author Lee
 *
 */
public class MessageDao extends MyDaoSupport {

	/**
	 * ����������Ϣ��ʵ��
	 * @param message	���͵���Ϣ
	 * @return			�������ı�ʶ����ֵ
	 */
	public int saveMessage(Message message) {
		return (Integer) getHibernateTemplate().save(message);
	}
	
	/**
	 * ����id��Messageʵ��
	 * @param id		ָ����id
	 * @return			��Ӧ��ʵ��
	 */
	public Message getMessage(int id) {
		return getHibernateTemplate().get(Message.class, id);
	}
	
	public void updateMessage(Message message) {
		getHibernateTemplate().update(message);
	}
	
	public List<Message> getMessages(String clientId) {
		String hql = "from Message where receiver.name = '" + clientId + "' and status = 'unread'";
		@SuppressWarnings("unchecked")
		List<Message> messages = getHibernateTemplate().find(hql);	
		for(Message message : messages) {
			message.setStatus("read");
			updateMessage(message);
		}
		return messages;
	}
}

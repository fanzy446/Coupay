package com.dao;

import java.util.List;

import com.domain.Message;
import com.util.MyDaoSupport;

/**
 * 封装对Message实例CURD操作的dao类。
 * @author Lee
 *
 */
public class MessageDao extends MyDaoSupport {

	/**
	 * 保存推送信息的实例
	 * @param message	推送的信息
	 * @return			保存对象的标识属性值
	 */
	public int saveMessage(Message message) {
		return (Integer) getHibernateTemplate().save(message);
	}
	
	/**
	 * 根据id查Message实例
	 * @param id		指定的id
	 * @return			对应的实例
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

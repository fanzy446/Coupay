package com.subsystem.serverpush;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import com.domain.Register;
import com.util.HqlUtil;

public class MessageDbClass implements IMessageDBClass {
	
	public void recordMessage(String userId,String message){
		com.domain.Message msg = new com.domain.Message();
		msg.setContent(message);
		msg.setStatus("unread");
		msg.setDate(new Date(System.currentTimeMillis()));
		String hql = "from Register where name = ?";
		String[] parameters = {userId};
		List<Object> list = HqlUtil.executeQuery(hql, parameters);
		if(list.size() == 1){
			Register receiver = (Register) list.get(0);
			msg.setReceiver(receiver);
			HqlUtil.save(msg);
		}
	}

	public List<Message> getMessage(String clientId) {
		String hql = "from Message where receiver.name = ? and status= ?";
		String[] parameters = {clientId,"unread"};
		List<Object> items = HqlUtil.executeQuery(hql, parameters);
		List<Message> messages = new LinkedList<Message>();
		for(Object item : items){
			com.domain.Message msg = (com.domain.Message) item;
			Message message = new Message();
			message.setContent(msg.getContent());
			message.setDate(msg.getDate().toString());
			messages.add(message);
			String hql2 = "update Message set status = 'read' where id=" + msg.getId();
			HqlUtil.executeUpdate(hql2, null);
		}
		return messages;
	}

	@Override
	public void removeMessage(String clientId) {

	}
	
	
}

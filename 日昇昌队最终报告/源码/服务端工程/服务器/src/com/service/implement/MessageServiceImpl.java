package com.service.implement;

import com.dao.MessageDao;
import com.domain.Message;
import com.service.interfaces.MessageService;

public class MessageServiceImpl implements MessageService{

	private MessageDao dao;
	public void setDao(MessageDao dao) {
		this.dao = dao;
	}
	@Override
	public void add(Message message) {
		dao.saveMessage(message);		
	}

}

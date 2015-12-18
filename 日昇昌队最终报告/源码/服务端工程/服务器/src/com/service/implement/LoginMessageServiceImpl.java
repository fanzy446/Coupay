package com.service.implement;

import com.dao.LoginMessageDao;
import com.domain.LoginMessage;
import com.service.interfaces.LoginMessageService;

public class LoginMessageServiceImpl implements LoginMessageService {

	private LoginMessageDao dao;
	@Override
	public void add(LoginMessage message) {
		dao.add(message);
	}
	public LoginMessageDao getDao() {
		return dao;
	}
	public void setDao(LoginMessageDao dao) {
		this.dao = dao;
	}

}

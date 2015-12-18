package com.service.implement;

import java.util.List;

import com.dao.ContactDao;
import com.dao.RegisterDao;
import com.domain.Contact;
import com.domain.Register;
import com.service.interfaces.RegisterService;

public class RegisterServiceImpl implements RegisterService {

	private RegisterDao dao;
	private ContactDao dao1;
	
	public void setDao(RegisterDao dao) {
		this.dao = dao;
	}

	public void setDao1(ContactDao dao1) {
		this.dao1 = dao1;
	}
	
	@Override
	public void addRegister(Register register) {
		dao.save(register);
	}

	@Override
	public Register getRegister(String name) {
		return dao.getRegister(name);
	}

	@Override
	public void updateRegister(Register register) {
		dao.update(register);
	}

	@Override
	public boolean exist(String name) {
		if(getRegister(name) != null)
			return true;
		return false;
	}

	@Override
	public boolean valifyLoinPassword(String name, String password) {
		Register register = getRegister(name);
		if(register != null && register.getLoginPassword().equals(password))
			return  true;
		return  false;
	}

	@Override
	public int login(String name, String password) {
		Register register = getRegister(name);
		if(register == null)
			return -1;
		else if(register.getLoginPassword().equals(password))
			return 1;
		return 0;
	}

	@Override
	public Register getRegister(int id) {
		return dao.get(id);
	}

	@Override
	public void addContact(Contact contact) {
		dao1.save(contact);
	}

	@Override
	public void updateContact(Contact contact) {
		dao1.updateContact(contact);
	}

	@Override
	public void deleteContact(Contact contact) {
		dao1.deleteContact(contact);
	}

}

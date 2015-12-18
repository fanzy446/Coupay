package com.service.interfaces;

import java.util.List;

import com.domain.Contact;
import com.domain.Register;

public interface RegisterService {
	
	public void addRegister(Register register);
	
	public Register getRegister(String name);
	
	public Register getRegister(int id);
	
	public void updateRegister(Register register);
	
	public boolean exist(String name);
	
	public boolean valifyLoinPassword(String name, String password);
	
	public int login(String name, String password);
	
	public void addContact(Contact contact);
	
	public void updateContact(Contact contact);
	
	public void deleteContact(Contact contact);
	
}

package com.service.implement;

import com.dao.BankAccountDao;
import com.domain.BankAccount;
import com.service.interfaces.BankAccountService;

public class BankAccountServiceImpl implements BankAccountService {

	private BankAccountDao dao;
	
	public BankAccountDao getDao() {
		return dao;
	}

	public void setDao(BankAccountDao dao) {
		this.dao = dao;
	}

	@Override
	public boolean exist(String cardNumber) {
		return dao.getBankAccount(cardNumber) != null;
	}

	@Override
	public BankAccount getBankAccount(String cardNumber) {
		return dao.getBankAccount(cardNumber);
	}

	@Override
	public double getBalance(String cardNumber, String password) {
		BankAccount account = getBankAccount(cardNumber);
		if(account != null && account.getPassword().equals(password))
			return account.getBalance();
		return -1;
	}

	@Override
	public boolean deposit(String cardNumber, double amount) {
		BankAccount account = getBankAccount(cardNumber);
		if(account != null && amount > 0) {
			account.setBalance(account.getBalance() + amount);
			return true;
		}
		return false;
	}

	@Override
	public boolean withDraw(String cardNumber, double amount) {
		BankAccount account = getBankAccount(cardNumber);
		if(account != null && amount > 0) {
			account.setBalance(account.getBalance() - amount);
			return true;
		}
		return false;
	}

}

package com.service.implement;

import com.dao.AccountDao;
import com.domain.Account;
import com.service.interfaces.AccountService;

public class AccountServiceImpl implements AccountService {

	private AccountDao dao;
	
	public AccountDao getDao() {
		return dao;
	}

	public void setDao(AccountDao dao) {
		this.dao = dao;
	}

	@Override
	public void addAccount(Account account) {
		dao.saveAccount(account);
	}

	@Override
	public Account getAccount(String name) {
		return dao.getAccount(name);
	}

	@Override
	public boolean valifyPayPassword(String name, String password) {
		Account account = getAccount(name);
		if(account != null && account.getPayPassword().equals(password))
			return true;
		return false;
	}

	@Override
	public void updateAccount(Account account) {
		dao.updateAccount(account);
	}

	@Override
	public boolean deposit(String name, double amount) {
		Account account = dao.getAccount(name);
		if(account != null) {
			account.setBalance(account.getBalance()+amount);
		}
		return false;
	}

	@Override
	public boolean withDraw(String name, String password, Double amount) {
		Account account = dao.getAccount(name);
		if(account != null && account.getPayPassword().equals(password)) {
			account.setBalance(account.getBalance() - amount);
			return true;
		}
		return false;
	}

	@Override
	public double getBalance(String name) {
		Account account = dao.getAccount(name);
		return account.getBalance();
	}

}

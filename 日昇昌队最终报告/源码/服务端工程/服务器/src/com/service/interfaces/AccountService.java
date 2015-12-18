package com.service.interfaces;

import com.domain.Account;

public interface AccountService {
	
	public void addAccount(Account account);
	
	public Account getAccount(String name);
	
	public boolean valifyPayPassword(String name, String password);
	
	public void updateAccount(Account account);
	
	public boolean deposit(String name, double amount);
	
	public boolean withDraw(String name, String password, Double amount);
	
	public double getBalance(String name);
}

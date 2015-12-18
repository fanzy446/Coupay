package com.service.interfaces;

import com.domain.BankAccount;

public interface BankAccountService {
	public boolean exist(String cardNumber);
	
	public BankAccount getBankAccount(String number);
	
	public double getBalance(String cardNumber, String password);
	
	public boolean deposit(String cardNumber, double amount);
	
	public boolean withDraw(String cardNumber, double amount);
}

package com.subsystem.bankSystem;

import com.externalSystemInteface.IBankSystem;

public class BankSystem implements IBankSystem {
	IBankSystem bank;
	public BankSystem(){
		bank = new MyBankSystem();
	}
	public void setBank(IBankSystem bank) {
		this.bank = bank;
	}
	@Override
	public boolean checkPassword(String cardNumber, String password) {
		return bank.checkPassword(cardNumber, password);
	}

	@Override
	public double getBalance(String cardNumber, String password) {
		return bank.getBalance(cardNumber, password);
	}

	@Override
	public boolean deposit(String cardNumber, double amount) {
		return bank.deposit(cardNumber, amount);
	}

	@Override
	public boolean withDraw(String cardNumber, String password, double amount) {
		return bank.withDraw(cardNumber, password, amount);
	}

	@Override
	public String getUserName(String cardNumber) {
		return bank.getUserName(cardNumber);
	}

	@Override
	public void doTransfer(String sender, String receiver, double amount) {
		bank.doTransfer(sender, receiver, amount);
		
	}
	@Override
	public boolean checkAccount(String cardNumber) {
		return bank.checkAccount(cardNumber);
	}
	
}

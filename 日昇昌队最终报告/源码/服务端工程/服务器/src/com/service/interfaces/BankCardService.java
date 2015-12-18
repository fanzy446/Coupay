package com.service.interfaces;

import java.util.List;

import com.domain.BankCard;

public interface BankCardService {
	
	public void addBankCard(BankCard card);
	
	public BankCard getBankCard(String cardNumber);
	
	public List<BankCard> getBankCards(String name);
	
	public void updateBankCard(BankCard card);
	
	public void deleteBankCard(BankCard card);
}

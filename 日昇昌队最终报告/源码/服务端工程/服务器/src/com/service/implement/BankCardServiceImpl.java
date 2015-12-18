package com.service.implement;

import java.util.List;

import com.dao.BankCardDao;
import com.domain.BankCard;
import com.service.interfaces.BankCardService;

public class BankCardServiceImpl implements BankCardService {

	private BankCardDao dao;
	
	public BankCardDao getDao() {
		return dao;
	}

	public void setDao(BankCardDao dao) {
		this.dao = dao;
	}

	@Override
	public void addBankCard(BankCard card) {
		dao.saveBankCard(card);
	}

	@Override
	public BankCard getBankCard(String cardNumber) {
		return dao.getBankCard(cardNumber);
	}


	@Override
	public List<BankCard> getBankCards(String name) {
		return dao.getBankCards(name);
	}

	@Override
	public void updateBankCard(BankCard card) {
		dao.updateBankCard(card);
	}

	@Override
	public void deleteBankCard(BankCard card) {
		dao.deleteBankCard(card);
	}
}

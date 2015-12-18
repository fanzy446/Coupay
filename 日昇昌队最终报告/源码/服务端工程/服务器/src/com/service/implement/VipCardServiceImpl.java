package com.service.implement;

import java.util.List;

import com.dao.VipCardDao;
import com.domain.VipCard;
import com.domain.VipCardCollection;
import com.service.interfaces.VipCardService;

public class VipCardServiceImpl implements VipCardService {

	private VipCardDao dao;
	
	
	public void setDao(VipCardDao dao) {
		this.dao = dao;
	}

	@Override
	public void saveVipCard(VipCard card) {
		dao.saveVipCard(card);
	}

	@Override
	public VipCard getVipCard(String sellerName, String cardNumber) {
		return dao.getVipCard(sellerName, cardNumber);
	}

	@Override
	public void deleteVipCard(VipCard card) {
		dao.deleteVipCard(card);
	}

	@Override
	public void addVipCardCollection(VipCardCollection collection) {
		dao.saveVipCardCollection(collection);
	}

	@Override
	public List<VipCard> getVipCard(String customerName) {
		return dao.getVipCardOfCustomer(customerName);
	}

	@Override
	public VipCard getVipCard(String customerName, String sellerName,
			String cardNumber) {
		return dao.getVipCard(customerName, sellerName, cardNumber);
	}

	@Override
	public void updateVipCardCollection(VipCardCollection collection) {
		dao.updateVipCardCollection(collection);
	}

	@Override
	public void deleteVipCardCollection(VipCardCollection collection) {
		dao.deleteVipCardCollection(collection);
	}

}

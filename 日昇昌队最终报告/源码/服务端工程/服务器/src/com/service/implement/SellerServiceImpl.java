package com.service.implement;

import java.util.List;

import com.dao.SellerDao;
import com.domain.Seller;
import com.service.interfaces.SellerService;

public class SellerServiceImpl implements SellerService {

	private SellerDao dao;
	
	public SellerDao getDao() {
		return dao;
	}

	public void setDao(SellerDao dao) {
		this.dao = dao;
	}

	@Override
	public void addSeller(Seller seller) {
		dao.save(seller);
	}

	@Override
	public Seller getSeller(String name) {
		return dao.getSeller(name);
	}

	@Override
	public void updateSeller(Seller seller) {
		dao.update(seller);
	}

	@Override
	public List<Seller> getSeller(double lon, double lat, float accuracy, int page, int pageSize) {
		return dao.getSeller(lon, lat, accuracy, page, pageSize);
	}

}

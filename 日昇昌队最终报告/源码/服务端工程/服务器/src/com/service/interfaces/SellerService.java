package com.service.interfaces;

import java.util.List;

import com.domain.Seller;

public interface SellerService {

	public void addSeller(Seller seller);
	
	public Seller getSeller(String name);
	
	public void updateSeller(Seller seller);
	
	public List<Seller> getSeller(double lon, double lat, float accuracy, int page, int pageSize);
}
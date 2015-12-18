package com.service.interfaces;

import java.util.List;
import com.domain.VipCard;
import com.domain.VipCardCollection;

public interface VipCardService {
	
	public void saveVipCard(VipCard card);
	
	public VipCard getVipCard(String sellerName,String cardNumber);
	
	public void deleteVipCard(VipCard card);
	
	public void addVipCardCollection(VipCardCollection collection);
	
	public List<VipCard> getVipCard(String customerName);
	
	public VipCard getVipCard(String customerName,String sellerName,String cardNumber);
	
	public void updateVipCardCollection(VipCardCollection collection);
	
	public void deleteVipCardCollection(VipCardCollection collection);
	
}

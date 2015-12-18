package com.subsystem.businessSystem;

import java.rmi.RemoteException;
import java.util.List;

import com.externalSystemInteface.IBusinessSystem;
import com.externalSystemInteface.ProductInfo;


public class BusinessSystemProxy implements IBusinessSystem{
	private IBusinessSystem businessSystem;
	
	@Override
	public ProductInfo getProductInfo(String productId) throws RemoteException {
		return businessSystem.getProductInfo(productId);
	}

	@Override
	public List<ProductInfo> getPagingProductInfo(int page, int perpage)
			throws RemoteException {
		return businessSystem.getPagingProductInfo(page, perpage);
	}
	
}

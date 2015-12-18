package com.subsystem.businessSystem;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import com.externalSystemInteface.IBusinessSystem;
import com.externalSystemInteface.ProductInfo;


public class BusinessSystem extends UnicastRemoteObject implements IBusinessSystem {

	private static final long serialVersionUID = 3713650291222313735L;

	protected BusinessSystem() throws RemoteException {
		super();
	}

	@Override
	public ProductInfo getProductInfo(String productId) throws RemoteException {
		
		return null;
	}

	@Override
	public List<ProductInfo> getPagingProductInfo(int page, int perpage)
			throws RemoteException {
		
		return null;
	}

}

package com.externalSystemInteface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IBusinessSystem extends Remote {
	ProductInfo getProductInfo(String productId)throws RemoteException;
	
	List<ProductInfo> getPagingProductInfo(int page,int perpage) throws RemoteException;

}

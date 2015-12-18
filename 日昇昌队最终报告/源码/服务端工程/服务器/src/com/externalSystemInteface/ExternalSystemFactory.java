package com.externalSystemInteface;

import java.rmi.Naming;

public class ExternalSystemFactory {
	private static ExternalSystemFactory instance;
	private ExternalSystemFactory() {
	
	}
	
	public synchronized ExternalSystemFactory getInstance(){
		if(instance == null)
			instance = new ExternalSystemFactory();
		return instance;
	}
	
	public IBankSystem getBankSystem(String name){
		try {
			IBankSystem system = (IBankSystem)Class.forName(name).newInstance();
			return system;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public IServerPushSystem getServerPushSystem(String name){
		try {
			IServerPushSystem system = (IServerPushSystem)Class.forName(name).newInstance();
			return system;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	
	public IBusinessSystem getBusinessSystem(String url){
		try{
			IBusinessSystem system = (IBusinessSystem)Naming.lookup(url);
			return system;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}

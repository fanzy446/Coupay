package com.domain;

import java.io.Serializable;

public class FriendshipId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int registerId1;
	private int registerId2;
	
	
	public int getRegisterId1() {
		return registerId1;
	}

	public void setRegisterId1(int registerId1) {
		this.registerId1 = registerId1;
	}

	public int getRegisterId2() {
		return registerId2;
	}

	public void setRegisterId2(int registerId2) {
		this.registerId2 = registerId2;
	}

	@Override
	public boolean equals(Object obj) {
		if(registerId1 == ((FriendshipId)obj).getRegisterId1() && registerId2 == ((FriendshipId)obj).getRegisterId2())
			return true;
		return false;
	}
	
	@Override
	public int hashCode() {
		return (registerId1 - registerId2)*(registerId1 + registerId2);
	}
	
}

package com.rmi;


public class GoodCatalog implements IGoodCatalog{

	public GoodDescription1 getGoodDescription(String id) {
		GoodDescription1 description = (GoodDescription1) HqlUtil.get(com.rmi.GoodDescription1.class, id);
		return description;
	}
	
	public void addGoodDescription(GoodDescription1 description){
		HqlUtil.save(description);
	}
	
	public void removeGoodDescription(GoodDescription1 description) {
		String hql = "delete from GoodDescription1 where serialNumber = '" 
				+ description.getSerialNumber() + "'";
		HqlUtil.executeUpdate(hql, null);
	}
	
	public void updateGoodDescription(GoodDescription1 description) {
	}
	
	public void addGood(Good1 good) {
		HqlUtil.save(good);
	}
	
	public void removeGood(Good1 good) {
		String hql = "delete from Good1 where goodId = '" + good.getGoodId() + "'";
		HqlUtil.executeUpdate(hql, null);
	}
	
}

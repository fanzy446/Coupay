package com.rmi;

public interface IGoodCatalog {
	
	public GoodDescription1 getGoodDescription(String id);

	public void addGoodDescription(GoodDescription1 description);
	
	public void removeGoodDescription(GoodDescription1 description);
	
	public void updateGoodDescription(GoodDescription1 description);
	
	public void addGood(Good1 good);
	
	public void removeGood(Good1 good);
}
